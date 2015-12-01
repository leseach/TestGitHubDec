package com.metier;

import java.util.ArrayList;
import java.util.Calendar;

public class Poubelle {
	private String idPoubelle;
	private TypeDechet nature;
	private String idHabitation;
	private ArrayList<Levee> lesLevees;
	
	
	public Poubelle(String id, TypeDechet nature, String idHabitation) {
		this.idPoubelle = id;
		this.nature = nature;
		this.idHabitation = idHabitation;
		lesLevees= new ArrayList<Levee>();
	}
	public Poubelle(String id, TypeDechet nature) {
		this.idPoubelle = id;
		this.nature = nature;
		this.idHabitation = "";
		lesLevees= new ArrayList<Levee>();
	}
	public String getIdPoubelle() {
		return idPoubelle;
	}
	public TypeDechet getNature() {
		return nature;
	}
	public String getIdHabitation() {
		return idHabitation;
	}
	
	public void setIdHabitation(String idHabitation) {
		this.idHabitation = idHabitation;
	}
	public void ajoutLevee(Levee uneLevee)
	{
		lesLevees.add(uneLevee);
	}
	
	public ArrayList<Levee> getLesLevees() {
		return lesLevees;
	}
	public ArrayList<Levee> getLesLevees(int an, int mois) {
		ArrayList<Levee> lesLeveesMoisAn= new ArrayList<Levee>();
		Calendar cal = Calendar.getInstance();
		for(Levee uneLevee : lesLevees)
		{
			// passage par la classe Calendar pour extraire les composants de la date
			cal.setTime(uneLevee.getLaDate());
			int year = cal.get(Calendar.YEAR);
			// extraction du mois mettre + 1 car démarre à 0 et non pas 1
			int month = cal.get(Calendar.MONTH) + 1;
			// cumul des poids pour le mois
			if ((year == an ) && (month == mois)) {

				lesLeveesMoisAn.add(uneLevee);
			}

		}
		return lesLeveesMoisAn;
	}
	public double getCout(int an, int mois) {
		double totalPoids=0;
		double cout;
		Calendar cal = Calendar.getInstance();
		for(Levee uneLevee : lesLevees)
		{
			// passage par la classe Calendar pour extraire les composants de la date
			cal.setTime(uneLevee.getLaDate());
			int year = cal.get(Calendar.YEAR);
			// extraction du mois mettre + 1 car démarre à 0 et non pas 1
			int month = cal.get(Calendar.MONTH) + 1;
			// cumul des poids pour le mois
			if ((year == an ) && (month == mois)) {

				totalPoids = totalPoids + uneLevee.getPoids();
			}

		}
		// prise en compte du tarif en fonction de la nature des déchets
		cout = totalPoids * nature.getTarif();
		return (double) Math.round(cout*100)/100;
	}
}
