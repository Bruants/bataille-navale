package miage.bataille;

import java.util.ArrayList;
/**
 * Represente une Zone Contigue
 * Une Zone Contigue est definie par une Cellule de d�but et une Cellule de fin
 * Les cellules pr�sentes entre les cellules de d�but/fin font parties de la Zone Contigue
 * Une zone contigue peut �tre coul�e si toutes les cellules pr�sentes sont touch�es
 * Une cellule peut avoir deux orientations
 *  - Horizontale : 0 degr� (de gauche � droite)
 *  - Verticale : 90 degr� (de base en haut)
 * Elle est definie par un type (Obstacle)
 * @author kevin.sannac
 * @version 0.1.0
 */
public class ZoneContigue {
	
    /**
     * D�finit le type de l'Obstacle (Batiment,Terre,...)
     */
    private Obstacle obstacle;

    /**
     * Toutes les cellules de la Zone Contigue
     */
    private ArrayList<Cellule> possede = new ArrayList<Cellule> ();

    public static ArrayList<Cellule> cellulesAAjouter(Obstacle type, int xDeb, int yDeb, int xFin, int yFin) {
    	ArrayList<Cellule> aAjouter = new ArrayList<Cellule> ();
    	int parcoursEnLargeur = xFin - xDeb + 1; // D�termine le nombre de case � parcourir en largeur, d�pend de l'orientation
    	int parcoursEnHauteur = yFin - yDeb + 1; // D�termine le nombre de case � parcourir en longueur, d�pend de l'orientation

    	// Cr�ation de toutes les cellules
    	for(int i = 0; i < parcoursEnLargeur ; i++) {
        	for(int j = 0; j < parcoursEnHauteur ; j++) {
        		aAjouter.add(new Cellule(xDeb + i, yDeb + j));
        	}
    	}
    	return aAjouter;
    }
    
    /**
     * Cr�e une zone contigue en pr�cisant son type d'Obstacle et avec une cellule de d�part et une cellule de fin
     * @param partie La partie mere de la zone courante
     * @param type Le type d'Obstacle de la Zone Contigue
     * @param xDeb La coordonn�e x du d�but de la zone 
     * @param yDeb La coordonn�e y de la fin de la zone
     * @param xFin La coordonn�e x du debut de la zone
     * @param yFin La coordonn�e y de la fin de la zone
     * @throws IllegalArgumentException En cas de coordonnees invalides
     */
    public ZoneContigue(Obstacle type, int xDeb, int yDeb, int xFin, int yFin) throws IllegalArgumentException {
    	obstacle = type;
    	if(!coordonneesValides(xDeb, yDeb, xFin, yFin)) {
    		throw new IllegalArgumentException("Coordonnees invalides");
    	}
    	ArrayList<Cellule> aAjouter = cellulesAAjouter(type, xDeb, yDeb, xFin, yFin); // Toutes les cellules que l'on veut ajouer 

    	possede = aAjouter;	
    }
    
    /**
     * V�rifie que les coordonnees sont valides et sont en ad�quation avec les dimensions de l'obstacle
     * Calcule l'orientation de la zone
     * @param type Le type d'Obstacle de la Zone Contigue
     * @param xDeb La coordonn�e x du d�but de la zone 
     * @param yDeb La coordonn�e y de la fin de la zone
     * @param xFin La coordonn�e x du debut de la zone
     * @param yFin La coordonn�e y de la fin de la zone
     * @return True si les coordonnees sont valides sont bonnes, sinosn false
     */
    private boolean coordonneesValides(int xDeb, int yDeb, int xFin, int yFin) {
    	/* On v�rifie les coordonees */
    	// Coordonnees hors de la carte
    	if (xDeb < 0 || yDeb < 0 || xFin < 0 || yFin < 0) {
    		return false;
    	}
//		TODO
//    	if (xDeb > TAILLE_CARTE || yDeb > TAILLE_CARTE || xFin > TAILLE_CARTE || yFin > TAILLE_CARTE) {
//    		return false;
//    	}
    	// Coordonnees non align�es / ne correspondent pas aux caract�ristiques de l'obstacle
    	if (!(xDeb + obstacle.tailleLgr - 1 == xFin && yDeb + obstacle.tailleHaut - 1 == yFin) // obstacle de gauche � droite
    			&& !(xDeb + obstacle.tailleHaut - 1 == xFin && yDeb + obstacle.tailleLgr - 1 == yFin)) { // obstacle de haut en bas
    		return false;    	
    	}
    	
    	return true;
    }

    /** 
     * Permet de recuperer la Cellule qui correspond aux coordonn�es x y correspondants � la Zone Contigue this
     * @param coordX La coordonn�e x de la Cellule tir�e
     * @param coordY La coordonn�e x de la Cellule tir�e
     * @return la cellule qui correspond aux coordonn�es x y, si elle n'existe pas null
     */
    public Cellule getCellule(int coordX, int coordY) {
    	int placement;
    	
    	/* Recherche la cellule en question parmis celles de la zone */
    	for (placement = 0 ; placement < possede.size() 
    			             && (possede.get(placement).getCoordX() != coordX
    			                 || possede.get(placement).getCoordY() != coordY); placement++);
    	
    	/* 
    	 * V�rifie que la cellule a bien �t� trouv�e. si getCoordX != x ou getCoordY != y,
    	 * la cellule n'existe pas.
    	 */
        return possede.size() > placement ? possede.get(placement) : null;
    }

    /**
     * D�termine si la cellule existe dans la zone contigue
     * @param x La coordonn�e x de la Cellule
     * @param y La coordonn�e x de la Cellule     
     */
    public boolean existe(int coordX, int coordY) {
    	return possede.contains(new Cellule(coordX, coordY));
    }
    
    /**
     * Defini si la zone contigue est coul�e, elle est coul�e si toutes les cellules
     * qui la compose sont touchees.
     * @return true si toutes les cellules sont coul�es
     *         false sinon
     */
    public boolean estCoule() {
    	boolean coule = true;
    	/* On v�rifie que toutes les cellules sont touch�es */
    	for (int i = 0 ; i < possede.size() && coule ; i++) {
    		coule = possede.get(i).getTouche();
    	}
    	return coule;
    }
    
    /**
     * Renvoie true si la zone est touchable, sinon faux
     * @return
     */
    public boolean estTouchable() {
    	return obstacle.touchable;
    }

	public ArrayList<Cellule> getPossede() {
		return possede;
	}
}
