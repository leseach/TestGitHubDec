package com.util;

import java.io.File;

public class Parametre {
	private static String nomFichier = "paramAppli.ini";
	public static String getCheminBd()
	{
		// ouverture du fichier en lecture
		FichierTexte t=new FichierTexte();
		t.openFileReader(nomFichier);
		// ram�ne la  ligne 2
		t.readLigne();
		return t.readLigne();
	}
	public static String getCheminLeveeATraiter()
	{
		// ouverture du fichier en lecture
		FichierTexte t=new FichierTexte();
		t.openFileReader(nomFichier);
		for (int i = 0 ; i < 3 ; i ++ ) {
			t.readLigne();
		}
		// ram�ne la  ligne 4
		return t.readLigne();
	}
	public static String getCheminLeveeLog()
	{
		// ouverture du fichier en lecture
		FichierTexte t=new FichierTexte();
		t.openFileReader(nomFichier);
		for (int i = 0 ; i < 9 ; i ++ ) {
			t.readLigne();
		}
		// ram�ne la  ligne 6
		return t.readLigne();
	}
	public static String getCheminLeveeTraites()
	{
		// ouverture du fichier en lecture
		FichierTexte t=new FichierTexte();
		t.openFileReader(nomFichier);
		for (int i = 0 ; i < 5 ; i ++ ) {
			t.readLigne();
		}
		// ram�ne la  ligne 6
		return t.readLigne();
	}
	public static String getCheminFacturePdf()
	{
		// ouverture du fichier en lecture
		FichierTexte t=new FichierTexte();
		t.openFileReader(nomFichier);
		for (int i = 0 ; i < 7 ; i ++ ) {
			t.readLigne();
		}
		// ram�ne la  ligne 8
		return t.readLigne();
	}
	
	public static String getExtensionFichier(String nomFichier) {
		String ext = "";
		  //     R�cup�ration extension d'un fichier
	    if (nomFichier.contains(".")) {
	    	 // caract�re sp�cial . veut dire n'importe quel caract�re pour regex donc on met \\.
	    	String[] elem =  nomFichier.split("\\.");
	 	    ext = elem[1];
	    	} else {
	    	    throw new IllegalArgumentException("String " + nomFichier + " does not contain .");
	    	}
	   
		return ext;
		
	}
	public static void creerDossier(String nom) {
		File f1=new File(nom); 
		f1.mkdir();
	}
	// ram�ne le nombre de fichiers du dossier atraiter
	public static int  nbLevee() {
		String cheminLevee = getCheminLeveeATraiter();
		File f = new File(cheminLevee);
		return f.listFiles().length; 
	}
	public static void enregistreParam(String url, String dossierfacture, String dossierAtraiter, String dossierTraites, String dossierLogLevee)
	{
		// ouverture du fichier en lecture
				FichierTexte t=new FichierTexte();
				t.openFileWriter(nomFichier);
				t.writeLigne("// url base de donn�es ");
				t.writeLigne(url);
				t.writeLigne("// chemin d'acc�s aux fichiers xml des lev�es" );
				t.writeLigne(dossierAtraiter);
				t.writeLigne("// chemin sauvegarde apr�s traitement");
				t.writeLigne(dossierTraites);
				t.writeLigne("//chemin d'acc�s aux factures en pdf");
				t.writeLigne(dossierfacture);
				t.writeLigne("//chemin d'acc�s aux fichiers logs des lev�es");
				t.writeLigne(dossierLogLevee);
				t.closeFileWriter();
	}
}
