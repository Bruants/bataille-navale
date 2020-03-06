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
     * Permet de recuperer la Cellule qui correspond aux coordonnées x y correspond à la Zone Contigue this
     * @param coordX La coordonnée x de la Cellule tirée
     * @param coordY La coordonnée x de la Cellule tirée
     * @return la cellule qui correspond aux coordonnées x y
     */
    @objid ("200c06b7-f732-4284-88f8-0d62881dc8e2")
    public Cellule getCellule(int coordX, int coordY) {
    	int placement;
    	
    	/* Recherche la cellule en question parmis celles de la zone */
    	for (placement = 0 ; placement < possede.size() 
    			             && (possede.get(placement).getCoordX() != coordX
    			                 || possede.get(placement).getCoordY() != coordY); placement++);
    	
    	/* 
    	 * Vérifie que la cellule a bien été trouvée. si getCoordX != x ou getCoordY != y,
    	 * la cellule n'existe pas.
    	 */
        return possede.get(placement).getCoordX() == coordX 
        	   && possede.get(placement).getCoordY() == coordY ? possede.get(placement) : null;
        /* TODO : vérifier si on vérife que la cellule existe ou si elle existe forcément */ 
    }

    /**
     * Détermine si la cellule existe dans la zone contigue
     * @param x La coordonnée x de la Cellule
     * @param y La coordonnée x de la Cellule     
     */
    @objid ("6f46b07f-cacb-4f9c-8864-8f87265fa3f7")
    public boolean existe(int coordX, int coordY) {
    	int placement;
    	
    	/* Recherche la cellule en question parmis celles de la zone */
    	for (placement = 0 ; placement < possede.size() 
    			             && (possede.get(placement).getCoordX() != coordX
    			                 || possede.get(placement).getCoordY() != coordY); placement++);
    	
        return possede.get(placement).getCoordX() == coordX 
        	   && possede.get(placement).getCoordY() == coordY;
    }
    
    /**
     * Defini si la zone contigue est coulée, elle est coulée si toutes les cellules
     * qui la compose sont touchees.
     * @return true si toutes les cellules sont coulées
     *         false sinon
     */
    @objid ("1e8fd3d2-552b-4844-8a32-acab6a84fb9e")
    public boolean estCoule() {
    	int placement;
    	
    	/* 
    	 * Recherche la cellule en question parmis celles de la zone et vérifie si
    	 * elles sont touchées ou non.
    	 */
    	for (placement = 0 ; placement < possede.size() 
    			             && possede.get(placement).getTouche(); placement++);
    	
    	return possede.get(placement).getTouche();
    }
    
    /** 
     * Permet de recuperer la Cellule qui correspond aux coordonées x y correspond à la 
     * Zone Contigue this
     * @param x La coordonnée x de la Cellule tirée
     * @param y La coordonnée x de la Cellule tirée
     * @return true si le batiment a été coulé
     *         false sinon
     */
    @objid ("200c06b7-f732-4284-88f8-0d62881dc8e2")
    public boolean tir(Cellule cel) {
    	/* TODO */
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
