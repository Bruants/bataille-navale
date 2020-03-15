/**
 * Main.java
 */
package miage.bataille.affichage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import miage.bataille.Configuration;
import miage.bataille.Partie;
import miage.bataille.Sauvegarder;

/**
 * @author Audric POUZELGUES, Damien AVETTA-RAYMOND
 */
public class DeroulementPartie {

	private static final String MESSAGE_ERREUR_DE_SAISIE = "Les coordonn�es "
			+ "saisies sont incorrectes. Les coordonn�es ne doivent pas "
			+ "d�passer la surface d�limit�e par la carte.\n";
	
	private static final String MESSAGE_ERREUR_NOM_FICHIER = "Le nom du fichier est incorrect. Les "
			                                                 + "caract�res suivans sont interdits : "
			                                                 + "/\\\\:*?\\\"<>|";
	
	private static final String MESSAGE_ERREUR_CHARGEMENT = "Erreur, le fichier que vous avez sp�cifi� "
			                                                + "n'existe pas.";
	
	/** 
	 * Liste contenant la situation des diff�rentes cellules 
	 * - : pas de coup
	 * * : touch�
	 * o : plouf
	 */
	private static HashMap<String, String> carte = new HashMap<String, String>();

	private static Partie partie;

	/** Largeur de la carte */
	private static int tailleLargeur;

	/** Hauteur de la carte */
	private static int tailleHauteur;
	
	/** Nombre de tour (ou coup) r�alis� pour la partie courante */
	public static int nbTour = 0;
	
	/** Permet de faire des saisies */
	private static Scanner entree = new Scanner(System.in);
	
