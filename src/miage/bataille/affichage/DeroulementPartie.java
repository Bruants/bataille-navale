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


	private final static String MESSAGE_ERREUR_DE_SAISIE = "Les coordonn�es saisies sont incorrectes. \n\n"
			+ "Veuillez saisir des coordonn�es du type LETTRE + CHIFFRE : 'A1' par exemple\n"
			+ "et faisant partit de la carte.\n";

	/** 
	 * Liste contenant la situation des diff�rentes cellules 
	 * - : pas de coup
	 * * : touch�
	 * o : plouf
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
		String cle;

		System.out.print("   ");
		for (int i = 1 ; i <= tailleLongueur ; i++) {
			if (i < 10) {
				System.out.print("  " + i + "  ");
			} else {
				System.out.print("  " + i + " ");
			}
		}
		
		for (int i = 0 ; i < tailleLongueur ; i++, lettre++) {
			System.out.print("\n\n");
			System.out.print(lettre + "  ");
			for (int j = 1 ; j <= tailleHauteur ; j++) {
				cle = Character.toString(lettre) + j;
				System.out.print(carte.get(cle));
			}		
		}
		
		System.out.print("\n\n");
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
				carte.put(celluleMap, "  -  ");
			}
		}
	}
	
	
	/**
	 * V�rifie la saisie de l'utilisateur pour tirer
	 * @return TRUE  -> Si la coordonn�e en abcisses est un nombre
	 * 				    compris entre 0 et la largeur de la carte.
	 * 				    Et si la coordon�es en ordonn�s est une lettre
	 * 				    de l'alphabet.
	 * 		   FALSE -> Si la coordonn�e en abcisses n'est pas comprise
	 * 					entre 0 et la largeur de la carte, la coordonn�e
	 * 					en abcisses n'est pas un nombre ou si la coordonn�e
	 * 				    en ordonn�s n'est pas une lettre.
	 */
	public static boolean verifierSaisie(String coordonnee) {
		
		boolean valide = false; 
		
		// V�rification de la taille et du contenu de la chaine 
		try {
			
			valide = coordonnee.charAt(0) >= 65 && coordonnee.charAt(0) < 65 + tailleHauteur
					&& Integer.parseInt(coordonnee.substring(1)) > 0 
					&& Integer.parseInt(coordonnee.substring(1)) <= tailleLongueur;
					
		} catch (NumberFormatException e) {
			valide = false;
		}
		
		return valide;
	}


	/**
	 * Saisie d'un tir sur la carte
	 * - Demande une lettre pour la colonne
	 * - Demande un nombre entier positif pour la ligne
	 */
	public static String saisieTir() {
		Scanner entree = new Scanner(System.in);
		String coordonnee;

		do {		

			System.out.print("Saisir une coordonn�e : ");
			coordonnee = entree.next() + entree.nextLine();
			
			coordonnee = coordonnee.toUpperCase();
			
			// Message d'erreur si les coordonn�es sont non valides
			if (!verifierSaisie(coordonnee)) {
				System.out.println(MESSAGE_ERREUR_DE_SAISIE);
			}

		} while (!verifierSaisie(coordonnee));

		return coordonnee;
	}


	/**
	 * @param args non utilis�
	 */
	public static void main(String[] args) {
		String coordonnee;
		String resultat; // Le r�sultat d'un tir

		int coordonneeX, coordonneeY;

		/* Phase 1 : initialisation de la partie */
		initialisation();
		
		/* Phase 2 : D�roulement d'un tour */
		for (int nbTour = 0; partie.getNbBatiments() > -1; nbTour++) {
			System.out.print("Coup " + nbTour + " > ");
			
			// R�cup�ration des coordonn�es saisies
			coordonnee = saisieTir();	
			
			// Conversion des coordonn�es saisies pour pouvoir tirer
			coordonneeX = Integer.parseInt(coordonnee.substring(1));
			coordonneeX -= 1;
			
			coordonneeY = coordonnee.charAt(0);
			coordonneeY -= 65;
			
			// Tir sur la carte
			resultat = partie.tirer(coordonneeX, coordonneeY);
			
//			int y = coordonnee.charAt(0);
//			y -= 65;
//			
//			int x = Integer.parseInt(coordonnee.substring(1));
//			x -= 1;
//			System.out.println("X : " + x);
//			System.out.println("Y : " + y);
			//resultat = partie.tirer(Integer.parseInt(coordonnee.substring(1)),
			//		Integer.parseInt(Character.toString(coordonnee.charAt(0))));
			
			if (resultat.equals("plouf")) {
				carte.put(coordonnee, "  o  ");
			} else {
				carte.put(coordonnee, "  *  ");
			}
			System.out.println(resultat + " !");
			afficherCarte();
		}
		/* Phase 3 : Fin de la partie */
		//TODO
	}

}
