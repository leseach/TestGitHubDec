package com.metier;

public class Utilisateur {
private String idUtilisateur;
private String nomUtilisateur;
private String prenomutilisateur;
private String login;
private String mdp;
private int niveau;
public Utilisateur(String idUtilisateur, String nomUtilisateur,
		String prenomutilisateur, String login, String mdp, int niveau) {
	super();
	this.idUtilisateur = idUtilisateur;
	this.nomUtilisateur = nomUtilisateur;
	this.prenomutilisateur = prenomutilisateur;
	this.login = login;
	this.mdp = mdp;
	this.niveau = niveau;
}
public String getIdUtilisateur() {
	return idUtilisateur;
}
public String getNomUtilisateur() {
	return nomUtilisateur;
}
public String getPrenomutilisateur() {
	return prenomutilisateur;
}
public String getLogin() {
	return login;
}
public String getMdp() {
	return mdp;
}

public int getNiveau() {
	return niveau;
}
@Override
public String toString() {
	return "Utilisateur [idUtilisateur=" + idUtilisateur + ", nomUtilisateur="
			+ nomUtilisateur + ", prenomutilisateur=" + prenomutilisateur
			+ ", login=" + login + ", mdp=" + mdp + ", niveau=" + niveau + "]";
}

}
