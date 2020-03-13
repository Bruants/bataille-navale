/**
 * Main.java
 */
package miage.bataille.affichage;

import java.util.HashMap;
import java.util.Scanner;

import miage.bataille.Configuration;
import miage.bataille.Partie;

/**
 * @author Audric POUZELGUES, Damien AVETTA-RAYMOND
 */
public class DeroulementPartie {

	/** 
	 * Liste contenant la situation des diff�rentes cellules 
	 * - : pas de coup
	 * * : touch�
	 * o : coup � l'eau
	 */
	private static HashMap<String, String> carte = new HashMap<String, String>();

	/**
	 * TODO
	 */
	private static Partie partie;
	
	/** Largeur de la carte */
	private static int tailleLongueur;
	
	/** Hauteur de la carte */
	private static int tailleHauteur;
	
	/**
	 * D�bute une partie : - initialisation des diff�rentes zones sur la map
	 *                     - placement des b�timents par le joueur
	 */
	public static void initialisation() {
		Configuration config;
		System.out.println("D�but du jeu\n");
		partie = new Partie();
		config = partie.getConfiguration();
		tailleHauteur = config.getHauteurCarte();
		tailleLongueur = config.getLongueurCarte();
		creerCarte();
		afficherCarte();
	}
	
	/**
	 * Afficher la carte de la bataille navale
	 */
	public static void afficherCarte() {
		
		char lettre = 'A';
		String cle = "";
		
		for (int i = 0 ; i < tailleLongueur ; i++, lettre++) {
			for (int j = 1 ; j <= tailleHauteur ; j++) {
				
				cle = lettre + "" + j;
				System.out.print(carte.get(cle) + " ");
			}
			
			System.out.print("\n");
		}
	}

	/**
	 * Initialise la carte � afficher
	 */
	public static void creerCarte() {
		char lettre;
		// Hash contenant toutes les cellules 
		String celluleMap;
		
		lettre = 'A';	
		for (int i = 0 ; i < tailleLongueur ; i++, lettre++) {
			for (int j = 1 ; j <= tailleHauteur ; j++) {
				celluleMap = Character.toString(lettre) + j;
				carte.put(celluleMap, " - ");
			}
		}
	}
	
	
	/**
	 * Saisie d'un tir sur la carte
	 * - Demande une lettre pour la colonne
	 * - Demande un nombre entier positif pour la ligne
	 */
	public static String saisieTir() {
		Scanner entree = new Scanner(System.in);
		String coordonnee;
		boolean valide;
		
		do {		
			
			System.out.print("Saisir une coordonn�e : ");
			coordonnee = entree.next() + entree.nextLine();
			
			// V�rification de la taille de la chaine 
			valide = coordonnee.length() == 2 
					 && coordonnee.charAt(0) >= 65 && coordonnee.charAt(0) < 65 + tailleLongueur
					 && coordonnee.charAt(1) >= 49 && coordonnee.charAt(1) <= 49 + tailleHauteur;
			
		} while (!valide);
		
		return coordonnee;
	}
	

	/**
	 * @param args non utilis�
	 */
	public static void main(String[] args) {
		String coordonnee;
		String resultat; // Le r�sultat d'un tir
		creerCarte();
		afficherCarte();
		
		/* Phase 1 : initialisation de la partie */
		initialisation();
		/* Phase 2 : D�roulement d'un tour */
		for (int nbTour = 1 ; partie.getNbBatiments() > 0 ; nbTour++) {
			System.out.print("Coup " + nbTour + " > ");
			coordonnee = saisieTir();	
			resultat = partie.tirer(coordonnee.charAt(1) - 1, coordonnee.charAt(0) - 65);
			if (resultat.equals("coup � l'eau")) {
				carte.put(coordonnee, " o ");
			} else {
				carte.put(coordonnee, " * ");
			}
			System.out.println(resultat + " !");
			afficherCarte();
		}
		/* Phase 3 : Fin de la partie */
		//TODO
	}

}
