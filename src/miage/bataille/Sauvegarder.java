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
	
	private static final String MESSAGE_ERREUR_CREATION_DIR = "Impossible de créer les répertoires de sauvegarde.";
	
	private static final String MESSAGE_ERREUR_SAUVEGARDE = "La sauvegarde s'est mal effectuée";

    /**
     * Sauvegarde une partie dans le dossier suivant : 
     * sauvegarde/parties/
     * avec pour extension : .data
     * @param nomSauvegarde le nom du fichier de sauvegarde
     * @param aSauvegarder tous les éléments qui sont à sauvegarder
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
	            System.out.println("La sauvegarde c'est bien effectuée !");
	        } catch (IOException exception) {
	        	System.out.println(MESSAGE_ERREUR_SAUVEGARDE);
	            System.out.println ("Erreur lors de l'écriture : " + exception.getMessage());
	        }
    	}
    }


    /**
     * Permet de récupérer une partie préalablement sauvegardée
     * @param fichier Le chemin qui contient la partie à charger
     * @return Les éléments de la partie sauvegardée.
     */
    public static ArrayList<Object> recupererPartie(String fichier)  {
        ArrayList<Object> elementsPartie = new ArrayList<Object>();
        
        try
        {
            ObjectInputStream save = new ObjectInputStream (new FileInputStream(fichier));
            try {
            	elementsPartie.add(save.readObject()); // Données de la partie
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
     * Vérifie qu'il existe bien des parties à charger
     * @return true s'il existe au moins un fichier de sauvegarde à charger
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
     * Liste toutes les parties sauvegardées dans le dossier sauvegarde/parties/
     * @return Toutes les parties contenues dans le dossier
     */
    public static void listerParties() {
        /* Permet de lister toutes les parties sauvegardées */
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