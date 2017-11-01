package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.colors.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.drawingModel.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Main JVDraw application. It's a basic paint application,
 * with the ability to draw lines, circles and filled circles.
 * Images can be exported as a jpg, png or a gif, and saved to later
 * be opened in a proprietary jvd format. Objects can be edited after
 * their initial creation by double clicking their name in a list. 
 * 
 * @author Franko Car
 *
 */
public class JVDraw extends JFrame {
	
	/** */
	private static final long serialVersionUID = 1L;
	/**
	 * Default foreground color
	 */
	private static final Color FOREGROUND = Color.BLACK;
	/**
	 * Default background color
	 */
	private static final Color BACKGROUND = Color.WHITE;
	
	/**
	 * Foreground color JColorArea
	 */
	private JColorArea fgColorArea;
	/**
	 * Background color JColorArea
	 */
	private JColorArea bgColorArea;
	
	/**
	 * Line toggle button
	 */
	private JToggleButton lineBtn;
	/**
	 * Circle toggle button
	 */
	private JToggleButton circleBtn;
	/**
	 * Filled circle toggle button
	 */
	private JToggleButton fCircleBtn;
	
	/**
	 * JList showing current objects
	 */
	private JList<GeometricalObject> list;
	
	/**
	 * Canvas to draw on
	 */
	private JDrawingCanvas canvas;
	/**
	 * Drawing model used
	 */
	private DrawingModel model;
	
	/**
	 * Flag to mark that the file has been modified
	 */
	private boolean modified = false;
	/**
	 * Path of a file
	 */
	private Path path;
		
	/**
	 * A constructor
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("JVDraw");
		setSize(800, 600);
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});
		
		fgColorArea = new JColorArea(FOREGROUND);
		bgColorArea = new JColorArea(BACKGROUND);
		model = new DrawingModelImpl();
		
		initGUI();
		
		setVisible(true);
	}

	/**
	 * GUI initialization method
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		initActions();
		initMenu();
		initToolbar();
		initCanvas();
		initList();
		initStatusbar();		
	}

	/**
	 * Action initialization method
	 */
	private void initActions() {	
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		
		saveDocumentAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
		saveDocumentAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		
		exportImageAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exportImageAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
	}

	/**
	 * Menu initialization method
	 */
	private void initMenu() {
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		file.add(new JMenuItem(openDocumentAction));
		file.add(new JMenuItem(saveDocumentAction));
		file.add(new JMenuItem(saveDocumentAsAction));
		file.add(new JMenuItem(exportImageAction));
		file.add(new JMenuItem(exitAction));
		file.setMnemonic(KeyEvent.VK_F);
		menu.add(file);
		
		this.setJMenuBar(menu);		
	}

	/**
	 * Toolabr initialization method
	 */
	private void initToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		
		ButtonGroup btnGroup = new ButtonGroup();
		lineBtn = new JToggleButton("Line");
		circleBtn = new JToggleButton("Circle");
		fCircleBtn = new JToggleButton("Filled circle");
		
		btnGroup.add(lineBtn);
		btnGroup.add(circleBtn);
		btnGroup.add(fCircleBtn);
		
		toolbar.addSeparator();
		toolbar.add(fgColorArea);
		toolbar.addSeparator();
		toolbar.add(bgColorArea);
		toolbar.addSeparator();
		
