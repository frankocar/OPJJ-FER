package hr.fer.zemris.java.hw11.jnotepadapp.local;

import javax.swing.JMenu;

/**
 * {@link JMenu} implementation that supports localization
 * 
 * @author Franko Car
 *
 */
public class LJMenu extends JMenu {

	/** Default serial version UID*/
	private static final long serialVersionUID = 1L;

	/**
	 * A constructor
	 * 
	 * @param key key for text to be shown
	 * @param provider Localization provider to use
	 */
	public LJMenu(String key, ILocalizationProvider provider) {		
		ILocalizationListener listener = new ILocalizationListener() {			
			@Override
			public void LocalizationChanged() {
				setText(provider.getString(key));
			}
		};
		
		listener.LocalizationChanged();
		provider.addLocalizationListener(listener);
	}
	
}
