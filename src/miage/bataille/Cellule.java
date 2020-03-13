/*
 * Cellule.java															06 mars 2020
 * L3 MIAGE 2019/2020, ni copyright, ni copyleft
 */
package miage.bataille;


/**
 * Represente une cellule de la mer de taille 1*1
 * Elle est presente dans les Zone Contigue et peut appartenir à un Obstacle
 * Zone de taille 1*1 du plan
 * @author Damien Avetta-Raymond
 */
public class Cellule {
	
	/** coordonnée X de la cellule dans le plan */
    private int coordX;

    /** coordonnée Y de la cellule dans le plan */
    private int coordY;

    /**
     * <p>Etat de la Cellule</p>
     * 
     * <ul>
     * 	<li>true : Cellule touchee comportant une partie de batiment</li>
     * 	<li>false : Cellule pas touchée, à l'eau</li>
     * </ul>
     */
    private boolean touche;

    /**
     * Initialise une celulle avec ses coordonnées
     * @param coordX La coordonee x de la Cellule
     * @param coordY La coordonee x de la Cellule
     */
    public Cellule(int coordX, int coordY) {
    	this.coordX = coordX;
    	this.coordY = coordY;
    	touche = false;
    }

    /**
     * Change l'etat de touche à  true, qui correspond à un tir touche sur un batiment.
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
    	return "[" + coordX + ";" + coordY + "]" + (touche ? " touché" : " non touché");
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) { 
            return true; 
        } 
  
        if (!(o instanceof Cellule)) { 
            return false; 
        }
        
        // Si ils ont les mêmes coordonnées
        if ((((Cellule) o).getCoordX() != this.getCoordX()) || ((Cellule) o).getCoordY() != this.getCoordY()) {
        	return false;
        }
        
        // else
    	return true;
    }
}
