package openintotalcmd.dialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class DialogHelper {

	/**
	 * Shows a dialog window
	 * @param window the WBW
	 * @param message
	 * @author Ivo van Dongen
	 */
	public static void showDialog(Shell shell, String message) {
		MessageDialog.openInformation(
				shell,
				"OpenInTotalcmd Plug-in",
				message);
	}
}
