package hr.fer.zemirs.java.p08;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class Prozor9 extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Random rnd = new Random();

	public Prozor9() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(500, 200);
		setTitle("Moj prvi prozor");
		
		initGUI();
		
		pack();
		
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		
		cp.setLayout(new BorderLayout());
		
		MojModel<String> model = new MojModel<>();
		model.dodaj("Ivo");
		model.dodaj("Anica");
		model.dodaj("Petra");
		JList<String> lista = new JList<>(model);
		
		cp.add(new JScrollPane(lista), BorderLayout.CENTER);
		
		JButton gumb = new JButton("Dodaj");
		cp.add(gumb, BorderLayout.PAGE_END);
		
		gumb.addActionListener(e -> {
			model.dodaj("Ime " + rnd.nextInt());
		});
		
		BrojElemenata<String> brojElemenata = new BrojElemenata<>(model);
		cp.add(brojElemenata, BorderLayout.PAGE_START);
		
	}
	
	private static class BrojElemenata<E> extends JLabel {
		/** */
		private static final long serialVersionUID = 1L;
		private ListModel<E> model;

		public BrojElemenata(ListModel<E> model) {
			super();
			this.model = model;
			this.model.addListDataListener(new ListDataListener() {
				
				@Override
				public void intervalRemoved(ListDataEvent e) {
					azuriraj();
				}
				
				@Override
				public void intervalAdded(ListDataEvent e) {
					azuriraj();
				}
				
				@Override
				public void contentsChanged(ListDataEvent e) {
					azuriraj();
				}
			});
			azuriraj();
		}
		
		private void azuriraj() {
			setText("Broj elemenata u listi: " + model.getSize());
		}
	}
	
	
	private static class MojModel<E> implements ListModel<E> {

		private List<E> podatci = new ArrayList<>();
		private List<ListDataListener> promatraci = new ArrayList<>();
		
		@Override
		public void addListDataListener(ListDataListener l) {
			promatraci.add(l);
		}

		@Override
		public E getElementAt(int index) {
			return podatci.get(index);
		}
		
		public void dodaj(E element) {
			podatci.add(element);
			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, podatci.size() - 1, podatci.size() - 1);
			for (ListDataListener l : promatraci) {
				l.intervalAdded(event);
			}
		}

		@Override
		public int getSize() {
			return podatci.size();
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			promatraci.remove(l);
		}
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Prozor9().setVisible(true);
		});
	}

}
