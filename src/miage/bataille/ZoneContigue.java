package miage.bataille;

import java.util.ArrayList;
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
     * D�finit le type de l'Obstacle (Batiment,Terre,...)
     */
    @objid ("2132ae47-3a82-4d9a-89ad-83899fefb346")
    private Obstacle obstacle;

    /**
     * Toutes les cellules de la Zone Contigue
     */
    @objid ("af33c602-1450-47fe-8dbd-c541d5b9105d")
    private ArrayList<Cellule> possede = new ArrayList<Cellule> ();

    /**
     * Cr�e une zone contigue en pr�cisant son type d'Obstacle et avec une cellule de d�part et une cellule de fin
     * @param type Le type d'Obstacle de la Zone Contigue
     * @param xDeb La coordonn�e x du d�but de la zone 
     * @param yDeb La coordonn�e y de la fin de la zone
     * @param xFin La coordonn�e x du debut de la zone
     * @param yFin La coordonn�e y de la fin de la zone
     * @throws IllegalArgumentException Si les coordon�es sont incorrectes
     */
    @objid ("1e4b1448-5f98-4465-9b41-780042ccf61c")
    public ZoneContigue(Obstacle type, int xDeb, int yDeb, int xFin, int yFin) throws IllegalArgumentException {
    	/* On v�rifie les coordonees */
    	// Coordonnees hors de la carte
    	if (xDeb < 0 || yDeb < 0 || xFin < 0 || yFin < 0) {
    		throw new IllegalArgumentException("Coordon�es hors de la carte");    	
    	}
//		TODO
//    	if (xDeb > TAILLE_CARTE || yDeb > TAILLE_CARTE || xFin > TAILLE_CARTE || yFin > TAILLE_CARTE) {
//    		throw new IllegalArgumentException("Coordon�es hors de la carte");
//    	}
    	// Coordonnees non align�es / ne correspondent pas aux caract�ristiques de l'obstacle
    	if (xDeb + obstacle.tailleHaut != xFin || xDeb + obstacle.tailleHaut != xFin) {
    		// TODO
    	}
    }

    /** 
     * Permet de recuperer la Cellule qui correspond aux coordon�es x y correspond � la Zone Contigue this
     * @param x La coordonn�e x de la Cellule tir�e
     * @param y La coordonn�e x de la Cellule tir�e
     * @return
     */
    @objid ("200c06b7-f732-4284-88f8-0d62881dc8e2")
    public Cellule getCellule(int x, int y) {
        return null;
    }

    /**
     * D�termine si la cellule existe dans la zone contigue
     * @param x La coordonn�e x de la Cellule
     * @param y La coordonn�e x de la Cellule     
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
     * Permet de recuperer la Cellule qui correspond aux coordon�es x y correspond � la Zone Contigue this
     * @param x La coordonn�e x de la Cellule tir�e
     * @param y La coordonn�e x de la Cellule tir�e
     * @return Vrai si le batiment a �t� coul� sinon faux
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
