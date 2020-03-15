package miage.bataille;

import java.io.Serializable;

/**
 * Batiment touchable, a deposer dans la mer.
 */
public class Batiment extends Obstacle implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -25306178857876905L;

    /**
     * Definit un batiment de hauteur (ou largeur) 1, 
     * de longueur définit ainsi qu'un nom
     * 
     * @param tailleLgr longueur du batiment
     * @param nom nom du batiment
     */
    public Batiment (int tailleLgr, String nom) {
    	this.nom = nom;
    	this.tailleLgr = tailleLgr;
    	this.tailleHaut = 1;
    	
    	this.touchable = true;
    }

    /* non-javadoc
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return nom + " de taille : "+ tailleLgr + '\n' + "largeur : " + tailleLgr;
    }
	
}
