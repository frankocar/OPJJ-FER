package hr.fer.zemris.java.hw11.jnotepadapp;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadapp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LJLabel;

/**
 * A statusbar component for use in {@link JNotepadPP}. Tracks current length of text,
 * line and column where the cursor is positioned, length of selection and current date and time.
 * 
 * @author Franko
 *
 */
public class StatusBar extends JPanel {

	/** Default serial version UID */
	private static final long serialVersionUID = 1L;
	/**
	 * A tab currently being tracked
	 */
	private TextAreaTab tab;
	/**
	 * A label marking the current length of text
	 */
	private JLabel lengthNum;
	/**
	 * A label marking the current line cursor position
	 */
	private JLabel lineNum;
	/**
	 * A label marking the current column cursor position
	 */
	private JLabel columnNum;
	/**
	 * A label marking the length of current selection
	 */
	private JLabel selectionNum;
	/**
	 * Clock label 
	 */
	private JLabel clock;
	
	/**
	 * Localization provider for the parent form
	 */
	private FormLocalizationProvider flp;
	
	/**
	 * Clock runs while this is false
	 */
	private volatile boolean stopClock;
	
	/**
	 * Listener for caret changes
	 */
	private CaretListener listener;
	
	/**
	 * Initializes a new statusbar
	 * 
	 * @param tab Tab currently being tracked
	 * @param parent Parent form
	 * @param flp Parent form language provider
	 */
	public StatusBar(TextAreaTab tab, JFrame parent, FormLocalizationProvider flp) {
		this.tab = tab;	
		this.flp = flp;
		clock = new JLabel();
		stopClock = false;
		startClock();
		
		parent.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				stopClock();
			}
		});
		
		setUp();
	}

	/**
	 * Sets up the GUI
	 */
	private void setUp() {		
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		
		lengthNum = new JLabel(": 0");
		lineNum = new JLabel(": 0");
		columnNum = new JLabel(": 0");
		selectionNum = new JLabel(": 0");
		
		listener = new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				JTextArea text = (JTextArea) e.getSource();
				updateLabel(text);
			}
		};
		tab.getTextArea().addCaretListener(listener);		
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));		
		add(new LJLabel("length", flp));
		add(lengthNum);
		add(Box.createHorizontalGlue());
		
		add(new LJLabel("line", flp));
		add(lineNum);
		add(Box.createHorizontalStrut(10));
		add(new LJLabel("column", flp));;
		add(columnNum);
		add(Box.createHorizontalStrut(10));
		add(new LJLabel("selection", flp));;
		add(selectionNum);
		add(Box.createHorizontalStrut(10));
		
		add(Box.createHorizontalGlue());
		add(clock);	
		
	}
	
	/**
	 * Updates the statusbar with current info when called
	 * 
	 * @param text Tracked {@link JTextArea}
	 */
	private void updateLabel(JTextArea text) {
		int caret = text.getCaretPosition();
		int lineNo;
		try {
			lineNo = text.getLineOfOffset(caret);
			lengthNum.setText(": " + tab.getNumOfAllChars());
			lineNum.setText(": " + lineNo);
			columnNum.setText(": " + (caret - text.getLineStartOffset(lineNo)));
			selectionNum.setText(": " + Math.abs(text.getCaret().getDot() - text.getCaret().getMark()));
		} catch (BadLocationException ignorable) {
		}
	}
	
	/**
	 * Changes the tab currently being tracked
	 * 
	 * @param newTab New tab to track
	 */
	public void changeTab(TextAreaTab newTab) {
		tab.getTextArea().removeCaretListener(listener);
		this.tab = newTab;
		updateLabel(tab.getTextArea());
		tab.getTextArea().addCaretListener(listener);
	}
	
	/**
	 * Starts a clock to show in right corner
	 */
	private void startClock() {
		Thread worker = new Thread(() -> {
			while (!stopClock) {
				SwingUtilities.invokeLater(() -> {
					clock.setText(
							LocalDateTime.now().toString().substring(0, 19).replace("T", " ").replaceAll("-", "/"));
				});
				try {
					Thread.sleep(250);
				} catch (InterruptedException ignorable) {
				}
			}
		});
		worker.setDaemon(true);
		worker.start();
	}
	
	/**
	 * Stops the clock 
	 */
	private void stopClock() {
		stopClock = true;
	}
	
}
