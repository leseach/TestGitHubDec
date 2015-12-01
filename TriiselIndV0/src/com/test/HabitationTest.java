package com.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.metier.Habitation;
import com.metier.Levee;
import com.metier.Poubelle;
import com.metier.TypeDechet;
import com.metier.Usager;

public class HabitationTest {
	private Habitation habIn = null;
	private Usager u = null;
	private	TypeDechet td;
	private	Poubelle pb;
	private Date d1 = null;
	private Date d2 = null ;
	private Date d3 = null;
	private Date d4 = null;
	private Levee le1=null;
	private Levee le2=null;
	private Levee le3=null;
	private Levee le4=null;
	private SimpleDateFormat dateFormat;
	@Before
	public void setUp() throws Exception {
		u = new Usager("u1", "Dupont", "Albert");
		habIn = new Habitation("hab1", "63 grand-rue", "29150 Châteaulin", u);
		td = new TypeDechet("Ver", "verre", 0.10);
		pb = new Poubelle("pb1", td , habIn.getIdHabitation());
		// instanciation dates de levée
		dateFormat =  new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			d1 = dateFormat.parse("15/05/2015");
			d2 = dateFormat.parse("30/05/2015");
			d3 = dateFormat.parse("15/06/2015");
			d4 = dateFormat.parse("30/06/2015");
		} catch (ParseException e){
			e.printStackTrace();
		} 
		// instanciation 3 levées pour la poubelle  poubelle
		// 2 en mai , 1 en juin
		le1 = new Levee(d1, 5, pb.getIdPoubelle());
		le2 = new Levee(d2, 10, pb.getIdPoubelle());
		le3 = new Levee(d3, 12, pb.getIdPoubelle());
		le4 = new Levee(d4, 30, pb.getIdPoubelle());
		// ajout des levées à la poubelle 
		pb.ajoutLevee(le1);
		pb.ajoutLevee(le2);
		pb.ajoutLevee(le3);
	}

	@Test
	public void testHabitation() {
		assertNotNull(habIn);
	}

	@Test
	public void testGetUnUsager() {
		assertEquals(habIn.getUsager(), u);
	}

	@Test
	public void testGetIdHabitation() {
		assertEquals(habIn.getIdHabitation(), "hab1");
	}
	@Test
	public void testGetLesPoubelles() {
		assertEquals(habIn.getLesPoubelles().size(), 0);
	}

	@Test
	public void testGetAdresseRue() {
		assertEquals(habIn.getAdresseRue(), "63 grand-rue");
	}

	@Test
	public void testGetAdresseVille() {
		assertEquals(habIn.getAdresseVille(), "29150 Châteaulin");
	}

	@Test
	public void testAjoutPoubelle() {

		habIn.ajoutPoubelle(pb);
		assertEquals(habIn.getLesPoubelles().size(), 1);
		assertEquals(habIn.getLesPoubelles().get(0), pb);	
	}

	@Test
	public void testGetCout() {
		int an = 2015;
		int mois = 5;
		habIn.ajoutPoubelle(pb);
		System.out.println(habIn.getCout(an, mois));
		assertTrue(habIn.getCout(an, mois) == 1.50);
	}
}
