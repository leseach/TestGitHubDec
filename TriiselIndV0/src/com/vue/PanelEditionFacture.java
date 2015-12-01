package com.vue;

import javax.swing.JPanel;

import com.metier.Habitation;
import com.pdf.Facture;
import com.persistance.AccesData;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Calendar;

public class PanelEditionFacture extends JPanel implements PropertyChangeListener{

	/**
	 * Create the panel.
	 */
	private JMonthChooser monthChooser;
	private JYearChooser yearChooser;
	private Calendar calendar;
	private JButton btnLancerEdition;
	private int an;
	private int mois;
	public PanelEditionFacture() {
		setLayout(null);
		calendar = Calendar.getInstance();
		an = getYear();
		mois  = getMonth() + 1;
		monthChooser = new JMonthChooser();
		monthChooser.addPropertyChangeListener(this);
		monthChooser.setBounds(58, 58, 97, 20);
		add(monthChooser);
		yearChooser = new JYearChooser();
		yearChooser.addPropertyChangeListener(this);
		yearChooser.setBounds(197, 58, 47, 20);
		add(yearChooser);

		btnLancerEdition = new JButton("Lancer \u00E9dition");
		btnLancerEdition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Habitation> listeHab = AccesData.getLesHabitations();
				for(Habitation h : listeHab) {
					if (h.getCout(an, mois) != 0) {
							Facture.editionFactureIndividuelle(h, mois, an);
					}
					else {
				System.out.println( "pas de levée pour habitation " + h.getIdHabitation() + " " + h.getUsager().getNom());
						
				}
					
				
			}}
		});
		btnLancerEdition.setBounds(68, 113, 176, 50);
		add(btnLancerEdition);

	}	 public int getYear(){
		return calendar.get(Calendar.YEAR);
	}
	public int getMonth(){
		return calendar.get(Calendar.MONTH);
	}

	// procédure de tests des sources d'évènement
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("year")){
			an = (Integer)evt.getNewValue();
		}
		if(evt.getPropertyName().equals("month")){
			mois  = (Integer)evt.getNewValue() + 1;
		}
	} 
}


