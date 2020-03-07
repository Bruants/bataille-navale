/**
 * 
 */
package miage.bataille.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import miage.bataille.Batiment;
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
		Partie partie = new Partie();

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


		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Test method for {@link miage.bataille.ZoneContigue#getCellule(int, int)}.
	 */
	@Test
	void testGetCellule() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link miage.bataille.ZoneContigue#existe(int, int)}.
	 */
	@Test
	void testExiste() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link miage.bataille.ZoneContigue#estCoule()}.
	 */
	@Test
	void testEstCoule() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link miage.bataille.ZoneContigue#estTouchable()}.
	 */
	@Test
	void testEstTouchable() {
		fail("Not yet implemented");
	}

}
