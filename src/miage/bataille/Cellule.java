/*
 * Cellule.java															06 mars 2020
 * L3 MIAGE 2019/2020, ni copyright, ni copyleft
 */
package miage.bataille;

import java.io.Serializable;

/**
 * Repr�sente une cellule de la mer de taille 1*1
 * Elle est pr�sente dans les ZoneContigue et peut appartenir � un Obstacle
 * Zone de taille 1*1 du plan
 * @author L3 MIAGE Rodez
 */
public class Cellule implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5936707995965872694L;

	/** coordonn�e X de la cellule dans le plan */
    private int coordX;

    /** coordonn�e Y de la cellule dans le plan */
    private int coordY;

    /**
     * <p>Etat de la Cellule</p>
     * 
     * <ul>
     * 	<li>true : Cellule touch�e comportant une partie de b�timent</li>
     * 	<li>false : Cellule pas touch�e, � l'eau</li>
     * </ul>
     */
    private boolean touche;

    /**
     * Initialise une cellule avec ses coordonn�es
     * @param coordX La coordon�e x de la Cellule
     * @param coordY La coordon�e x de la Cellule
     */
    public Cellule(int coordX, int coordY) {
    	this.coordX = coordX;
    	this.coordY = coordY;
    	touche = false;
    }

    /**
     * Change l'�tat de touche � true, qui correspond � un tir touch� sur un b�timent.
     */
    public void aEteTouche() {
    	touche = true;
    }
    
    /**
     * V�rifie si la cellule est touch�e ou non
     * @return true si la cellule a �t� touch�e 
     *         false sinon
     */
    public boolean getTouche() {
    	return touche;
    }
    
    /**
     * Renvoie la coordonn�e X du plan de la cellule
     * @return la coordonn�e X
     */
    public int getCoordX() {
    	return coordX;
    }

    /**
     * Renvoie la coordonn�e Y du plan de la cellule
     * @return la coordonn�e Y
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
        
        // S'ils ont les m�mes coordonn�es
        if ((((Cellule) o).getCoordX() != this.getCoordX()) || ((Cellule) o).getCoordY() != this.getCoordY()) {
        	return false;
        }
    	return true;
    }
}
