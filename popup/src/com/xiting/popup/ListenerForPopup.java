package com.xiting.popup;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.sap.adt.project.AdtCoreProjectServiceFactory;
import com.sap.adt.project.IProjectProvider;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;

public class ListenerForPopup implements Runnable, IPartListener2 {
	private static final String ADT_PREFIX = "com.sap.adt";

	@Override
	public void partOpened(IWorkbenchPartReference partRef) {
		if (!(partRef.getPart(true) instanceof IProjectProvider))
			return;
		String id = partRef.getId();
		if (id.startsWith(ADT_PREFIX)) {
			JCoDestination destination;
			try {
				destination = getDestination(partRef.getPart(true));
				String objectName = partRef.getPart(true).getTitle().substring(5); //Trim off sy-sysid
				new FunctionModuleCaller().run(objectName, destination);
			} catch (JCoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IPartService partService = window.getPartService();
		IPartListener2 listener = new ListenerForPopup();
		partService.addPartListener(listener);
	}

	private JCoDestination getDestination(IWorkbenchPart part) throws JCoException {
		IProjectProvider projectProvider = (IProjectProvider) part;
		IProject project = projectProvider.getProject();
		String destinationId = AdtCoreProjectServiceFactory.createCoreProjectService().getDestinationId(project);
		return JCoDestinationManager.getDestination(destinationId);
	}

}
