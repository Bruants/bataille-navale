package miage.bataille;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

/**
 * Batiment touchable, a deposer dans la mer.
 */
@objid ("fa9e0afe-a153-45ea-b905-ad7b9b1283fb")
public class Batiment extends Obstacle {
	
	/**
	 * Nom du batiment courant
	 */
    @objid ("6695bf68-a5ff-4657-af1d-44173e63c0ce")
    public String nom;

    public Batiment (int tailleLgr, String nom) {
    	this.nom = nom;
    	this.tailleLgr = tailleLgr;
    }
    
}
