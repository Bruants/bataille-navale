/**
 * Main.java
 */
package miage.bataille.affichage;

import java.util.HashMap;
import java.util.Scanner;

import miage.bataille.Cellule;
import miage.bataille.Partie;

/**
 * @author Audric POUZELGUES, Damien AVETTA-RAYMOND
 */
public class Main {

	private static HashMap<String, String> map = new HashMap<String, String>();

	private static Partie partie = new Partie();
	
	//TODO: Remplacer les tailles
	private static int tailleLargeur = 5;
	
	private static int tailleHauteur = 5;
	
	/**
	 * Afficher la carte de la bataille navale
	 */
	public static void afficherCarte() {
		
		char lettre = 'A';
		String cle = "";
		
		for (int i = 0 ; i < tailleLargeur ; i++, lettre++) {
			for (int j = 1 ; j <= tailleHauteur ; j++) {
				
				cle = lettre + "" + j;
				System.out.print(map.get(cle) + " ");
			}
			
			System.out.print("\n");
		}
	}

	/**
	 * Initialise la carte à afficher
	 */
	public static void creerCarte() {
		// Nombre de cases disponibles sur la carte
		int nombreDeCases;
		char lettre;
		// Hash contenant toutes les cellules 
		String celluleMap;
		
		lettre = 'A';	
		nombreDeCases = tailleLargeur * tailleLargeur;
		
		for (int i = 0 ; i < tailleLargeur ; i++, lettre++) {
			for (int j = 1 ; j <= tailleHauteur ; j++) {
				celluleMap = Character.toString(lettre) + j;
				map.put(celluleMap, " - ");
			}
		}
	}
	
	
	/**
	 * Saisie d'un tir sur la carte
	 * - Demande une lettre pour la colonne
	 * - Demande un nombre entier positif pour la ligne
	 */
	public static String saisieTir() {
		
		String coordonnee;
		
		Scanner entree = new Scanner(System.in);
		int ligne = 0;
		char colonne = 0;
		
		boolean valide;
		
		do {		
			
			System.out.print("Saisir une coordonnée : ");
			coordonnee = entree.next() + entree.nextLine();
			
			// Vérification de la taille de la chaine 
			valide = coordonnee.length() == 2 && coordonnee.charAt(0) >= 65 && coordonnee.charAt(0) < 65 + tailleLargeur
					&& coordonnee.charAt(1) >= 49 && coordonnee.charAt(1) <= 49 + tailleHauteur;
			
		} while (!valide);
		
		return coordonnee;
	}
	

	/**
	 * @param args non utilisé
	 */
	public static void main(String[] args) {
		
		String coordonnee;
		
		creerCarte();
		afficherCarte();
		coordonnee = saisieTir();

//		System.out.println("Tirer : ");
//		Scanner sc = new Scanner(System.in);
//		
//		System.out.print("Saisir la colonne : ");
//		x = sc.next().charAt(0);
//		
//		System.out.print("Saisir la ligne : ");
//		y = sc.nextInt();
				
		map.put(coordonnee, " x ");		
		partie.tirer(coordonnee.charAt(0) - 65, coordonnee.charAt(1) - 1);
		
		System.out.println(Integer.parseInt(Character.toString(coordonnee.charAt(1))));
		
		afficherCarte();

		//System.out.println(map.toString());
		//  rien = -
		//  tiré = x
		// touché = o



	}

}
