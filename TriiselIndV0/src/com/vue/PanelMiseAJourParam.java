package com.vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.util.Parametre;

/**
 * Panel qui permet la saisie de l'URL de l abase dans une boite de dialogue 
 * et la sélection des chemins des dossiers data  de l'application
 * avec le contrôle JFileChooser
 * les informations sont ensuite écrites dans le fichier texte paramAppli.ini
 * 
 */
public class PanelMiseAJourParam extends JPanel {

	private JFileChooser chooser;
	private  JButton btnChoisirDossierFacture;
	private JButton btnChoisirDossierLevee;
	private JButton btnDossierTraites;
	private String dossierFacture = "";
	private String dossierATraiter = "";
	private String dossierTraites = "";
	private String dossierLogLevee = "";
	private JButton btnURLbd;
	private String urlBd ;
	private JButton btnEnregistrer;
	private int nbParam = 0;
	private JButton btnLogLevee;
	public PanelMiseAJourParam() {
		this.setBounds(100, 100, 450, 300);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		// saisie de l'url de la base dans une boite de dialogue
		btnURLbd = new JButton("Saisir URL de la base ");
		btnURLbd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				urlBd = (String)JOptionPane.showInputDialog( null, "Donner l'url de la base de données :", "Dialog", JOptionPane.PLAIN_MESSAGE);
				// test bouton annuler qui ramène null;
				if (urlBd != null) {
					// test sur bouton ouvrir sans saisie
					// on boucle sur la saisie
					Boolean sortie = false;
					while (urlBd.equals("") && (!sortie) ) {
						urlBd = (String)JOptionPane.showInputDialog( null, "Donner l'url de la base de données :", "Dialog", JOptionPane.PLAIN_MESSAGE);
						if (urlBd == null) {
							urlBd = "";
							sortie = true;
						}
					}
					if (!sortie) {
						System.out.println(urlBd);
						btnURLbd.setEnabled(false);
						testNbParam();
					}
				}
			}

		});
		btnURLbd.setBounds(71, 30, 242, 23);
		this.add(btnURLbd);
		// instanciation file chooser
		chooser = new JFileChooser();
		// mode 0 on ne peut sélectionné que les fichiers
		// mode 1 dossiers uniquement
		// mode 2 dossiers et fichiers
		// ici c'est le mode 2 qui nous intéresse
		chooser.setFileSelectionMode(1);
		// changement du chemin d'accès par défaut pour affichage
		String curDir="C:\\Microsoft Forefront TMG Client";
		File f= new File(curDir);
		chooser.setCurrentDirectory(f);
		btnChoisirDossierFacture = new JButton("Choisir dossier facture");
		btnChoisirDossierFacture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser.setDialogTitle("choisir le dossier facture");
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					dossierFacture = chooser.getSelectedFile().getAbsolutePath();
					btnChoisirDossierFacture.setEnabled(false);
					testNbParam();
				}
			}
		});
		btnChoisirDossierFacture.setBounds(71, 64, 242, 23);
		this.add(btnChoisirDossierFacture);

		btnChoisirDossierLevee = new JButton("Choisir dossier lev\u00E9es \u00E0 traiter ");
		btnChoisirDossierLevee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser.setDialogTitle("choisir le dossier des fichiers levées à traiter");
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					dossierATraiter	 = chooser.getSelectedFile().getAbsolutePath();
					btnChoisirDossierLevee.setEnabled(false);
					testNbParam();
				}}
		});
		btnChoisirDossierLevee.setBounds(71, 98, 242, 23);
		this.add(btnChoisirDossierLevee);

		btnDossierTraites = new JButton("Choisir dossier lev\u00E9es trait\u00E9es");
		btnDossierTraites.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooser.setDialogTitle("choisir le dossier des fichiers  traités");
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					dossierTraites	 = chooser.getSelectedFile().getAbsolutePath();
					btnDossierTraites.setEnabled(false);
					testNbParam();
				}
			}
		});
		btnDossierTraites.setBounds(71, 132, 242, 23);
		this.add(btnDossierTraites);

		btnLogLevee = new JButton("choisir dossier log lev\u00E9e");
		btnLogLevee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser.setDialogTitle("choisir le dossier des fichiers logs pour les levées");
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					dossierLogLevee	 = chooser.getSelectedFile().getAbsolutePath();
					System.out.println(dossierLogLevee);
					btnLogLevee.setEnabled(false);
					testNbParam();
				}
			}
		});
		btnLogLevee.setBounds(71, 166, 242, 23);
		add(btnLogLevee);
	
		btnEnregistrer = new JButton("Enregistrer");
		btnEnregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Parametre.enregistreParam(urlBd, dossierFacture, dossierATraiter, dossierTraites, dossierLogLevee);
			}
		});
		btnEnregistrer.setBounds(213, 201, 160, 23);
		this.add(btnEnregistrer);
		btnEnregistrer.setEnabled(false);
		
		
	}
	public void testNbParam() {
		// on incrémente le nombre de paramètres renseignés
		nbParam++;
		// si on arrive à 4 tout est Ok, on peut activer  le bouton enregistrer
		if (nbParam == 5) {
			btnEnregistrer.setEnabled(true);
		}
	}
}


