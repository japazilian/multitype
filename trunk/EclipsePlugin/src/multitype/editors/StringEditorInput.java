package multitype.editors;

import java.io.InputStream;
import java.io.StringBufferInputStream;

import multitype.Activator;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;

public class StringEditorInput implements IStorageEditorInput {

	private final String inputString;
	private int fileID;

	public StringEditorInput(String inputString, int fileID) {
		this.inputString = inputString;
		this.fileID = fileID;
	}

	public boolean exists() {
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public Object getAdapter(Class adapter) {
		return null;
	}

	public String getName() {
		return Activator.getDefault().sharedFiles.get(fileID) + " (shared)";
	}

	public String getToolTipText() {
		return "tool tip";
	}

	public IStorage getStorage() {

		return new MyStorage();
	}

	private final class MyStorage implements IStorage {
		public InputStream getContents() throws CoreException {
			return new StringBufferInputStream(inputString);
		}

		public IPath getFullPath() {
			return null;
		}

		public String getName() {
			return StringEditorInput.this.getName();
		}

		public boolean isReadOnly() {
			return false;
		}

		public Object getAdapter(Class adapter) {
			return null;
		}
	}
}