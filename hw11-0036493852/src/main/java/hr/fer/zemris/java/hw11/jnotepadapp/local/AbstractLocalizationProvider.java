package hr.fer.zemris.java.hw11.jnotepadapp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract implementation of a {@link ILocalizationProvider} that contains a list of listeners
 * and a method to notify them all.
 * 
 * @author Franko Car
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Localization listeners of a child object.
	 */
	private List<ILocalizationListener> listeners;
	
	/**
	 * A constructor
	 */
	public AbstractLocalizationProvider() {
		this.listeners = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notifies all interested listeners
	 */
	public void fire() {
		for (ILocalizationListener listener : listeners) {
			listener.LocalizationChanged();
		}

	}

}
