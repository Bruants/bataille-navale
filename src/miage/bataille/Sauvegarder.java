/*
 * Sauvegarde.java 
 */
package miage.bataille;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Sauvegarde les parties
 * @author k.sannac
 */
public class Sauvegarder {
	
	private static final String MESSAGE_ERREUR_CREATION_DIR = "Impossible de cr�er les r�pertoires de sauvegarde.";
	
	private static final String MESSAGE_ERREUR_SAUVEGARDE = "La sauvegarde c'est mal effectu�e";

    /**
     * Sauvegarde une partie dans le dossier suivant : 
     * sauvegarde/parties/
     * avec pour extension : .data
     * @param nomSauvegarde le nom du fichier de sauvegarde
     * @param aSauvegarder tous les �l�ments qui sont � sauvegarder
     */
    public static void sauverPartie(String nomSauvegarde, Object... aSauvegarder) {

        File fichier = new File("sauvegarde/parties/" + nomSauvegarde + ".data");
        File directory = new File("sauvegarde/parties");
        boolean repertoireExistant;
        
        repertoireExistant = directory.exists() || directory.mkdirs();
        if (!repertoireExistant) {
    		System.out.println(MESSAGE_ERREUR_CREATION_DIR);
    	} else {
	        try {
	        	if (fichier.exists()) {
	        		fichier.delete();
	        	}
	            ObjectOutputStream save = new ObjectOutputStream (new FileOutputStream (fichier));
	            for (Object objet : aSauvegarder) {
	            	save.writeObject (objet);
	            }
	            save.close();
	            System.out.println("La sauvegarde c'est bien effectu�e");
	        }
	        catch (IOException exception){
	        	System.out.println(MESSAGE_ERREUR_SAUVEGARDE);
	            System.out.println ("Erreur lors de l'�criture : " + exception.getMessage());
	        }
    	}
    }


    /**
     * Permet de r�cup�rer une partie pr�alablement sauvegard�e
     * @param fichier Le chemin du fichier qui contient la partie
     * @return La partie sauvegard�e
     */
    public static Partie recupererPartie(String fichier)  {

        Partie part; // Contiendra la partie que l'on veut lancer
        part = null; // Initialise, si partie retourn�e est null il n'y a aucune partie sauvegard�e
        try
        {
            ObjectInputStream save = new ObjectInputStream (new FileInputStream(fichier));
            try {
                part = (Partie)save.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            save.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return part;
    }

    /**
     * Liste toutes les parties sauvegardées dans le dossier sauvegarde/parties/
     * @return Toutes les parties contenues dans le dossier
     */
    public static ArrayList<Partie> listerParties() {
        ArrayList<Partie> partiesSauvegardees = new ArrayList<Partie>(); // Contiendra toutes les parties

        /* Permet de lister toutes les parties sauvegardées */
            try {
                Files.list(Paths.get("sauvegarde/parties/")).filter(Files::isRegularFile)
                .forEach(elt -> {
                    partiesSauvegardees.add(recupererPartie(elt.toString()));
                    
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        return partiesSauvegardees;
    }

}