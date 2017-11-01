package hr.fer.zemris.java.hw11.jnotepadapp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * A class to manage localization of a form and its components
 * 
 * @author Franko Car
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * A constructor
	 * 
	 * @param parent parent localization provider
	 * @param frame frame to manage
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);

		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
			
		});
		
	}

}
