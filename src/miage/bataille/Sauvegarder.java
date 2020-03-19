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
import java.util.HashMap;

/**
 * Sauvegarde les parties
 * @author L3 MIAGE Rodez
 */
public class Sauvegarder {
	
	private static final String MESSAGE_ERREUR_CREATION_DIR = "Impossible de cr�er les r�pertoires de sauvegarde.";
	
	private static final String MESSAGE_ERREUR_SAUVEGARDE = "La sauvegarde s'est mal effectu�e";

    /**
     * Sauvegarde une partie dans le dossier suivant : 
     * sauvegarde/parties/
     * avec pour extension : .data
     * @param nomSauvegarde le nom du fichier de sauvegarde
     * @param aSauvegarder tous les �l�ments qui sont � sauvegarder
     */
    public static void sauverPartie(String nomSauvegarde, Object... aSauvegarder) {

        File fichier = new File("donnees/sauvegarde/parties/" + nomSauvegarde + ".data");
        File directory = new File("donnees/sauvegarde/parties");
        ObjectOutputStream save;
        boolean repertoireExistant;
        
        repertoireExistant = directory.exists() || directory.mkdirs();
        if (!repertoireExistant) {
    		System.out.println(MESSAGE_ERREUR_CREATION_DIR);
    	} else {
	        try {
	        	if (fichier.exists()) {
	        		fichier.delete();
	        	}
	            save = new ObjectOutputStream(new FileOutputStream (fichier));
	            for (Object objet : aSauvegarder) {
	            	save.writeObject (objet);
	            }
	            save.close();
	            System.out.println("La sauvegarde c'est bien effectu�e !");
	        } catch (IOException exception) {
	        	System.out.println(MESSAGE_ERREUR_SAUVEGARDE);
	            System.out.println ("Erreur lors de l'�criture : " + exception.getMessage());
	        }
    	}
    }


    /**
     * Permet de r�cup�rer une partie pr�alablement sauvegard�e
     * @param fichier Le chemin qui contient la partie � charger
     * @return Les �l�ments de la partie sauvegard�e.
     */
    public static ArrayList<Object> recupererPartie(String fichier)  {
        ArrayList<Object> elementsPartie = new ArrayList<Object>();
        
        try
        {
            ObjectInputStream save = new ObjectInputStream (new FileInputStream(fichier));
            try {
            	elementsPartie.add(save.readObject()); // Donn�es de la partie
            	elementsPartie.add(save.readObject()); // Carte du jeu
            	elementsPartie.add(save.readObject()); // Nombre de tours ayant eu lieu
            	elementsPartie.add(save.readObject()); // Historique des coups
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            save.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return elementsPartie;
    }
   
    /**
     * V�rifie qu'il existe bien des parties � charger
     * @return true s'il existe au moins un fichier de sauvegarde � charger
     *         false sinon
     */
    public static boolean verifierNbPartiesACharger() {
    	File directory = new File("donnees/sauvegarde/parties/");
    	
    	try {
			return directory.exists() 
				   && Files.list(Paths.get("donnees/sauvegarde/parties/")).filter(Files::isRegularFile).count() > 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
    }

    /**
     * Liste toutes les parties sauvegard�es dans le dossier sauvegarde/parties/
     * @return Toutes les parties contenues dans le dossier
     */
    public static void listerParties() {
        /* Permet de lister toutes les parties sauvegard�es */
    	System.out.println("Liste des parties :");
        try {
            Files.list(Paths.get("donnees/sauvegarde/parties/")).filter(Files::isRegularFile)
            .forEach(elt -> {
                System.out.println(elt.getFileName().toString());
            });
        } catch (IOException e) {
            e.printStackTrace();
       }
    }

}