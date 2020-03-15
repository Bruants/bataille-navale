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


	private static final String MESSAGE_ERREUR_DE_SAISIE = "Les coordonn�es "
			+ "saisies sont incorrectes. Les coordonn�es ne doivent pas "
			+ "d�passer la surface d�limit�e par la carte.\n";

	private static final String MESSAGE_INFORMATION_POUR_SAISIR_COORDONNEES = 
			"Veuillez saisir des coordonn�es du type LETTRE + CHIFFRE : "
					+ "'A1' par exemple. \n";

	private static final String MESSAGE_ERREUR_SAISIE_INITIALISATION_CONFIGURATION = "La"
			+ "configuration choisie ne peut pas �tre null.";
	
	private static final String MESSAGE_ERREUR_SAUVEGARDE = "La sauvegarde n'a pas pu s'effectuer";
	
	private static final String MESSAGE_ANNULATION_SAUVEGARDE = "La sauvegarde ne s'est pas bien effectu�e";


	private static String coordonnee;

	private static int coordonneeX;

	private static int coordonneeY;

	private static String resultat;

	/** 
	 * Liste contenant la situation des diff�rentes cellules 
	 * - : pas de coup
	 * * : touch�
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
	 * D�bute une partie par d�faut : 
	 * 					   - initialisation des diff�rentes zones sur la map
	 *                     - placement des b�timents par le joueur
	 */
	public static void initialisationParDefaut() {
		Configuration configurationDeLaPartie;
		System.out.println("D�but du jeu\n");
		partie = new Partie();
		configurationDeLaPartie = partie.getConfiguration();
		tailleHauteur = configurationDeLaPartie.getHauteurCarte();
		tailleLargeur = configurationDeLaPartie.getLongueurCarte();
		creerNouvelleCarte();
	}

	/**
	 * D�bute une partie avec une configuration choisie
	 * 					   - initialisation des diff�rentes zones sur la map
	 *                     - placement des b�timents par le joueur
	 * @param configurationChoisie
	 */
	public static void initialisationAvecUneConfiguration(Configuration configurationChoisie) {

		if (configurationChoisie == null) {
			initialisationParDefaut();
		} else {
			System.out.println("D�but du jeu\n");
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
			System.out.println("D�but du jeu\n");
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
	 * Initialise la carte vide � afficher
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
	 * Initialise la carte  � afficher � partir d'une sauvegarde existante
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
	 * V�rifie la saisie de l'utilisateur pour tirer
	 * @return TRUE  -> Si : 
	 * 					- la coordonn�e en abcisses est un nombre compris entre
	 * 					  0 et la largeur de la carte.
	 * 				    - et que la coordon�es en ordonn�s est une lettre
	 * 				      de l'alphabet.
	 * 		   FALSE -> Si : 
	 * 					- la coordonn�e en abcisses n'est pas comprise
	 * 					  entre 0 et la largeur de la carte
	 * 					- la coordonn�e en abcisses n'est pas un 
	 * 					  nombre
	 * 					- la coordonn�e en ordonn�s n'est pas
	 * 					  une lettre.
	 */
	public static boolean verifierSaisie(String coordonnee) {

		boolean valide = false; 

		// V�rification de la taille et du contenu de la chaine 
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

			System.out.print("Saisir une coordonn�e : ");
			reponse = entree.next() + entree.nextLine();

			reponse = reponse.toUpperCase();

			// Message d'erreur si les coordonn�es sont non valides
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
	 * Fait d�rouler la partie en faisant jouer l'utilisateur.
	 */
	public static void lancerUnePartie() {

		/* Phase 1 : initialisation de la partie */
		Configuration config = new Configuration().recupererConfig("Config1");
		initialisationAvecUneConfiguration(config);

		afficherCarte();

		/* Phase 2 : D�roulement d'un tour */
		for (int nbTour = 0; partie.getNbBatiments() > 0; nbTour++) {
			System.out.print("Coup " + nbTour + " => ");

			// R�cup�ration des coordonn�es saisies
			coordonnee = saisieTir();	

			// Conversion des coordonn�es saisies pour pouvoir tirer
			coordonneeX = Integer.parseInt(coordonnee.substring(1));
			coordonneeX -= 1;	
			coordonneeY = coordonnee.charAt(0);
			coordonneeY -= 65;

			// Tir sur la carte
			System.out.println("coordonn�e X : " + coordonneeX);
			System.out.println("coordonn�e Y : " + coordonneeY);
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
	 * Lance une partie sauvegard�e et r�alise l'affichage de celle-ci
	 * � l'aide de sa liste de coups d�j� tir�s.
	 * @param partieExistante -> sauvegarde de la partie qui est
	 * 		  r�cup�r�e pour reprendre une partie.
	 */
	//TODO: � remplacer si cela ne convient pas...
	public static void lancerUnePartie(Partie partieExistante) {
		//TODO: Compl�ter le corps de cette m�thode � l'aide
		/* de ce qui a �t� �crit dans le main et en le modifiant
			    pour afficher la carte � l'aide des coups d�j� tir�s */

		/* Phase 1 : initialisation de la partie */
		initialisationAvecUneSauvegarde(partieExistante);
		afficherCarte();

		/* Phase 2 : D�roulement d'un tour */
		for (int nbTour = 0; partie.getNbBatiments() > -1; nbTour++) {
			System.out.println("/!\\ Saisissez S pour sauvegarder /!\\");
			System.out.print("Coup " + nbTour + " => ");

			// R�cup�ration des coordonn�es saisies
			coordonnee = saisieTir();	

			// Conversion des coordonn�es saisies pour pouvoir tirer
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
	 * TODO: Cr�er une m�thode LancerPartie() -> qui va r�aliser ce que 
	 * fait le main actuellement. Initialiser une partie, faire d�rouler la partie
	 * et la fin de la partie
	 * @param args non utilis�
	 */
	public static void main(String[] args) {
		
		lancerUnePartie();
		/* Phase 3 : Fin de la partie */
		//TODO
	}
}
