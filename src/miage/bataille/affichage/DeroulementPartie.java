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


	private final static String MESSAGE_ERREUR_DE_SAISIE = "Les coordonnées saisies sont incorrectes. \n\n"
			+ "Veuillez saisir des coordonnées du type LETTRE + CHIFFRE : 'A1' par exemple\n"
			+ "et faisant partit de la carte.\n";

	/** 
	 * Liste contenant la situation des différentes cellules 
	 * - : pas de coup
	 * * : touché
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
	 * Débute une partie : - initialisation des différentes zones sur la map
	 *                     - placement des bâtiments par le joueur
	 */
	public static void initialisation() {
		Configuration config;
		System.out.println("Début du jeu\n");
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
	 * Initialise la carte à afficher
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
	 * Vérifie la saisie de l'utilisateur pour tirer
	 * @return TRUE  -> Si la coordonnée en abcisses est un nombre
	 * 				    compris entre 0 et la largeur de la carte.
	 * 				    Et si la coordonées en ordonnés est une lettre
	 * 				    de l'alphabet.
	 * 		   FALSE -> Si la coordonnée en abcisses n'est pas comprise
	 * 					entre 0 et la largeur de la carte, la coordonnée
	 * 					en abcisses n'est pas un nombre ou si la coordonnée
	 * 				    en ordonnés n'est pas une lettre.
	 */
	public static boolean verifierSaisie(String coordonnee) {
		
		boolean valide = false; 
		
		// Vérification de la taille et du contenu de la chaine 
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

			System.out.print("Saisir une coordonnée : ");
			coordonnee = entree.next() + entree.nextLine();
			
			coordonnee = coordonnee.toUpperCase();
			
			// Message d'erreur si les coordonnées sont non valides
			if (!verifierSaisie(coordonnee)) {
				System.out.println(MESSAGE_ERREUR_DE_SAISIE);
			}

		} while (!verifierSaisie(coordonnee));

		return coordonnee;
	}


	/**
	 * @param args non utilisé
	 */
	public static void main(String[] args) {
		String coordonnee;
		String resultat; // Le résultat d'un tir

		int coordonneeX, coordonneeY;

		/* Phase 1 : initialisation de la partie */
		initialisation();
		
		/* Phase 2 : Déroulement d'un tour */
		for (int nbTour = 0; partie.getNbBatiments() > -1; nbTour++) {
			System.out.print("Coup " + nbTour + " > ");
			
			// Récupération des coordonnées saisies
			coordonnee = saisieTir();	
			
			// Conversion des coordonnées saisies pour pouvoir tirer
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
