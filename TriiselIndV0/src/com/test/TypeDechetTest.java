package com.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.metier.*;


public class TypeDechetTest {
private TypeDechet td = null;
	@Before
	public void setUp() throws Exception {
		td = new TypeDechet("ver", "verre", 0.10);
	}

	@Test
	public void testTypeDechet() {
		assertNotNull(td);
	}

	@Test
	public void testGetTarif() {
		assertTrue(td.getTarif() == 0.10);
		assertFalse(td.getTarif() == 0.15);
	}

	@Test
	public void testSetTarif() {
		td.setTarif(0.25);
		assertTrue(td.getTarif() == 0.25);
	}

	@Test
	public void testGetCode() {
		assertEquals(td.getCode(), "ver");
	}

	@Test
	public void testGetLibelle() {
		assertEquals(td.getLibelle(), "verre");
	}

}
