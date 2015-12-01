package com.pdf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.metier.Habitation;
import com.metier.Usager;
import com.util.Parametre;

public class UtilF {
	private static final String in = "Trisel.jpg";
	private static final String  tabMois[]={"","janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre" };
	private static final String  tabMoisDossier[]={"","janvier", "fevrier", "mars", "avril", "mai", "juin", "juillet", "aout", "septembre", "octobre", "novembre", "decembre" };
	public static Paragraph getIdentite(Usager unUsager) {
		return new Paragraph(unUsager.getNom() + "  " + unUsager.getPrenom());
	}
	public static Paragraph getAdresseRue(Habitation h) {
		return  new Paragraph(h.getAdresseRue());
	}
	public static Paragraph getAdresseVille(Habitation h) {
		return  new Paragraph(h.getAdresseVille());
	}
	public static PdfPCell titrePoubelle(String numPoubelle, String nature) {
		// cellule avec numéro de poubelle et nature des déchets
		PdfPCell cell = new PdfPCell(new Phrase("Poubelle:" + numPoubelle + " Nature des déchets: " + nature,FontFactory.getFont(FontFactory.COURIER, 12f,Font.BOLD)));
		// occupe les 4 colonnes
		cell.setColspan(4);
		// centrage de l'affichage
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
		return cell;
	}
	public static PdfPCell elementEntete(String libelle) {
		PdfPCell cell = new PdfPCell(new Phrase(libelle));
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
		return cell;
	}
	public static PdfPCell elementLigne(double montant) {
		montant = (double) Math.round(montant * 1000) / 1000;
		PdfPCell cell = new PdfPCell(new Phrase(Double.toString(montant)));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
		return cell;
	}
	public static PdfPCell elementLigne(String libelle) {
		PdfPCell cell = new PdfPCell(new Phrase(libelle));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
		return cell;
	}
	public static PdfPCell elementTotal(String libelle) {
		PdfPCell cell = new PdfPCell(new Phrase(libelle));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
		return cell;
	}
	public static PdfPCell elementTotal(double montant) {
		//  précision 2 pour le total
		// ne pas oublier de transtyper en double
		montant = (double) Math.round(montant * 100) / 100;
		PdfPCell cell = new PdfPCell(new Phrase(Double.toString(montant)));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
		return cell;
	}
	public static String getNomFichier(Habitation hab, int mois, int an)
	{ 
		
		// caractere \\ pour \
		String nomDossier = Parametre.getCheminFacturePdf() + "\\" + tabMoisDossier[mois];
		String nomFichier = hab.getIdHabitation() + "-Facture-" + tabMoisDossier[mois] + "-" + an + ".pdf";
		String cheminComplet  = nomDossier + "\\" + nomFichier;
		return cheminComplet;
		
	}
	public static Image  getImage() {
		// logo trisel
		// récupération image
		Image image = null;
		try {
			image = Image.getInstance(in);
			// positionnement en fonction du repère coin gauche en bas de la page
			image.setAbsolutePosition (20f, 680F);
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}
	public static Paragraph getPeriodeFacture(int mois) {
		// instanciation paragraphe pour la date, police courrier , taille 11 , 0?
		Paragraph periodeFacture = new Paragraph("Facture du mois de " + tabMois[mois] ,FontFactory.getFont(FontFactory.COURIER, 11f,0));
		// alignement à droite
		periodeFacture.setAlignment(Element.ALIGN_RIGHT);
		return periodeFacture;
	}
	public static Paragraph getDateFacture() {
		// récupération date du jour à partir de la classe calendar
		Calendar cal = Calendar.getInstance();
		java.util.Date date = cal.getTime();
		//format de la date
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE  dd MMMM yyyy ");
		Paragraph dateFacture = new Paragraph("Editée le " + sdf.format(date) ,FontFactory.getFont(FontFactory.COURIER, 11f,0));
		// alignement à droite
		dateFacture.setAlignment(Element.ALIGN_RIGHT);// alignement à droite
		return dateFacture;
	}
	public static Paragraph getParagrapheVide(int nbLigne) {
		String ligne="";
		for (int i=0; i < nbLigne; i++) {
			ligne = ligne + "\n";
		}
		return new Paragraph(ligne);
	}

}
