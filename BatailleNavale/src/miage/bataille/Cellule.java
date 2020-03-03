package miage.bataille;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("75ca9bcd-11cc-4d06-9e09-568386daf11f")
public class Cellule {
    @objid ("7adaa27c-1f8a-4bb0-ab6d-48dca30c037c")
    private int coordX;

    @objid ("8b4c19ca-e7bb-40ae-92d8-da95d7300f9a")
    private int coordY;

    /**
     * <p>Etat de la Cellule</p>
     * 
     * <ul>
     * 	<li>true : Cellule touch&eacute;e comportant une partie de batiment</li>
     * 	<li>false : Cellule pas touchee, a l&#39;eau</li>
     * </ul>
     */
    @objid ("b4ca99a7-ebe1-425c-a5a2-6b57126835d0")
    private boolean touche;

    @objid ("c4c1176f-4893-431b-9fed-fbc8bd56f3eb")
    public Cellule(int coordX, int coordY) {
    }

    /**
     * Change l'etat de touche à true, qui correspond à un tir touche sur un batiment.
     */
    @objid ("20346ad2-a476-41ce-9ad7-240d5891111c")
    public void aEteTouche() {
    }

}
