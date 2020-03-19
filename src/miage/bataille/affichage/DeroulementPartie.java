/**
 * Main.java																					05/03/2020
 * MIAGE Rodez 2019-2020, ni copyright, ni copyleft
 */
package miage.bataille.affichage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import miage.bataille.Batiment;
import miage.bataille.Configuration;
import miage.bataille.Partie;
import miage.bataille.Sauvegarder;

import static miage.bataille.Configuration.CHEMIN_CONFIGS_JSON;
/**
 * Programme d'execution de l'affichage dediee au jeu de la bataille navale.
 * Permet d'acceder aux differentes actions de l'application :
 * - Nouvelle partie
 * 	- Choix d'une configuration existante
 * 	- Utilisation de la configuration par défaut
 * 	- Création d'une nouvelle configuration
 * - Charger une partie
 * 	- Charger une sauvegarde et reprendre la partie là où le joueur c'était arrete
 * - Deroulement d'une partie
 * 	- Phase 1 : initialisation de la carte et placement des bateaux
 * 	- Phase 2 : Deroulement de chaque tour, tir du joueur avec en réponse : plouf, touche ou coule
 * 	- Phase 3 : fin de partie avec les resultats : nombre de coups realises et historique des coups
 * @author MIAGE L3 Rodez 2019-2020
 */
public class DeroulementPartie {

	private static final String MESSAGE_ERREUR_DE_SAISIE = "Les coordonnees "
			+ "saisies sont incorrectes. Les coordonnees ne doivent pas "
			+ "depasser la surface delimitee par la carte.\n";
	
	private static final String MESSAGE_ERREUR_NOM_FICHIER = "Le nom du fichier est incorrect. Les "
			                                                 + "caracteres suivans sont interdits : "
			                                                 + "/\\\\:*?\\\"<>|";
	
	private static final String MESSAGE_ERREUR_CHARGEMENT = "Erreur, le fichier que vous avez specifiees "
			                                                + "n'existe pas.";
	
	private static final String MESSAGE_ERREUR_CONFIGURATION = "Aucune configuration existe.";
	
	/** 
	 * Liste contenant la situation des differentes cellules 
	 * - : pas de coup
	 * * : touchee
	 * o : plouf
	 */
	private static HashMap<String, String> carte = new HashMap<String, String>();

	private static Partie partie;

	/** Largeur de la carte */
	private static int tailleLongueur;

	/** Hauteur de la carte */
	private static int tailleHauteur;
	
	/** Nombre de tour (ou coup) realisee pour la partie courante */
	public static int nbTour = 0;
	
	/** Permet de faire des saisies */
	private static Scanner entree = new Scanner(System.in);
	
	/** Liste des coups effectuees */
	private static ArrayList<String> coups = new ArrayList<String>();

	/**
	 * Dï¿½bute une partie par defaut : 
	 * 					   - initialisation des differentes zones sur la map
	 *                     - placement des batiments par le joueur
	 */
	public static void initialisationParDefaut() {
		Configuration configurationDeLaPartie;
		System.out.println("Debut du jeu\n");
		partie = new Partie();
		configurationDeLaPartie = partie.getConfiguration();
		tailleHauteur = configurationDeLaPartie.getHauteurCarte();
		tailleLongueur = configurationDeLaPartie.getLongueurCarte();
		creerNouvelleCarte();
	}

	/**
	 * Dï¿½bute une partie avec une configuration choisie
	 * 					   - initialisation des differentes zones sur la map
	 *                     - placement des batiments par le joueur
	 * @param configurationChoisie
	 */
	public static void initialisationAvecUneConfiguration(Configuration configurationChoisie) {

        if (configurationChoisie == null) {
            initialisationParDefaut();
        } else {
            System.out.println("Debut du jeu\n");
            tailleHauteur = configurationChoisie.getHauteurCarte();
            tailleLongueur = configurationChoisie.getLongueurCarte();
            /* Si nbTour > 0, alors il s'agit d'un chargement de partie, aucune donnÃ©e Ã  crÃ©er */
            if (nbTour == 0) {
                partie = new Partie(configurationChoisie);
                creerNouvelleCarte();
            }
        }
    }

