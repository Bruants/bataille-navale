package miage.bataille;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

/**
 * Description d'une zone contigue, identifie par une taille et de sa touchabilite.
 */
@objid ("51d60614-5c30-45a9-9d85-cced39152d6b")
public abstract class Obstacle {
    @objid ("3a532678-d9e1-4881-9921-5d34a4f99e40")
    private int taille;

    /**
     * Definit si l'obstacle sera touchable, puis coulable.
     */
    @objid ("afb441a8-5412-4ab1-8773-5e1cdb696159")
    private boolean touchable;

}
