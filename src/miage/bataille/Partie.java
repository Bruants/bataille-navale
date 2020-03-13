/*
 * 
 */

package miage.bataille;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author Damien Avetta
 *
 */
public class Partie {
	
	/**
	 * Configuration de la partie courante
	 */
	private Configuration config;
	
	/**
	 * Nombre de batiments non coulés
	 */
	private int nbBatiments;
	
    /**
     * Zones contigues représentant la flotte de la partie
     */
    private ArrayList<ZoneContigue> compose;

    /**
     * Liste des coups réalisés durant la partie
     */
    public ArrayList<Cellule> coups;
    
    
    /**
     * Initialise une nouvelle partie par défaut.
     */
    public Partie() {	
    	this(new Configuration());
    }
    
    /**
     * Initialise la partie avec une configuration
     * @param conf
     */
    public Partie(Configuration confPartie) {
    	config = confPartie;

    	compose = new ArrayList<ZoneContigue>();
    	coups = new ArrayList<Cellule>();
    	nbBatiments = compose.size();
    }
    
    /**
     * Permet de mettre en place la flotte d'un joueur
     */
    public void placementFlotteAuto(ArrayList<Batiment> flotte) {
    	for(Batiment bat:flotte) {
    		placementBatimentAuto(bat);
    	}
    }
    
    /**
     * Permet de placer un batiment sur la mer de maniére pseudo-aléatoire
     * et minimiser les chances que les batiments doit cote à cote avec
     * un autre batiment
     * @param aPLacer Batiment a placer sur la mer
     */
    private boolean placementBatimentAuto(Batiment aPLacer) {
    	
    	int xAPlacer = config.getLongueurCarte() + (int)(Math.random() * (config.getLongueurCarte()));
    	int yAPLacer = config.getHauteurCarte() + (int)(Math.random() * (config.getHauteurCarte()));

    	boolean valide = false;
    	boolean vertical = (Math.random() < 0.5);
    	
    	ZoneContigue zoneAAjouter;
    	
    	//Tests si sa ne sort pas de la mer
    	if( (!vertical && xAPlacer + aPLacer.tailleLgr > config.getLongueurCarte())
    			|| (vertical && yAPLacer + aPLacer.getTailleHaut() > config.getHauteurCarte())) {
    		//Hors de la mer: relance du placement
    		return placementBatimentAuto(aPLacer);
    	}
    	

    	// Test si la zone n'en empietre pas une autre
    	try {
	    	//Ajoute le batiment comme zone dans les zones de la mer
	    	zoneAAjouter =  new ZoneContigue( aPLacer, xAPlacer, yAPLacer, 
	    			//Si horizontal alors ajouter la taille du batiment au x
	    			!vertical?( xAPlacer + aPLacer.getTailleLgr() ):xAPlacer, 	
	    			//Si vertical alors ajoute la taille du batiment au y
	    			vertical?( yAPLacer+ aPLacer.getTailleHaut() ):yAPLacer );
    	} catch(IllegalArgumentException e) {
    		return placementBatimentAuto(aPLacer);
    	}
    	
    	//TODO: Test que le batiment ne soit pas à coté d'un déja en jeu
//    	for(int indiceZone = 0; indiceZone < compose.size() && valide; indiceZone++) {
//    		if(compose.get(indiceZone).existe(xAPlacer, yAPLacer) ){
//    			return placementBatimentAuto(aPLacer);
//    		}
//    	}
    	
    	//Ajout de la zone correctement déroulé
    	ajouterZoneContigue(zoneAAjouter);
    	return true;
    }
    
