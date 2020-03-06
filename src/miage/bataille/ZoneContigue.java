package miage.bataille;

import java.util.ArrayList;
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
     * Définit le type de l'Obstacle (Batiment,Terre,...)
     */
    @objid ("2132ae47-3a82-4d9a-89ad-83899fefb346")
    private Obstacle obstacle;

    /**
     * Toutes les cellules de la Zone Contigue
     */
    @objid ("af33c602-1450-47fe-8dbd-c541d5b9105d")
    private ArrayList<Cellule> possede = new ArrayList<Cellule> ();

    /**
     * Crée une zone contigue en précisant son type d'Obstacle et avec une cellule de départ et une cellule de fin
     * @param type Le type d'Obstacle de la Zone Contigue
     * @param xDeb La coordonnée x du début de la zone 
     * @param yDeb La coordonnée y de la fin de la zone
     * @param xFin La coordonnée x du debut de la zone
     * @param yFin La coordonnée y de la fin de la zone
     * @throws IllegalArgumentException Si les coordonées sont incorrectes
     */
    @objid ("1e4b1448-5f98-4465-9b41-780042ccf61c")
    public ZoneContigue(Obstacle type, int xDeb, int yDeb, int xFin, int yFin) throws IllegalArgumentException {
    	/* On vérifie les coordonees */
    	// Coordonnees hors de la carte
    	if (xDeb < 0 || yDeb < 0 || xFin < 0 || yFin < 0) {
    		throw new IllegalArgumentException("Coordonées hors de la carte");    	
    	}
//		TODO
//    	if (xDeb > TAILLE_CARTE || yDeb > TAILLE_CARTE || xFin > TAILLE_CARTE || yFin > TAILLE_CARTE) {
//    		throw new IllegalArgumentException("Coordonées hors de la carte");
//    	}
    	// Coordonnees non alignées / ne correspondent pas aux caractéristiques de l'obstacle
    	if (xDeb + obstacle.tailleHaut != xFin || xDeb + obstacle.tailleHaut != xFin) {
    		// TODO
    	}
    }

    /** 
     * Permet de recuperer la Cellule qui correspond aux coordonées x y correspond à la Zone Contigue this
     * @param x La coordonnée x de la Cellule tirée
     * @param y La coordonnée x de la Cellule tirée
     * @return
     */
    @objid ("200c06b7-f732-4284-88f8-0d62881dc8e2")
    public Cellule getCellule(int x, int y) {
        return null;
    }

    /**
     * Détermine si la cellule existe dans la zone contigue
     * @param x La coordonnée x de la Cellule
     * @param y La coordonnée x de la Cellule     
     */
    @objid ("6f46b07f-cacb-4f9c-8864-8f87265fa3f7")
    public boolean existe(int coordX, int coordY) {
        return false;
    }
    
    /**
     * Definit si la zone contigue est coulee, elle est coulee si toutes les cellules
     * qui la compose sont touchees.
     */
    @objid ("1e8fd3d2-552b-4844-8a32-acab6a84fb9e")
    public boolean estCoule() {
    	return false; //stub
    }
    
    /** 
     * Permet de recuperer la Cellule qui correspond aux coordonées x y correspond à la Zone Contigue this
     * @param x La coordonnée x de la Cellule tirée
     * @param y La coordonnée x de la Cellule tirée
     * @return Vrai si le batiment a été coulé sinon faux
     */
    @objid ("200c06b7-f732-4284-88f8-0d62881dc8e2")
    public boolean tir(Cellule cel) {
        return false;
    }
    
    /**
     * Renvoie true si la zone est touchable, sinon faux
     * @return
     */
    public boolean estTouchable() {
    	return obstacle.touchable;
    }

}
