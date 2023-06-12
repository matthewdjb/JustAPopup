package com.xiting.popup;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;

public class FunctionModuleCaller {
	private static final String FM = "Z_MATT_TEST1";

	public void run(String objectName, JCoDestination destination) throws JCoException {
		JCoFunction function = destination.getRepository().getFunction(FM);
		function.getImportParameterList().setValue("I_OBJECT", objectName);
		function.execute(destination);
		String message = function.getExportParameterList().getString("E_MESSAGE");
		if (!(message.isBlank()))
			MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "", message);
	}

}
