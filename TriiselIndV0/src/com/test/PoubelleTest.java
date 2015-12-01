package com.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.metier.Levee;
import com.metier.Poubelle;
import com.metier.TypeDechet;

public class PoubelleTest {
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
	td = new TypeDechet("Ver", "verre", 0.10);
	pb = new Poubelle("pb1", td);
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
	// instanciation 3 levées par poubelle
	// le1pi1  : levée 1 pour poubelle individuelle 1
	// 2 en mai , 1 en juin
	le1 = new Levee(d1, 5, pb.getIdPoubelle());
	le2 = new Levee(d2, 10, pb.getIdPoubelle());
	le3 = new Levee(d3, 12, pb.getIdPoubelle());
	le4 = new Levee(d4, 30, pb.getIdPoubelle());
	// ajout des levées au poubelles
	pb.ajoutLevee(le1);
	pb.ajoutLevee(le2);
	pb.ajoutLevee(le3);
}

	@Test
	public void testPoubelle() {
		assertNotNull(pb);
	}

	@Test
	public void testAjoutLevee() {
		pb.ajoutLevee(le4);
		assertEquals(pb.getLesLevees().size(), 4);
		
	}

	@Test
	public void testGetIdPoubelle() {
		assertEquals(pb.getIdPoubelle(), "pb1");
	}

	@Test
	public void testGetNature() {
		assertEquals(pb.getNature(), td);
	}

	@Test
	public void testGetLesLevees() {
		assertEquals(pb.getLesLevees().size(), 3);
		assertEquals(pb.getLesLevees().get(0), le1);
		assertEquals(pb.getLesLevees().get(1), le2);
		assertEquals(pb.getLesLevees().get(2), le3);
	}

	@Test
	public void testGetCout() {
		int annee = 2015;
		int mois = 5;
		assertTrue(pb.getCout(annee, mois) == 1.5);
		assertFalse(pb.getCout(annee, mois) == 2.5);
	}

}
