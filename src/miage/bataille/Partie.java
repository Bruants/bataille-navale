package miage.bataille;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("df73f8f0-8de5-486c-802d-307ecfe68b1b")
public class Partie {
    /**
     * <Enter note text here>
     */
    @objid ("22a7e834-949f-4ee9-ba2a-48ddd79efc94")
    private List<ZoneContigue> compose = new ArrayList<ZoneContigue> ();

    /**
     * <Enter note text here>
     */
    @objid ("0b60c20d-3aa2-41ea-b085-67752fed6387")
    public List<Cellule> coups = new ArrayList<Cellule> ();

    @objid ("56659bde-06ae-44a1-abe1-82902d3b39cc")
    public boolean tirer(int x, int y) {
        return false;
    }

    /**
     * Renvoie la Cellule recherchee
     */
    @objid ("2ef7390a-0dbf-41c9-b8d0-5f83c1742a80")
    public Cellule rechercheZone(int x, int y) {
        return null;
    }

    /**
     * Enregistre le coup pour la posterité
     */
    @objid ("ce21b39e-cd8e-47d8-89ae-fe405c9c39ae")
    private void enregistrerCoup(Cellule celluleTiree) {
    }

}
