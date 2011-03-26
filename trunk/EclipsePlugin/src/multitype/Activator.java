package multitype;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "MultiType"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	public BackendClient client = null;	// also used by ViewDriver....
	private FEUListener feuListener;
	public UserInfo userInfo;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		userInfo = new UserInfo();
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	
	/**
	 * Instantiates a FEUListener and BackendConnection
	 * @param url
	 * @param port
	 */
	public void connect(String username, String url, int port)
	{
		// Initialize username in UserInfo
		userInfo.setUsername(username);
		
		// Construct a BackendClient
		client = new BackendClient(url, port);
		
		// Construct a FEUListener and start thread
		feuListener = new FEUListener(client);
		feuListener.start();
		
		// Start BackendClient
		client.connect();
	}
	
	public void showDialogAsync(final String title, final String message)
	{
		Display.getDefault().asyncExec(new Runnable() {
		    @Override
		    public void run() {
		      MessageDialog.openInformation(null, title, message);
		    }
		  });
		
	}
	

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}