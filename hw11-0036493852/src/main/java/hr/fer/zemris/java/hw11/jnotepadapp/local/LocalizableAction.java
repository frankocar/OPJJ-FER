package hr.fer.zemris.java.hw11.jnotepadapp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

import hr.fer.zemris.java.hw11.jnotepadapp.icons.IconLoader;

/**
 * An abstract class whose implementations will support localization by a given provider in
 * addition to functionality provided by {@link AbstractAction}.
 * 
 * @author Franko Car
 *
 */
public abstract class LocalizableAction extends AbstractAction {	
	
	/** Default serial version UID */
	private static final long serialVersionUID = 1L;
	
	/**
	 * A constructor
	 * 
	 * @param key Key of a requested string
	 * @param provider Localization provider to use
	 */
	public LocalizableAction(String key, ILocalizationProvider provider) {		
		ILocalizationListener listener = new ILocalizationListener() {			
			@Override
			public void LocalizationChanged() {
				putValue(Action.NAME, provider.getString(key));
				putValue(Action.SHORT_DESCRIPTION, provider.getString(key + "_SD"));
				try {
					putValue(Action.SMALL_ICON, IconLoader.loadIcon("..\\icons\\" + key + ".png", this));
				} catch (IllegalArgumentException noIcon) { //just skip the icon if one doesn't exist
				}
			}
		};
		
		listener.LocalizationChanged();
		provider.addLocalizationListener(listener);
	}

}
