package miage.bataille;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;
/**
 * Represente une Zone Contigue
 * Une Zone Contigue est definie par une Cellule de d�but et une Cellule de fin
 * Les cellules pr�sentes entre les cellules de d�but/fin font parties de la Zone Contigue
 * Une zone contigue peut �tre coul�e si toutes les cellules pr�sentes sont touch�es
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
     * D�finit le type de l'Obstacle (Batiment,Terre,...)
     */
    @objid ("2132ae47-3a82-4d9a-89ad-83899fefb346")
    private Obstacle obstacle;

    /**
     * Toutes les cellules de la Zone Contigue
     */
    @objid ("af33c602-1450-47fe-8dbd-c541d5b9105d")
    private List<Cellule> possede = new ArrayList<Cellule> ();

    /**
     * Cr�e une zone contigue en pr�cisant son type d'Obstacle et avec une cellule de d�part et une cellule de fin
     * @param type Le type d'Obstacle de la Zone Contigue
     * @param xDeb La coordonn�e x du d�but de la zone
     * @param yDeb La coordonn�e y de la fin de la zone
     * @param xFin La coordonn�e x du debut de la zone
     * @param yFin La coordonn�e y de la fin de la zone
     */
    @objid ("1e4b1448-5f98-4465-9b41-780042ccf61c")
    public ZoneContigue(Obstacle type, int xDeb, int yDeb, int xFin, int yFin) {
    }

    /** 
     * Permet de savoir si la Cellule qui correspond aux coordon�es x y correspond � la Zone Contigue this
     * Si la cellule est touchee, elle passe � l'�tat touche
     * @param x La coordonn�e x de la Cellule tir�e
     * @param y La coordonn�e x de la Cellule tir�e
     * @return
     */
    @objid ("200c06b7-f732-4284-88f8-0d62881dc8e2")
    public Cellule tentativeTir(int x, int y) {
        return null;
    }

    /**
     * R�cupere la cellule touchee si elle existe. Sinon cree une cellule non touche.
     * @param x La coordonn�e x de la Cellule
     * @param y La coordonn�e x de la Cellule     
     */
    @objid ("6f46b07f-cacb-4f9c-8864-8f87265fa3f7")
    public Cellule existe(int coordX, int coordY) {
        return null;
    }

}
