package hr.fer.zemris.java.hw11.jnotepadapp.local;

/**
 * A decorator for another {@link ILocalizationProvider}. Offers two additional methods, 
 * {@code connect()} and {@code disconnect()}, and it manages a connection status, so double
 * connections aren't possible. It delegates a request to the wrapped {@link ILocalizationProvider}.
 * 
 * @author Franko Car
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Current connection status
	 */
	private boolean connected;
	
	/**
	 * Parent provider
	 */
	private ILocalizationProvider parent;
	/**
	 * Interested listener
	 */
	private ILocalizationListener listener;
	
	/**
	 * A constructor
	 * 
	 * @param parent {@link ILocalizationProvider} parent
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		this.listener = new ILocalizationListener() {			
			@Override
			public void LocalizationChanged() {
				fire();				
			}
		};
	}



	@Override
	public String getString(String key) {
		return parent.getString(key);
	}
	
	/**
	 * Connects to the parent object
	 */
	public void connect() {
		if (!connected) {
			connected = true;
			parent.addLocalizationListener(listener);
		}
	}
	
	/**
	 * Diconnects from the parent object.
	 */
	public void disconnect() {
		if (connected) {
			connected = false;
			parent.removeLocalizationListener(listener);
		}
	}

}