	/** Liste des coups effectu�s */
	private static ArrayList<String> coups = new ArrayList<String>();

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
	 * Saisie lors d'un tour : sauvegarde / quitter la partie ou tir sur la carte :
	 * - Demande une lettre pour la colonne
	 * - Demande un nombre entier positif pour la ligne
	 * @return la r�ponse saisie : une coordonn�e ou "Q" si l'utilisateur quitte la partie
	 */
	public static String saisieTour() {
		String reponse;
		boolean valide = false;

		do {		

			System.out.print("Saisir une coordonn�e : ");
			reponse = entree.next() + entree.nextLine();

			reponse = reponse.toUpperCase().trim();

			// Message d'erreur si les coordonn�es sont non valides
			if (reponse.equals("S")) {
				effectuerSauvegarde();
			} else if (reponse.equals("Q")){
				valide = quitterPartie();
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
	 * D�termine si l'utilisateur souhaite vraiment quitter la partie.
	 * Si l'utilisateur quitte la partie, une sauvegarde lui est propos�e.
	 * @return true si l'utilisateur quitte la partie
	 *         false sinon
	 */
	public static boolean quitterPartie() {
		boolean quitter;
		
		System.out.print("Voulez-vous vraiment quitter la partie ? (o/n) : ");
		quitter = reponseValide();
		if (quitter) {
			System.out.print("Souhaitez-vous sauvegarder avant de quitter la partie ? (o/n) : ");
			if (reponseValide()) {
				effectuerSauvegarde();
			}
		}
		
		return quitter;
	}
	
	/**
	 * Traitement de la sauvegarde :
	 * 	- Saisie du nom de la sauvegarde, v�rification et validation par l'utilisateur
	 *  - Cr�ation de la sauvegarde
	 */
	public static void effectuerSauvegarde() {
		String nomFichier;
		boolean validationSauvegarde;
		boolean confirmationSauvegarde;
		
		do {
			/* Saisie du nom de la sauvegarde */
			nomFichier = saisieNom("Quel nom voulez-vous donner � la sauvegarde ? : ");
			/* Valide que le fichier peut etre cr�� */
			validationSauvegarde = sauvegardePeutEtreCree(nomFichier);
			/* Demande de confirmation pour sauvegarder */
			if (validationSauvegarde) {
				System.out.print("Confirmez-vous la sauvegarde ? (o/n) : ");
				confirmationSauvegarde = reponseValide();
			} else {
				System.out.print("Souhaitez-vous toujours sauvegarder ? (o/n) : ");
				validationSauvegarde = !reponseValide();
				confirmationSauvegarde = !validationSauvegarde;
			}
		} while(!validationSauvegarde);
		
		/* Cr�� le fichier de sauvegarde si cette derni�re est confirm�e */
		if (confirmationSauvegarde) {
			Sauvegarder.sauverPartie(nomFichier, partie, carte, nbTour, coups);
		}
	}
	
	/**
	 * Demande � l'utilisateur de saisir un nom.
	 * V�rifie si le nom est correct dans le format d'un fichier (sans caract�res sp�ciaux).
	 * @return le nom de la sauvegarde
	 */
	public static String saisieNom(String question) {
		boolean valide;
		String nom;
		
		do {
			System.out.print(question);
			nom = entree.next() + entree.nextLine();
			valide = nomFichierCorrect(nom);
			if (!valide) {
				System.out.println(MESSAGE_ERREUR_NOM_FICHIER);
			}
		} while (!valide);
		
		return nom;
	}
	
	/**
	 * D�termine si le nom du fichier est correcte en v�rifiant qu'il n'y ait pas de caract�res sp�ciaux
	 * @param nomFichier le nom de la sauvegarde
	 * @return true si nomFichier ne contient aucun caract�res sp�ciaux
	 *         false sinon
	 */
	public static boolean nomFichierCorrect(String nomFichier) {
		String caracteresSpeciaux = "/\\:*?\"<>|";
		boolean valide = true;
		
		for (int i = 0 ; i < caracteresSpeciaux.length() && valide ; i++) {
			valide = !nomFichier.contains(Character.toString(caracteresSpeciaux.charAt(i)));
		}
		
		return valide;
	}
	
	/**
	 * D�termine si le fichier existe ou non.
	 * Si le fichier existe : demande � l'utilisateur s'il souhaite l'�craser ou non
	 * @param nomFichier le nom du fichier
	 * @return true si le fichier n'existe pas o� si l'utilisateur souhaite �craser la sauvegarde existante
	 *         false sinon (la sauvegarde ne peut pas �tre cr��e)
	 */
	public static boolean sauvegardePeutEtreCree(String nomFichier) {
		File fichier = new File("sauvegarde/parties/" + nomFichier + ".data");
		boolean peutEtreCree;
		
		peutEtreCree = !fichier.exists();
		if (!peutEtreCree) {
			System.out.print("Le fichier existe d�j�. Souhaitez-vous l'�craser ? (o/n) : ");
			peutEtreCree = reponseValide();
		}

		return peutEtreCree;
	}
	
	/**
	 * Demande � l'utilisateur une r�ponse : oui ou non et d�termine laquelle a �t� r�pondue.
	 * Une r�ponse si oui si il s'agit de "o" ou "O". 
	 * Sinon c'est non.
	 * @return true si la r�ponse est oui
	 *         false sinon
	 */
	public static boolean reponseValide() {
		String reponse;
		
		reponse = entree.next() + entree.nextLine();
		return reponse.toUpperCase().trim().equals("O");
	}

	/**
	 * Lance une partie avec une nouvelle configuration
	 * et initialise l'affichage de la partie. 
	 * Fait d�rouler la partie en faisant jouer l'utilisateur.
	 */
	public static void lancerUnePartie() {
		String reponse;
		String resultat;
		boolean finDePartieForcee = false;
		
		/* Phase 1 : initialisation de la partie */
		if (nbTour == 0) {
			choisirConfiguration();
		}
		afficherCarte();

		/* Phase 2 : D�roulement d'un tour */
		for (; partie.getNbBatiments() > 0 && !finDePartieForcee; nbTour++) {
			System.out.println("/!\\ Saisissez S pour Sauvegarder et Q pour quitter /!\\");
			System.out.print("Coup " + nbTour + " => ");
			// R�cup�ration d'une r�ponse de saisie : coordonn�es ou "Q" si l'utilisateur quitte la partie
			reponse = saisieTour();	
			finDePartieForcee = reponse.equals("Q");

			if (!finDePartieForcee) {
				// Conversion des coordonn�es saisies pour pouvoir tirer
				resultat = partie.tirer(Integer.parseInt(reponse.substring(1)) - 1, reponse.charAt(0) - 65);
				// Enregistrement des coups dans la liste
				coups.add(reponse);
				if (resultat.equals("plouf")) {
					carte.put(reponse, "  o  ");
				} else {
					carte.put(reponse, "  *  ");
				}
				System.out.println(resultat + " !");
				afficherCarte();
			}
		}
		
		/* Phase 3 : Fin de la partie */
		System.out.println("Fin de partie");
		if (!finDePartieForcee) {
			System.out.println("R�sultat :\n"
					           + "Nombre de coups : " + nbTour);
			affichageListeCoups();
		}
	}
	
	/**
	 * Affiche la liste des coups fait durant une partie
	 */
	public static void affichageListeCoups() {
		System.out.println("Liste des coups :");
		for (int i = 0 ; i < coups.size() ; i++) {
			System.out.println("Coup " + i + " > " + coups.get(i));
		}
	}
	
	/**
	 * Demande � l'utilisateur de choisir une configuration parmi celles existantes.
	 * Charge une configuration.
	 */
	public static void choisirConfiguration() {
		Configuration config = new Configuration();
		String reponse;
		boolean valide;
		
		config.afficherConfig();
		do {
			System.out.print("Quelle configuration voulez-vous choisir ? : ");
			reponse = entree.next() + entree.nextLine();
			reponse = reponse.trim();
			valide = config.configEstPresente(reponse);
			if (!valide) {
				System.out.println("La configuration que vous avez saisie n'existe pas");
			}
		} while	(!valide);
		config = config.recupererConfig(reponse);
		initialisationAvecUneConfiguration(config);
	}

	
	/**
	 * Demande � l'utilisateur le nom de la partie qu'il souhaite charger.
	 * R�cup�ration des donn�es.
	 * Lancement de la partie � l'instant o� elle �tait arr�t�e.
	 */
	public static void chargerUnePartie() {
		File fichier;
		ArrayList<Object> chargement;
		
		Sauvegarder.listerParties();
		
		fichier = rechercheFichier();
		chargement = Sauvegarder.recupererPartie(fichier.getPath());
		partie = (Partie) chargement.get(0);
		carte = (HashMap<String, String>) chargement.get(1);
		nbTour = (int) chargement.get(2);
		coups = (ArrayList<String>) chargement.get(3);
		System.out.println("Partie charg�e !");
		lancerUnePartie();
	}
	
	/**
	 * Demande � l'utilisateur le fichier qu'il souhaite charger.
	 * V�rifie si le fichier existe selon plusieurs options : 
	 * - Pas de sp�cification particuli�re
	 * - Sp�cification du r�pertoire
	 * - Sp�cification de l'extension
	 * - Sp�cification du chemin (r�pertoire + extension)
	 * @return le fichier � charger.
	 */
	public static File rechercheFichier() {
		String nomFichier;
		File fichier;
		boolean fichierExiste;
		
		do {
			nomFichier = saisieNom("Quelle partie vous souhaitez charger ? : ");
			if (nomFichier.contains("sauvegarde/parties/")) {
				fichier = new File(nomFichier + ".data");
			} else if (nomFichier.contains(".data")) {
				fichier = new File("sauvegarde/parties/" + nomFichier);
			} else if (nomFichier.contains("sauvegarde/parties/") && nomFichier.contains(".data")) {
				fichier = new File(nomFichier);
			} else {
				fichier = new File("sauvegarde/parties/" + nomFichier + ".data");
			}
			fichierExiste = fichier.exists();
			if (!fichierExiste) {
				System.out.println(MESSAGE_ERREUR_CHARGEMENT);
			}
		} while(!fichierExiste);
		
		return fichier;
	}

	/**
	 * Menu du jeu : 
	 * - Lancer une partie (nouvelle partie)
	 * - Charger une partie sauvegard�e
	 * - Quitter l'application
	 * @param args non utilis�
	 */
	public static void main(String[] args) {
		String reponse;
		boolean quitter = false;
		
		System.out.println("Bienvenue dans le jeu de la bataille navale !");
		do {
			System.out.println("\nQue voulez-vous faire ?\n"
					           + "N : Lancer une nouvelle partie\n"
					           + "C : Charger une nouvelle partie\n"
					           + "Q : Quitter");
			reponse = entree.next() + entree.nextLine();
			reponse = reponse.toUpperCase().trim();
			if (reponse.equals("N")) {
				lancerUnePartie();
			} else if (reponse.equals("C")) {
				if (Sauvegarder.verifierNbPartiesACharger()) {
					chargerUnePartie();
				} else {
					System.out.println("Il n'existe aucune partie � charger.");
				}
			} else if (!reponse.equals("Q")) {
				System.out.println("Saisie incorrectez. Saisissez soit N, soit C, soit Q.");
			}
			quitter = reponse.equals("Q");
		} while (!quitter);
	}
}