		toolbar.add(lineBtn);
		toolbar.add(circleBtn);
		toolbar.add(fCircleBtn);
		toolbar.addSeparator();
		
		
		getContentPane().add(toolbar, BorderLayout.PAGE_START);
		
	}

	/**
	 * Canvas initialization method
	 */
	private void initCanvas() {
		canvas = new JDrawingCanvas();
		model.addDrawingModelListener(canvas);
		getContentPane().add(canvas, BorderLayout.CENTER);
		
		MouseAdapter listener = new MouseAdapter() {
			boolean firstClick = true;
			GeometricalObject obj;
			
			@Override
			public void mouseClicked(MouseEvent e) {
				modified = true;
				int x = e.getX();
				int y = e.getY();
				
				if (firstClick) {
					firstClick = false;
					obj = getSelectedObject();
					
					obj.setStartX(x);
					obj.setStartY(y);
				} else {
					firstClick = true;
					if (obj == null) return;
					
					obj.setEndX(x);
					obj.setEndY(y);
					JVDraw.this.model.update(obj);
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if (firstClick) return;
				if (obj == null) return;
				
				obj.setEndX(e.getX());
				obj.setEndY(e.getY());
				JVDraw.this.model.update(obj);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if (firstClick) return;
				firstClick = true;
				
				if (obj != null) {
					JVDraw.this.model.remove(obj);
				}
			}
			
		};
		
		canvas.addMouseListener(listener);
		canvas.addMouseMotionListener(listener);		
	}

	/**
	 * Object list initialization method
	 */
	private void initList() {
		list = new DrawingObjectList(new DrawingObjectListModel(model));
		
		getContentPane().add(list, BorderLayout.LINE_END);
	}

	/**
	 * Statusbar initialization method
	 */
	private void initStatusbar() {
		Statusbar sb = new Statusbar(fgColorArea, bgColorArea);
		getContentPane().add(sb, BorderLayout.PAGE_END);
		
	}
	
	/**
	 * Creates a new {@link GeometricalObject} according to current user selection
	 * 
	 * @return new {@link GeometricalObject}
	 */
	private GeometricalObject getSelectedObject() {
		if (fCircleBtn.isSelected()) {
			return new FilledCircle(fgColorArea.getCurrentColor(), bgColorArea.getCurrentColor());
		} else if (circleBtn.isSelected()) {
			return new Circle(fgColorArea.getCurrentColor());
		} else {
			return new Line(fgColorArea.getCurrentColor());
		}
	}
	
	/**
	 * Parses a string in a valid format as a new {@link GeometricalObject}
	 * 
	 * @param line String to parse
	 * @return new {@link GeometricalObject}
	 * @throws IllegalArgumentException If format isn't valid
	 */
	private GeometricalObject parseLine(String line) throws IllegalArgumentException {
		String[] data = line.split(" ");
		
		if (data[0].toLowerCase().equals("line")) {
			int[] num = stringArrayToIntArray(data);
			return new Line(num[0], num[2], num[1], num[3], new Color(num[4], num[5], num[6]));
		} else {
			int[] num = stringArrayToIntArray(data);
			int startX = num[0];
			int startY = num[1];
			int endX = num[0] + num[2];
			int endY = num[1];
			if (data[0].toLowerCase().equals("circle")) {
				return new Circle(startX, endX, startY, endY, new Color(num[3], num[4], num[5]));
			} else if (data[0].toLowerCase().equals("fcircle")) {
				return new FilledCircle(startX, endX, startY, endY, new Color(num[3], num[4], num[5]), new Color(num[6], num[7], num[8]));
			}
		}
		
		throw new IllegalArgumentException("File contains an unreadable type");
	}
	
	/**
	 * Converts an array of strings to integer array. Skips the first string array value
	 * 
	 * @param data String array to convert
	 * @return integer array
	 * @throws NumberFormatException If a string isn't readable as an integre 
	 */
	private int[] stringArrayToIntArray(String[] data) throws NumberFormatException {
		int[] numbers = new int[data.length - 1];
		
		for (int i = 1; i < data.length; i++) {
			numbers[i - 1] = Integer.parseInt(data[i]);
		} 
		
		return numbers;
	}

	/**
	 * Main method
	 * 
	 * @param args none require
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JVDraw());

	}
	
	/**
	 * Action to exit the application. Checks for modifications and asks the
	 * user for confirmation if data is about to be lost.
	 */
	private Action exitAction = new AbstractAction("Exit") {
		
		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {			
			if (modified) {
				int result = JOptionPane.showConfirmDialog(JVDraw.this, "Do you want to save the file?", "File not saved", JOptionPane.YES_NO_CANCEL_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					saveDocumentAction.actionPerformed(null);
				} else if (result == JOptionPane.CANCEL_OPTION) {
					return;
				}
			}			
			dispose();			
		}
	};
	
	/**
	 * An action to save the document to a known path.
	 */
	private Action saveDocumentAction = new AbstractAction("Save") {
		
		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (path == null) {
				saveDocumentAsAction.actionPerformed(null);
				return;
			}

			StringBuilder sb = new StringBuilder();
			for (int i = 0, n = model.getSize(); i < n; i++) {
				sb.append(model.getObject(i).export());
				sb.append(String.format("%n"));
			}
			
			try {
				Files.write(path, sb.toString().getBytes(StandardCharsets.UTF_8));
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(JVDraw.this,
						"Save failed", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			JOptionPane.showMessageDialog(JVDraw.this, "Save successful", "Info",
					JOptionPane.INFORMATION_MESSAGE);
			modified = false;
		};
		
	};
	
	/**
	 * An action to save a document to a chosen path
	 */
	private Action saveDocumentAsAction = new AbstractAction("Save as") {
		
		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JVDraw drawings", "jvd");
			fc.setFileFilter(filter);
			fc.setDialogTitle("Save");
			if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JVDraw.this, "Save canceled", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			File file = fc.getSelectedFile();
			
			if (!file.toString().endsWith(".jvd")) {
				path = Paths.get(file.toString() + ".jvd");
			} else {
				path = fc.getSelectedFile().toPath();
			}
			
			if (Files.exists(path)) {
				int result = JOptionPane.showConfirmDialog(JVDraw.this, "Are you sure you want to overwrite this file?", "Warning",
						JOptionPane.YES_NO_OPTION);
				
				if (result == JOptionPane.NO_OPTION) {
					return;
				}
			}

			saveDocumentAction.actionPerformed(e);
		};
		
	};
	
	/**
	 * An action to open a document form a chosen path
	 */
	private Action openDocumentAction = new AbstractAction("Open") {
				
		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JVDraw drawings", "jvd");
			fc.setFileFilter(filter);
			fc.setDialogTitle("Open");
			if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			Path openFile = fc.getSelectedFile().toPath();
			
			if (!Files.isReadable(openFile)) {
				JOptionPane.showMessageDialog(JVDraw.this,
						"Can't open selected file", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			
			try {
				List<String> lines = Files.readAllLines(openFile);
				
				for (int i = model.getSize() - 1; i >= 0; i--) {
					model.remove(model.getObject(i));
				}
				
				for (String line : lines) {
					model.add(parseLine(line));
				}
				
				modified = false;				
			} catch (IOException | IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(JVDraw.this,
						"Can't read selected file: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			
		}

	};
	
	/**
	 * An action to save current image as a raster image in gif, jpg, or png format, as
	 * chosen by the user.
	 */
	private Action exportImageAction = new AbstractAction("Export") {
		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int[] boundingBox = model.getBoundingBox(); //minX, minY, maxX, maxY
			int imgWidth = boundingBox[2] - boundingBox[0];
			int imgHeight = boundingBox[3] - boundingBox[1];
			BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_3BYTE_BGR);
			
			int offsetX = boundingBox[0];
			int offsetY = boundingBox[1];
			
			Graphics2D g = img.createGraphics();
			g.setColor(BACKGROUND);
			g.fillRect(0, 0, imgWidth, imgHeight);
			
			for (int i = 0, n = model.getSize(); i < n; i++) {
				GeometricalObject obj = model.getObject(i);
				int oldStartX = obj.getStartX();
				int oldStartY = obj.getStartY();
				int oldEndX = obj.getEndX();
				int oldEndY = obj.getEndY();
				
				obj.setEndX(oldEndX - offsetX);
				obj.setEndY(oldEndY - offsetY);
				obj.setStartX(oldStartX - offsetX);
				obj.setStartY(oldStartY - offsetY);
				
				obj.draw(g);
				
				obj.setEndX(oldEndX);
				obj.setEndY(oldEndY);
				obj.setStartX(oldStartX);
				obj.setStartY(oldStartY);				
			}
			
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filterPng = new FileNameExtensionFilter(".png", "png");
			FileNameExtensionFilter filterJpg = new FileNameExtensionFilter(".jpg", "jpg");
			FileNameExtensionFilter filterGif = new FileNameExtensionFilter(".gif", "gif");
			fc.setFileFilter(filterPng);
			fc.setFileFilter(filterJpg);
			fc.setFileFilter(filterGif);
			fc.setDialogTitle("Save");
			if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JVDraw.this, "Export canceled", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			String type = fc.getFileFilter().getDescription();
			Path imgPath = Paths.get(fc.getSelectedFile().toString() + type);
			type = type.substring(1);
			
			if (Files.exists(imgPath)) {
				int result = JOptionPane.showConfirmDialog(JVDraw.this, "Are you sure you want to overwrite this file?", "Warning",
						JOptionPane.YES_NO_OPTION);
				
				if (result == JOptionPane.NO_OPTION) {
					return;
				}
			}
			
			try {
				ImageIO.write(img, type, imgPath.toFile());
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(JVDraw.this, "Export failed", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			JOptionPane.showMessageDialog(JVDraw.this, "Export successful", "Info",
					JOptionPane.INFORMATION_MESSAGE);
			return;
			
		}
	};
	
}
