/*
 * 
 */

package miage.bataille;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Centralise toutes les informations relatives à une partie
 * @author L3 MIAGE Rodez
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
	 * Nombre de bâtiments non coulés
	 */
	private int nbBatiments;

	/**
	 * Zones contigües représentant la flotte de la partie
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
	public boolean placementFlotteAuto(ArrayList<Batiment> flotte) {
		for(Batiment bat:flotte) {
			try {
				placementBatimentAuto(bat);
			} catch (StackOverflowError e) {
				System.out.println("Placement automatique des bateaux impossible");
				return false;
			}
		}
		return true;
	}

	/**
	 * Permet de placer un batiment sur la mer de manière pseudo-aléatoire
	 * et minimiser les chances que les bâtiments soient côte a côte avec
	 * un autre bâtiment
	 * @param aPLacer Batiment à placer sur la mer
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

		// Teste si ca ne sort pas de la mer
		if( xAPlacerMax > config.getLongueurCarte() - 1
				|| yAPlacerMax > config.getHauteurCarte() - 1) {
			// Hors de la mer: relance du placement
			passer = placementBatimentAuto(aPLacer);
		}   

		// Test si la zone n'empiete pas sur une autre
		for(int i = 0; i < aPLacer.getTailleLgr() && !passer; i++ ) {
			if (empieteUneCellule(!vertical?(xAPlacer+i):xAPlacer, 
					vertical?(yAPLacer+i):yAPLacer)) {
				passer = placementBatimentAuto(aPLacer);
			}
		}
		
		// Teste que le batiment ne soit pas à côté d'un déjà en jeu selon l'état de relanceAdgacent
    	for(int i = 0; i < aPLacer.getTailleLgr() && !passer && relanceAdjacent; i++ ) {
    		if( empieteUneCellule(xAPlacer + (!vertical?i:0) + 1, yAPLacer + (vertical?i:0)) 
    				|| empieteUneCellule(xAPlacer + (!vertical?i:0) - 1, yAPLacer + (vertical?i:0))
    				|| empieteUneCellule(xAPlacer + (!vertical?i:0), yAPLacer + (vertical?i:0) + 1)
    				|| empieteUneCellule(xAPlacer + (!vertical?i:0), yAPLacer + (vertical?i:0) - 1)){
    			passer = placementBatimentAuto(aPLacer);
    		}
    	}
    	
		if( !passer ) {
			// L'ajout de la zone s'est correctement déroulé
			ajouterZoneContigue( new ZoneContigue( aPLacer, xAPlacer, yAPLacer, 
					// Si horizontal alors ajouter la taille du batiment au x
					xAPlacerMax, 	
					// Si vertical alors ajouter la taille du batiment au y
					yAPlacerMax));
		}
		return true;
	}

	/**
	 * Ajoute la zone contigue à la partie si elle n'empiete sur aucune autre zone
	 * @param aAjouter
	 * @throws IllegalArgumentException La zone contigue empiete sur une cellule déjà utilisée
	 */
	public void ajouterZoneContigue(ZoneContigue aAjouter) throws IllegalArgumentException {
		boolean valide = true;
		/* Parcours des cellules de la zone en paramï¿½tre */
		for (int i = 0 ; i < aAjouter.getPossede().size() && valide; i++) {
			/* Verifie si la cellule est disponible */
			if (empieteUneCellule(aAjouter.getPossede().get(i).getCoordX(), aAjouter.getPossede().get(i).getCoordY())){
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
	 * definit en paramétre, empiete une cellule déja existante 
	 * dans la mer
	 * @param x coordonee de la horizontal
	 * @param y coordonee de la vertical
	 * @return	true si coordonee n'empiete pas
	 * 			false si coordonnee empiete
	 */
	private boolean empieteUneCellule(int x, int y) {
		boolean empiete = false;

		for (int j = 0 ; j < compose.size() && !empiete; j++) {
			if (compose.get(j).existe(x,y)){
				empiete = true;
			}
		}
		return empiete;
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
	 * Vérifie si une zone contigüe contenant des bateaux a été touchée
	 * Sauvegarde le coup tiré
	 * @param x -> Coordonnée en abcisses de la cellule tirée
	 * @param y -> Coordonnée en ordonnées de la cellule tirée
	 * @return le résultat du tir : Coup à l'eau / touché ou coulé
	 */
	public String tirer(int x, int y) throws IllegalArgumentException { 
		String resultat; // Résultat du tir si un batiment est touché (et coulé) ou non
		ZoneContigue zoneVisee;
		Cellule celluleTiree; // Cellule tirée

		if(x < 0 || y < 0 ) {
			throw new IllegalArgumentException("Coordonée négatixe x:" + x + " y:" + y);
		}

		// Vérifie si la cellule choisie a touché un batiment ou non
		if ((zoneVisee = rechercheZone(x, y)) == null) {	
			// Bï¿½timent non trouvï¿½
			celluleTiree = new Cellule(x, y);
			resultat = "plouf";
			enregistrerCoup(celluleTiree);
		} else {
			// Bâtiment trouvé
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
		return resultat;
	}


	/**
	 * Recherche à quelle zone appartient une cellule. Renvoie null si la cellule spécifiée
	 * par les coordonnées n'appartient à aucune zone
	 * @return ZoneContigue - renvoie la zone contigüe où se trouve la cellule
	 *                      - si aucune zone n'existe, renvoie null
	 */
	private ZoneContigue rechercheZone(int x, int y) {

		ZoneContigue trouve = null; // Cellule trouve dans une zone contigüe

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
	 * Enregistre le coup pour la postérité
	 * Ajoute la cellule tirée à la liste des coups
	 * @param cellule tirée
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