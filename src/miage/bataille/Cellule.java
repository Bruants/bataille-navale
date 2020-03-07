/*
 * Cellule.java															06 mars 2020
 * L3 MIAGE 2019/2020, ni copyright, ni copyleft
 */
package miage.bataille;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

/**
 * Represente une cellule de la mer de taille 1*1
 * Elle est presente dans les Zone Contigue et peut appartenir � un Obstacle
 * Zone de taille 1*1 du plan
 * @author Damien Avetta-Raymond
 */
@objid ("75ca9bcd-11cc-4d06-9e09-568386daf11f")
public class Cellule {
	/** coordonn�e X de la cellule dans le plan */
    @objid ("7adaa27c-1f8a-4bb0-ab6d-48dca30c037c")
    private int coordX;

    /** coordonn�e Y de la cellule dans le plan */
    @objid ("8b4c19ca-e7bb-40ae-92d8-da95d7300f9a")
    private int coordY;

    /**
     * <p>Etat de la Cellule</p>
     * 
     * <ul>
     * 	<li>true : Cellule touchee comportant une partie de batiment</li>
     * 	<li>false : Cellule pas touch�e, � l'eau</li>
     * </ul>
     */
    @objid ("b4ca99a7-ebe1-425c-a5a2-6b57126835d0")
    private boolean touche;

    /**
     * Initialise une celulle avec ses coordonn�es
     * @param coordX La coordonee x de la Cellule
     * @param coordY La coordonee x de la Cellule
     */
    @objid ("c4c1176f-4893-431b-9fed-fbc8bd56f3eb")
    public Cellule(int coordX, int coordY) {
    	this.coordX = coordX;
    	this.coordY = coordY;
    	touche = false;
    }

    /**
     * Change l'etat de touche � true, qui correspond � un tir touche sur un batiment.
     */
    @objid ("20346ad2-a476-41ce-9ad7-240d5891111c")
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
