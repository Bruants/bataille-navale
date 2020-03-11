package miage.bataille;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllegalFormatWidthException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Correspond � une configuration de la partie
 * de bataille navale
 * 
 * @author alexis vivier
 *
 */
public class Configuration {

	/**
	 * D�finit la longueur maximale de la mer
	 */
	private static final int LONGUEUR_MAX = 26;
	
	/**
	 * Definit la hauteur maximale de la mer
	 */
	private static final int HAUTEUR_MAX = 26;
	
	/** Longueur maximale de la carte d�finit sur X */
	private int longueurCarte;

	/** Hauteur de la carte d�finit */
	private int hauteurCarte;

	/** Stocke la flotte */
	private ArrayList<Batiment> flotte;
	
	/** Le nom de la config */
	private String nom;
	
	/** Toutes les configurations charg�es */
	private static ArrayList<Configuration> listeDeConfigs = chargerConfig("./src/configs.json");

	/** 
	 * Cr�ation d'une configuration de base
	 */
	public Configuration() {
		this(12, 12, "D�faut", new ArrayList<Batiment>());

		flotte.add(new Batiment(4, "porte-avion"));
		flotte.add(new Batiment(3, "croiseur"));
		flotte.add(new Batiment(3, "croiseur"));
		flotte.add(new Batiment(2, "sous-marin"));
		flotte.add(new Batiment(2, "sous-marin"));
		flotte.add(new Batiment(2, "sous-marin"));
		flotte.add(new Batiment(1, "vedette"));
		flotte.add(new Batiment(1, "vedette"));
		flotte.add(new Batiment(1, "vedette"));
		flotte.add(new Batiment(1, "vedette"));
		
	}

	/**
	 * Cr�e une configuration definit par la longueur, la hauteur et 
	 * les batiments definissant les flottes 
	 * @param longueurCarte longueur x de la carte
	 * @param hauteurCarte hauteur y de la carte
	 * @param nom Le nom de la configuration
	 * @param batFlotte batiments � ajouter a la flotte sous forme de tableau
	 */
	public Configuration(int longueurCarte, int hauteurCarte, String nom, Batiment...batFlotte) {
		this( longueurCarte, hauteurCarte, nom, new ArrayList<Batiment>(Arrays.asList(batFlotte)) );	
	}
	
	/**
	 * Cr�e une configuration definit par la longueur, la hauteur et 
	 * les batiments definissant les flottes 
	 * @param longueurCarte longueur x de la carte
	 * @param hauteurCarte hauteur y de la carte
	 * @param nom Le nom de la configuration
	 * @param batFlotte batiments � ajouter a la flotte sous forme de liste
	 * @exception IllegalFormatWidthException Taille de la longueur ou hauteur innatendu
	 */
	public Configuration(int longueurCarte, int hauteurCarte, String nom, ArrayList<Batiment> batFlotte) throws IllegalFormatWidthException{
		/* Tests si les param�tres donn�s sont dans les intervalles */
		if (longueurCarte > LONGUEUR_MAX || longueurCarte <= 0) {
			throw new IllegalFormatWidthException(longueurCarte);
		}
		if(hauteurCarte > HAUTEUR_MAX || hauteurCarte <= 0) {
			throw new IllegalFormatWidthException(hauteurCarte);
		}
		
		this.longueurCarte = longueurCarte;
		this.hauteurCarte = hauteurCarte;
		this.nom = nom;
		this.flotte = batFlotte;
	}

	/**
	 * @return la longueur x de la carte
	 */
	public int getLongueurCarte() {
		return longueurCarte;
	}

	/**
	 * Modifie la longueur de carte courante
	 * @param longueurCarte nouvelle longueur de carte
	 */
	public void setLongueurCarte(int longueurCarte) {
		this.longueurCarte = longueurCarte;
	}

	/**
	 * @return la hauteur y de la carte
	 */
	public int getHauteurCarte() {
		return hauteurCarte;
	}

