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

    /**
     * Sauvegarde une partie dans le dossier suivant : 
     * sauvegarde/parties/
     * avec pour extension : .partie
     * @param partie La partie que l'on veut sauvegarder
     */
    public static String sauverPartie(Partie partie) {

        File fichier = new File ("sauvegarde/parties/" + System.currentTimeMillis() + ".partie");

        try
        {
            ObjectOutputStream save = new ObjectOutputStream (new FileOutputStream (fichier));
            save.writeObject (partie);
            save.close();
        }
        catch (IOException exception)
        {
        	exception.printStackTrace();
            System.out.println ("Erreur lors de l'�criture : " + exception.getMessage());
        }
        
        return "sauvegarde/parties/" + partie.toString() + ".partie";
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            save.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
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
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        return partiesSauvegardees;
    }

}