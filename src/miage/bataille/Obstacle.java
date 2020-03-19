package miage.bataille;


/**
 * Description d'une zone contigue, 
 * identifie par une taille et sa touchabilité.
 * @author L3 MIAGE Rodez
 */
public abstract class Obstacle {
	
	/**
	 * Nom du bâtiment courant
	 */
    public String nom;
	
	/**
	 * Taille de l'obstacle en longueur
	 */
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
     * Définit si l'obstacle sera touchable, puis coulable.
     */
    protected boolean touchable;
    
    /**
     * @return nom du batiment courant
     */
	public String getNom() {
		return nom;
	}

    /* non-javadoc
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Obstacle de longueur : " + tailleLgr + '\n' + "largeur : " + tailleLgr;
    }
    
}
