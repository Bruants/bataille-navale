package miage.bataille;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("2e8da21e-beba-42d5-bf0e-19b344dad88e")
public class ZoneContigue {
    /**
     * Definit l'etat de la zone contigue, qui arrive quand l'ensemble des cellules associé deviennent touche.
     */
    @objid ("1e8fd3d2-552b-4844-8a32-acab6a84fb9e")
    public boolean coule;

    @objid ("2132ae47-3a82-4d9a-89ad-83899fefb346")
    private Obstacle obstacle;

    @objid ("af33c602-1450-47fe-8dbd-c541d5b9105d")
    private List<Cellule> possede = new ArrayList<Cellule> ();

    @objid ("1e4b1448-5f98-4465-9b41-780042ccf61c")
    public ZoneContigue(Obstacle type, int xDeb, int yDeb, int xFin, int yFin) {
    }

    @objid ("200c06b7-f732-4284-88f8-0d62881dc8e2")
    public Cellule tentativeTir(int x, int y) {
        return null;
    }

    /**
     * Récupére la cellule touchée si elle existe. Sinon crée une cellule non touche.
     */
    @objid ("6f46b07f-cacb-4f9c-8864-8f87265fa3f7")
    public Cellule existe(int coordX, int coordY) {
        return null;
    }

}
