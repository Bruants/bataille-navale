package miage.bataille;

import java.util.ArrayList;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("df73f8f0-8de5-486c-802d-307ecfe68b1b")
public class Partie {
	
    /**
     * Zones contigues repr�sentant la flotte de la partie
     */
    @objid ("22a7e834-949f-4ee9-ba2a-48ddd79efc94")
    private ArrayList<ZoneContigue> compose;

    /**
     * Liste des coups r�alis�s durant la partie
     */
    @objid ("0b60c20d-3aa2-41ea-b085-67752fed6387")
    public ArrayList<Cellule> coups;
    
    
    /**
     * Initialise une nouvelle partie par d�faut.
     */
    //TODO: Probl�me -> aucun b�timents initialis�s
    public Partie() {
    	
    	compose = new ArrayList<ZoneContigue>();
    	coups = new ArrayList<Cellule>();
    }
    
    /**
     * Ajoute la zone contigue � la partie si elle n'empiete sur aucune autre zone
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
     * @param listeBatiments -> Liste des b�timents � placer
     * 							dans la partie.
     */
    public Partie(ArrayList<ZoneContigue> listeBatiments) {
    	
    	compose = listeBatiments;
    	coups = new ArrayList<Cellule>();
    }
    
    /**
     * Tire sur une cellule aux coordonn�es x et y
     * V�rifie si une zone contigue contenant des bateaux a �t� touch�
     * Sauvegarde le coup tir�
     * @param x -> Coordonn�e en abcisses de la cellule tir�e
     * @param y -> Coordonn�e en ordonn�e de la cellule tir�e
     */
    @objid ("56659bde-06ae-44a1-abe1-82902d3b39cc")
    public void tirer(int x, int y) {
        Cellule celluleTiree; // Cellule tir�e
        
        // TODO: V�rifier si les coordonn�es ne d�passent pas la taille de la carte.
        if (x >= 0 && y >= 0) {

	        // V�rifie si la cellule choisie � touch� un batiment ou non
	    	if ((celluleTiree = rechercheZone(x, y)) == null) {
	    		
	    		// B�timent non trouv�
	    		celluleTiree = new Cellule(x, y);
	    		
		    } else {
		    	// B�timent trouv�
		    	celluleTiree.aEteTouche();	    	
	    	}
	    	
	    	// Enregistrement du coup dans la liste des coups
	    	enregistrerCoup(celluleTiree);
        }
    }
    

    /**
     * Renvoie la Cellule recherchee, sinon renvois null
     * @return Cellule  - renvois la cellule de coordon�e
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
     * Ajoute la cellule tir�e � la liste des coups
     * @param celulle tir�e
     */
    @objid ("ce21b39e-cd8e-47d8-89ae-fe405c9c39ae")
    private void enregistrerCoup(Cellule celluleTiree) {
    	
    	coups.add(celluleTiree);
    }
  
    /**
     * @return La liste de toutes les cellules tir�es
     */
    public ArrayList<Cellule> getCellulesTirees(){
    	
        return coups;
    }

	public ArrayList<ZoneContigue> getCompose() {
		return compose;
	}

}
