package hr.fer.zemris.java.hw11.jnotepadapp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultEditorKit;

import hr.fer.zemris.java.hw11.jnotepadapp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LJToolBar;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadapp.local.LocalizationProvider;

/**
 * A notepad program with some extra functionality. Besides basic notepad operations 
 * such as opening and saving files it allows alphabetical sorting of lines, removing 
 * line duplicates, changing letter case, keeping track of basic test statistics and
 * it supports multiple languages, English, Croatian and German(machine translated). 
 * All files are opened in separate tabs, so editing of multiple files at the same time
 * is supported.
 * 
 * @author Franko Car
 *
 */
public class JNotepadPP extends JFrame{

	/** Serial version UID */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Main area of the program containing all tabs
	 */
	private JTabbedPane tabPane;
	/**
	 * Currently selected tab
	 */
	private TextAreaTab currentTab;
	/**
	 * Index of a currently selected tab 
	 */
	private int currentTabIndex;
	/**
	 * Status bar component
	 */
	private StatusBar statusbar;
	
	/**
	 * Localization provider for this window
	 */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	
	/**
	 * List of actions that should be disabled when they don't do anything(nothing is selected)
	 */
	private List<Action> actionsToDisable;

	/**
	 * Default constructor
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("JNotepad++");
		setSize(800, 600);
		setLocationRelativeTo(null);		
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		initGUI();
		setVisible(true);
	}
	
	/**
	 * Initializes the GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		tabPane = new JTabbedPane();
		tabPane.addChangeListener(new ChangeListener() {			
			@Override
			public void stateChanged(ChangeEvent e) {
				currentTabIndex = tabPane.getSelectedIndex();
				if (currentTabIndex < 0) {
					return;
				}
				
				if (!(tabPane.getComponentAt(currentTabIndex) instanceof TextAreaTab)) {
					return;
				}
				
				currentTab = (TextAreaTab) tabPane.getComponentAt(currentTabIndex);
				
				Path path = currentTab.getFilePath();
				setTitle(path == null ? "JNotepad++" : path.toAbsolutePath().toString() + " - JNotepad++");
				
				if (statusbar != null) {
					statusbar.changeTab(currentTab);
				}
			}
		});
		
		TextAreaTab tab = new TextAreaTab(flp);
		tabPane.add(tab);
		tabPane.setToolTipTextAt(0, tab.getToolTipText());
		tabPane.setTitleAt(0, tab.getName());
		tabPane.setIconAt(0, tab.getIcon());
		
		statusbar = new StatusBar(tab, this, flp);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});
		
		cp.add(tabPane, BorderLayout.CENTER);
		cp.add(statusbar, BorderLayout.PAGE_END);
		createActions();
		createMenus();
		createToolbars();
		
	}
	
	/**
	 * Initalizes all actions with their accelerator keys and mnemonics. 
	 * Populates the list of actions to disable.
	 */
	private void createActions() {
		actionsToDisable = new LinkedList<>();
		
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt F4"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		
		closeTabAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeTabAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		
		saveDocumentAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveDocumentAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		
		createNewDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNewDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		
		statsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		statsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		
		croatianAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control H"));
		croatianAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);
		
		englishAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		englishAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		
		germanAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control G"));
		germanAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		
		toUppercaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUppercaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		toUppercaseAction.setEnabled(false);
		actionsToDisable.add(toUppercaseAction);
		
		toLowercaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		toLowercaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		toLowercaseAction.setEnabled(false);
		actionsToDisable.add(toLowercaseAction);
		
		toggleCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		toggleCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
		toggleCaseAction.setEnabled(false);
		actionsToDisable.add(toggleCaseAction);
		
		sortAscendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control K"));
		sortAscendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		sortAscendingAction.setEnabled(false);
		actionsToDisable.add(sortAscendingAction);
		
		sortDescendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control J"));
		sortDescendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		sortDescendingAction.setEnabled(false);
		actionsToDisable.add(sortDescendingAction);
		
		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		uniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
		uniqueAction.setEnabled(false);
		actionsToDisable.add(uniqueAction);
		
	}

	/**
	 * Creates menus.
	 */
	private void createMenus() {
		JMenuBar menubar = new JMenuBar();
		
		//File
		JMenu file = new LJMenu("fileMenu", flp);
		file.setMnemonic(KeyEvent.VK_F);
		menubar.add(file);
		
		file.add(new JMenuItem(createNewDocumentAction));
		file.add(new JMenuItem(openDocumentAction));
		file.addSeparator();
		file.add(new JMenuItem(saveDocumentAction));
		file.add(new JMenuItem(saveDocumentAsAction));
		file.addSeparator();
		file.add(new JMenuItem(closeTabAction));
		file.add(new JMenuItem(exitAction));
		
		//Edit
		JMenu edit = new LJMenu("editMenu", flp);
		file.setMnemonic(KeyEvent.VK_E);
		menubar.add(edit);
		
		edit.add(copyAction);
		edit.add(cutAction);
		edit.add(pasteAction);
		
		//Tools
		JMenu tools = new LJMenu("toolsMenu", flp);
		file.setMnemonic(KeyEvent.VK_S);
		menubar.add(tools);
		
		JMenuItem changeCase = new LJMenu("changeCaseMenu", flp);
			changeCase.setMnemonic(KeyEvent.VK_C);
			tools.add(changeCase);
		
			changeCase.add(new JMenuItem(toUppercaseAction));
			changeCase.add(new JMenuItem(toLowercaseAction));
			changeCase.add(new JMenuItem(toggleCaseAction));
		
		JMenuItem sort = new LJMenu("sortMenu", flp);
			sort.setMnemonic(KeyEvent.VK_S);
			tools.add(sort);
		
			sort.add(new JMenuItem(sortAscendingAction));
			sort.add(new JMenuItem(sortDescendingAction));

		tools.add(uniqueAction);
		tools.add(statsAction);
		
		//Languages
		JMenu languages = new LJMenu("languagesMenu", flp);
		file.setMnemonic(KeyEvent.VK_L);
		menubar.add(languages);
		
		languages.add(croatianAction);
		languages.add(englishAction);
		languages.add(germanAction);
		
		this.setJMenuBar(menubar);
		
		
		
	}

	/**
	 * Creates a toolbar
	 */
	private void createToolbars() {
		JToolBar toolbar = new LJToolBar("toolbar", flp);
		toolbar.setFloatable(true);
		
		toolbar.add(createNewDocumentAction);
		toolbar.add(closeTabAction);
		toolbar.addSeparator();
		
		toolbar.add(openDocumentAction);
		toolbar.add(saveDocumentAction);
		toolbar.add(saveDocumentAsAction);
		toolbar.addSeparator();
		
		toolbar.add(copyAction);
		toolbar.add(cutAction);
		toolbar.add(pasteAction);
		toolbar.addSeparator();
		
		toolbar.add(statsAction);
		toolbar.add(toUppercaseAction);
		toolbar.add(toLowercaseAction);
		toolbar.add(toggleCaseAction);
		toolbar.addSeparator();
		
		toolbar.add(sortAscendingAction);
		toolbar.add(sortDescendingAction);
		toolbar.add(uniqueAction);
		toolbar.addSeparator();
		
		toolbar.add(croatianAction);
		toolbar.add(germanAction);
		toolbar.add(englishAction);
		toolbar.addSeparator();
		
		toolbar.add(exitAction);
		
		
		this.getContentPane().add(toolbar, BorderLayout.PAGE_START);
		
	}

	/**
	 * Updates icons and titles when called
	 */
	public void update() {
		for (int i = 0, n = tabPane.getTabCount(); i < n; i++) {
			if (!(tabPane.getComponentAt(i) instanceof TextAreaTab)) {
				continue;
			}
			
			TextAreaTab tab = (TextAreaTab) tabPane.getComponentAt(i);
			tabPane.setToolTipTextAt(i, tab.getToolTipText());
			tabPane.setTitleAt(i, tab.getName());
			tabPane.setIconAt(i, tab.getIcon());
		}
		
		setTitle(currentTab.getFilePath() == null ? "JNotepad++" : currentTab.getFilePath().toAbsolutePath().toString() + " - JNotepad++");
	}
	
	/**
	 * Changes the visibility of actions that support it, 
	 * disables them when there is no selection in the text and 
	 * enables them when there is.
	 */
	public void changeVisibility() {
		for (Action action : actionsToDisable) {
			action.setEnabled(currentTab.isSelected());
		}
	}

	/**
	 * Main method
	 * 
	 * @param args none required
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP());

	}
	
	//actions	
	/**
	 * Action to exit the application.
	 */
	private Action exitAction = new LocalizableAction("exitAction", flp) {	
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {			
			for (int i = 0, n = tabPane.getTabCount(); i < n; i++) {
				tabPane.setSelectedIndex(0);
				
				int before = tabPane.getTabCount();
				closeTabAction.actionPerformed(null);
				
				if (tabPane.getTabCount() >= before) {
					return;
				}
				
			}
			dispose();			
		}
	};
	
	/**
	 * Action that will close the current tab.
	 */
	private Action closeTabAction = new LocalizableAction("closeTabAction", flp) {		
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentTab != null && currentTab.isModified()) {
				int result = JOptionPane.showConfirmDialog(JNotepadPP.this, flp.getString("savingAtClose"), flp.getString("closingConfirm"), JOptionPane.YES_NO_CANCEL_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					saveDocumentAction.actionPerformed(null);
				} else if (result == JOptionPane.CANCEL_OPTION) {
					return;
				}
			}
			
			tabPane.remove(currentTabIndex);
			if (tabPane.getTabCount() == 0) {
				dispose();
			}
		}
	};
	
	/**
	 * Action to save the current document
	 */
	private Action saveDocumentAction = new LocalizableAction("saveDocumentAction", flp) {
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentTab.getFilePath() == null) {
				saveDocumentAsAction.actionPerformed(e);;
				return;
			}
			
			try {
				Files.write(currentTab.getFilePath(), currentTab.getTextArea().getText().getBytes(StandardCharsets.UTF_8));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						flp.getString("saveFailure"), flp.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("saveSuccess"), flp.getString("info"),
					JOptionPane.INFORMATION_MESSAGE);
			
			currentTab.setModified(false);
			tabPane.setIconAt(tabPane.getSelectedIndex(), currentTab.getIcon());
		}
	};
	
	/**
	 * Action to open a save as dialog
	 */
	private Action saveDocumentAsAction = new LocalizableAction("saveAsDocumentAction", flp) {		
		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(flp.getString("saveWindow"));
			if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString("saveQuit"), flp.getString("warning"),
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			Path savedPath = fc.getSelectedFile().toPath();
			
			if (Files.exists(savedPath)) {
				int result = JOptionPane.showConfirmDialog(JNotepadPP.this, flp.getString("saveOverwrite") + savedPath.getFileName() + "?" , flp.getString("warning"),
						JOptionPane.YES_NO_OPTION);
				
				if (result == JOptionPane.NO_OPTION) {
					return;
				}
			}
			
			currentTab.setFilePath(savedPath);
			saveDocumentAction.actionPerformed(e);			
		}
	};
	
	/**
	 * Action to create a new document in a new tab
	 */
	private Action createNewDocumentAction = new LocalizableAction("createNewDocumentAction", flp) {	
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			tabPane.add(new TextAreaTab(flp));
			update();
			currentTabIndex = tabPane.getTabCount() - 1;
			currentTab = (TextAreaTab) tabPane.getComponentAt(currentTabIndex);
			tabPane.setSelectedIndex(currentTabIndex);
			statusbar.changeTab(currentTab);
		}
	};
	
	/**
	 * Action to open an open file dialog and allow user to select a file to open. File 
	 * will be opened in a new tab
	 */
	private Action openDocumentAction = new LocalizableAction("openDocumentAction", flp) {
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle(flp.getString("saveWindow"));
			if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			Path filePath = fc.getSelectedFile().toPath();
			
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						flp.getString("fileUnreadable") + filePath.toAbsolutePath().toString() + ".", flp.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String text = null;
			
			try {
				text = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						flp.getString("fileUnreadable") + filePath.toAbsolutePath().toString() + ".", flp.getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			createNewDocumentAction.actionPerformed(e);
			currentTab.getTextArea().setText(text);
			currentTab.setFilePath(filePath);
			currentTab.setModified(false);
			statusbar.changeTab(currentTab);
			
		}
	};
	
	/**
	 * Action to copy, uses system action
	 */
	private Action copyAction = new LocalizableAction("copyAction", flp) {
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			new DefaultEditorKit.CopyAction().actionPerformed(e);
		}
	};
	
	/**
	 * Action to cut, uses system action
	 */
	private Action cutAction = new LocalizableAction("cutAction", flp) {
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			new DefaultEditorKit.CutAction().actionPerformed(e);
		}
	};
	
	/**
	 * Action to paste, uses system action
	 */
	private Action pasteAction = new LocalizableAction("pasteAction", flp) {	
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			new DefaultEditorKit.PasteAction().actionPerformed(e);
		}
	};
	
	/**
	 * Action to show a window with current text statistics
	 */
	private Action statsAction = new LocalizableAction("statsAction", flp) {
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			String message = String.format("%s %d %s, %d %s %d %s.", flp.getString("statsCharNum"), currentTab.getNumOfAllChars(),
					flp.getString("statsChar"), currentTab.getNumOfTextChars(), flp.getString("statsNonBlank"), currentTab.getNumOfLines(),
					flp.getString("statsLines"));
			JOptionPane.showMessageDialog(JNotepadPP.this, message, flp.getString("info"), JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	/**
	 * Action to switch the language to Croatian
	 */
	private Action croatianAction = new LocalizableAction("hr", flp) {	
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			LocalizationProvider.getInstance().setLanguage("hr");
			
		}
	};
	
	/**
	 * Action to switch the language to English
	 */
	private Action englishAction = new LocalizableAction("en", flp) {		
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			LocalizationProvider.getInstance().setLanguage("en");
			
		}
	};
	
	/**
	 * Action to switch the language to German
	 */
	private Action germanAction = new LocalizableAction("de", flp) {		
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			LocalizationProvider.getInstance().setLanguage("de");
			
		}
	};
	
	/**
	 * Action that will change selected text to uppercase
	 */
	private Action toUppercaseAction = new LocalizableAction("toUppercaseAction", flp) {		
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			currentTab.changeCase(String::toUpperCase);			
		}
	};
	
	/**
	 * Action that will change selected text to lowercase
	 */
	private Action toLowercaseAction = new LocalizableAction("toLowercaseAction", flp) {		
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			currentTab.changeCase(String::toLowerCase);			
		}
	};
	
	/**
	 * Action to change the case of selected text
	 */
	private Action toggleCaseAction = new LocalizableAction("toggleCaseAction", flp) {		
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			currentTab.changeCase((x) -> {
				char[] chars = x.toCharArray();
				for (int i = 0; i < chars.length; i++) {
					if (Character.isLowerCase(chars[i])) {
						chars[i] = Character.toUpperCase(chars[i]);
					} else if (Character.isUpperCase(chars[i])) {
						chars[i] = Character.toLowerCase(chars[i]);
					}
				}
				
				return new String(chars);
			});			
		}
	};
	
	/**
	 * Action to sort selected lines in ascending order
	 */
	private Action sortAscendingAction = new LocalizableAction("sortAscendingAction", flp) {
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			currentTab.changeLines((x) -> sortLines(x, false));			
		}
	};
	
	/**
	 * Action to sort selected lines in descending ending order
	 */
	private Action sortDescendingAction = new LocalizableAction("sortDescendingAction", flp) {
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			currentTab.changeLines((x) -> sortLines(x, true));		
		}
	};
	
	/**
	 * Sorts a list of lines according to current localization settings.
	 * Allows for ascending and descending sorting.
	 * 
	 * @param lines List of strings to sort
	 * @param reverse True for descending order, false for ascending
	 * @return Sorted list of strings
	 */
	private List<String> sortLines(List<String> lines, boolean reverse) {
		Locale locale = LocalizationProvider.getInstance().getLocale();
		Collator collator = Collator.getInstance(locale);
		Collections.sort(lines, reverse ? collator.reversed() : collator);
		return lines;
	}
	
	/**
	 * Action to remove all duplicates of a line, leaves just the first occurrence. 
	 */
	private Action uniqueAction = new LocalizableAction("uniqueAction", flp) {
		/** Default serial version UID */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			currentTab.changeLines((x) -> {
				Set<String> set = new LinkedHashSet<>(x);
				return new ArrayList<String>(set);
			});		
		}
	};

}
