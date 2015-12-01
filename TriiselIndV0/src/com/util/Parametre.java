package com.util;

import java.io.File;

public class Parametre {
	private static String nomFichier = "paramAppli.ini";
	public static String getCheminBd()
	{
		// ouverture du fichier en lecture
		FichierTexte t=new FichierTexte();
		t.openFileReader(nomFichier);
		// ramène la  ligne 2
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
		// ramène la  ligne 4
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
		// ramène la  ligne 6
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
		// ramène la  ligne 6
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
		// ramène la  ligne 8
		return t.readLigne();
	}
	
	public static String getExtensionFichier(String nomFichier) {
		String ext = "";
		  //     Récupération extension d'un fichier
	    if (nomFichier.contains(".")) {
	    	 // caractère spécial . veut dire n'importe quel caractère pour regex donc on met \\.
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
	// ramène le nombre de fichiers du dossier atraiter
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
				t.writeLigne("// url base de données ");
				t.writeLigne(url);
				t.writeLigne("// chemin d'accès aux fichiers xml des levées" );
				t.writeLigne(dossierAtraiter);
				t.writeLigne("// chemin sauvegarde après traitement");
				t.writeLigne(dossierTraites);
				t.writeLigne("//chemin d'accès aux factures en pdf");
				t.writeLigne(dossierfacture);
				t.writeLigne("//chemin d'accès aux fichiers logs des levées");
				t.writeLigne(dossierLogLevee);
				t.closeFileWriter();
	}
}
