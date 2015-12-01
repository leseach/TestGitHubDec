package com.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

import com.metier.Levee;
import com.metier.Poubelle;
import com.metier.TypeDechet;

public class LeveeTest {
	private Date d1 = null;
	private Date d2 = null;
	private SimpleDateFormat dateFormat = null;
	private Calendar cal = null;
	private Levee le1 = null;
	private Levee le2 = null;
	private	TypeDechet td;
	private	Poubelle pb;
	@Before
	public void setUp() throws Exception {
		td = new TypeDechet("Ver", "verre", 0.10);
		pb = new Poubelle("pb1", td);
		// format de la date
		dateFormat =  new SimpleDateFormat("dd/MM/yyyy");
		cal = Calendar.getInstance();
		try
		{
			// instanciation date de levée au format français
			d1 = dateFormat.parse("15/05/2015");
			// instanciation objet Levée
			le1 = new Levee(d1, 5, pb.getIdPoubelle());
			le2 = new Levee(2, d1, 10, pb.getIdPoubelle());
		} catch (ParseException e){
			e.printStackTrace();
		} 
	}

	@Test
	public void testLevee() {
		// test existence objet 
		assertNotNull(le1);
		assertNotNull(le2);
	}

	@Test
	public void testGetLaDate() {

		// récupération date de la levée l1
		cal.setTime(le1.getLaDate());
		int year = cal.get(Calendar.YEAR);
		// extraction du mois mettre + 1 car démarre à 0 et non pas 1
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		// comparaison des éléments extraits
		assertEquals(year,2015);
		assertEquals(month, 5);
		assertEquals(day, 15);
		// date de levée l2
		cal.setTime(le2.getLaDate());
		 year = cal.get(Calendar.YEAR);
		// extraction du mois mettre + 1 car démarre à 0 et non pas 1
		 month = cal.get(Calendar.MONTH) + 1;
		 day = cal.get(Calendar.DAY_OF_MONTH);
		// comparaison des éléments extraits
		assertEquals(year,2015);
		assertEquals(month, 5);
		assertEquals(day, 15);
		
	}
	

	@Test
	public void testSetLaDate() {
		// changement de date
		try
		{
			// instanciation date de levée au format français
			d2 = dateFormat.parse("30/06/2015");
			// changement date de levée
			le1.setLaDate(d2);
			// récupération date de la levée
			cal.setTime(le1.getLaDate());
			int year = cal.get(Calendar.YEAR);
			// extraction du mois mettre + 1 car démarre à 0 et non pas 1
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			// comparaison des éléments extraits
			assertEquals(year,2015);
			assertEquals(month,6);
			assertEquals(day, 30);

		} catch (ParseException e){
			e.printStackTrace();
		} 

	}

	@Test
	public void testGetPoids() {
		// assertEquals deprecated, remplacement par assertTrue
		assertTrue(le1.getPoids() == 5);
		assertTrue(le2.getPoids() == 10);
	}
	@Test
	public void testGetIdPoubelle() {
		// assertEquals deprecated, remplacement par assertTrue
		assertEquals(le1.getIdPoubelle(), "pb1");
		
	}
	@Test
	public void testGetIdLevee() {
		// assertEquals deprecated, remplacement par assertTrue
		
		assertTrue(le2.getIdLevee() == 2);
	}
	@Test
	public void testSetPoids() {
		le1.setPoids(10);
		assertFalse(le1.getPoids() == 5);
		assertTrue(le1.getPoids() == 10);
	}
}
