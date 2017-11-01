package hr.fer.zemris.java.hw11.jnotepadapp;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.swing.Icon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadapp.icons.IconLoader;
import hr.fer.zemris.java.hw11.jnotepadapp.local.FormLocalizationProvider;

/**
 * A component to show as a tab in {@link JNotepadPP}. It's a JScrollPane 
 * decorating a JTextArea with aditional method to track changes.
 * 
 * @author Franko Car
 *
 */
public class TextAreaTab extends JScrollPane {
	
	/** Default serial version UID */
	private static final long serialVersionUID = 1L;
	/**
	 * JTextArea used
	 */
	private JTextArea textArea;
	/**
	 * Path of a current file
	 */
	private Path filePath;
	/**
	 * Tab name
	 */
	private String name;
	/**
	 * Keeps track if a current file has been modified
	 */
	private boolean modified;
	/**
	 * Keeps track if any selections currently exist
	 */
	private boolean selected;
	/**
	 * Icon shown for unmodified files
	 */
	private static Icon savedIcon;
	/**
	 * Icon shown for modified files
	 */
	private static Icon unsavedIcon;
	/**
	 * Parent form
	 */
	private JNotepadPP tabParent;
	
	/**
	 * Total number of characters currently in document
	 */
	private int numOfAllChars;
	/**
	 * Total number of lines currently in document
	 */
	private int numOfLines;
	/**
	 * Total number of characters that aren't whitespace currently in document
	 */
	private int numOfTextChars;
	
	/**
	 * Localization provider of a parent form
	 */
	private FormLocalizationProvider flp;
		
	
	/**
	 * Initializes a tab
	 * 
	 * @param flp Form localization provider
	 */
	public TextAreaTab(FormLocalizationProvider flp) {
		this.flp = flp;
		textArea = new JTextArea();
		name = flp.getString("new");
		setUp();
	}

	/**
	 * Sets up user interface
	 */
	private void setUp() {
		modified = false;
		setViewportView(textArea);
		savedIcon = IconLoader.loadIcon("icons\\savedIcon.png", this);
		unsavedIcon = IconLoader.loadIcon("icons\\unsavedIcon.png", this);
		
		
		
		textArea.getDocument().addDocumentListener(new DocumentListener() {			
			@Override
			public void removeUpdate(DocumentEvent e) {
				update(e);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				update(e);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				update(e);
			}
			
			/**
			 * Updates current document status
			 * 
			 * @param e Trigger event
			 */
			private void update(DocumentEvent e) {
				tabParent = ((JNotepadPP) TextAreaTab.this.getTopLevelAncestor());
				
				Document doc = e.getDocument();
				modified = true;
				
				if (tabParent != null) {
					tabParent.update();
				}
				
				numOfAllChars = doc.getLength();
				numOfLines = textArea.getLineCount();
				numOfTextChars = textArea.getText().replaceAll("\\s+", "").length();
			}
		});
		
		textArea.addCaretListener(new CaretListener() {			
			@Override
			public void caretUpdate(CaretEvent e) {
				boolean hasSelected = (textArea.getCaret().getDot() - textArea.getCaret().getMark()) != 0;
				if (selected != hasSelected) {
					selected = hasSelected;
					tabParent.changeVisibility();					
				}
				
			}
		});
		
	}	

	/**
	 * Text area getter
	 * 
	 * @return Text area
	 */
	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * Text area setter
	 * 
	 * @param textArea Text area
	 */
	public void setTextArea(JTextArea textArea) {
		if (textArea == null) {
			throw new IllegalArgumentException("Given text area can't be null");
		}
		
		if (tabParent != null) {
			tabParent.update();
		}
		
		this.textArea = textArea;
	}

	/**
	 * File path getter
	 * 
	 * @return Path
	 */
	public Path getFilePath() {
		return filePath;
	}

	/**
	 * File path setter
	 * 
	 * @param filePath File path
	 */
	public void setFilePath(Path filePath) {		
		this.name = filePath.toAbsolutePath().getFileName().toString();
		this.filePath = filePath;
		
		if (tabParent != null) {
			tabParent.update();
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
		
		if (tabParent != null) {
			tabParent.update();
		}
	}
	
	@Override
	public String getToolTipText() {
		if (filePath == null) {
			return flp.getString("unsaved");
		}
		
		return filePath.toAbsolutePath().toString();
	}
	
	/**
	 * Returns an icon depending on a tab status
	 * 
	 * @return Icon
	 */
	public Icon getIcon() {
		return modified ? unsavedIcon : savedIcon;
	}

	/**
	 * Checks if a file is modified
	 * 
	 * @return boolean true if modified, false otherwise
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * Sets file modified flag
	 * 
	 * @param modified Modified boolean
	 */
	public void setModified(boolean modified) {
		this.modified = modified;
		
		if (tabParent != null) {
			tabParent.update();
		}
	}

	/**
	 * Checks if there is a selection
	 * 
	 * @return boolean true if selected, false otherwise
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Sets selected value
	 * 
	 * @param selected Selected boolean 
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		
		if (tabParent != null) {
			tabParent.update();
		}
	}
	
	/**
	 * Applies a {@link Function} to the selected text. Meant to be used
	 * to change letter case.
	 * 
	 * @param function Function to apply.
	 */
	public void changeCase(Function<String, String> function) {
		if (!this.selected) {
			return;
		}
		
		int dot = textArea.getCaret().getDot();
		int mark = textArea.getCaret().getMark();
		int length = Math.abs(dot - mark);
		
		int offset = Math.min(dot, mark);
		
		try {
			Document document = textArea.getDocument();
			String selection = document.getText(offset, length);
			selection = function.apply(selection);
			document.remove(offset, length);
			document.insertString(offset, selection, null);
			
			textArea.setSelectionStart(offset);
			textArea.setSelectionEnd(offset == dot ? mark : dot);
		} catch (BadLocationException ignorable) {
		}		
	}
	
	/**
	 * Uses a {@link Function} to modify selected lines in a text
	 * 
	 * @param function Function to apply
	 */
	public void changeLines(Function<List<String>, List<String>> function) {
		if (!this.selected) {
			return;
		}
		
		try {
			int start = textArea.getSelectionStart();
			start = textArea.getLineOfOffset(start);
			start = textArea.getLineStartOffset(start);
		
			int end = textArea.getSelectionEnd();
			end = textArea.getLineOfOffset(end);
			end = textArea.getLineEndOffset(end);
			
			int length = end - start;
			String text = textArea.getDocument().getText(start, length);
			textArea.getDocument().remove(start, length);
			
			List<String> lines = Arrays.asList(text.split("[\\r\\n]"));
			lines = function.apply(lines);
			
			StringBuilder sb = new StringBuilder();
			for (String line : lines) {
				sb.append(line);
				sb.append("\n");
			}
			
			textArea.getDocument().insertString(start, sb.toString(), null);
			
			textArea.setSelectionStart(start);
			textArea.setSelectionEnd(end);
		} catch (BadLocationException ignorable) {
		}
	}

	/**
	 * Number of all characters
	 * 
	 * @return Number of chars
	 */
	public int getNumOfAllChars() {
		return numOfAllChars;
	}

	/**
	 * Number of all lines
	 * 
	 * @return Number of lines
	 */
	public int getNumOfLines() {
		return numOfLines;
	}

	/**
	 * Number of non-whitespace characters
	 * 
	 * @return Number of non-whitespace chars
	 */
	public int getNumOfTextChars() {
		return numOfTextChars;
	}

}
