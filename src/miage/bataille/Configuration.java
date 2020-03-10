package miage.bataille;

import java.util.ArrayList;

/**
 * Correspond à une configuration de la partie
 * de bataille navale
 * 
 * @author alexis vivier
 *
 */
public class Configuration {

	/** Longueur maximale de la carte définit sur X */
	private int longueurCarte;
	
	/** Hauteur de la carte définit */
	private int hauteurCarte;
	
	/** Stocke la flotte */
	private ArrayList<Batiment> flotte;
	
	/** 
	 * Création d'une configuration de base
	 */
	public Configuration() {
		
		flotte = new ArrayList<Batiment>();
		
		longueurCarte = 12;
		hauteurCarte = 12;
		
		flotte.add(new Batiment(4, "porte-avion"));
		flotte.add(new Batiment(3, "croiseur"));
		flotte.add(new Batiment(3, "croiseur"));
		flotte.add(new Batiment(2, "sous-marin"));
		flotte.add(new Batiment(2, "sous-marin"));
		flotte.add(new Batiment(2, "sous-marin"));
		flotte.add(new Batiment(1, "vedette"));
		flotte.add(new Batiment(1, "vedette"));
		flotte.add(new Batiment(1, "vedette"));
		flotte.add(new Batiment(1, "vedette"));
	}
	
	/**
	 * Crée une configuration definit par la longueur, la hauteur et 
	 * les batiments definissant les flottes 
	 * @param longueurCarte longueur x de la carte
	 * @param hauteurCarte hauteur y de la carte
	 * @param batFlotte batiments à ajouter a la flotte
	 */
	public Configuration(int longueurCarte, int hauteurCarte, Batiment...batFlotte) {
		//TODO
	}

	/**
	 * @return la longueur x de la carte
	 */
	public int getLongueurCarte() {
		return longueurCarte;
	}

	/**
	 * Modifie la longueur de carte courante
	 * @param longueurCarte nouvelle longueur de carte
	 */
	public void setLongueurCarte(int longueurCarte) {
		this.longueurCarte = longueurCarte;
	}

	/**
	 * @return la hauteur y de la carte
	 */
	public int getHauteurCarte() {
		return hauteurCarte;
	}

	/**
	 * Modifie la haute de la carte courante
	 * @param hauteurCarte nouvelle hauteur de la carte
	 */
	public void setHauteurCarte(int hauteurCarte) {
		this.hauteurCarte = hauteurCarte;
	}

	/**
	 * @return renvois la flotte des équipes
	 */
	public ArrayList<Batiment> getFlotte() {
		return flotte;
	}

	/**
	 * Modifie la flotte courante
	 * @param flotte nouvelle flotte de la configuration courante
	 */
	public void setFlotte(ArrayList<Batiment> flotte) {
		this.flotte = flotte;
	}
}