	/**
	 * Afficher la carte de la bataille navale
	 */
	public static void afficherCarte() {

		char lettre = 'A';
		String cle;

		System.out.print("   ");
		/* Affiche la première ligne : les valeurs des colonnes : 1, 2, 3... */
		for (int i = 1 ; i <= tailleLongueur ; i++) {
			if (i < 10) {
				System.out.print("  " + i + "  ");
			} else {
				System.out.print("  " + i + " ");
			}
		}

		/* 
		 * Affiche les autres lignes. La 1ere colonne possèdera les lettres qui 
		 * sont les valeurs de chaque ligne 
		 */
		for (int i = 0 ; i < tailleHauteur ; i++, lettre++) {
			System.out.print("\n\n");
			/* Valeur de la colonne */
			System.out.print(lettre + "  ");
			for (int j = 1 ; j <= tailleLongueur ; j++) {
				cle = Character.toString(lettre) + j;
				System.out.print(carte.get(cle));
			}		
		}

		System.out.print("\n\n");
	}

	/**
	 * Initialise la carte vide a afficher
	 */
	public static void creerNouvelleCarte() {
		char lettre;
		// Hash contenant toutes les cellules 
		String celluleMap;

		lettre = 'A';	
		for (int i = 0 ; i < tailleHauteur ; i++, lettre++) {

			for (int j = 1 ; j <= tailleLongueur ; j++) {
				celluleMap = Character.toString(lettre) + j;
				carte.put(celluleMap, "  -  ");
			}
		}
	}

	/**
	 * Verifie la saisie de l'utilisateur pour tirer
	 * @return TRUE  -> Si : 
	 * 					- la coordonnee en abcisses est un nombre compris entre
	 * 					  0 et la largeur de la carte.
	 * 				    - et que la coordonee en ordonnee est une lettre
	 * 				      de l'alphabet.
	 * 		   FALSE -> Si : 
	 * 					- la coordonnee en abcisses n'est pas comprise
	 * 					  entre 0 et la largeur de la carte
	 * 					- la coordonnee en abcisses n'est pas un 
	 * 					  nombre
	 * 					- la coordonnee en ordonnees n'est pas
	 * 					  une lettre.
	 */
	public static boolean verifierSaisie(String coordonnee) {
		boolean valide = false; 

		// Verification de la taille et du contenu de la chaine 
		try {

			valide = coordonnee.charAt(0) >= 65 
					&& coordonnee.charAt(0) < 65 + tailleHauteur
					&& Integer.parseInt(coordonnee.substring(1)) > 0 
					&& Integer.parseInt(coordonnee.substring(1)) <= tailleLongueur;

		} catch (NumberFormatException e) {
			valide = false;
		}

		return valide;
	}


