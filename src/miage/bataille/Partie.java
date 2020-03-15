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
	 * Nombre de batiments non coul�s
	 */
	private int nbBatiments;

	/**
	 * Zones contigues repr�sentant la flotte de la partie
	 */
	private ArrayList<ZoneContigue> compose;

	/**
	 * Liste des coups r�alis�s durant la partie
	 */
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
		
		//Ajout de la zone correctement d�roul�
		ajouterZoneContigue( new ZoneContigue( aPLacer, xAPlacer, yAPLacer, 
				//Si horizontal alors ajouter la taille du batiment au x
				xAPlacerMax, 	
				//Si vertical alors ajoute la taille du batiment au y
				yAPlacerMax));
		return true;
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
	 * Permet de savoir si une futur cellule de coordon�e
	 * definit en param�tre, empietre une cellule d�ja existante 
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
	public String tirer(int x, int y) throws IllegalArgumentException { 
		String resultat; // R�sultat du tir si un batiment est touch� (et coul�) ou non
		ZoneContigue zoneVisee;
		Cellule celluleTiree; // Cellule tir�e

		if(x < 0 || y < 0 ) {
			throw new IllegalArgumentException("Coordon�e n�gatixe x:" + x + " y:" + y);
		}

		// V�rifie si la cellule choisie � touch� un batiment ou non
		if ((zoneVisee = rechercheZone(x, y)) == null) {	
			// B�timent non trouv�
			celluleTiree = new Cellule(x, y);
			resultat = "plouf";
			enregistrerCoup(celluleTiree);
		} else {
			// B�timent trouv�
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
	 * Recherche � quelle zone appartient une cellule. Renvoie null si la cellule sp�cifi�e
	 * par les coordonn�es n'appartient � aucune zone
	 * @return ZoneContigue - renvois la zone contigu� o� se trouve la cellule
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
	 * Ajoute la cellule tir�e � la liste des coups
	 * @param celulle tir�e
	 */
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
