package hr.fer.zemris.java.hw11.jnotepadapp.local;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Singleton implementation of an {@link AbstractLocalizationProvider}.
 * 
 * @author Franko Car
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Single instance to return 
	 */
	private static LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * Current language
	 */
	private String language;
	/**
	 * Resource bundle
	 */
	private ResourceBundle bundle;
	/**
	 * Current locale
	 */
	private Locale locale;
	
	/**
	 * A default constructor initializing English language
	 */
	private LocalizationProvider() {
		setLanguage("en");
	}
	
	/**
	 * A setter for language
	 * 
	 * @param langauge desired language
	 */
	public void setLanguage(String langauge) {		
		this.language = langauge;
		
		locale = Locale.forLanguageTag(this.language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadapp.local.prijevodi", locale);
		fire();		
	}
	
	/**
	 * Returns an instance of a {@link LocalizationProvider}
	 * 
	 * @return {@link LocalizationProvider} instance
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	/**
	 * Returns current locale
	 * 
	 * @return current locale
	 */
	public Locale getLocale() {
		return locale;
	}

	@Override
	public String getString(String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException ex) {
			return "/" + key + "/";
		}
	}

}
