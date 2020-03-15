/*
 * 
 */

package miage.bataille;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * @author 	Damien Avetta,
 * 			alexis vivier
 *
 */
public class Partie implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5137745755361982976L;

	/**
	 * Configuration de la partie courante
	 */
	private Configuration config;

	/**
	 * Nombre de batiments non coulï¿½s
	 */
	private int nbBatiments;

	/**
	 * Zones contigues reprï¿½sentant la flotte de la partie
	 */
	private ArrayList<ZoneContigue> compose;

	/**
	 * Liste des coups rï¿½alisï¿½s durant la partie
	 */
	public ArrayList<Cellule> coups;


	/**
	 * Initialise une nouvelle partie par dï¿½faut.
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
		placementFlotteAuto(config.getFlotte());
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
	 * Permet de placer un batiment sur la mer de maniere pseudo-aleatoire
	 * et minimiser les chances que les batiments soient cote a cote avec
	 * un autre batiment
	 * @param aPLacer Batiment a placer sur la mer
	 */
	private boolean placementBatimentAuto(Batiment aPLacer) {
		
		final double CHANCE_RELANCE_ADJACENT = 0.2;
		
		boolean passer = false;
		boolean relanceAdjacent = (Math.random() < CHANCE_RELANCE_ADJACENT);
		boolean vertical = (Math.random() < 0.5);

		int xAPlacer = (int)(Math.random() * (config.getLongueurCarte() - (!vertical?aPLacer.getTailleLgr()-1:0)));
		int yAPLacer = (int)(Math.random() * (config.getHauteurCarte() - (vertical?aPLacer.getTailleLgr()-1:0)));

		int xAPlacerMax = !vertical?( xAPlacer + (aPLacer.getTailleLgr() - 1 )):xAPlacer;
		int yAPlacerMax = vertical?( yAPLacer + (aPLacer.getTailleLgr() - 1 )):yAPLacer;

		//Tests si sa ne sort pas de la mer
		if( xAPlacerMax > config.getLongueurCarte() - 1
				|| yAPlacerMax > config.getHauteurCarte() - 1) {
			//Hors de la mer: relance du placement
			passer = placementBatimentAuto(aPLacer);
		}   

		// Test si la zone n'empiete pas sur une autre
		for(int i = 0; i < aPLacer.getTailleLgr() && !passer; i++ ) {
			if (empietreUneCellule(!vertical?(xAPlacer+i):xAPlacer, 
					vertical?(yAPLacer+i):yAPLacer)) {
				passer = placementBatimentAuto(aPLacer);
			}
		}
		
		//Test que le batiment ne soit pas a cote d'un deja en jeu selon l'etat de relanceAdgacent
    	for(int i = 0; i < aPLacer.getTailleLgr() && !passer && relanceAdjacent; i++ ) {
    		if( empietreUneCellule(xAPlacer + (!vertical?i:0) + 1, yAPLacer + (vertical?i:0)) 
    				|| empietreUneCellule(xAPlacer + (!vertical?i:0) - 1, yAPLacer + (vertical?i:0))
    				|| empietreUneCellule(xAPlacer + (!vertical?i:0), yAPLacer + (vertical?i:0) + 1)
    				|| empietreUneCellule(xAPlacer + (!vertical?i:0), yAPLacer + (vertical?i:0) - 1)){
    			passer = placementBatimentAuto(aPLacer);
    		}
    	}
		
		//Ajout de la zone correctement dï¿½roulï¿½
		ajouterZoneContigue( new ZoneContigue( aPLacer, xAPlacer, yAPLacer, 
				//Si horizontal alors ajouter la taille du batiment au x
				xAPlacerMax, 	
				//Si vertical alors ajoute la taille du batiment au y
				yAPlacerMax));
		return true;
	}

	/**
	 * Ajoute la zone contigue ï¿½ la partie si elle n'empiete sur aucune autre zone
	 * @param aAjouter
	 * @throws IllegalArgumentException La zone contigue empietre sur une cellule dï¿½ja utilisï¿½
	 */
	public void ajouterZoneContigue(ZoneContigue aAjouter) throws IllegalArgumentException {
		boolean valide = true;
		/* Parcours des cellules de la zone en paramï¿½tre */
		for (int i = 0 ; i < aAjouter.getPossede().size() && valide; i++) {
			/* Verifie si la cellule est disponible */
			if (empietreUneCellule(aAjouter.getPossede().get(i).getCoordX(), aAjouter.getPossede().get(i).getCoordY())){
				throw new IllegalArgumentException("La cellule " + (i+1) + " empiete une deja existante");
			}
		}
		compose.add(aAjouter);
		if (aAjouter.estTouchable()) {
			nbBatiments++;
		}
	}

	/**
	 * Permet de savoir si une futur cellule de coordonée
	 * definit en paramétre, empietre une cellule déja existante 
	 * dans la mer
	 * @param x coordonee de la horizontal
	 * @param y coordonee de la vertical
	 * @return	true si coordonee n'empietre pas
	 * 			false si coordonnee empietre
	 */
	private boolean empietreUneCellule(int x, int y) {
		boolean empietre = false;

		for (int j = 0 ; j < compose.size() && empietre; j++) {
			if (!compose.get(j).existe(x,y)){
				empietre = true;
			}
		}
		return empietre;
	}

	/**
	 * Initialise une nouvelle partie
	 * @param listeBatiments -> Liste des bï¿½timents ï¿½ placer
	 * 							dans la partie.
	 */
	public Partie(ArrayList<ZoneContigue> listeBatiments) {
		compose = listeBatiments;
		coups = new ArrayList<Cellule>();
	}

	/**
	 * Tire sur une cellule aux coordonnï¿½es x et y
	 * Vï¿½rifie si une zone contigue contenant des bateaux a ï¿½tï¿½ touchï¿½
	 * Sauvegarde le coup tirï¿½
	 * @param x -> Coordonnï¿½e en abcisses de la cellule tirï¿½e
	 * @param y -> Coordonnï¿½e en ordonnï¿½e de la cellule tirï¿½e
	 * @return le rï¿½sultat du tir : Coup ï¿½ l'eau / touchï¿½ ou coulï¿½
	 */
	public String tirer(int x, int y) throws IllegalArgumentException { 
		String resultat; // Rï¿½sultat du tir si un batiment est touchï¿½ (et coulï¿½) ou non
		ZoneContigue zoneVisee;
		Cellule celluleTiree; // Cellule tirï¿½e

		if(x < 0 || y < 0 ) {
			throw new IllegalArgumentException("Coordonée négatixe x:" + x + " y:" + y);
		}

		// Vï¿½rifie si la cellule choisie ï¿½ touchï¿½ un batiment ou non
		if ((zoneVisee = rechercheZone(x, y)) == null) {	
			// Bï¿½timent non trouvï¿½
			celluleTiree = new Cellule(x, y);
			resultat = "plouf";
			enregistrerCoup(celluleTiree);
		} else {
			// Bï¿½timent trouvï¿½
			celluleTiree = zoneVisee.getCellule(x, y);
			celluleTiree.aEteTouche();	 
			// Enregistrement du coup dans la liste des coups
			enregistrerCoup(celluleTiree);
			if (zoneVisee.estCoule()) {
				nbBatiments--;
				resultat = "coule " + zoneVisee.getObstacle().getNom();
			} else {
				resultat = celluleTiree.getTouche() ? "touche" : "plouf";
			}
		}
		return resultat;  //TODO: renvoyer un autre type que string.
	}


	/**
	 * Recherche ï¿½ quelle zone appartient une cellule. Renvoie null si la cellule spï¿½cifiï¿½e
	 * par les coordonnï¿½es n'appartient ï¿½ aucune zone
	 * @return ZoneContigue - renvois la zone contiguï¿½ oï¿½ se trouve la cellule
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
	 * Ajoute la cellule tirï¿½e ï¿½ la liste des coups
	 * @param celulle tirï¿½e
	 */
	private void enregistrerCoup(Cellule celluleTiree) {
		coups.add(celluleTiree);
	}

	/**
	 * @return La liste de toutes les cellules tirï¿½es
	 */
	public ArrayList<Cellule> getCellulesTirees() {
		return coups;
	}

	public ArrayList<ZoneContigue> getCompose() {
		return compose;
	}

	/**
	 * @return le nombre de bï¿½timents encore en jeu
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
