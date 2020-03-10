/**
 * 
 */
package miage.bataille.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import miage.bataille.Batiment;
import miage.bataille.Cellule;
import miage.bataille.Partie;
import miage.bataille.ZoneContigue;

/**
 * @author k.sannac
 *
 */
class ZoneContigueTest {
	
	Batiment[] batiments = {
			new Batiment(6, "Grand"),
			new Batiment(3, "Moyen"),
			new Batiment(1, "Petit")
	};
	
	ZoneContigue[] zones = { 
			new ZoneContigue(batiments[0],0,0,0,5),
			new ZoneContigue(batiments[1],2,1,2,3),
			new ZoneContigue(batiments[2],1,2,1,2),
			new ZoneContigue(batiments[2],1,1,1,1),
			new ZoneContigue(batiments[0],0,3,0,8) // Superposiition sur un bateau existant
	};
	
	Partie partie = new Partie();

	/**
	 * Test method for {@link miage.bataille.ZoneContigue#cellulesAAjouter(miage.bataille.Obstacle, int, int, int, int)}.
	 */
	@Test
	void testCellulesAAjouter() {
		System.out.println(ZoneContigue.cellulesAAjouter(batiments[1], 2,3,4,3));
		System.out.println(ZoneContigue.cellulesAAjouter(batiments[1], 2,3,2,5));
	}

	/**
	 * Test method for {@link miage.bataille.ZoneContigue#ZoneContigue(miage.bataille.Partie, miage.bataille.Obstacle, int, int, int, int)}.
	 */
	@Test
	void testZoneContigue() {
		try {
			ZoneContigue premierBateau = zones[0];
			partie.ajouterZoneContigue(premierBateau);
			assertEquals(1, partie.getCompose().size());
			ZoneContigue deuxiemeBateau = zones[1];
			partie.ajouterZoneContigue(deuxiemeBateau);
			assertEquals(2, partie.getCompose().size());
			ZoneContigue troisiemeBateau = zones[2];
			partie.ajouterZoneContigue(troisiemeBateau);
			assertEquals(3, partie.getCompose().size());
			try {
				ZoneContigue bateauSurUnAutreBateau = zones[4];
				partie.ajouterZoneContigue(bateauSurUnAutreBateau);
			} catch (Exception e) {
			}
			assertEquals(3, partie.getCompose().size());


		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Test method for {@link miage.bataille.ZoneContigue#getCellule(int, int)}.
	 */
	@Test
	void testGetCellule() {
		Cellule estPresent;
		
		// Valid
		estPresent = zones[0].getCellule(0, 0);
		if(estPresent.getCoordX() != 0 || estPresent.getCoordY() != 0 || estPresent.getTouche() == true) {
			fail("Cellule inexistante [0,0]");
		}
		estPresent = zones[0].getCellule(0, 1);
		if(estPresent.getCoordX() != 0 || estPresent.getCoordY() != 1 || estPresent.getTouche() == true) {
			fail("Cellule inexistante [0,1]");
		}
		
		// Invalid
		estPresent = zones[0].getCellule(1, 1);
		if(estPresent != null) {
			fail("Cellule existante [1,1]");
		}
		estPresent = zones[0].getCellule(-1, -1);
		if(estPresent != null) {
			fail("Cellule existante [-1,-1]");
		}
		estPresent = zones[2].getCellule(2, 2);
		if(estPresent != null) {
			fail("Cellule existante [2,2]");
		}
	}

	/**
	 * Test method for {@link miage.bataille.ZoneContigue#existe(int, int)}.
	 */
	@Test
	void testExiste() {
		// Coord Négatives
		assertFalse(zones[0].existe(-1, -1));
		assertFalse(zones[0].existe(-1, 0));
		assertFalse(zones[0].existe(0, -1));
		
		// Coord existantes
		assertTrue(zones[0].existe(0, 0));
		assertTrue(zones[0].existe(0, 1));
		assertTrue(zones[0].existe(0, 2));
		assertTrue(zones[0].existe(0, 3));
		assertTrue(zones[0].existe(0, 4));
		assertTrue(zones[0].existe(0, 5));
		
		assertTrue(zones[2].existe(1, 2));
		
		//Coord inexistantes
		assertFalse(zones[0].existe(1, 0));
		assertFalse(zones[0].existe(0, 6));
		assertFalse(zones[0].existe(5, 4));
		assertFalse(zones[2].existe(1, 1));
		
	}

	/**
	 * Test method for {@link miage.bataille.ZoneContigue#estCoule()}.
	 */
	@Test
	void testEstCoule() {
		try {
			partie.ajouterZoneContigue(zones[3]); // bateau de 1 case en 1,1
			assertFalse(zones[3].estCoule());
			partie.tirer(1, 1);
			assertTrue(zones[3].estCoule());
			partie.ajouterZoneContigue(zones[1]); // bateau de 3 cases en 2,1 2,2 2,3
			partie.tirer(2, 1);
			assertFalse(zones[1].estCoule());
			partie.tirer(2, 2);
			assertFalse(zones[1].estCoule());
			partie.tirer(2, 3);
			assertTrue(zones[1].estCoule());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link miage.bataille.ZoneContigue#estTouchable()}.
	 */
	@Test
	void testEstTouchable() {
		assertTrue(zones[0].estTouchable());
//		fail("Not yet implemented");
	}

}