	/**
	 * Modifie la haute de la carte courante
	 * @param hauteurCarte nouvelle hauteur de la carte
	 */
	public void setHauteurCarte(int hauteurCarte) {
		this.hauteurCarte = hauteurCarte;
	}

	/**
	 * @return renvois la flotte des �quipes
	 */
	public ArrayList<Batiment> getFlotte() {
		return flotte;
	}

	/**
	 * Modifie la flotte courante
	 * @param flotte nouvelle flotte de la configuration courante
	 */
	public void setFlotte(ArrayList<Batiment> flotte) {
		this.flotte = flotte;
	}

	/**
	 * R�cup�re les configurations stockees dans le fichier chemin
	 * Chaque configuration poss�de les informations suivantes :
	 * 	- nom (String)
	 *  - carte (Object)
	 *  	- longueurCarte (int)
	 *  	- largeurCarte (int)
	 *  - flotte (Array of Object)
	 *  	- taille (int)
	 *  	- nom (String)
	 * Au format suivant :
	 * {
	 *	    "config" : [
	 *	        {
	 *	            "nom" : "Config1",
	 *	            "carte" : { 
	 *	                "longueurCarte":40,
	 *	                "largeurCarte":30
	 *	            },
	 *				"flotte" : [
	 *	                {"taille": 4, "nom": "porte-avion"},
	 *	                {"taille": 3, "nom": "croiseur"},
	 *	                ...
	 *	            ]
	 *	        },
	 *	        {
	 *	            "nom" : "Config2",
	 *	            "carte" : { 
	 *	                "longueurCarte":10,
	 *	                "largeurCarte":20
	 *	            },
	 *	            "flotte" : [
	 *	                {"taille": 9, "nom": "porte-croiseur"},
	 *	                {"taille": 3, "nom": "croiseur"},
	 *	                ...
	 *	            ]
	 *	        }
	 *	    ]
	 *	}
	 * @param chemin Le path vers le fichier de configuration
	 * @return Une liste qui contient toutes les configurations du fichier
	 */
	public static ArrayList<Configuration> chargerConfig(String chemin) {
		ArrayList<Configuration> aCharger = new ArrayList<Configuration>();
		
		JSONParser parser = new JSONParser();
		try {
			FileReader reader = new FileReader(chemin); // Lecture du fichier
			JSONObject jsonObject = (JSONObject) parser.parse(reader); // Parse en JSONObject
			JSONArray array = (JSONArray) jsonObject.get("config"); // On r�cup�re les diff�rentes configs
			// On parcourt toutes les configs
			for (Object elt : array) {
				long largeurCarte;
				long longueurCarte;
				String nom;
				ArrayList<Batiment> flotte = new ArrayList<Batiment>();
				JSONObject config = (JSONObject) elt; // Parse en json
				// R�cup�re les informations
				nom = (String)config.get("nom");
				largeurCarte = (long)((JSONObject)config.get("carte")).get("largeurCarte");
				longueurCarte = (long)((JSONObject)config.get("carte")).get("longueurCarte");
				// On cr�ee tous les bateaux
				for(Object bateau : (JSONArray)config.get("flotte")) {
					JSONObject bateauJSON = (JSONObject) bateau; // Le JSON du bateau
					flotte.add(new Batiment((int)(long)bateauJSON.get("taille"), (String)bateauJSON.get("nom"))); // Ajout � la flotte
				}
				aCharger.add(new Configuration((int)longueurCarte,(int)largeurCarte, nom, flotte));
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aCharger;
	}
	
	/**
	 * Ajoute la configuration aux liste de configuration
	 * @param aAjouter La configuration qui sera ajout�e
	 */
	public void ajouterConfig(Configuration aAjouter) {
		listeDeConfigs.add(aAjouter);
	}
	
	
	/**
	 * Parcours la liste listeDeConfigs et dit si la config est pr�sente ou non
	 */
	public boolean configEstPresente() {
		return false;
	}
}
