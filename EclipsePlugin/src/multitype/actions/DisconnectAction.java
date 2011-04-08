/**
 * @author Ryan Miller
 */

package multitype.actions;

import multitype.Activator;
import multitype.FEUSender;
import multitype.FrontEndUpdate;
import multitype.FrontEndUpdate.NotificationType;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class DisconnectAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	boolean added = false;
	/**
	 * The constructor.
	 */
	public DisconnectAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		if (Activator.getDefault().isConnected == true) {
			Activator.getDefault().disconnect();
			Activator.getDefault().isConnected = false;
			FrontEndUpdate feu = FrontEndUpdate.createNotificationFEU(
					NotificationType.User_Disconnected, -1, Activator.getDefault().userInfo.getUserid(), 
					Activator.getDefault().userInfo.getUsername());
			FEUSender.send(feu);
			Activator.getDefault().userList.setButton(false);
			System.out.println("Disconnected from server.");
		}
		else {
			/*if (added == false) {
			FrontEndUpdate feu = FrontEndUpdate.createNotificationFEU(
					NotificationType.User_Disconnected, -1, -1, 
					"duhhhh");
			Activator.getDefault().addUserToList(feu);
			added= true;
			}
			else {
				FrontEndUpdate feu = FrontEndUpdate.createNotificationFEU(
						NotificationType.User_Disconnected, -1, -1, 
						"duhhhh");
				Activator.getDefault().deleteUserFromList(feu);
				added = false;
			}*/
			
			////testing purpose//////
			FrontEndUpdate feu = FrontEndUpdate.createNotificationFEU(
					NotificationType.User_Disconnected, -1, -1, 
					"zzzzzz");
			Activator.getDefault().userList.addUserToList(feu.getContent(), feu.getUserId());
			Activator.getDefault().userList.setButton(true);
			////testing purpose//////
			
			System.out.println("not connected to a server.");
		}
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}