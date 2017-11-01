package hr.fer.zemris.java.hw11.jnotepadapp.local;

/**
 * Interface whose implementations will be able to return translations for given keys
 * 
 * @author Franko Car
 *
 */
public interface ILocalizationProvider {
	
	/**
	 * Adds a {@link ILocalizationListener} implementation to the list of listeners
	 * 
	 * @param listener Listener to add
	 */
	void addLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Removes a {@link ILocalizationListener} implementation from the list of listeners
	 * 
	 * @param listener Listener to remove
	 */
	void removeLocalizationListener(ILocalizationListener listener);
	
	/**
	 * Returns localized name of a string described by a given key
	 * 
	 * @param key String key
	 * @return Localized string
	 */
	String getString(String key);
	
}
