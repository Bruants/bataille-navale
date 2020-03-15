/**
 * Main.java
 */
package miage.bataille.affichage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

import org.hamcrest.core.IsNull;

import miage.bataille.Cellule;
import miage.bataille.Configuration;
import miage.bataille.Partie;

/**
 * @author Audric POUZELGUES, Damien AVETTA-RAYMOND
 */
public class DeroulementPartie {


	private static final String MESSAGE_ERREUR_DE_SAISIE = "Les coordonnées "
			+ "saisies sont incorrectes. Les coordonnées ne doivent pas "
			+ "dépasser la surface délimitée par la carte.\n";

	private static final String MESSAGE_INFORMATION_POUR_SAISIR_COORDONNEES = 
			"Veuillez saisir des coordonnées du type LETTRE + CHIFFRE : "
					+ "'A1' par exemple. \n";

	private static final String MESSAGE_ERREUR_SAISIE_INITIALISATION_CONFIGURATION = "La"
			+ "configuration choisie ne peut pas être null.";
	
	private static final String MESSAGE_ERREUR_SAUVEGARDE = "La sauvegarde n'a pas pu s'effectuer";
	
	private static final String MESSAGE_ANNULATION_SAUVEGARDE = "La sauvegarde ne s'est pas bien effectuée";


	private static String coordonnee;

	private static int coordonneeX;

	private static int coordonneeY;

	private static String resultat;

	/** 
	 * Liste contenant la situation des différentes cellules 
	 * - : pas de coup
	 * * : touché
	 * o : plouf
	 */
	private static HashMap<String, String> carte 
	= new HashMap<String, String>();


	private static Partie partie;

	/** Largeur de la carte */
	private static int tailleLargeur;

	/** Hauteur de la carte */
	private static int tailleHauteur;
	
	/** Permet de faire des saisies */
	private static Scanner entree = new Scanner(System.in);

	/**
	 * Débute une partie par défaut : 
	 * 					   - initialisation des différentes zones sur la map
	 *                     - placement des bâtiments par le joueur
	 */
	public static void initialisationParDefaut() {
		Configuration configurationDeLaPartie;
		System.out.println("Début du jeu\n");
		partie = new Partie();
		configurationDeLaPartie = partie.getConfiguration();
		tailleHauteur = configurationDeLaPartie.getHauteurCarte();
		tailleLargeur = configurationDeLaPartie.getLongueurCarte();
		creerNouvelleCarte();
	}

	/**
	 * Débute une partie avec une configuration choisie
	 * 					   - initialisation des différentes zones sur la map
	 *                     - placement des bâtiments par le joueur
	 * @param configurationChoisie
	 */
	public static void initialisationAvecUneConfiguration(Configuration configurationChoisie) {

		if (configurationChoisie == null) {
			initialisationParDefaut();
		} else {
			System.out.println("Début du jeu\n");
			tailleHauteur = configurationChoisie.getHauteurCarte();
			tailleLargeur = configurationChoisie.getLongueurCarte();
			partie = new Partie(configurationChoisie);
			creerNouvelleCarte();
		}
	}

	/**
	 * Initialise l'affichage de la partie avec un sauvegarde existante
	 * @param partieSauvegardee
	 */
	public static void initialisationAvecUneSauvegarde(Partie partieSauvegardee) {

		Configuration configurationDePartie;

		if (partieSauvegardee == null) {
			initialisationParDefaut();
		} else {
			System.out.println("Début du jeu\n");
			partie = partieSauvegardee;
			configurationDePartie = partie.getConfiguration();
			tailleHauteur = configurationDePartie.getHauteurCarte();
			tailleLargeur = configurationDePartie.getLongueurCarte();
			creerCarteAPartirDeSauvegarde();
		}
	}

