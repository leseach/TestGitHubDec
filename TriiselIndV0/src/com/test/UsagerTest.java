package com.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import com.metier.Habitation;
import com.metier.Usager;

public class UsagerTest {
private Usager u = null;;
private Habitation habInd = null;

	@Before
	public void setUp() throws Exception {
		u = new Usager("u1", "Dupont", "Laurent");
	}
	@Test
	public void testUsager() {
		assertNotNull(u);
	}

	@Test
	public void testGetCode() {
		assertEquals(u.getCode(), "u1");
	}

	@Test
	public void testGetNom() {
		assertEquals(u.getNom(), "Dupont");
	}

	
	@Test
	public void testGetPrenom() {
		assertEquals(u.getPrenom(), "Laurent");
	}
	
	@Test
	public void testGetNomUser() {
		assertEquals(u.getNomUser(), "");
	}
	
	@Test
	public void testGetMotDePasse() {
		assertEquals(u.getMotDePasse(), "");
	}
	
	@Test
	public void testSetNomUser() {
		u.setNomUser("user1");
		assertEquals(u.getNomUser(), "user1");
	}
	
	@Test
	public void testSetMotDePasse() {
		u.setMotDePasse("azrzz");
		assertEquals(u.getMotDePasse(), "azrzz");
	}
	
	@Test
	public void testAjoutHabInd() {
		habInd = new Habitation("hab1", "63 grand-rue",  "29150 Chateaulin", u);
		u.ajoutHabInd(habInd);
		assertEquals(u.getListeHabInd().size() , 1);
		assertEquals(u.getListeHabInd().get(0) , habInd);
	}

	
	@Test
	public void testGetListeHabInd() {
		assertEquals(u.getListeHabInd().size() , 0);
	}

	
	@Test
	public void testToString() {
		assertEquals(u.toString(), "Usager [code=u1, nom=Dupont, prenom=Laurent]");
	}

}
