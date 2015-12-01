package com.test;

import junit.framework.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value={   // on note la liste des classes de test à enchainer
		LeveeTest.class,
		TypeDechetTest.class,
		PoubelleTest.class,
		UsagerTest.class,
		HabitationTest.class
		})
public class ExecuterTest {
	public static void main(String args[]) {
		org.junit.runner.JUnitCore.main(
				"ExecuterTest");
	}}
