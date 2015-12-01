package com.vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.util.InsertionLevee;
import com.util.Parametre;
/**
 * Panel Lev�e
 * v�rifie le nombre de lev�es � traiter
 * si �gal 0 message " aucun fichier � traiter"
 * sinon bouton lancement mise � jour actif
 * fait appel � la m�thode insertion l�v�e de la classe InsertionLevee
 */
public class PanelLevee extends JPanel {
    // bouton mise � jour	
	private JButton btnLancer;
	// zone de texte pour afficher le nombre de fichiers � traiter
	private JLabel lblInfo;
	
	public PanelLevee() {
		// r�cup�ration du nombre de fichiers � traiter
		int nbFichier = Parametre.nbLevee();
		// instanciation objets graphiques
		setLayout(null);
		btnLancer = new JButton("Lancer la mise \u00E0 jour");
		btnLancer.setBounds(65, 124, 253, 39);
		// proc�dure �v�nementielle sur bouton qui lance l'insertion
		btnLancer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertionLevee.insertionLevee();
				afficheMessage("Mise � jour effectu�e");
			}
		});
		add(btnLancer);
		// initialisation label de la zone de texte
		String textelabel = " Il y a  " + nbFichier + " fichier(s) � traiter";
		lblInfo = new JLabel(textelabel);
		lblInfo.setBounds(65, 75, 210, 14);
		add(lblInfo);
		// d�sactivation du bouton si pas de fichier � traiter
		if (nbFichier == 0) {
			btnLancer.setEnabled(false);
			}
		
	}
	// m�thode pour afficher un message dans une boite de dial
	private void afficheMessage(String message)
	{
		JOptionPane.showMessageDialog(null,message);
	}
}
