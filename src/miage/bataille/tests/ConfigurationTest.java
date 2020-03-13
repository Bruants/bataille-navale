package miage.bataille.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatWidthException;

import org.junit.jupiter.api.Test;

import miage.bataille.Batiment;
import miage.bataille.Configuration;

/**
 * Classe de tests pour la classe Configuration
 * @author Corentin Goyat, Alexis Vivier
 */
class ConfigurationTest {
	
	/**
	 * Fixture de test
	 */
	private Configuration fixture = new Configuration(15, 15, "COnfig 1", new Batiment(1, "Coco"), new Batiment(2, "Blublu"));
	
	@Test
	void testConfiguration() {
		Configuration cfg = new Configuration();
		assertEquals(cfg.getHauteurCarte(), 12);
		assertEquals(cfg.getLongueurCarte(), 12);
		assertEquals(cfg.getFlotte().get(3).getNom(), "sous-marin");
	}

	@Test
	void testConfigurationIntIntBatimentArray() {

		assertEquals(fixture.getFlotte().get(0).getNom(), "Coco");
		assertEquals(fixture.getFlotte().get(0).getTailleLgr(), 1);
		assertEquals(fixture.getFlotte().get(0).getTailleHaut(), 1);
		assertEquals(fixture.getFlotte().get(1).getNom(), "Blublu");
		assertEquals(fixture.getFlotte().get(1).getTailleLgr(), 2);
		assertEquals(fixture.getFlotte().get(1).getTailleHaut(), 1);
		assertEquals(fixture.getHauteurCarte(), 15);
		assertEquals(fixture.getLongueurCarte(), 15);
		
		assertThrows(IllegalFormatWidthException.class, 
				() -> new Configuration(-2, -2, "impo", new Batiment(2, "coco")));
		assertThrows(IllegalFormatWidthException.class, 
				() -> new Configuration(27, -27, "impo", new Batiment(2, "coco")));
		
	}
	
	@Test
	void testChargerConfig() {
		HashMap<String,Configuration> configs = Configuration.chargerConfig("./tests/configs.json");
		// Test de la 1ere config
		
	}
	
	@Test
	void testEnregistrerConfig() {
		Configuration.enregistrerConfig("./tests/configs_enregistrer.json");
		// Test de la 1ere config
		
	}

	@Test
	void testGetLongueurCarte() {
		assertEquals((new Configuration()).getLongueurCarte(), 12);
		assertEquals(fixture.getLongueurCarte(), 15);
	}

	@Test
	void testSetLongueurCarte() {
		Configuration cfg = fixture;
		cfg.setLongueurCarte(5);
		assertEquals(cfg.getLongueurCarte(), 5);
	}

	@Test
	void testGetHauteurCarte() {
		assertEquals((new Configuration()).getHauteurCarte(), 12);
		assertEquals(fixture.getHauteurCarte(), 15);
	}

	@Test
	void testSetHauteurCarte() {
		Configuration cfg = fixture;
		cfg.setHauteurCarte(3);
		assertEquals(cfg.getHauteurCarte(), 3);
	}

	@Test
	void testGetFlotte() {
		assertEquals(fixture.getFlotte().get(0).getNom(), "Coco");
		assertEquals(fixture.getFlotte().get(1).getNom(), "Blublu");
	}

	@Test
	void testSetFlotte() {
		Configuration cfg = fixture;
		ArrayList<Batiment> flotte = cfg.getFlotte();
		flotte.add(new Batiment(2, "CroustiMiam"));
		cfg.setFlotte(flotte);
		assertEquals(flotte.get(2).getNom(), "CroustiMiam");
	}
}
