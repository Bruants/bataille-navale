/**
 * Main.java
 */
package miage.bataille.affichage;

import java.util.Scanner;

import miage.bataille.Cellule;
import miage.bataille.Partie;

/**
 * @author audric.pouzelgues
 */
public class Main {


	private static String carte;

	private static Partie partie = new Partie();

	public static void actualiserCarte() {

		//TODO: Corriger la taille de la carte
		int tailleLargeur = 10,
				tailleHauteur = 10;
		
		// Nombre de cases disponibles sur la carte
		int nombreDeCases = tailleLargeur * tailleLargeur;
		
		String caractere; // caractère définissant le statut d'une cellule sur la carte
		
		StringBuilder builderCarte = new StringBuilder();
		
		// Tableau contenant toutes les cellules 
		String[] tableau = new String[nombreDeCases];
		
		// Placement des caractères à afficher dans le tableau
		for (int i = 1; i <= nombreDeCases; i++) {
			caractere = " - ";
			int ligne = i/tailleLargeur;
			int colonne = i%tailleLargeur;
			for (Cellule cellule : partie.getCellulesTirees()) {
				
				caractere = cellule.getCoordX() == colonne && cellule.getCoordY() == ligne ? " x ": caractere;
			}
			
			if (i%tailleLargeur == 0) {
				caractere += "\n";
			}
			
			tableau[i-1] = caractere;
		}
		
		for (String elt : tableau) {
			builderCarte.append(elt);
		}
		
		carte = builderCarte.toString();
		
	}

	/**
	 * @param args non utilisé
	 */
	public static void main(String[] args) {

		int x = 0, y = 0;

		actualiserCarte();

		System.out.println(carte.toString());

		System.out.println("Tirer : ");
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Saisir x : ");
		x = sc.nextInt();
		
		System.out.print("Saisir y : ");
		y = sc.nextInt();
		
		partie.tirer(x+1, y);

		actualiserCarte();
		
		System.out.println(carte.toString());


		//  rien = -
		//  tiré = x
		// touché = o



	}

}
