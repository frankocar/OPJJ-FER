package hr.fer.zemris.java.hw11.jnotepadapp.local;

import javax.swing.JToolBar;

/**
 * {@link JToolBar} implementation that supports localization
 * 
 * @author Franko Car
 *
 */
public class LJToolBar extends JToolBar {

	/** Default serial version UID*/
	private static final long serialVersionUID = 1L;

	/**
	 * A constructor
	 * 
	 * @param key key for text to be shown
	 * @param provider Localization provider to use
	 */
	public LJToolBar(String key, ILocalizationProvider provider) {		
		ILocalizationListener listener = new ILocalizationListener() {			
			@Override
			public void LocalizationChanged() {
				setName(provider.getString(key));
			}
		};
		
		listener.LocalizationChanged();
		provider.addLocalizationListener(listener);
	}
	
}
