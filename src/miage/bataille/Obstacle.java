package miage.bataille;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

/**
 * Description d'une zone contigue, identifie par une taille et de sa touchabilite.
 */
@objid ("51d60614-5c30-45a9-9d85-cced39152d6b")
public abstract class Obstacle {
	
	/**
	 * Taille de l'obstacle en longueur
	 */
    @objid ("3a532678-d9e1-4881-9921-5d34a4f99e40")
    protected int tailleLgr;
    
	/**
	 * Taille de l'obstacle en hauteur
	 */
    protected int tailleHaut;

    /**
     * @return la longueur en cellules de l'obstacle
     */
    public int getTailleLgr() {
		return tailleLgr;
	}

    /**
     * @return la hauteur en cellules de l'obstacle
     */
	public int getTailleHaut() {
		return tailleHaut;
	}

	/**
     * Definit si l'obstacle sera touchable, puis coulable.
     */
    @objid ("afb441a8-5412-4ab1-8773-5e1cdb696159")
    protected boolean touchable;

    /* non-javadoc
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Obstacle de longueur : " + tailleLgr + '\n' + "largeur : " + tailleLgr;
    }
    
}