    /**
     * Ajoute la zone contigue à la partie si elle n'empiete sur aucune autre zone
     * @param aAjouter
     * @throws IllegalArgumentException La zone contigue empietre sur une cellule déja utilisé
     */
    public void ajouterZoneContigue(ZoneContigue aAjouter) throws IllegalArgumentException {
    	boolean valide = true;
    	/* Parcours des cellules de la zone en paramétre */
    	for (int i = 0 ; i < aAjouter.getPossede().size() && valide; i++) {
    		/* Verifie si la cellule est disponible */
    		for (int j = 0 ; j < compose.size() && valide; j++) {
    			if (!compose.get(j).existe(aAjouter.getPossede().get(i).getCoordX(),aAjouter.getPossede().get(i).getCoordY())){
    	    		throw new IllegalArgumentException("La cellule " + (i+1) + ';' +(j+1) + " empiete une déja existante");
    			}
    		
    		}
    	}
    	compose.add(aAjouter);
    	if (aAjouter.estTouchable()) {
    		nbBatiments++;
    	}
    }
    
    /**
     * Initialise une nouvelle partie
     * @param listeBatiments -> Liste des bâtiments à placer
     * 							dans la partie.
     */
    public Partie(ArrayList<ZoneContigue> listeBatiments) {
    	compose = listeBatiments;
    	coups = new ArrayList<Cellule>();
    }
    
    /**
     * Tire sur une cellule aux coordonnées x et y
     * Vérifie si une zone contigue contenant des bateaux a été touché
     * Sauvegarde le coup tiré
     * @param x -> Coordonnée en abcisses de la cellule tirée
     * @param y -> Coordonnée en ordonnée de la cellule tirée
     * @return le résultat du tir : Coup à l'eau / touché ou coulé
     */
    public String tirer(int x, int y) { 
    	String resultat; // Résultat du tir si un batiment est touché (et coulé) ou non
    	ZoneContigue zoneVisee;
        Cellule celluleTiree; // Cellule tirée
        
	    // Vérifie si la cellule choisie à touché un batiment ou non
	  	if ((zoneVisee = rechercheZone(x, y)) == null) {	
	  		// Bâtiment non trouvé
	  		celluleTiree = new Cellule(x, y);
	  		resultat = "plouf";
		} else {
			// Bâtiment trouvé
			celluleTiree = zoneVisee.getCellule(x, y);
		    celluleTiree.aEteTouche();	 
		 // Enregistrement du coup dans la liste des coups
		    enregistrerCoup(celluleTiree);
		    if (zoneVisee.estCoule()) {
		    	nbBatiments--;
		    	resultat = "coule";
		    } else {
		    	resultat = celluleTiree.getTouche() ? "touche" : "plouf";
		    }
	    }
	    return resultat;  //TODO: renvoyer un autre type que string.
    }
    

    /**
     * Recherche à quelle zone appartient une cellule. Renvoie null si la cellule spécifiée
     * par les coordonnées n'appartient à aucune zone
     * @return ZoneContigue - renvois la zone contiguë où se trouve la cellule
     *                      - si aucune zone existe, renvoie null
     */
    private ZoneContigue rechercheZone(int x, int y) {
        
        ZoneContigue trouve = null; // Cellule trouve dans une zone contigue
        
        // Parcours de toutes les zones de la mer
        for (int indiceRecherche = 0; indiceRecherche < compose.size() && trouve == null ; 
             indiceRecherche++) {
        	
            if (compose.get(indiceRecherche).existe(x, y)) {
            	trouve = compose.get(indiceRecherche);
            }
        }

        return trouve;
    }

    /**
     * Enregistre le coup pour la posterite
     * Ajoute la cellule tirée à la liste des coups
     * @param celulle tirée
     */
    private void enregistrerCoup(Cellule celluleTiree) {
    	coups.add(celluleTiree);
    }
  
    /**
     * @return La liste de toutes les cellules tirées
     */
    public ArrayList<Cellule> getCellulesTirees() {
        return coups;
    }

	public ArrayList<ZoneContigue> getCompose() {
		return compose;
	}
	
	/**
	 * @return le nombre de bâtiments encore en jeu
	 */
	public int getNbBatiments() {
		return nbBatiments;
	}
	
	/**
	 * @return la configuration courante de la partie
	 * @return
	 */
	public Configuration getConfiguration() {
		return config;
	}
}
