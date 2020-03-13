/*
 * Cellule.java															06 mars 2020
 * L3 MIAGE 2019/2020, ni copyright, ni copyleft
 */
package miage.bataille;


/**
 * Represente une cellule de la mer de taille 1*1
 * Elle est presente dans les Zone Contigue et peut appartenir � un Obstacle
 * Zone de taille 1*1 du plan
 * @author Damien Avetta-Raymond
 */
public class Cellule {
	
	/** coordonn�e X de la cellule dans le plan */
    private int coordX;

    /** coordonn�e Y de la cellule dans le plan */
    private int coordY;

    /**
     * <p>Etat de la Cellule</p>
     * 
     * <ul>
     * 	<li>true : Cellule touchee comportant une partie de batiment</li>
     * 	<li>false : Cellule pas touch�e, � l'eau</li>
     * </ul>
     */
    private boolean touche;

    /**
     * Initialise une celulle avec ses coordonn�es
     * @param coordX La coordonee x de la Cellule
     * @param coordY La coordonee x de la Cellule
     */
    public Cellule(int coordX, int coordY) {
    	this.coordX = coordX;
    	this.coordY = coordY;
    	touche = false;
    }

    /**
     * Change l'etat de touche � true, qui correspond � un tir touche sur un batiment.
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
    	return "[" + coordX + ";" + coordY + "]" + (touche ? " touch�" : " non touch�");
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) { 
            return true; 
        } 
  
        if (!(o instanceof Cellule)) { 
            return false; 
        }
        
        // Si ils ont les m�mes coordonn�es
        if ((((Cellule) o).getCoordX() != this.getCoordX()) || ((Cellule) o).getCoordY() != this.getCoordY()) {
        	return false;
        }
        
        // else
    	return true;
    }
}
