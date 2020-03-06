package miage.bataille;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
/**
 * Represente une Zone Contigue
 * Une Zone Contigue est definie par une Cellule de début et une Cellule de fin
 * Les cellules présentes entre les cellules de début/fin font parties de la Zone Contigue
 * Une zone contigue peut être coulée si toutes les cellules présentes sont touchées
 * Elle est definie par un type (Obstacle)
 * @author kevin.sannac
 */
@objid ("2e8da21e-beba-42d5-bf0e-19b344dad88e")
public class ZoneContigue {
    /**
     * Definit si la zone contigue est coulee, elle est coulee si toutes les cellules
     * qui la compose sont touchees.
     */
    @objid ("1e8fd3d2-552b-4844-8a32-acab6a84fb9e")
    public boolean coule;

    /**
     * Définit le type de l'Obstacle (Batiment,Terre,...)
     */
    @objid ("2132ae47-3a82-4d9a-89ad-83899fefb346")
    private Obstacle obstacle;

    /**
     * Toutes les cellules de la Zone Contigue
     */
    @objid ("af33c602-1450-47fe-8dbd-c541d5b9105d")
    private List<Cellule> possede = new ArrayList<Cellule> ();

    /**
     * Crée une zone contigue en précisant son type d'Obstacle et avec une cellule de départ et une cellule de fin
     * @param type Le type d'Obstacle de la Zone Contigue
     * @param xDeb La coordonnée x du début de la zone
     * @param yDeb La coordonnée y de la fin de la zone
     * @param xFin La coordonnée x du debut de la zone
     * @param yFin La coordonnée y de la fin de la zone
     */
    @objid ("1e4b1448-5f98-4465-9b41-780042ccf61c")
    public ZoneContigue(Obstacle type, int xDeb, int yDeb, int xFin, int yFin) {
    }

    /** 
     * Permet de savoir si la Cellule qui correspond aux coordonées x y correspond à la Zone Contigue this
     * Si la cellule est touchee, elle passe à l'état touche
     * @param x La coordonnée x de la Cellule tirée
     * @param y La coordonnée x de la Cellule tirée
     * @return
     */
    @objid ("200c06b7-f732-4284-88f8-0d62881dc8e2")
    public Cellule tentativeTir(int x, int y) {
        return null;
    }

    /**
     * Récupere la cellule touchee si elle existe. Sinon cree une cellule non touche.
     * @param x La coordonnée x de la Cellule
     * @param y La coordonnée x de la Cellule     
     */
    @objid ("6f46b07f-cacb-4f9c-8864-8f87265fa3f7")
    public Cellule existe(int coordX, int coordY) {
        return null;
    }

}
