package miage.bataille.tests.affichage;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import static miage.bataille.affichage.DeroulementPartie.*;

import org.junit.jupiter.api.Test;

import miage.bataille.Configuration;
import miage.bataille.affichage.DeroulementPartie;


/**
 * Test de la class DeroulementPartie
 * @author Audric POUZELGUES, Damien AVETTA-RAYMOND
 */
class DeroulementPartieTest {
	
	private Configuration config = Configuration.recupererConfig("Config1");

	@Test
	void testVerifierSaisie() {
		String[] paramTestTrue = {"A1", "A8", "L1", "L8", "K5"},
				 paramTestFalse = {"651", "M1", "A9", " ", "A", "\n", " 1", "1"};
		
		initialisationAvecUneConfiguration(config);
		
		/* Cas qui renvoient : vrai */
		for (int i = 0 ; i < paramTestTrue.length ; i++) {
			assertTrue(verifierSaisie(paramTestTrue[i]));
		}
		
		/* Cas qui renvoie : false */
		for (int i = 0 ; i < paramTestFalse.length ; i++) {
			assertFalse(verifierSaisie(paramTestFalse[i]));
		}
	}

	@Test
	void testNomFichierCorrect() {
		String[] nomFichierATester = {"Partie01",
                "partie", "partie 54", "partie/a", "partie\b",
                "\\partiec", "par:tied", "p*rtie", "part?ie",
                "partie\"coucou", "pa<>rtie", "par<tie", "part|iefgh"};

        for (int indice = 0; indice < 3; indice++) {
            assertTrue(DeroulementPartie.nomFichierCorrect
                    (nomFichierATester[indice], false));
        }

        for (int indice = 3; indice < nomFichierATester.length; indice++) {
            assertFalse(DeroulementPartie.nomFichierCorrect
                    (nomFichierATester[indice], false));
        }
	}

//	@Test
//	void testRechercheFichier() {
//		final int NB_CAS_TEST = 4; // Cas de test : {"sauvegarde/parties/save.data", "save", 
//		                           //                "sauvegarde/parties/save", "save.data"}
//		for (int i = 0 ; i < NB_CAS_TEST ; i++) {
//			//assertEquals(new File("sauvegarde/parties/save.data"), rechercheFichier());
//		}
//	}
}