	/**
	 * Afficher la carte de la bataille navale
	 */
	public static void afficherCarte() {

		char lettre = 'A';
		String cle;

		System.out.print("   ");
		for (int i = 1 ; i <= tailleHauteur ; i++) {
			if (i < 10) {
				System.out.print("  " + i + "  ");
			} else {
				System.out.print("  " + i + " ");
			}
		}

		for (int i = 0 ; i < tailleLargeur ; i++, lettre++) {
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
	 * Initialise la carte vide à afficher
	 */
	public static void creerNouvelleCarte() {
		char lettre;
		// Hash contenant toutes les cellules 
		String celluleMap;

		lettre = 'A';	
		for (int i = 0 ; i < tailleLargeur ; i++, lettre++) {

			for (int j = 1 ; j <= tailleHauteur ; j++) {
				celluleMap = Character.toString(lettre) + j;
				carte.put(celluleMap, "  -  ");
			}
		}
	}

	/**
	 * Initialise la carte  à afficher à partir d'une sauvegarde existante
	 */
	//TODO: A tester
	public static void creerCarteAPartirDeSauvegarde() {
		char lettre;
		// Hash contenant toutes les cellules 
		String celluleMap;


		for (Cellule c : partie.coups) {

			celluleMap = Character.toString((char)(c.getCoordY() + 65)) + c.getCoordX();

			if (partie.getCompose().contains(c)) {
				carte.put(celluleMap, "  *  ");
			} else {
				carte.put(celluleMap, "  o  ");
			}
		}
		//		lettre = 'A';	
		//		for (int i = 0 ; i < tailleLargeur ; i++, lettre++) {
		//
		//			for (int j = 1 ; j <= tailleHauteur ; j++) {
		//				celluleMap = Character.toString(lettre) + j;
		//				carte.put(celluleMap, "  -  ");
		//			}
		//		}
	}


	/**
	 * Vérifie la saisie de l'utilisateur pour tirer
	 * @return TRUE  -> Si : 
	 * 					- la coordonnée en abcisses est un nombre compris entre
	 * 					  0 et la largeur de la carte.
	 * 				    - et que la coordonées en ordonnés est une lettre
	 * 				      de l'alphabet.
	 * 		   FALSE -> Si : 
	 * 					- la coordonnée en abcisses n'est pas comprise
	 * 					  entre 0 et la largeur de la carte
	 * 					- la coordonnée en abcisses n'est pas un 
	 * 					  nombre
	 * 					- la coordonnée en ordonnés n'est pas
	 * 					  une lettre.
	 */
	public static boolean verifierSaisie(String coordonnee) {

		boolean valide = false; 

		// Vérification de la taille et du contenu de la chaine 
		try {

			valide = coordonnee.charAt(0) >= 65 
					&& coordonnee.charAt(0) < 65 + tailleLargeur
					&& Integer.parseInt(coordonnee.substring(1)) > 0 
					&& Integer.parseInt(coordonnee.substring(1)) <= tailleHauteur;

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
		String reponse;
		boolean valide = false;

		do {		

			System.out.print("Saisir une coordonnée : ");
			reponse = entree.next() + entree.nextLine();

			reponse = reponse.toUpperCase();

			// Message d'erreur si les coordonnées sont non valides
			if (reponse.equals("S")) {
				/* TODO appel sauvegarde */
			} else {
				valide = verifierSaisie(reponse);
				if (!valide) {
					System.out.println(MESSAGE_ERREUR_DE_SAISIE);
				}
			}
		} while (!valide);

		return reponse;
	}

	/**
	 * Lance une partie avec une nouvelle configuration
	 * et initialise l'affichage de la partie. 
	 * Fait dérouler la partie en faisant jouer l'utilisateur.
	 */
	public static void lancerUnePartie() {

		/* Phase 1 : initialisation de la partie */
		Configuration config = new Configuration().recupererConfig("Config1");
		initialisationAvecUneConfiguration(config);

		afficherCarte();

		/* Phase 2 : Déroulement d'un tour */
		for (int nbTour = 0; partie.getNbBatiments() > 0; nbTour++) {
			System.out.print("Coup " + nbTour + " => ");

			// Récupération des coordonnées saisies
			coordonnee = saisieTir();	

			// Conversion des coordonnées saisies pour pouvoir tirer
			coordonneeX = Integer.parseInt(coordonnee.substring(1));
			coordonneeX -= 1;	
			coordonneeY = coordonnee.charAt(0);
			coordonneeY -= 65;

			// Tir sur la carte
			System.out.println("coordonnée X : " + coordonneeX);
			System.out.println("coordonnée Y : " + coordonneeY);
			resultat = partie.tirer(coordonneeX, coordonneeY);

			if (resultat.equals("plouf")) {
				carte.put(coordonnee, "  o  ");
			} else {
				carte.put(coordonnee, "  *  ");
			}
			System.out.println(resultat + " !");
			afficherCarte();

		}
	}


	/**
	 * Lance une partie sauvegardée et réalise l'affichage de celle-ci
	 * à l'aide de sa liste de coups déjà tirés.
	 * @param partieExistante -> sauvegarde de la partie qui est
	 * 		  récupérée pour reprendre une partie.
	 */
	//TODO: à remplacer si cela ne convient pas...
	public static void lancerUnePartie(Partie partieExistante) {
		//TODO: Compléter le corps de cette méthode à l'aide
		/* de ce qui a été écrit dans le main et en le modifiant
			    pour afficher la carte à l'aide des coups déjà tirés */

		/* Phase 1 : initialisation de la partie */
		initialisationAvecUneSauvegarde(partieExistante);
		afficherCarte();

		/* Phase 2 : Déroulement d'un tour */
		for (int nbTour = 0; partie.getNbBatiments() > -1; nbTour++) {
			System.out.println("/!\\ Saisissez S pour sauvegarder /!\\");
			System.out.print("Coup " + nbTour + " => ");

			// Récupération des coordonnées saisies
			coordonnee = saisieTir();	

			// Conversion des coordonnées saisies pour pouvoir tirer
			coordonneeX = Integer.parseInt(coordonnee.substring(1));
			coordonneeX -= 1;	
			coordonneeY = coordonnee.charAt(0);
			coordonneeY -= 65;

			// Tir sur la carte
			resultat = partie.tirer(coordonneeX, coordonneeY);

			if (resultat.equals("plouf")) {
				carte.put(coordonnee, "  o  ");
			} else {
				carte.put(coordonnee, "  *  ");
			}
			System.out.println(resultat + " !");
			afficherCarte();

		}

	}


	/**
	 * TODO: Créer une méthode LancerPartie() -> qui va réaliser ce que 
	 * fait le main actuellement. Initialiser une partie, faire dérouler la partie
	 * et la fin de la partie
	 * @param args non utilisé
	 */
	public static void main(String[] args) {
		
		lancerUnePartie();
		/* Phase 3 : Fin de la partie */
		//TODO
	}
}
