package com.util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.metier.Levee;
import com.persistance.AccesData;
// ok appele par testBd
public class InsertionLevee {

	public static void insertionLevee() {
		// récupération du chemin du dossier des levées
		String cheminLevee = Parametre.getCheminLeveeATraiter();
		// récupération chemin
		String cheminTraite = Parametre.getCheminLeveeTraites();		
		// ouverture du dossier des levées
		File dossierLevee = new File(cheminLevee);
		for (File fichierLevee : dossierLevee.listFiles())
		{
			if (Parametre.getExtensionFichier(fichierLevee.getName()).equals("txt")) {
				// appel de la procédure de traitement du fichier xml
				traitementFichierTexte(fichierLevee.getAbsolutePath());
				// déplacement des fichiers traités
				transfertFichier(fichierLevee.getAbsolutePath(), cheminTraite, fichierLevee.getName());
			}
			if (Parametre.getExtensionFichier(fichierLevee.getName()).equals("xml")) {
				// appel de la procédure de traitement du fichier xml
				traitementFichierXml(fichierLevee.getAbsolutePath());
				// déplacement des fichiers traités
				transfertFichier(fichierLevee.getAbsolutePath(), cheminTraite, fichierLevee.getName());
			}
		}
	}

	// deplace et supprime le fichier traité dans le dossier traite
	private static void transfertFichier(String cheminSource, String cheminDestination, String fichier) {
        // on récupère la date système
		java.util.Date dateTraitement = new java.util.Date();
		// format de la date en français
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateT = sdf.format(dateTraitement);
		File source = new File(cheminSource);
		// on integre la date de transfert
		cheminDestination = cheminDestination + "\\" +  dateT + "-" + fichier;
		File destination = new File(cheminDestination);
		try {
			source.renameTo(destination);
		} catch (Exception e) {
			System.out.println("echec deplacement");
		}
	}
	// ajout des levées dans la base de données
	private static void traitementFichierXml(String cheminLevee) {

		Levee uneLevee = null;
		Document document =  null;
		Element racine = null;
		//On crée une instance de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();
		try {
			// ouverture du fichier xml
			document = sxb.build(new File(cheminLevee));
			// récupération de l'élement racine
			racine = document.getRootElement();
			// récupération de l'élément date
			Element ladate = racine.getChild("Date");
			// format de la date en français
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				// conversion de la chaine en date java.util
				java.util.Date laDate = sdf.parse(ladate.getText());
				//On crée une List contenant tous les noeuds "Levee"
				List<Element> listeLevee = racine.getChildren("Levee");
				// parcours des levées
				for (Element e: listeLevee)
				{
					// on récupère le numéro de poubelle
					String p = e.getChild("poubelle").getText();
					// on récupère le poids que l'on converti en double
					double poids =  Double.parseDouble(e.getChild("poids").getText());
					// on instancie un objet levée sans id car numero auto dans la base
					uneLevee = new Levee(laDate, poids, p);
					// on ajoute la levée à la base
					AccesData.ajoutLevee(uneLevee);
				} 
			}
			catch (ParseException e1) {
				e1.printStackTrace();
			}
		} catch (JDOMException e2) {

			e2.printStackTrace();
		} catch (IOException e2) {

			e2.printStackTrace();
		}
	}
	private static void traitementFichierTexte(String cheminLevee) {
		System.out.println("fichier texte trouvé");
		Levee uneLevee = null;
		FichierTexte ft = new FichierTexte();
		ft.openFileReader(cheminLevee);
		
		// on récupère la date ligne 1 et on la transforme en java.util.Date
		String date = ft.readLigne();
		System.out.println("ligne 1 date :" + date );
		// format de la date en français
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			// conversion de la chaine en date java.util
			java.util.Date laDate = sdf.parse(date);
			// parcours des lignes levee
			String ligne;
			while ((ligne = ft.readLigne()) != null) {
				// extraction des données séparateur :
				String data[]   = ligne.split(":");
				System.out.println(data.length);
				System.out.println(data[0]);
				System.out.println(data[1]);
				String p = data[1];
				System.out.println(data[2]);
				double poids = Double.parseDouble(data[2]);
				// on instancie un objet levée sans id car numero auto dans la base
				//uneLevee = new Levee(laDate, poids, p);
				// on ajoute la levée à la base
			//	AccesDataV1.ajoutLevee(uneLevee);
			}
			
		}
		catch (ParseException e1) {
			e1.printStackTrace();
		}
		finally {
			ft.closeFileReader();
		}
	}
}
