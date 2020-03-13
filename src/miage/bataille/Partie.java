/*
 * 
 */

package miage.bataille;

import java.util.ArrayList;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("df73f8f0-8de5-486c-802d-307ecfe68b1b")
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
	 * Nombre de batiments non coul�s
	 */
	private int nbBatiments;
	
    /**
     * Zones contigues repr�sentant la flotte de la partie
     */
    @objid ("22a7e834-949f-4ee9-ba2a-48ddd79efc94")
    private ArrayList<ZoneContigue> compose;

    /**
     * Liste des coups r�alis�s durant la partie
     */
    @objid ("0b60c20d-3aa2-41ea-b085-67752fed6387")
    public ArrayList<Cellule> coups;
    
    
    /**
     * Initialise une nouvelle partie par d�faut.
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
     * Ajoute la zone contigue � la partie si elle n'empiete sur aucune autre zone
     * @param aAjouter
     * @throws IllegalArgumentException La zone contigue empietre sur une cellule d�ja utilis�
     */
    public void ajouterZoneContigue(ZoneContigue aAjouter) throws IllegalArgumentException {
    	boolean valide = true;
    	/* Parcours des cellules de la zone en param�tre */
    	for (int i = 0 ; i < aAjouter.getPossede().size() && valide; i++) {
    		/* Verifie si la cellule est disponible */
    		for (int j = 0 ; j < compose.size() && valide; j++) {
    			if (!compose.get(j).existe(aAjouter.getPossede().get(i).getCoordX(),aAjouter.getPossede().get(i).getCoordY())){
    	    		throw new IllegalArgumentException("La cellule " + (i+1) + ';' +(j+1) + " empiete une d�ja existante");
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
     * @param listeBatiments -> Liste des b�timents � placer
     * 							dans la partie.
     */
    public Partie(ArrayList<ZoneContigue> listeBatiments) {
    	compose = listeBatiments;
    	coups = new ArrayList<Cellule>();
    }
    
    /**
     * Tire sur une cellule aux coordonn�es x et y
     * V�rifie si une zone contigue contenant des bateaux a �t� touch�
     * Sauvegarde le coup tir�
     * @param x -> Coordonn�e en abcisses de la cellule tir�e
     * @param y -> Coordonn�e en ordonn�e de la cellule tir�e
     * @return le r�sultat du tir : Coup � l'eau / touch� ou coul�
     */
    @objid ("56659bde-06ae-44a1-abe1-82902d3b39cc")
    public String tirer(int x, int y) { 
    	String resultat; // R�sultat du tir si un batiment est touch� (et coul�) ou non
    	ZoneContigue zoneVisee;
        Cellule celluleTiree; // Cellule tir�e
        
        System.out.println(x  + "" + y);

	    // V�rifie si la cellule choisie � touch� un batiment ou non
	  	if ((zoneVisee = rechercheZone(x, y)) == null) {	
	  		// B�timent non trouv�
	  		celluleTiree = new Cellule(x, y);
	  		resultat = "plouf";
		} else {
			// B�timent trouv�
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
     * Recherche � quelle zone appartient une cellule. Renvoie null si la cellule sp�cifi�e
     * par les coordonn�es n'appartient � aucune zone
     * @return ZoneContigue - renvois la zone contigu� o� se trouve la cellule
     *                      - si aucune zone existe, renvoie null
     */
    @objid ("2ef7390a-0dbf-41c9-b8d0-5f83c1742a80")
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
     * Ajoute la cellule tir�e � la liste des coups
     * @param celulle tir�e
     */
    @objid ("ce21b39e-cd8e-47d8-89ae-fe405c9c39ae")
    private void enregistrerCoup(Cellule celluleTiree) {
    	coups.add(celluleTiree);
    }
  
    /**
     * @return La liste de toutes les cellules tir�es
     */
    public ArrayList<Cellule> getCellulesTirees() {
        return coups;
    }

	public ArrayList<ZoneContigue> getCompose() {
		return compose;
	}
	
	/**
	 * @return le nombre de b�timents encore en jeu
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
