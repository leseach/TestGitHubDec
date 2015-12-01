package com.vue;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import com.metier.*;
import com.persistance.AccesData;
import com.sun.media.sound.Toolkit;
/**
 * Cr�er le formulaire avec le menu g�n�ral
 * lance un formulaire d'authentification
 * v�rifie l'authentification par acc�s � la base de donn�es
 * si authentification correcte  active les items menus en fonction du niveau de droit
 * et affiche un �vran de bienvenue
 * sinon affiche un message d'erreur et redemande l'authentification
 * g�re les �v�nements du menu pour charger le panel associ�
 * fonctionnalit� pour insertion des pes�es
 * fonctionnalit� pour g�n�rer les factures
 */
public class MenuTrisel extends JFrame {
	// proprit��s du formulaire 
	private JMenuBar menuBar;
	private JMenu mnFichier;
	private JMenu mnLevee;
	private JMenu mnFacture;
	private JMenu mnParametre;
	private JMenuItem mntmQuitter;
	private JMenuItem mntmEditionFacture;
	private JMenuItem mntmInsertionLevee;
	private JMenuItem mntmMiseAJourParametre;
	private JMenuItem mntmMiseAJourParametreBdialog;
	// composants pour l'authentification
	// doivent �tre globaux pour �tre accessibles � la proc�dure �v�nementielle
	private JTextField txtNomUtilisateur;
	private JPasswordField pwdMotDePasse;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// changement icone
					Image icone = java.awt.Toolkit.getDefaultToolkit().getImage("Trisel.jpg");
					// instanciation fen�tre    
					MenuTrisel frame = new MenuTrisel();
					// affectation nouvelle icone � la fen�tre
					frame.setIconImage(icone);
					// affichage de la fen�tre
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public MenuTrisel() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 754, 544);
		// initialisation du titre de la fen�tre
		setTitle("Soci�t� Trisel Gestion facture ordures m�nag�res");
		// pas de gestionnaire de placement
		this.getContentPane().setLayout(null);
		// cr�ation barre de menu
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		// instanciation item Fichier et ajout � la barre de menu
		mnFichier = new JMenu("Fichier");
		menuBar.add(mnFichier);
		// instanciation itemmenu Quitter
		mntmQuitter = new JMenuItem("Quitter");
		// �v�nement sur quitter, fermeture de la fen�tre
		mntmQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		// ajout de l'item au menu fichier
		mnFichier.add(mntmQuitter);
		// item Lev�e
		mnLevee = new JMenu("Lev�e");
		menuBar.add(mnLevee);
		// item facture
		mnFacture = new JMenu("Facture");
		menuBar.add(mnFacture);
		//this.setContentPane(new PanelAuthentification());
		this.setContentPane(panelAuthentification());
		mntmEditionFacture = new JMenuItem("Edition facture");
		mntmEditionFacture.setEnabled(false);
		mntmEditionFacture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editionfacture();
			}
		});
		mnFacture.add(mntmEditionFacture);
		mntmInsertionLevee = new JMenuItem("Insertion lev�e");
		mntmInsertionLevee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertionLevee();
			}
		});
		mnLevee.add(mntmInsertionLevee);
		mntmInsertionLevee.setEnabled(false);
		
		mnParametre = new JMenu("Param�tre");
		menuBar.add(mnParametre);
		
		mntmMiseAJourParametre = new JMenuItem("Mise � jour parametres fichier texte");
		mntmMiseAJourParametre.setEnabled(false);
		mntmMiseAJourParametre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String 	nomFichier = "paramAppli.ini";
				try { 
					Runtime.getRuntime().exec("explorer.exe " + nomFichier);
				} 
				catch (IOException ex) { ex.printStackTrace(); 
				}
			}
		});
	
		mnParametre.add(mntmMiseAJourParametre);
		mntmMiseAJourParametre.setEnabled(false);
		mntmMiseAJourParametreBdialog = new JMenuItem("Mise � jour parametres boite de dialogue");
		mntmMiseAJourParametreBdialog.setEnabled(false);
		mntmMiseAJourParametreBdialog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				miseAJourParametre();
			}
		});
	
		mnParametre.add(mntmMiseAJourParametreBdialog);
		mntmMiseAJourParametreBdialog.setEnabled(false);
		this.setContentPane(panelAuthentification());
	
	}
	private void insertionLevee() {
		this.setContentPane(new PanelLevee());
		this.revalidate();
	}
	private void editionfacture() {
		this.setContentPane(new PanelEditionFacture());
		this.revalidate();
	}
	private void affichageFondEcran(String nomUtilisateur) 	{
		this.setContentPane(new PanelFondEcran(nomUtilisateur));
		this.revalidate();
	}
	public  void miseAJourParametre() {
		this.setContentPane(new PanelMiseAJourParam());
		this.revalidate();
	}
	// panel authentification 
	// doit �tre dans le formulaire car on doit agir sur les items menu 
	// en fonction du niveau de l'utilisateur
	public JPanel panelAuthentification() {
		JButton btnValider;
		JPanel p = new JPanel();
		p.setLayout(null);
		JLabel lblNomUtilisateur = new JLabel("Login");
		lblNomUtilisateur.setBounds(91, 96, 100, 14);
		p.add(lblNomUtilisateur);
		JLabel lblMotDePasse = new JLabel("Mot de passe ");
		lblMotDePasse.setBounds(91, 147, 89, 14);
		p.add(lblMotDePasse);
		txtNomUtilisateur = new JTextField();
		txtNomUtilisateur.setBounds(267, 93, 116, 20);
		p.add(txtNomUtilisateur);
		txtNomUtilisateur.setColumns(10);
		pwdMotDePasse = new JPasswordField();
		pwdMotDePasse.setBounds(267, 147, 89, 20);
		p.add(pwdMotDePasse);
		btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// r�cup�ration des donn�es saisies
				String login = txtNomUtilisateur.getText();
				// la m�thode getText sur un password est d�pr�ci�e
				// il faut passer par getpassword qui ram�ne un tableau de caractere
				// il faut donc reconstituer la chaine
				String mdp = new String(pwdMotDePasse.getPassword());
				// r�cup�ration de l'utilisateur dans la base
				Utilisateur u = AccesData.getUtilisateur(login, mdp);
				// test d'existence
				if (u != null) {
					// activation menuItem en fonction du niveau
					paramNiveau(u.getNiveau());
					// insertion du panel de bienvenue
					affichageFondEcran(u.getNomUtilisateur());
				}
				else
				{
					// affichage message
					afficheMessage("Authentification erron�e");
					// effacement donn�es du formulaire
					txtNomUtilisateur.setText("");
					pwdMotDePasse.setText("");
					txtNomUtilisateur.requestFocus();
				}

			}
		});
		btnValider.setBounds(267, 201, 100, 34);
		p.add(btnValider);
		return p;
	}
	// parametrage visualisation item menu en fonction du niveau
	// niveau 0 acc�s total
	// niveau 1 Levee uniquement
	// niveau 2 facture uniquement
	public void paramNiveau(int niveau) {
		switch (niveau) {
		case 0 :{
			mntmEditionFacture.setEnabled(true);
			mntmInsertionLevee.setEnabled(true);
			mntmMiseAJourParametre.setEnabled(true);
			mntmMiseAJourParametreBdialog.setEnabled(true);
			break; 
		}
		case 1 :{
			mntmEditionFacture.setEnabled(true);
			break; 
		}
		case 2 :{
			mntmInsertionLevee.setEnabled(true);
			break; 
		}
		default : break;
		}
	}
	
	private void afficheMessage(String message)
	{
		JOptionPane.showMessageDialog(null,message);
	}
}


