package miage.bataille;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IllegalFormatWidthException;
import java.util.Iterator;
import java.util.Set;

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
public class Configuration implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 973882853661990838L;

	/**
	 * D�finit la longueur maximale de la mer
	 */
	public static final int LONGUEUR_MAX = 26;
	
	/**
	 * Definit la hauteur maximale de la mer
	 */
	public static final int HAUTEUR_MAX = 26;
	
	/** Longueur maximale de la carte d�finit sur X */
	private int longueurCarte;

	/** Hauteur de la carte d�finit */
	private int hauteurCarte;

	/** Stocke la flotte */
	private ArrayList<Batiment> flotte;
	
	/** Le nom de la config */
	private String nom;
	
	/** Toutes les configurations charg�es */
	private static HashMap<String,Configuration> listeDeConfigs = chargerConfig("./src/configs.json");

	/** 
	 * Cr�ation d'une configuration de base
	 */
	public Configuration() {

		this(12, 12, "D�faut", new ArrayList<Batiment>());

		flotte.add(new Batiment(4, "porte-avion"));
		flotte.add(new Batiment(3, "croiseur"));
		flotte.add(new Batiment(2, "sous-marin"));
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
	 * @exception IllegalFormatWidthException Taille de la longueur ou hauteur innatendu
	 */
	public Configuration(int longueurCarte, int hauteurCarte, String nom, Batiment...batFlotte) throws IllegalArgumentException{
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
	public Configuration(int longueurCarte, int hauteurCarte, String nom, ArrayList<Batiment> batFlotte) throws IllegalArgumentException{
		int tailleFlotte;
		
		/* Tests si les param�tres donn�s sont dans les intervalles */
		if (longueurCarte > LONGUEUR_MAX || longueurCarte <= 0) {
			throw new IllegalFormatWidthException(longueurCarte);
		}
		if(hauteurCarte > HAUTEUR_MAX || hauteurCarte <= 0) {
			throw new IllegalFormatWidthException(hauteurCarte);
		}
		
		/* Tests si les bateaux soient tous posable */
		tailleFlotte = 0;
		for(Batiment bat:batFlotte) { tailleFlotte += bat.getTailleLgr(); }
		
		if(longueurCarte*hauteurCarte < tailleFlotte) {
			throw new IllegalArgumentException("Taille disponible : " + longueurCarte*hauteurCarte 
					+ " taille de la flotte : " + tailleFlotte);
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
	 *  	- hauteurCarte (int)
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
	 *	                "hauteurCarte":30
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
	 *	                "hauteurCarte":20
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
	public static HashMap<String,Configuration> chargerConfig(String chemin) {
		HashMap<String,Configuration> aCharger = new HashMap<String,Configuration>();
		File fichierJson = new File(chemin);
		FileReader reader;
		FileWriter writer;
		JSONObject jsonObject,
		           config;
		JSONArray array;
		long hauteurCarte,
		     longueurCarte;
		String nom;
		ArrayList<Batiment> flotte;
		JSONParser parser = new JSONParser();;
		
		try {
			if (!fichierJson.exists()) {
				fichierJson.createNewFile();
				writer = new FileWriter(fichierJson);
				writer.write("{\r\n" + 
						     "    \"config\" : []\r\n" + 
							 "}");
				writer.close();
			}
			reader = new FileReader(fichierJson); // Lecture du fichier
			jsonObject = (JSONObject) parser.parse(reader); // Parse en JSONObject
			array = (JSONArray) jsonObject.get("config"); // On r�cup�re les diff�rentes configs
			reader.close(); // fermeture du fichier
			// On parcourt toutes les configs
			for (Object elt : array) {
				config = (JSONObject) elt; // Parse en json
				flotte = new ArrayList<Batiment>();
				
				// R�cup�re les informations
				nom = (String)config.get("nom");
				hauteurCarte = (long)((JSONObject)config.get("carte")).get("hauteurCarte");
				longueurCarte = (long)((JSONObject)config.get("carte")).get("longueurCarte");
				// On cr�ee tous les bateaux
				for(Object bateau : (JSONArray)config.get("flotte")) {
					JSONObject bateauJSON = (JSONObject) bateau; // Le JSON du bateau
					flotte.add(new Batiment((int)(long)bateauJSON.get("taille"), (String)bateauJSON.get("nom"))); // Ajout � la flotte
				}
				try {
					aCharger.put(nom,new Configuration((int)longueurCarte,(int)hauteurCarte, nom, flotte));
				} catch (IllegalFormatWidthException e) {
					System.out.println("Probl�me dans les tailles de la configuration " + e.getMessage());
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
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
     * Recup�re la config pass�e en param�tre
     * @param config La config que l'on veut r�cup�rer
     * @return L'objet configuration que l'on veut
     */
    public static Configuration recupererConfig(String config) {
    	return listeDeConfigs.get(config);
    }
    
    /**
     * Recup�re toutes les configs pass�es en param�tre
     * @param config La config que l'on veut r�cup�rer
     * @return Toutes les configurations enregsitr�es
     */
    public static HashMap<String,Configuration> recupererToutesLesConfigs() {
    	return listeDeConfigs;
    }
    
    /**
     * Affiche les noms de toutes les configurations existantes.
     */
    public static String afficherConfig() {
    	StringBuilder aRenvoyer = new StringBuilder();
    	Object[] keys = listeDeConfigs.keySet().toArray();
    	for (int i = 0 ; i < listeDeConfigs.size() ; i++) {
    		aRenvoyer.append(listeDeConfigs.get(keys[i]).nom + '\n');
    	}
    	return aRenvoyer.toString();
    }
	
	/**
	 * Enregistre listeDeConfigs dans le path pass� en param�tre 
	 * Chaque configuration poss�de les informations suivantes :
	 * 	- nom (String)
	 *  - carte (Object)
	 *  	- longueurCarte (int)
	 *  	- hauteurCarte (int)
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
	 *	                "hauteurCarte":30
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
	 *	                "hauteurCarte":20
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
	 */
	public static void enregistrerConfig(String chemin) {		
		JSONObject configs = new JSONObject();
		Set<String> cles = listeDeConfigs.keySet();
		Iterator<String> i = cles.iterator();
		JSONArray base = new JSONArray();

		try {
			// On parcourt toutes les configs
			while (i.hasNext()) {
				Configuration elt = listeDeConfigs.get(i.next());
				
				JSONObject config = new JSONObject(); // Contient la config en cours
				// Enregistre les feuilles de premier niveau (nom, longueurCarte, hauteurCarte)
				config.put("nom", elt.nom);
				JSONObject carte = new JSONObject();
				carte.put("longueurCarte", elt.longueurCarte);
				carte.put("hauteurCarte", elt.hauteurCarte);
				config.put("carte", carte);

				// Enregistre les batiments
				JSONArray flotteArray = new JSONArray();
				for(Batiment bateau : elt.flotte) {
					// Cr�e l'objet bateau
					JSONObject bateauObj = new JSONObject();
					bateauObj.put("taille",bateau.tailleLgr);
					bateauObj.put("nom",bateau.nom);
					flotteArray.add(bateauObj); // Enregistre le bateau
				}
				config.put("flotte", flotteArray);
				// Enregsitre la config
				base.add(config);
			}
			configs.put("config",base);
			// Ecriture de toutes les configs
			FileWriter writer = new FileWriter(chemin);
			writer.write(configs.toJSONString());
			writer.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ajoute la configuration aux liste de configuration
	 * @param aAjouter La configuration qui sera ajout�e
	 */
	public static void ajouterConfig(Configuration aAjouter) {
		listeDeConfigs.put(aAjouter.nom,aAjouter);
	}
	
	
	/**
	 * Parcours la liste listeDeConfigs et dit si la config est pr�sente ou non
	 * @param nom le nom de la config recherch�e
	 * @return true si la config existe
	 *         false sinon
	 */
	public static boolean configEstPresente(String nom) {
		int i;
		Object[] keys = listeDeConfigs.keySet().toArray();
		
		for (i = 0 ; i < listeDeConfigs.size() && !listeDeConfigs.get(keys[i]).nom.equals(nom) ; i++);
		
		return listeDeConfigs.size() > 0 && i != listeDeConfigs.size() 
			   && listeDeConfigs.get(keys[i]).nom.equals(nom);
	}
}
