package com.metier;

import java.util.ArrayList;

public  class Habitation {
	private String idHabitation;
	private String adresseRue;
	private String adresseVille;
	private Usager usager;
	private ArrayList<Poubelle> lesPoubelles;
	public Habitation(String idHabitation, String adresseRue, String adresseVille, Usager unUsager ) {
			this.idHabitation = idHabitation;
		    this.adresseRue = adresseRue;
			this.adresseVille = adresseVille;
			usager = unUsager;
			lesPoubelles = new ArrayList<Poubelle>();
	}
	public String getIdHabitation() {
		return idHabitation;
	}
	public String getAdresseRue() {
		return adresseRue;
	}
	public String getAdresseVille() {
		return adresseVille;
	}
	public Usager getUsager() {
		return usager;
	}
	public ArrayList<Poubelle> getLesPoubelles() {
		return lesPoubelles;
	}
	public void ajoutPoubelle(Poubelle unePoubelle) {
	lesPoubelles.add(unePoubelle);
	}
	public double getCout(int an , int mois)
	{
		double total=0;
		// parcours des poubelles pour cumul des poubelles du mois et de l'année
		for(Poubelle p : lesPoubelles) {
			total = total + p.getCout(an, mois);
		}
		return (double) Math.round(total * 100) / 100;
	}
	@Override
	public String toString() {
		return "Habitation [idHabitation=" + idHabitation + ", adresseRue="
				+ adresseRue + ", adresseVille=" + adresseVille + "]";
	}
}
