package hr.fer.zemris.java.zavrsni.drugi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import hr.fer.zemris.java.zavrsni.prvi.FileParser;


public class UIApp extends JFrame {
	
	/** */
	private static final long serialVersionUID = 1L;

	private JTextArea textArea;
	private JTable table = new JTable(new String[][]{{"", ""},{"", ""}}, new String[]{"", ""});

	public UIApp() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("UIApp");
		setSize(800, 800);		
		textArea = new JTextArea();
		initToolbar();
		initGUI();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new UIApp());

	}

	private void initGUI() {		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(textArea), new JScrollPane(table));
		splitPane.setResizeWeight(1f);
		add(splitPane, BorderLayout.CENTER);
		
	}

	private void initToolbar() {
		JMenuBar menubar = new JMenuBar();
		
		//File
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menubar.add(file);
	
		file.add(new JMenuItem(openDocumentAction));		
		file.add(new JMenuItem(exitAction));

		this.setJMenuBar(menubar);
		
	}
	

	protected void generateTable(Path path) throws RuntimeException{
		FileParser fp = new FileParser(path.toString());
		
		List<String> colName = new ArrayList<>();
		colName.addAll(fp.getInputVars());
		colName.addAll(fp.getOutputFormat());
		String[] nameArray = new String[colName.size()];
		colName.toArray(nameArray);
		
		List<String> output = fp.getOutputList();
		
		String[][] data = new String[output.size()][nameArray.length];
		
		for (int i = 0, n = output.size(); i < n; i++) {
			String[] tmp = output.get(i).split(";");
			data[i] = tmp;
		}
		
		table.setModel(new DefaultTableModel(data, nameArray));
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
		
//		initGUI();
//		add(table, BorderLayout.CENTER);
        
        table.repaint();
		
		
	}
	
	private Action openDocumentAction = new AbstractAction("Open") {
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open");
			if (fc.showOpenDialog(UIApp.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			Path filePath = fc.getSelectedFile().toPath();
			
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(UIApp.this,
						"File unreadable " + filePath.toAbsolutePath().toString() + ".", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			List<String> text = null;
			
			try {
				textArea.setText("");
				text = Files.readAllLines(filePath);
				Iterator<String> it = text.iterator();
				while (it.hasNext()) {
					String tmp = it.next();
					if (tmp.isEmpty() || Character.isDigit(tmp.trim().charAt(0))) {
						it.remove();
					}
				}
				
				for(String x : text) {
					  textArea.append(x);
					  textArea.append(String.format("%n"));
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(UIApp.this,
						"File unreadable" + filePath.toAbsolutePath().toString() + ".", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Path outPath = filePath;
			
			try {
				generateTable(outPath);
			} catch (RuntimeException ex) {
				JOptionPane.showMessageDialog(UIApp.this,
						"File error" + filePath.toAbsolutePath().toString() + ".", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			
		}
	};
	
	private Action exitAction = new AbstractAction("Exit") {	
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {			
			dispose();			
		}
	};
	
}
