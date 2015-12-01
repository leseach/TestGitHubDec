package com.persistance;

import com.metier.Habitation;
import com.metier.Levee;
import com.metier.Poubelle;
import com.metier.TypeDechet;
import com.metier.Usager;
import com.metier.Utilisateur;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
public class AccesData {
	private static Connection con = AccesBd.getInstance();
	public static Usager getUsager(String idUsager)
	{
		Usager unUsager = null;
		ResultSet rsUsager = null;
		String sql = "select * from usager where idUsager ='"+idUsager +"'";
		try {
			rsUsager = con.createStatement().executeQuery(sql);
			if (rsUsager.next())
			{
				unUsager = new Usager(idUsager, rsUsager.getString("nom"), rsUsager.getString("prenom"));
				unUsager.setNomUser(rsUsager.getString("nomUser"));
				unUsager.setMotDePasse(rsUsager.getString("motDePasse"));
			}
		}
		catch (SQLException e)	{ 
			unUsager = null;
		}
		return unUsager;
	}
public static ArrayList<Habitation> getLesHabitations() {
	ArrayList<Habitation> lesHabitations = new ArrayList<Habitation>();
	ResultSet rsHab = null;
	String sql = "select idHabitation from habitation";
	try {
		rsHab = con.createStatement().executeQuery(sql);
		while  (rsHab.next())
		{
			lesHabitations.add(getHabitation(rsHab.getString("idHabitation")));
		}
	}
	catch (SQLException e)	{ 
		 
	}
	return lesHabitations;
		
}
	public static Habitation getHabitation(String idHabitation) {
		Habitation hab = null ;
		Usager u = null;
		Poubelle pb = null;
		TypeDechet typeD = null;
		Levee lv = null;
		String  sqlHab, sqlPoubelle, sqlLevee, sqlTypeDechet; 
		ResultSet rsHab, rsPoubelle, rsLevee, rsTypeDechet;
		// requête de sélection de l'habitation
		sqlHab = "select * from habitation  where idHabitation ='"+idHabitation +"'";
		try {
			// exécution requête
			rsHab = con.createStatement().executeQuery(sqlHab);
			// tentative lecture car 0 ou 1 tuple
			if (rsHab.next())
			{
				// récupération de l'usager concerné
				u = getUsager(rsHab.getString("idUsager"));
				// instanciation objet habitation
				hab = new Habitation(idHabitation, rsHab.getString("adresseRue"), rsHab.getString("adresseVille"), u);
				// recherche des poubelles de l'habitation dans la base
				sqlPoubelle = "select * from poubelle where idHabitation ='"+idHabitation +"'";
				rsPoubelle = con.createStatement().executeQuery(sqlPoubelle);
				// parcours car requête ramène 0 ou n tuples
				while (rsPoubelle.next()) {
					// recherche type de déchet
					String type = rsPoubelle.getString("idTypeDechet");
					// requête de recherche des informations sur le type de déchet
					sqlTypeDechet = "select * from typedechet where idtypedechet ='" + type + "'";
					// exécution requête , ramène 1 tuple
					rsTypeDechet = con.createStatement().executeQuery(sqlTypeDechet);
					if (rsTypeDechet.next()) {
						// instanciation objet TypeDechet
						typeD = new TypeDechet(type, rsTypeDechet.getString("libelle"), rsTypeDechet.getDouble("tarif"));
					}
					// instanciation objet poubelle
					pb = new Poubelle(rsPoubelle.getString("idPoubelle"), typeD, idHabitation);
					// recherche des levées
					sqlLevee = "select * from levee where idPoubelle='" + pb.getIdPoubelle() + "'";
					rsLevee = con.createStatement().executeQuery(sqlLevee);
					//parcours des levées
					while (rsLevee.next()) {
						lv = new Levee(rsLevee.getInt("idlevee"), rsLevee.getDate("ladate"), rsLevee.getDouble("poids"),pb.getIdPoubelle());
						pb.ajoutLevee(lv);
					}
					hab.ajoutPoubelle(pb);
				}
			}
		}
		catch (SQLException e)	{ 
			hab = null; 
		}
		return hab;
	}
	public static boolean ajoutLevee(Levee uneLevee)
	{
		
		boolean ok ;// conversion de la date en java.sql.Date
		java.sql.Date laDatesql= new java.sql.Date(uneLevee.getLaDate().getTime());
		// la date doit être entre ' ' pour que cela fonctionne
		String sqlLevee = "insert into levee(laDate, poids, idPoubelle) values('";
		sqlLevee = sqlLevee + laDatesql + "', "+ uneLevee.getPoids() + ", '" + uneLevee.getIdPoubelle() + "')";
		// System.out.println(sqlLevee);
		try {
			con.createStatement().execute(sqlLevee);
			ok = true;
		}
		catch (SQLException e)	{ 
			ok = false; 
		}
		return ok;
	}
	public static Utilisateur getUtilisateur(String login, String mdp) {
		Utilisateur u = null;
		ResultSet rsUtilisateur = null;
		String sqlUtilisateur = " select * from utilisateur where login ='" + login + "' and mdp ='"  + mdp + "'";
		try {
			rsUtilisateur = con.createStatement().executeQuery(sqlUtilisateur);
			if (rsUtilisateur.next())
			{
				String idUtilisateur = rsUtilisateur.getString("idUtilisateur");
				String nomUtilisateur = rsUtilisateur.getString("nomUtilisateur");
				String prenomUtilisateur = rsUtilisateur.getString("prenomUtilisateur");
				int niveau = rsUtilisateur.getInt("niveau");
				// instanciation objet Utilisateur
				u = new Utilisateur(idUtilisateur, nomUtilisateur, prenomUtilisateur, login, mdp, niveau);
			}
		}
		catch (SQLException e)	{ 
			u = null; 
		}
		return u;
	}
	public static Poubelle getPoubelle(String idPoubelle) {
		Poubelle p = null;
		TypeDechet typeD = null;
		Levee lv = null;
		ResultSet rsPoubelle = null;
		ResultSet rsTypeDechet = null;
		ResultSet rsLevee = null;
	
		// recherche des poubelles de l'habitation dans la base
		String sqlPoubelle = "select * from poubelle where idPoubelle ='"+idPoubelle +"'";
		try {
			rsPoubelle = con.createStatement().executeQuery(sqlPoubelle);
			// parcours car requête ramène 0 ou n tuples
			if (rsPoubelle.next()) {
				// recherche type de déchet
				String type = rsPoubelle.getString("idTypeDechet");
				// requête de recherche des informations sur le type de déchet
				String sqlTypeDechet = "select * from typedechet where idtypedechet ='" + type + "'";
				// exécution requête , ramène 1 tuple
				rsTypeDechet = con.createStatement().executeQuery(sqlTypeDechet);
				if (rsTypeDechet.next()) {
					// instanciation objet TypeDechet
					typeD = new TypeDechet(type, rsTypeDechet.getString("libelle"), rsTypeDechet.getDouble("tarif"));
				}
				// instanciation objet poubelle
				p = new Poubelle(rsPoubelle.getString("idPoubelle"), typeD, rsPoubelle.getString("idHabitation"));
				// recherche des levées
				String sqlLevee = "select * from levee where idPoubelle='" + p.getIdPoubelle() + "'";
				rsLevee = con.createStatement().executeQuery(sqlLevee);
				//parcours des levées
				while (rsLevee.next()) {
					lv = new Levee(rsLevee.getInt("idlevee"), rsLevee.getDate("ladate"), rsLevee.getDouble("poids"),p.getIdPoubelle());
					p.ajoutLevee(lv);
				}
			}
		}
		catch (SQLException e)	{ 
			p = null; 
		}
		return p;
	}
}
