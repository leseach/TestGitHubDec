package com.persistance;

import java.sql.*;

import com.util.Parametre;
//	 classe permettant l'ouverture, la fermeture de la base mysql bd_trisel sur localhost p
public class AccesBd {
	// description des propri�t�s
	private  static Connection con=null;
	private static String url;
	public static Connection  getInstance() {
		// acc�s direct sans source de donn�es odbc
		//url="jdbc:mysql://localhost/bd_trisel";
		url = Parametre.getCheminBd();
		if (con==null)
		{
			try
			{// chargement du driver, librairie mysql connector li�e au projet
				Class.forName("com.mysql.jdbc.Driver");
				// connexion utilisateur root, pas de mdp
				con=DriverManager.getConnection(url,"root","");
			}
			// ouverture de la connexion
			catch (ClassNotFoundException e)
			{
				System.out.println(e.getMessage());
				System.out.println("�chec driver");
			}
			catch (SQLException e)
			{
				System.out.println(e.getMessage());
				System.out.println("�chec de connexion bd ");
			}
		}

		return con;
	}
	//	 fermeture de la connexion
	public static void close(){
		try { 
			con.close();
		}
		catch(Exception e) {e.printStackTrace();
		System.out.println("probl�me lors de la fermeture");}
	}
}


