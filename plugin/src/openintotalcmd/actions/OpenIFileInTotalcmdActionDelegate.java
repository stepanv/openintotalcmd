package openintotalcmd.actions;

import static openintotalcmd.dialog.DialogHelper.showDialog;
import openintotalcmd.totalcmd.Totalcmd;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Handles context menu actions from the package explorer and navigator
 * 
 * @author Ivo van Dongen
 *
 */
public class OpenIFileInTotalcmdActionDelegate implements IObjectActionDelegate {

	private IWorkbenchPart part;
	private ISelection selection;

	/**
	 * Does the actual work
	 */
	public void run(IAction action) {
		if (selection instanceof StructuredSelection) {
			StructuredSelection structuredSelection = (StructuredSelection) selection;
			for (Object object : structuredSelection.toList()) {				
				Totalcmd.openInTotalcmd(part.getSite().getShell(), object);
			}
		} else {
			showDialog(part.getSite().getShell(), "Class for selection: " + selection.getClass());
		}
	}
	
	//Setters

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.part = targetPart;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

}
