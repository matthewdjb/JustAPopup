package com.xiting.popup;

import org.eclipse.swt.widgets.Display;

public class RegisterListenerAtStartUp implements org.eclipse.ui.IStartup {

	@Override
	public void earlyStartup() {
		Display.getDefault().asyncExec(new ListenerForPopup());
	}

}
