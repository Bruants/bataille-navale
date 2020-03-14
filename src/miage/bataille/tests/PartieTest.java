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
		
		// Le cas ou aucun b�timent n'a �t� touch�
		for (Cellule cellule : fixture[0].getCellulesTirees()) {
			
			// V�rifie si la cellule aux coordonn�s x y est dans la liste
			if (cellule.getCoordX() == 0 && cellule.getCoordY() == 0) {
				tire = true;
			}
		}
		assertTrue(tire);
		
		//TODO: Le cas ou un b�timent est touch�
		
		
		// Le cas ou les coordonn�es sont invalides
		fixture[0].tirer(-1, 5);
		tire = false;
		// Parcours de la liste des cellules d�j� tir�es
		for (Cellule cellule : fixture[0].getCellulesTirees()) {
			
			// V�rifie si la cellule aux coordonn�s x y est dans la liste
			if (cellule.getCoordX() == -1 && cellule.getCoordY() == 5) {
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
		
		fixture[0].tirer(-1, 0);
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
	 * Test method for {@link miage.bataille.Partie#placementBatimentAuto()}
	 */
	@Test
	void testPlacementFlotteAuto(ArrayList<Batiment> flotteAPlacer) {
		//TODO: vérifier que le batiment n'est pas placé hors de la mer : à terminer
		int nbBatiments, nbZones, i, j;
		Configuration cfg = new Configuration(12, 12, "cfgTest", new Batiment(3, "zoyzoy"));
		fixture[0].placementFlotteAuto(flotteAPlacer);
		
		ArrayList<ZoneContigue> zonesFlottePlacee = fixture[0].getCompose();
		nbBatiments = zonesFlottePlacee.size();
		
		for(i = 0; i < nbBatiments; i++) {
			ZoneContigue zone = zonesFlottePlacee.get(i);
			ArrayList<Cellule> cellulesZone = zone.getPossede();
			nbZones = cellulesZone.size();
			
			for(j = 0; j < nbZones; j++) {
				Cellule cellule = cellulesZone.get(j);
				// tester (en abscisses) si une cellule de la zone a été placée en dehors de la mer
				assertTrue(cellule.getCoordX() < 12 && cellule.getCoordX() >= 0);
				// tester (en ordonnées) si une cellule de la zone a été placée en dehors de la mer
				assertTrue(cellule.getCoordY() < 12 && cellule.getCoordY() >= 0);
			}
		}
	}
}
