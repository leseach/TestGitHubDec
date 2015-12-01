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
 * Panel Levée
 * vérifie le nombre de levées à traiter
 * si égal 0 message " aucun fichier à traiter"
 * sinon bouton lancement mise à jour actif
 * fait appel à la méthode insertion lévée de la classe InsertionLevee
 */
public class PanelLevee extends JPanel {
    // bouton mise à jour	
	private JButton btnLancer;
	// zone de texte pour afficher le nombre de fichiers à traiter
	private JLabel lblInfo;
	
	public PanelLevee() {
		// récupération du nombre de fichiers à traiter
		int nbFichier = Parametre.nbLevee();
		// instanciation objets graphiques
		setLayout(null);
		btnLancer = new JButton("Lancer la mise \u00E0 jour");
		btnLancer.setBounds(65, 124, 253, 39);
		// procédure évènementielle sur bouton qui lance l'insertion
		btnLancer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertionLevee.insertionLevee();
				afficheMessage("Mise à jour effectuée");
			}
		});
		add(btnLancer);
		// initialisation label de la zone de texte
		String textelabel = " Il y a  " + nbFichier + " fichier(s) à traiter";
		lblInfo = new JLabel(textelabel);
		lblInfo.setBounds(65, 75, 210, 14);
		add(lblInfo);
		// désactivation du bouton si pas de fichier à traiter
		if (nbFichier == 0) {
			btnLancer.setEnabled(false);
			}
		
	}
	// méthode pour afficher un message dans une boite de dial
	private void afficheMessage(String message)
	{
		JOptionPane.showMessageDialog(null,message);
	}
}
