/**
 * Main.java
 */
package miage.bataille.affichage;

import java.util.HashMap;
import java.util.Scanner;

import miage.bataille.Cellule;
import miage.bataille.Configuration;
import miage.bataille.Partie;

/**
 * @author Audric POUZELGUES, Damien AVETTA-RAYMOND
 */
public class DeroulementPartie {


	private final static String MESSAGE_ERREUR_DE_SAISIE = "Les coordonn�es "
			+ "saisies sont incorrectes. Les coordonn�es ne doivent pas "
			+ "d�passer la surface d�limit�e par la carte.\n";
	
	private final static String MESSAGE_INFORMATION_POUR_SAISIR_COORDONNEES = 
			"Veuillez saisir des coordonn�es du type LETTRE + CHIFFRE : "
			+ "'A1' par exemple. \n";

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

	/**
	 * D�bute une partie : - initialisation des diff�rentes zones sur la map
	 *                     - placement des b�timents par le joueur
	 */
	//TODO: Prendre en compte la r�cup�ration d'une partie existante
	//TODO: Pouvoir choisir une configuration existante ou prendre celle par d�faut
	public static void initialisation() {
		Configuration configurationDeLaPartie;
		System.out.println("D�but du jeu\n");
		partie = new Partie();
		configurationDeLaPartie = partie.getConfiguration();
		tailleHauteur = configurationDeLaPartie.getHauteurCarte();
		tailleLargeur = configurationDeLaPartie.getLongueurCarte();
		creerCarte();
	}

	/**
	 * Afficher la carte de la bataille navale
	 */
	public static void afficherCarte() {

		char lettre = 'A';
		String cle;

		System.out.print("   ");
		for (int i = 1 ; i <= tailleLargeur ; i++) {
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
	 * Initialise la carte � afficher
	 */
	public static void creerCarte() {
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
				&& coordonnee.charAt(0) < 65 + tailleHauteur
				&& Integer.parseInt(coordonnee.substring(1)) > 0 
				&& Integer.parseInt(coordonnee.substring(1)) <= tailleLargeur;
					
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
		String coordonnee = "";

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
	 * Lance une partie avec une nouvelle configuration
	 * et initialise l'affichage de la partie. 
	 * Fait d�rouler la partie en faisant jouer l'utilisateur.
	 */
	//TODO: � remplacer si cela ne convient pas...
	public static void LancerUnePartie() {
		
		//TODO: Compl�ter le corps de cette m�thode � l'aide
		// de ce qui a �t� �crit dans le main
		
		
	}
	
	
	/**
	 * Lance une partie sauvegard�e et r�alise l'affichage de celle-ci
	 * � l'aide de sa liste de coups d�j� tir�s.
	 * @param partieExistante -> sauvegarde de la partie qui est
	 * 		  r�cup�r�e pour reprendre une partie.
	 */
	//TODO: � remplacer si cela ne convient pas...
	public static void LancerUnePartie(Partie partieExistante) {
		//TODO: Compl�ter le corps de cette m�thode � l'aide
		     /* de ce qui a �t� �crit dans le main et en le modifiant
			    pour afficher la carte � l'aide des coups d�j� tir�s */
	}


	/**
	 * TODO: Cr�er une m�thode LancerPartie() -> qui va r�aliser ce que 
	 * fait le main actuellement. Initialiser une partie, faire d�rouler la partie
	 * et la fin de la partie
	 * @param args non utilis�
	 */
	public static void main(String[] args) {
		String coordonnee;
		String resultat; // Le r�sultat d'un tir

		int coordonneeX, 
			coordonneeY;

		/* Phase 1 : initialisation de la partie */
		initialisation();
		afficherCarte();
		
		/* Phase 2 : D�roulement d'un tour */
		for (int nbTour = 0; partie.getNbBatiments() > -1; nbTour++) {
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
		/* Phase 3 : Fin de la partie */
		//TODO
	}

}
