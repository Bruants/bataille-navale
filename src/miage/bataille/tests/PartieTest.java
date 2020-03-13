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
	void testPlacementBatimentAuto(Batiment aPLacer) {
		//TODO: vérifier que le batiment n'est pas placé hors de la mer
		ArrayList<Batiment> flotte = new ArrayList<Batiment>();
		flotte.add(new Batiment(3, "test"));
		Configuration cfg = fixture[0].getConfiguration();
		fixture[0].placementFlotteAuto(flotte);
	}
}
