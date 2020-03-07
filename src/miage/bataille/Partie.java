package miage.bataille;

import java.util.ArrayList;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("df73f8f0-8de5-486c-802d-307ecfe68b1b")
public class Partie {
	
    /**
     * Zones contigues représentant la flotte de la partie
     */
    @objid ("22a7e834-949f-4ee9-ba2a-48ddd79efc94")
    private ArrayList<ZoneContigue> compose;

    /**
     * Liste des coups réalisés durant la partie
     */
    @objid ("0b60c20d-3aa2-41ea-b085-67752fed6387")
    public ArrayList<Cellule> coups;
    
    
    /**
     * Initialise une nouvelle partie par défaut.
     */
    //TODO: Problème -> aucun bâtiments initialisés
    public Partie() {
    	
    	compose = new ArrayList<ZoneContigue>();
    	coups = new ArrayList<Cellule>();
    }
    
    /**
     * Ajoute la zone contigue à la partie si elle n'empiete sur aucune autre zone
     * @param aAjouter
     * @throws Exception TODO
     */
    public void ajouterZoneContigue(ZoneContigue aAjouter) throws Exception {
    	boolean valide = true;
    	for(int i = 0 ; i < aAjouter.getPossede().size() && valide; i++) {
    		for(int j = 0 ; j < compose.size() && valide; j++) {
    			valide = !compose.get(j).existe(aAjouter.getPossede().get(i).getCoordX(),aAjouter.getPossede().get(i).getCoordY());
    		}
    	}
    	
    	if (!valide) {
    		throw new Exception("TODO"); //TODO
    	} // else
    	compose.add(aAjouter);
    }
    
    /**
     * Initialise une nouelle partie
     * @param listeBatiments -> Liste des bâtiments à placer
     * 							dans la partie.
     */
    public Partie(ArrayList<ZoneContigue> listeBatiments) {
    	
    	compose = listeBatiments;
    	coups = new ArrayList<Cellule>();
    }
    
    /**
     * Tire sur une cellule aux coordonnées x et y
     * Vérifie si une zone contigue contenant des bateaux a été touché
     * Sauvegarde le coup tiré
     * @param x -> Coordonnée en abcisses de la cellule tirée
     * @param y -> Coordonnée en ordonnée de la cellule tirée
     */
    @objid ("56659bde-06ae-44a1-abe1-82902d3b39cc")
    public void tirer(int x, int y) {
        Cellule celluleTiree; // Cellule tirée
        
        // TODO: Vérifier si les coordonnées ne dépassent pas la taille de la carte.
        if (x >= 0 && y >= 0) {

	        // Vérifie si la cellule choisie à touché un batiment ou non
	    	if ((celluleTiree = rechercheZone(x, y)) == null) {
	    		
	    		// Bâtiment non trouvé
	    		celluleTiree = new Cellule(x, y);
	    		
		    } else {
		    	// Bâtiment trouvé
		    	celluleTiree.aEteTouche();	    	
	    	}
	    	
	    	// Enregistrement du coup dans la liste des coups
	    	enregistrerCoup(celluleTiree);
        }
    }
    

    /**
     * Renvoie la Cellule recherchee, sinon renvois null
     * @return Cellule  - renvois la cellule de coordonée
     *                  definit si elle est une zonecontigue
     *                  - sinon renvois null, si aucunes cellule n'est trouve
     */
    @objid ("2ef7390a-0dbf-41c9-b8d0-5f83c1742a80")
    private Cellule rechercheZone(int x, int y) {
        
        Cellule trouve = null; // Cellule trouve dans une zone contigue
        
        // Parcours de toutes les zones de la mer
        for(int indiceRecherche = 0; indiceRecherche < compose.size(); indiceRecherche++) {
        	
            if( compose.get(indiceRecherche).existe(x, y)) {
            	trouve = compose.get(indiceRecherche).getCellule(x, y);
            }
        }

        return trouve;
    }

    /**
     * Enregistre le coup pour la posterite
     * Ajoute la cellule tirée à la liste des coups
     * @param celulle tirée
     */
    @objid ("ce21b39e-cd8e-47d8-89ae-fe405c9c39ae")
    private void enregistrerCoup(Cellule celluleTiree) {
    	
    	coups.add(celluleTiree);
    }
  
    /**
     * @return La liste de toutes les cellules tirées
     */
    public ArrayList<Cellule> getCellulesTirees(){
    	
        return coups;
    }

	public ArrayList<ZoneContigue> getCompose() {
		return compose;
	}

}