	/**
	 * Saisie lors d'un tour : sauvegarde / quitter la partie ou tir sur la carte :
	 * - Demande une lettre pour la colonne
	 * - Demande un nombre entier positif pour la ligne
	 * @return la reponse saisie : une coordonnee ou "Q" si l'utilisateur quitte la partie
	 */
	public static String saisieTour() {
		String reponse;
		boolean valide = false;

		do {		

			System.out.print("Saisir une coordonnee : ");
			reponse = entree.next() + entree.nextLine();

			reponse = reponse.toUpperCase().trim();

			// Message d'erreur si les coordonnees sont non valides
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
	 * Determine si l'utilisateur souhaite vraiment quitter la partie.
	 * Si l'utilisateur quitte la partie, une sauvegarde lui est proposee.
	 * @return true si l'utilisateur quitte la partie
	 *         false sinon
	 */
	public static boolean quitterPartie() {
		boolean quitter;
		
		quitter = reponseValide("Voulez-vous vraiment quitter la partie ?");
		if (quitter) {
			
			if (reponseValide("Souhaitez-vous sauvegarder avant de quitter la partie ?")) {
				effectuerSauvegarde();
			}
		}
		
		return quitter;
	}
	
	/**
	 * Traitement de la sauvegarde :
	 * 	- Saisie du nom de la sauvegarde, verification et validation par l'utilisateur
	 *  - Creation de la sauvegarde
	 */
	public static void effectuerSauvegarde() {
		String nomFichier;
		boolean validationSauvegarde;
		boolean confirmationSauvegarde;
		
		do {
			/* Saisie du nom de la sauvegarde */
			nomFichier = saisieNom("Quel nom voulez-vous donner a la sauvegarde ? : ", false);
			/* Valide que le fichier peut etre cree */
			validationSauvegarde = sauvegardePeutEtreCree(nomFichier);
			/* Demande de confirmation pour sauvegarder */
			if (validationSauvegarde) {
				confirmationSauvegarde = reponseValide("Confirmez-vous la sauvegarde ?");
			} else {
				validationSauvegarde = !reponseValide("Souhaitez-vous toujours sauvegarder ?");
				confirmationSauvegarde = !validationSauvegarde;
			}
		} while(!validationSauvegarde);
		
		/* Crï¿½ï¿½ le fichier de sauvegarde si cette derniere est confirmee */
		if (confirmationSauvegarde) {
			Sauvegarder.sauverPartie(nomFichier, partie, carte, nbTour, coups);
		}
	}
	
	/**
	 * Demande a l'utilisateur de saisir un nom.
	 * Verifie si le nom est correct dans le format d'un fichier (sans caractï¿½res spï¿½ciaux).
	 * @param question La question a poser pour la saisie
	 * @param autoriseSlash si true les caracteres / sont autorises sinon ils ne le sont pas.
	 * @return le nom de la sauvegarde
	 */
	public static String saisieNom(String question, boolean autoriseSlash) {
		boolean valide;
		String nom;
		
		do {
			System.out.print(question);
			nom = entree.next() + entree.nextLine();
			valide = nomFichierCorrect(nom, autoriseSlash);
			if (!valide) {
				System.out.println(MESSAGE_ERREUR_NOM_FICHIER);
			}
		} while (!valide);
		
		return nom;
	}
	
	/**
	 * Determine si le nom du fichier est correcte en verifiant qu'il n'y ait pas de caracteres speciaux
	 * @param nomFichier le nom de la sauvegarde
	 * @param autoriseSlash si true alors le caractere / est autorisee sinon il n'est pas autorise
	 * @return true si nomFichier ne contient aucun caracteres speciaux
	 *         false sinon
	 */
	public static boolean nomFichierCorrect(String nomFichier, boolean autoriseSlash) {
		String caracteresSpeciaux = "\\:*?\"<>|";
		boolean valide = true;
		
		if (!autoriseSlash) {
			caracteresSpeciaux += "/";
		}
		
		for (int i = 0 ; i < caracteresSpeciaux.length() && valide ; i++) {
			valide = !nomFichier.contains(Character.toString(caracteresSpeciaux.charAt(i)));
		}
		
		return valide;
	}
	
	/**
	 * Determine si le fichier existe ou non.
	 * Si le fichier existe : demande a l'utilisateur s'il souhaite l'ecraser ou non
	 * @param nomFichier le nom du fichier
	 * @return true si le fichier n'existe pas ou si l'utilisateur souhaite ecraser la sauvegarde existante
	 *         false sinon (la sauvegarde ne peut pas etre creee)
	 */
	public static boolean sauvegardePeutEtreCree(String nomFichier) {
		File fichier = new File("sauvegarde/parties/" + nomFichier + ".data");
		boolean peutEtreCree;
		
		peutEtreCree = !fichier.exists();
		if (!peutEtreCree) {
			peutEtreCree = reponseValide("Le fichier existe deja. Souhaitez-vous l'ecraser ?");
		}

		return peutEtreCree;
	}
	
	/**
	 * Demande a l'utilisateur une reponse : oui ou non et determine laquelle a etre repondue.
	 * Une reponse si oui si il s'agit de "o" ou "O". 
	 * Sinon c'est non.
	 * @param question La question posee a l'utilisateur auquel il doit repondre oui ou non
	 * @return true si la reponse est oui
	 *         false sinon
	 */
	public static boolean reponseValide(String question) {
		String reponse;
		
		System.out.println(question + " (o/n) : ");
		reponse = entree.next() + entree.nextLine();
		return reponse.toUpperCase().trim().equals("O");
	}

	/**
	 * Lance une partie avec une nouvelle configuration
	 * et initialise l'affichage de la partie. 
	 * Fait derouler la partie en faisant jouer l'utilisateur.
	 */
	public static void lancerUnePartie() {
		String reponse;
		String resultat;
		boolean finDePartieForcee = false;
		
		/* Phase 1 : initialisation de la partie */
		if (nbTour == 0) {
			choisirConfiguration();
			partie.placementFlotteAuto(partie.getConfiguration().getFlotte());
		} else {
			initialisationAvecUneConfiguration(partie.getConfiguration());
		}
		afficherCarte();
		

		/* Phase 2 : Deroulement d'un tour */
		for (; partie.getNbBatiments() > 0 && !finDePartieForcee; nbTour++) {
			System.out.println("/!\\ Saisissez S pour Sauvegarder et Q pour quitter /!\\");
			System.out.print("Coup " + (nbTour+1) + " => ");
			// Recuperation d'une reponse de saisie : coordonnees ou "Q" si l'utilisateur quitte la partie
			reponse = saisieTour();	
			finDePartieForcee = reponse.equals("Q");

			if (!finDePartieForcee) {
				// Conversion des coordonnees saisies pour pouvoir tirer
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
			System.out.println("Resultat :\n"
					           + "Nombre de coups : " + nbTour);
			affichageListeCoups();
		} else {
			nbTour = 0;
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
	 * Demande a l'utilisateur de choisir une configuration parmi celles existantes.
	 * Charge une configuration.
	 */
	public static void choisirConfiguration() {
		Configuration config;
		String reponse,
		       affichageConfig;
		boolean valide;
		
		affichageConfig = Configuration.afficherConfig();
		if (affichageConfig != null && affichageConfig.length() > 0) {
			System.out.println("Liste des configurations disponibles :");
			System.out.println(affichageConfig);
		} else {
			System.out.println(MESSAGE_ERREUR_CONFIGURATION);
		}
		do {
			System.out.println("/!\\ Commande pour utiliser la configuration par defaut : D /!\\");
			System.out.println("/!\\ Commande pour creer une nouvelle configuration : N /!\\");
			System.out.print("Quelle configuration voulez-vous choisir ? : ");
			reponse = entree.next() + entree.nextLine();
			reponse = reponse.trim();
			if (reponse.equals("N")) {
				reponse = nouvelleConfiguration();
				valide = true;
			} else if (reponse.equals("D")) {
				config = new Configuration();
				valide = true;
			} else {
				valide = Configuration.configEstPresente(reponse);
			}
			if (!valide) {
				if (affichageConfig != null && affichageConfig.length() > 0) {
					System.out.println("\nLa configuration que vous avez saisie n'existe pas\n");
				} else {
					System.out.println("\nAucune configuration existe, veuillez prendre la configuration par "
					           + "defaut ou en creer une nouvelle.\n"); 
				}
			}
		} while	(!valide);
		config = Configuration.recupererConfig(reponse);
		initialisationAvecUneConfiguration(config);
	}
	/**
	 * Menu qui permet de gérer les configurations
	 */
	public static void gestionConfiguration() {
		String reponse;
		boolean quitter = false;
		
		do {
			System.out.println("\nQue voulez-vous faire ?\n"
					           + "N : Créer une configuration\n"
					           + "C : Supprimer une configuration\n"
					           + "D : Afficher les configurations\n"
					           + "Q : Quitter");
			reponse = entree.next() + entree.nextLine();
			reponse = reponse.toUpperCase().trim();
			if (reponse.equals("N")) {
				nouvelleConfiguration();
			} else if (reponse.equals("C")) {
				supprimerConfiguration();
			} else if (reponse.equals("D")) {
				System.out.println(Configuration.afficherConfig());
			} else if (!reponse.equals("Q")) {
				System.out.println("Saisie incorrectez. Saisissez soit N, soit C, soit D, soit Q.");
			}
			quitter = reponse.equals("Q");
		} while (!quitter);
	}
	
	/**
	 * Demande a l'utilisateur de choisir une configuration parmi celles existantes.
	 * Supprime la configuration choisie
	 */
	public static void supprimerConfiguration() {
		Configuration config;
		String reponse,
		       affichageConfig;
		boolean valide;
		
		affichageConfig = Configuration.afficherConfig();
		if (affichageConfig != null && affichageConfig.length() > 0) {
			System.out.println("Liste des configurations disponibles :");
			System.out.println(affichageConfig);
		} else {
			System.out.println(MESSAGE_ERREUR_CONFIGURATION);
		}
		do {
			System.out.println("Entrez le nom de la configuration a supprimer (N pour annuler) : ");
			reponse = entree.next() + entree.nextLine();
			reponse = reponse.trim();
			if (reponse.equals("N")) {
				valide = true;
			} else {
				valide = Configuration.configEstPresente(reponse);
				if(valide) {
					Configuration.supprimerConfig(reponse);
					Configuration.enregistrerConfig(CHEMIN_CONFIGS_JSON);
					System.out.println("Configuration " + reponse + " supprimee.");
				}
			}
		} while	(!valide);
	}
	
	/**
	 * Cree une nouvelle configuration dans le fichier de sauvegarde
	 * selon les donnees entrees par le configurateur
	 */
	private static String nouvelleConfiguration()
	{
		boolean valide = false;
		int nbTypesBatiments = 0, 
			nbBatiments = 0, 
			tailleBatiment = 0, 
			hauteurCarte = 0, 
			longueurCarte = 0;
		String nomConfig = "",
			   typeBatiment = "";
		ArrayList<Batiment> flotte = new ArrayList<Batiment>();
		
		while(!valide) {
			System.out.print("\nCreation d'une nouvelle configuration de jeu.\n"
					+ "Nom de la nouvelle configuration : ");
			nomConfig = entree.next() + entree.nextLine();
			
			System.out.println("\nEntrez la taille de la carte.");
			
			// controle de saisie de la hauteur de la carte
			System.out.print("Nombre de lignes : ");
			valide = false;
			while(!valide) {
				if(entree.hasNextInt()) {
					hauteurCarte = entree.nextInt();
					if(hauteurCarte > 0 && hauteurCarte <= 26) {
						valide = true;
					} else {
						System.out.println("Veuillez entrer un entier entre 1 et 26.");
					}
				} else {
					System.out.println("Veuillez entrer un entier.");
				}
				entree.nextLine();
			}
			
			// controle de saisie de la longueur de la carte
			System.out.print("Nombre de colonnes : ");
			valide = false;
			while(!valide) {
				if(entree.hasNextInt()) {
					longueurCarte = entree.nextInt();
					if(longueurCarte > 0 && longueurCarte <= 26) {
						valide = true;
					} else {
						System.out.println("Veuillez entrer un entier entre 1 et 26.");
					}
				} else {
					System.out.println("Veuillez entrer un entier.");
				}
				entree.nextLine();
			}
			
			// controle de saisie du nombre de types de batiments
			System.out.print("Combien y aura-t-il de types de batiments ? ");
			valide = false;
			while(!valide) {
				if(entree.hasNextInt()) {
					nbTypesBatiments = entree.nextInt();
					if(nbTypesBatiments > 0) {
						valide = true;
					} else {
						System.out.println("Veuillez entrer un entier suparieur a  0.");
					}
				} else {
					System.out.println("Veuillez entrer un entier.");
				}
				entree.nextLine();
			}
			
			System.out.println("Pour chaque type de batiment, indiquer le nom du "
					           + "batiment, sa taille et l'effectif de ce type de batiment:");
			
			for(int idType = 0; idType < nbTypesBatiments; idType++) {
				
				System.out.println("Type de batiment 1:");
				System.out.print("nom: ");
				typeBatiment = entree.next() + entree.nextLine();
				
				// controle de saisie taille des batiments
				System.out.print("taille: ");
				valide = false;
				while(!valide) {
					if(entree.hasNextInt()) {
						tailleBatiment = entree.nextInt();
						if(tailleBatiment > 0) {
							valide = true;
						} else {
							System.out.println("Veuillez entrer un entier superieur a  0.");
						}
					} else {
						System.out.println("Veuillez entrer un entier");
					}
					entree.nextLine();
				}
				
				// controle de saisie effectif des batiments
				System.out.print("effectif: ");
				valide = false;
				while(!valide) {
					if(entree.hasNextInt()) {
						nbBatiments = entree.nextInt();
						if(nbBatiments > 0) {
							valide = true;
						} else {
							System.out.println("Veuillez entrer un entier superieur a  0.");
						}
					} else {
						System.out.println("Veuillez entrer un entier.");
					}
					entree.nextLine();
				}
				
				// creation des batiments un par un
				for(int idBatiment = 0; idBatiment < nbBatiments; idBatiment++) {
					flotte.add(new Batiment(tailleBatiment, (typeBatiment + " " + idBatiment)));
				}
			}
			
			valide = false;
			try {
				Configuration.ajouterConfig(new Configuration(longueurCarte, hauteurCarte, nomConfig, flotte));
				Configuration.enregistrerConfig(CHEMIN_CONFIGS_JSON);
				valide = true;
			} catch(IllegalArgumentException e) {
				System.out.println("Erreur: Format de la carte incorrect.");
				System.out.println(e.getMessage());
			}
			if(!valide) {
				System.out.println("Configuration invalide, veuillez recommencer la saisie.");
			}
		}
		return nomConfig;
	}
	
	/**
	 * Demande a l'utilisateur le nom de la partie qu'il souhaite charger.
	 * Recuperation des donnees.
	 * Lancement de la partie a l'instant ou elle etait arretee.
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
		System.out.println("Partie chargee !");
		lancerUnePartie();
	}
	
	/**
	 * Demande ï¿½ l'utilisateur le fichier qu'il souhaite charger.
	 * Verifie si le fichier existe selon plusieurs options : 
	 * - Pas de specification particuliere
	 * - Specification du repertoire
	 * - Specification de l'extension
	 * - Specification du chemin (repertoire + extension)
	 * @return le fichier a charger.
	 */
	public static File rechercheFichier() {
		String nomFichier;
		File fichier;
		boolean fichierExiste;
		
		do {
			nomFichier = saisieNom("Quelle partie vous souhaitez charger ? : ", true);
			if (nomFichier.contains("sauvegarde/parties/") && nomFichier.contains(".data")) {
				fichier = new File(nomFichier);
			} else if (nomFichier.contains("sauvegarde/parties/")) {
				fichier = new File(nomFichier + ".data");
			} else if (nomFichier.contains(".data")) {
				fichier = new File("sauvegarde/parties/" + nomFichier);
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
	 * - Charger une partie sauvegardee
	 * - Quitter l'application
	 * @param args non utilisee
	 */
	public static void main(String[] args) {
		String reponse;
		boolean quitter = false;
		
		System.out.println("Bienvenue dans le jeu de la bataille navale !");
		do {
			System.out.println("\nQue voulez-vous faire ?\n"
					           + "N : Lancer une nouvelle partie\n"
					           + "C : Charger une nouvelle partie\n"
					           + "D : Gérer les configurations\n"
					           + "Q : Quitter");
			reponse = entree.next() + entree.nextLine();
			reponse = reponse.toUpperCase().trim();
			if (reponse.equals("N")) {
				lancerUnePartie();
			} else if (reponse.equals("C")) {
				if (Sauvegarder.verifierNbPartiesACharger()) {
					chargerUnePartie();
				} else {
					System.out.println("Il n'existe aucune partie a charger.");
				}
			} else if (reponse.equals("D")) {
				gestionConfiguration();
			} else if (!reponse.equals("Q")) {
				System.out.println("Saisie incorrectez. Saisissez soit N, soit C, soit D, soit Q.");
			}
			quitter = reponse.equals("Q");
		} while (!quitter);
	}
}