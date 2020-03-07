/*
 * Test de Cellule.java
 * CelluleTest.java														06 mars 2020
 * L3 MIAGE 2019/2020, ni copyright, ni copyleft
 */
package miage.bataille.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import miage.bataille.Cellule;

/**
 * Test des méthodes de la class Cellule
 * @author Damien Avetta-Raymond
 */
class CelluleTest {
	
	/**
	 * Test method for {@link miage.bataille.Cellule#Cellule(int, int)}.
	 */
	@Test
	void testCellule() {
		assert (new Cellule(0, 0)) != null;
	}
	
	/**
	 * Test method for {@link miage.bataille.Cellule#equals(Object)}.
	 */
	@Test
	void testEquals() {
		// Valid
		assertTrue(new Cellule(0, 0).equals(new Cellule(0,0)));
		assertTrue(new Cellule(4, 3).equals(new Cellule(4,3)));
		assertTrue(new Cellule(1, 45).equals(new Cellule(1,45)));
		
		// Invalid
		assertFalse(new Cellule(0, 0).equals(new Cellule(-1,0)));
		assertFalse(new Cellule(0, -1).equals(new Cellule(0,0)));
		assertFalse(new Cellule(0, 0).equals(new Cellule(0,15)));
		assertFalse(new Cellule(42, 9).equals(new Cellule(12,105)));

	}

	/**
	 * Test method for {@link miage.bataille.Cellule#aEteTouche()}.
	 */
	@Test
	void testAEteTouche() {
		Cellule aTester = new Cellule(0,0);
		assertFalse(aTester.getTouche());
		aTester.aEteTouche();
		assertTrue(aTester.getTouche());
	}

}
