package com.metier;

public class TypeDechet {
	private String code;
	private String libelle;
	private double tarif;
	public TypeDechet(String code, String libelle, double tarif) {
		this.code = code;
		this.libelle = libelle;
		this.tarif = tarif;
	}
	public double getTarif() {
		return tarif;
	}
	public void setTarif(double tarif) {
		this.tarif = tarif;
	}
	public String getCode() {
		return code;
	}
	public String getLibelle() {
		return libelle;
	}
}
