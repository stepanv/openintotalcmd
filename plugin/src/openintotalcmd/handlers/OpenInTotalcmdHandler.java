package openintotalcmd.handlers;

import static openintotalcmd.dialog.DialogHelper.showDialog;
import static openintotalcmd.totalcmd.Totalcmd.openInTotalcmd;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Handler for the command action (button press). Opens the total commander
 * @author Ivo van Dongen
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class OpenInTotalcmdHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public OpenInTotalcmdHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context and shos the totalcmd if possible
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		//If the current selection is an editor window, retrieve the source there
		if (window.getActivePage().getActivePart() instanceof IEditorPart) {
			IEditorPart selectedEditor = (IEditorPart) window.getActivePage().getActivePart();
			
			if(selectedEditor.getEditorInput() instanceof IFileEditorInput) {
				IFile file = ((IFileEditorInput) selectedEditor.getEditorInput()).getFile();
				if(FileEditorInput.isLocalFile(file)) {
					//Open in totalcmd
					openInTotalcmd(window.getShell(), file);
				} else {
					//Not a local file
					showDialog(window.getShell(), "Not a localfile: " + file.getLocationURI());
				}
			} else {
				//Error, not a file
				showDialog(window.getShell(), "Not a file in selected editor");
			}
		} else {
			//Check for a current selection in some other window
			ISelection selection = window.getSelectionService().getSelection();
			if (selection != null && selection instanceof TreeSelection) {
				TreeSelection treeSelection = (TreeSelection) selection;
				
				//Loop over all selected reources
				for (Object object : treeSelection.toList()) {
					openInTotalcmd(window.getShell(), object);
				}
			} else {
				//No editor selected
				showDialog(window.getShell(), "Nothing applicable selected");
			}
		}
		return null;
	}
}
	
