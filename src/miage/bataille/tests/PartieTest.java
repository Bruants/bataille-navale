/**
 * 
 */
package miage.bataille.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import miage.bataille.Batiment;
import miage.bataille.Cellule;
import miage.bataille.Configuration;
import miage.bataille.Partie;
import miage.bataille.ZoneContigue;

/**
 * @author Audric
 *
 */
class PartieTest {
	
	
	Partie[] fixture = {
		
		new Partie(),
				
	};

	/**
	 * Test method for {@link miage.bataille.Partie#tirer(int, int)}.
	 */
	@Test
	void testTirer() {
		
		fixture[0].tirer(0, 0);
		boolean tire = false;
		
		// Le cas ou aucun batiment n'a ete touche
		for (int i = 0; i < fixture[0].getCellulesTirees().size() && !tire; i++) {
			
			// Verifie si la cellule aux coordonnees x y est dans la liste
			if (fixture[0].getCellulesTirees().get(i).getCoordX() == 0 
					&& fixture[0].getCellulesTirees().get(i).getCoordY() == 0) {
				tire = true;
				assertTrue(tire);
			}
		}
		
		//TODO: Le cas ou un batiment est touche
		
		
		// Le cas ou les coordonnees sont invalides
		assertThrows(IllegalArgumentException.class, () -> fixture[0].tirer(-1, 5));

		tire = false;
		// Parcours de la liste des cellules d�j� tir�es
		for (int i = 0; i < fixture[0].getCellulesTirees().size() && !tire; i++) {
			
			// Verifie si la cellule aux coordonnees x y est dans la liste
			if (fixture[0].getCellulesTirees().get(i).getCoordX() == -1 
					&& fixture[0].getCellulesTirees().get(i).getCoordY() == 5) {
				tire = true;
			}
		}
		assertFalse(tire);
		
	}
	
	/**
	 * Test method for {@link miage.bataille.Partie#rechercherZone(int,int)}.
	 */
	@Test
	void testRechercheZone() {
		
		//TODO: Attendre d'avoir termin� la classe ZoneContigue
		fail("Not yey implemented");
	}
	

	/**
	 * Test method for {@link miage.bataille.Partie#getCellulesTirees()}.
	 */
	@Test
	void testGetCellulesTirees() {
		
		assertEquals(0, fixture[0].getCellulesTirees().size());
		
		assertThrows(IllegalArgumentException.class, () -> fixture[0].tirer(-1, 0));
		assertEquals(0, fixture[0].getCellulesTirees().size());
		
		fixture[0].tirer(0, 0);
		assertEquals(1, fixture[0].getCellulesTirees().size());
		
		fixture[0].tirer(0,0);
		assertEquals(2, fixture[0].getCellulesTirees().size());
		
		fixture[0].tirer(5, 2);
		assertEquals(3, fixture[0].getCellulesTirees().size());
		
		assertEquals(2, fixture[0].getCellulesTirees().get(2).getCoordY());
		assertEquals(5, fixture[0].getCellulesTirees().get(2).getCoordX());
		
		for (Cellule cellule : fixture[0].getCellulesTirees()) {
			assertTrue(cellule.getCoordX() >= 0);
		}
		
		//TODO: Tester les cas ou l'on d�passe la taille de la carte
	}
	
	/**
	 * Test method for {@link miage.bataille.Partie#placementFlotteAuto()}
	 * verifie que les batiments ne sont pas placee hors de la mer
	 */
	@Test
	void testPlacementFlotteAuto() {
		
		// créer une carte de 12x12 avec une flotte constituée d'un seul bateau
		Configuration cfg = new Configuration(12, 12, "cfgTest", new Batiment(3, "zoyzoy"));
		ArrayList<Batiment> flotteAPlacer = cfg.getFlotte();
		Partie partie = fixture[0];
		
		// utiliser cette nouvelle flotte pour le test
		partie.placementFlotteAuto(flotteAPlacer);
		
		// récupérer les zones contigues de chaque batiment de la flotte placee
		ArrayList<ZoneContigue> zonesFlottePlacee = partie.getCompose();
		
		// pour chaque zone occupée par un batiment
		for(ZoneContigue zone:zonesFlottePlacee) {
			ArrayList<Cellule> cellulesZone = zone.getPossede();
			
			// pour chaque cellule de la zone contigue occupee par un batiment
			for(Cellule cellule:cellulesZone) {
				// tester (en abscisses) si une cellule de la zone est bien placée dans la mer
				assertTrue(cellule.getCoordX() < cfg.getLongueurCarte() && cellule.getCoordX() >= 0);
				// pareil en ordonnées
				assertTrue(cellule.getCoordY() < cfg.getHauteurCarte() && cellule.getCoordY() >= 0);
			}
		}
	}
}
