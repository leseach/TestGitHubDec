package com.pdf;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.metier.Habitation;
import com.metier.Levee;
import com.metier.Poubelle;
import com.metier.Usager;


public class Facture {
	private static final String out = "FactureHabInd.pdf";

	private static final double tauxTva  = 20.6;
	private static String nomFichier = null;
	private static Document document = null;
	private static Usager unUsager = null;
	private static PdfPTable table = null;
	private static PdfPCell cell = null;
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static final String  tabMois[]={"","janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre" };
	public static void editionFactureIndividuelle(Habitation hab, int mois , int an) {
		
		// récupération de l'usager
		unUsager = hab.getUsager();
		// etape 1 : création du document
		nomFichier=UtilF.getNomFichier(hab, mois, an);
		document = new Document();
		try {
			// etape 2: creation du writer -> PDF 
			PdfWriter.getInstance(document, new FileOutputStream(nomFichier));
			// etape 3: ouverture du document
			document.open();
			// etape 4 : Populate du document (ajout des éléments) 
			populate(hab,  mois ,  an);
		}
		catch(DocumentException de) {
			System.out.println("Cette facture a déjà été éditée");
			System.err.println(de.getMessage());
		}
		catch(IOException ioe) {
			System.out.println("Cette facture a déjà été éditée");
			System.err.println(ioe.getMessage());
		}
		// etape 5: fermeture du document
		document.close();
		// affichage du fichier généré dans acrobate
		try { 
			Runtime.getRuntime().exec("explorer.exe " + nomFichier);
		} 
		catch (IOException ex) { ex.printStackTrace(); }
		/*// affichage du document
		PDFViewerBean bean = new PDFViewerBean();
		try {
			bean.loadPDF(nomFichier);
			bean.getToolbar().setVisible (true);
		} catch (PDFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println("Document '"+nomFichier+"' generated"); 
		}
	
	private static void populate(Habitation hab, int mois , int an ) throws DocumentException {
		try {
			// préparation entête avec logo et date d'édition du document
			// ajout image au document
			document.add(UtilF.getImage());
			// ajout de la période de facturation
			document.add(UtilF.getPeriodeFacture(mois));
			// ajout de la date d'édition du document
			document.add(UtilF.getDateFacture());
			// instanciation de paragraphes pour l'identité et l'adresse de l'usager
			Paragraph identite = UtilF.getIdentite(unUsager);
			Paragraph adresseRue = UtilF.getAdresseRue(hab);
			Paragraph adresseVille = UtilF.getAdresseVille(hab);
			// décalage à 300 
			identite.setIndentationLeft(300);
			adresseRue.setIndentationLeft(300);
			adresseVille.setIndentationLeft(300);
			// on descend de quelques lignes
			document.add(UtilF.getParagrapheVide(5));
			// on ajoute les éléments sur l'usager
			document.add(identite);
			document.add(adresseRue);
			document.add(adresseVille);
			// on descend de quelques lignes
			document.add(UtilF.getParagrapheVide(4));
			document.add(new Paragraph("Code usager : " + unUsager.getCode()));
			document.add(new Paragraph("Récapitulatif des pesées des poubelles au mois de : " + tabMois[mois]));
			// on descend de quelques lignes
			document.add(UtilF.getParagrapheVide(3));
			// on récupère les poubelles de l'habitation
			ArrayList<Poubelle> listePoubelle = hab.getLesPoubelles();
			// on traite les poubelles partie commune, 0 pour usager individuel
			traitementPoubelle(listePoubelle, an , mois, 0);
			document.add(table);
		}

		catch(Exception e) {
			throw new DocumentException(e);
		}
	}


	
	private static void traitementPoubelle(ArrayList<Poubelle> listeP, int an , int mois, int type) {
		// total général facture
		double totalGeneral = 0;
		for (Poubelle p : listeP) {
			// instanciation du tableau pour la poubelle
			table = new PdfPTable(4);
			// première ligne : numero de poubelle et nature des déchets
			table.addCell(UtilF.titrePoubelle(p.getIdPoubelle().toString(), p.getNature().getLibelle()));
			// ligne entête levée
			table.addCell(UtilF.elementEntete("Date de pesée"));
			table.addCell(UtilF.elementEntete("nombre de kg"));
			table.addCell(UtilF.elementEntete("prix HT au kg"));
			table.addCell(UtilF.elementEntete("total HT "));
			// récupération des levées pour le mois et l'année concernés
			ArrayList<Levee> leveeMois = p.getLesLevees(an, mois);
			// déclaration et mise à zéro variable de cumul poubelle
			double totalPoubelle = 0;
			// parcours des levées
			for(Levee l : leveeMois)
			{
				// ajout des cellules pour une levée
				// date de levée

				table.addCell(UtilF.elementLigne(sdf.format(l.getLaDate())));
				// poids de la levée
				table.addCell(UtilF.elementLigne(Double.toString(l.getPoids())));
				// prix unitaire au kg
				table.addCell(UtilF.elementLigne(Double.toString(p.getNature().getTarif())));
				// calcul et ajout du totalLigne
				double totalLigne = l.getPoids() * p.getNature().getTarif();
				// cumul totalLigne dans totalPoubelle
				totalPoubelle = totalPoubelle + totalLigne;
				// total ligne
				table.addCell(UtilF.elementLigne(totalLigne));
			}
			// libellé ligne de totalisation
			table.addCell(UtilF.elementTotal("total HT    "));
			// cellule totalPoubelle en gris
			cell = UtilF.elementTotal(totalPoubelle);
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(cell);
			// ajout de la table au document
			try {
				document.add(table);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// ajout de paragraphe vide
			try {
				document.add(UtilF.getParagrapheVide(2));
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			// cumul totalPoubelle dans total general
			totalGeneral = totalGeneral + totalPoubelle;
		}
		table = new PdfPTable(4);
		table.addCell(UtilF.elementTotal("Total général HT  "));
		table.addCell(UtilF.elementTotal(totalGeneral));
		double tva = totalGeneral * tauxTva / 100; 
		tva = (double) Math.round(tva * 100)/ 100; 
		double totalTTC = totalGeneral + tva;
		table.addCell(UtilF.elementTotal("Montant TVA  "));
		table.addCell(UtilF.elementTotal(tva));
		// total TTC
		table.addCell(UtilF.elementTotal("Total TTC  "));
		cell = UtilF.elementTotal(totalTTC);
		// cellule montant ttc en gris
		if (type == 0) {
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		}
		table.addCell(cell);
	}
}


