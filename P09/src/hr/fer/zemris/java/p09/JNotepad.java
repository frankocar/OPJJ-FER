package hr.fer.zemris.java.p09;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.Document;

public class JNotepad extends JFrame{

	/**  */
	private static final long serialVersionUID = 1L;
	private JTextArea editor;
	private Path openedFilePath;
	
	public JNotepad() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		setLocationRelativeTo(null);
		
		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		editor = new JTextArea();
		cp.add(new JScrollPane(editor), BorderLayout.CENTER);
		
		createActions();
		createMenus();
		createToolbars();
	}
	
	private Action openDocumentAction = new AbstractAction() {		
		
		/**  */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			
			if (fc.showOpenDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			Path filePath = fc.getSelectedFile().toPath();
			
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepad.this, "Datoteka " + filePath + " se ne može čitati", "Pogreska",
						JOptionPane.ERROR);
				return;
			}
			
			String text = null;
			
			try {
				text = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(JNotepad.this,
						"Dogodila se pogreska pri učitavanju teksta iz datoteke " + filePath + ".", "Pogreska",
						JOptionPane.ERROR);
				return;
			}
			
			editor.setText(text);
			openedFilePath = filePath;
		}
	};
	
	private Action saveDocumentAction = new AbstractAction() {
		
		/**  */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (openedFilePath == null) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Save file");
				if (fc.showOpenDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JNotepad.this, "Odustali ste od snimanja, ništa nije pohranjeno", "Upozorenje",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				openedFilePath = fc.getSelectedFile().toPath();
			}
			
			try {
				Files.write(openedFilePath, editor.getText().getBytes(StandardCharsets.UTF_8));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(JNotepad.this,
						"Snimanje nije uspjelo. Nije jasno kakav je sadržaj datoteke na disku", "Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			JOptionPane.showMessageDialog(JNotepad.this, "Snimanje je uspješno provedeno!", "Informacija",
					JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	private Action exitAction = new AbstractAction() {
		
		/**  */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};
	
	private Action deleteSelectedPartAction = new AbstractAction() {
		
		/**  */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			
			if (len == 0) {
				return;
			}
			
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			
			try {
				doc.remove(offset, len);
			} catch (Exception ignorable) {
			}
		}
	};

	private Action toggleSelectedPartAction = new AbstractAction() {
		
		/**  */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			
			int offset = 0;
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			
			if (len == 0) {
				len = doc.getLength();
			} else {
				offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());				
			}			
			
			try {
				String text = doc.getText(offset, len);
				text = toggleText(text);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch (Exception ignorable) {
			}
		}

		private String toggleText(String text) {
			StringBuilder sb = new StringBuilder(text.length());
			
			for (char c : text.toCharArray()) {
				if (Character.isUpperCase(c)) {
					sb.append(Character.toLowerCase(c));
				} else if (Character.isLowerCase(c)) {
					sb.append(Character.toUpperCase(c));
				} else {
					sb.append(c);
				}
			}
			
			return sb.toString();
		}
	};
	
	private void createActions() {
		openDocumentAction.putValue(Action.NAME, "Open");
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to open document from disk");
		
		saveDocumentAction.putValue(Action.NAME, "Save");
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save a document to disk");
		
		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Used to terminate application");
		
		deleteSelectedPartAction.putValue(Action.NAME, "Delete selected part");
		deleteSelectedPartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F2"));
		deleteSelectedPartAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		deleteSelectedPartAction.putValue(Action.SHORT_DESCRIPTION, "Used to delete selced part of text");
		
		toggleSelectedPartAction.putValue(Action.NAME, "Toogle case");
		toggleSelectedPartAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F3"));
		toggleSelectedPartAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		toggleSelectedPartAction.putValue(Action.SHORT_DESCRIPTION, "Used to toggle case in selection or in whole document if no selection exists");		
	}

	private void createMenus() {
		JMenuBar menubar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		menubar.add(fileMenu);
		
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		
		JMenu editMenu = new JMenu("Edit");
		menubar.add(editMenu);
		editMenu.add(new JMenuItem(deleteSelectedPartAction));
		editMenu.add(new JMenuItem(toggleSelectedPartAction));		
		
		setJMenuBar(menubar);
	}

	private void createToolbars() {
		JToolBar toolbar = new JToolBar("Alatna traka");
		toolbar.setFloatable(true);
		
		toolbar.add(new JButton(openDocumentAction));
		toolbar.add(new JButton(saveDocumentAction));
		toolbar.addSeparator();
		toolbar.add(new JButton(deleteSelectedPartAction));
		toolbar.add(new JButton(toggleSelectedPartAction));
		
		getContentPane().add(toolbar, BorderLayout.PAGE_START);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepad().setVisible(true));
	}
	
}
