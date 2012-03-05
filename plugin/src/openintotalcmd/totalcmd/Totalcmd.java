package openintotalcmd.totalcmd;

import static openintotalcmd.dialog.DialogHelper.showDialog;
import static openintotalcmd.preferences.PreferenceConstants.PREFERENCES_DIR_VAR;
import static openintotalcmd.preferences.PreferenceConstants.PREFERENCES_INITIAL_COMMAND;
import static openintotalcmd.preferences.PreferenceConstants.PREFERENCES_TOTALCMD_COMMAND;

import java.io.IOException;

import openintotalcmd.Activator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.swt.widgets.Shell;

/**
 * Handles the totalcmd side of things
 * 
 * @author Ivo van Dongen
 *
 */
public class Totalcmd {
	
	/**
	 * Opens the given object in a shell after determining if this is even possible
	 * @param shell the current shell
	 * @param object an openable object
	 */
	public static void openInTotalcmd(Shell shell, Object object) {
		if (object instanceof IFile) {
			//Open the file
			Totalcmd.openInTotalcmd(shell, (IFile) object);
		} else if (object instanceof IContainer) {
			//Open the container
			Totalcmd.openInTotalcmd(shell, ((IContainer) object).getLocation());
		} else if (object instanceof IProjectNature) {
			//Open the project folder
			Totalcmd.openInTotalcmd(shell, ((IProjectNature)object).getProject().getLocation());
		} else if (object instanceof IJavaElement) {
			//Get the full path to the Java element
			IJavaElement javaElement = (IJavaElement) object;
			IPath fullPath = javaElement.getJavaProject().getProject().getLocation().append(javaElement.getPath().removeFirstSegments(1));
			
			//Trim the path if this is not a container
			if(object instanceof ICompilationUnit) {
				fullPath = fullPath.removeLastSegments(1);
			}
			
			//Open it
			Totalcmd.openInTotalcmd(shell, fullPath);
		} else {
			//No applicatble selection
			showDialog(shell, "No applicable resource selected: " + object.getClass());
		}
	}
	

	/**
	 * Opens the file's parent folder in a totalcmd
	 * @param window
	 * @param file
	 */
	public static void openInTotalcmd(Shell shell, IFile file) {
		try {
			Runtime.getRuntime().exec(getCommand(file.getLocation()));
		} catch (IOException e) {
			showDialog(shell, "Could not open totalcmd: " + e.getMessage());
		}
	}
	
	/**
	 * Opens the folder in a totalcmd
	 * @param window
	 * @param path the path to a folder
	 */
	public static void openInTotalcmd(Shell shell, IPath path) {
		try {
			Runtime.getRuntime().exec(getCommand(path));
		} catch (IOException e) {
			showDialog(shell, "Could not open totalcmd: " + e.getMessage());
		}
	}
	
	/**
	 * Creates the complete command array from the preferences and the current dir.
	 * @param file
	 * @return
	 */
	private static String getCommand(IPath path) {
		//Get command from preferences
		String command = Activator.getDefault().getPreferenceStore().getString(PREFERENCES_TOTALCMD_COMMAND);
		String initial = Activator.getDefault().getPreferenceStore().getString(PREFERENCES_INITIAL_COMMAND);
		
		//Add the Current dir
		initial = initial.replace(PREFERENCES_DIR_VAR, path.toOSString());
				
		return command + " " + initial;
	}
}
