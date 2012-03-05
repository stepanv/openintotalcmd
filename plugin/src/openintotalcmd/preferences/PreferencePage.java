package openintotalcmd.preferences;

import static openintotalcmd.preferences.PreferenceConstants.*;
import openintotalcmd.Activator;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Preference page to be able to customize the totalcmd executable and options
 * 
 * @author Ivo van Dongen
 *
 */
public class PreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/**
	 * Sets up the page basics
	 */
	public PreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Open In Totalcmd Preferences");
	}
	
	/**
	 * Adds the Preference fields
	 */
	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor(PREFERENCES_TOTALCMD_COMMAND, "Totalcmd executable (+options)", getFieldEditorParent()));
		addField(new InitialCommandFieldEditor());
	}

	/**
	 * Unused
	 */
	public void init(IWorkbench workbench) { }
	
	/**
	 * Subclassed to add validation
	 * @author ivovandongen
	 *
	 */
	private class InitialCommandFieldEditor extends StringFieldEditor {

		public InitialCommandFieldEditor() {
			super(PREFERENCES_INITIAL_COMMAND, "Totalcmd initial command", getFieldEditorParent());
		}
		
		/**
		 * Check that the ${DIR} var is included
		 */
		@Override
		protected boolean doCheckState() {
			return getTextControl().getText().contains(PREFERENCES_DIR_VAR);
		}
	}
	
}
