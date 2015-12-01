package com.metier;

import java.util.ArrayList;

public class Usager {
	private String code;
	private String nom;
	private String prenom;
	private String nomUser; 
	private String motDePasse;
	// mise en place navigabilité bidirectionnelle
	private ArrayList<Habitation> listeHabInd;
	
	public Usager(String code, String nom, String prenom) {
		this.code = code;
		this.nom = nom;
		this.prenom = prenom;
		nomUser = "";
		motDePasse = "";
		listeHabInd = new ArrayList<Habitation>();
		
	}
	public String getCode() {
		return code;
	}
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
	
	public String getNomUser() {
		return nomUser;
	}
	public void setNomUser(String nomUser) {
		this.nomUser = nomUser;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public void ajoutHabInd(Habitation habIn) {
		listeHabInd.add(habIn);
	}
	
	
	public ArrayList<Habitation> getListeHabInd() {
		return listeHabInd;
	}

	@Override
	public String toString() {
		return "Usager [code=" + code + ", nom=" + nom + ", prenom=" + prenom + "]";
	}
}
