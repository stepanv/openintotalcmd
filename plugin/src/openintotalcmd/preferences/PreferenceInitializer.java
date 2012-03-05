package openintotalcmd.preferences;

import openintotalcmd.Activator;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import static openintotalcmd.preferences.PreferenceConstants.*;

/**
 * Sets the default preferences
 * @author Ivo van Dongen
 *
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PREFERENCES_TOTALCMD_COMMAND, "\"C:\\Program Files (x86)\\Total Commander\\TOTALCMD64.EXE\" /O /T");
		store.setDefault(PREFERENCES_INITIAL_COMMAND, "/L=\"" + PREFERENCES_DIR_VAR + "\"");
	}
	
}