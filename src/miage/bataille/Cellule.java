/*
 * Cellule.java															06 mars 2020
 * L3 MIAGE 2019/2020, ni copyright, ni copyleft
 */
package miage.bataille;

import java.io.Serializable;

/**
 * Représente une cellule de la mer de taille 1*1
 * Elle est présente dans les ZoneContigue et peut appartenir à un Obstacle
 * Zone de taille 1*1 du plan
 * @author L3 MIAGE Rodez
 */
public class Cellule implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5936707995965872694L;

	/** coordonnée X de la cellule dans le plan */
    private int coordX;

    /** coordonnée Y de la cellule dans le plan */
    private int coordY;

    /**
     * <p>Etat de la Cellule</p>
     * 
     * <ul>
     * 	<li>true : Cellule touchée comportant une partie de bâtiment</li>
     * 	<li>false : Cellule pas touchée, à l'eau</li>
     * </ul>
     */
    private boolean touche;

    /**
     * Initialise une cellule avec ses coordonnées
     * @param coordX La coordonée x de la Cellule
     * @param coordY La coordonée x de la Cellule
     */
    public Cellule(int coordX, int coordY) {
    	this.coordX = coordX;
    	this.coordY = coordY;
    	touche = false;
    }

    /**
     * Change l'état de touche à true, qui correspond à un tir touché sur un bâtiment.
     */
    public void aEteTouche() {
    	touche = true;
    }
    
    /**
     * Vérifie si la cellule est touchée ou non
     * @return true si la cellule a été touchée 
     *         false sinon
     */
    public boolean getTouche() {
    	return touche;
    }
    
    /**
     * Renvoie la coordonnée X du plan de la cellule
     * @return la coordonnée X
     */
    public int getCoordX() {
    	return coordX;
    }

    /**
     * Renvoie la coordonnée Y du plan de la cellule
     * @return la coordonnée Y
     */
    public int getCoordY() {
    	return coordY;
    }
    
    /**
     * 
     */
    @Override
    public String toString() {
    	return "[" + coordX + ";" + coordY + "]" + (touche ? " touche" : " non touche");
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) { 
            return true; 
        } 
  
        if (!(o instanceof Cellule)) { 
            return false; 
        }
        
        // S'ils ont les mêmes coordonnées
        if ((((Cellule) o).getCoordX() != this.getCoordX()) || ((Cellule) o).getCoordY() != this.getCoordY()) {
        	return false;
        }
    	return true;
    }
}
