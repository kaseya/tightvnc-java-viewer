//
//  Copyright (C) 2001,2002 HorizonLive.com, Inc.  All Rights Reserved.
//  Copyright (C) 1999 AT&T Laboratories Cambridge.  All Rights Reserved.
//
//  This is free software; you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation; either version 2 of the License, or
//  (at your option) any later version.
//
//  This software is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with this software; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,
//  USA.
//

//
// ButtonPanel class implements panel with four buttons in the
// VNCViewer desktop window.
//

import java.awt.*;
import java.awt.event.*;
import java.io.*;

class ButtonPanel extends Panel implements ActionListener {
  static String fitToWindowLabel = "Fit to Window";
  VncViewer viewer;
  Button disconnectButton;
  Button optionsButton;
  Button recordButton;
  Button clipboardButton;
  Button ctrlAltDelButton;
  Button refreshButton;
  Button fitToWindowButton;

  ButtonPanel(VncViewer v,
              boolean showDisconnectButton,
              boolean showOptionsButton,
              boolean showClipboardButton,
              boolean showRecordButton,
              boolean showCtrlAltDelButton,
              boolean showRefreshButton,
	          boolean showFitToScreenButton) {
    viewer = v;

    setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 01/29/2010 - A modification was made here by Kaseya International Limited to the 1.3.10 source.
    // The modification adds supports for optionally enabling/disabling buttons on the Applet control
    // panel via parameters.
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    if(showDisconnectButton) {
        disconnectButton = new Button("Disconnect");
        disconnectButton.setEnabled(false);
        add(disconnectButton);
        disconnectButton.addActionListener(this);
    }
    if(showOptionsButton) {
        optionsButton = new Button("Options");
        add(optionsButton);
        optionsButton.addActionListener(this);
    }
    if(showClipboardButton) {
        clipboardButton = new Button("Clipboard");
        clipboardButton.setEnabled(false);
        add(clipboardButton);
        clipboardButton.addActionListener(this);
    }
    if(showRecordButton && viewer.rec != null) {
        recordButton = new Button("Record");
        add(recordButton);
        recordButton.addActionListener(this);
    }
    if(showCtrlAltDelButton) {
        ctrlAltDelButton = new Button("Send Ctrl-Alt-Del");
        ctrlAltDelButton.setEnabled(false);
        add(ctrlAltDelButton);
        ctrlAltDelButton.addActionListener(this);
    }
    if(showRefreshButton) {
        refreshButton = new Button("Refresh");
        refreshButton.setEnabled(false);
        add(refreshButton);
        refreshButton.addActionListener(this);
    }
    if(showFitToScreenButton) {
	    fitToWindowButton = new Button( fitToWindowLabel );
	    fitToWindowButton.setEnabled(false);
	    add(fitToWindowButton);
	    fitToWindowButton.addActionListener(this);
    }
  }

  //
  // Enable buttons on successful connection.
  //

  public void enableButtons() {
    if(disconnectButton != null) {
        disconnectButton.setEnabled(true);
    }
    if(clipboardButton != null) {
        clipboardButton.setEnabled(true);
    }
    if(refreshButton != null) {
        refreshButton.setEnabled(true);
    }
	if(fitToWindowButton != null) {
		fitToWindowButton.setEnabled(true);
	}
  }

  //
  // Disable all buttons on disconnect.
  //

  public void disableButtonsOnDisconnect() {
    if(disconnectButton != null) {
        remove(disconnectButton);
        disconnectButton = new Button("Hide desktop");
        disconnectButton.setEnabled(true);
        add(disconnectButton, 0);
        disconnectButton.addActionListener(this);
    }
    if(optionsButton != null) {
        optionsButton.setEnabled(false);
    }
    if(clipboardButton != null) {
        clipboardButton.setEnabled(false);
    }
    if(ctrlAltDelButton != null) {
        ctrlAltDelButton.setEnabled(false);
    }
    if(refreshButton != null) {
        refreshButton.setEnabled(false);
    }
	if(fitToWindowButton != null) {
		fitToWindowButton.setEnabled(false);
	}
    validate();
  }

  //
  // Enable/disable controls that should not be available in view-only
  // mode.
  //

  public void enableRemoteAccessControls(boolean enable) {
    if(ctrlAltDelButton != null) {
        ctrlAltDelButton.setEnabled(enable);
    }
  }

  //
  // Event processing.
  //

  public void actionPerformed(ActionEvent evt) {

    viewer.moveFocusToDesktop();

    if (evt.getSource() == disconnectButton) {
      viewer.disconnect();

    } else if (evt.getSource() == optionsButton) {
      viewer.options.setVisible(!viewer.options.isVisible());

    } else if (evt.getSource() == recordButton) {
      viewer.rec.setVisible(!viewer.rec.isVisible());

    } else if (evt.getSource() == clipboardButton) {
      viewer.clipboard.setVisible(!viewer.clipboard.isVisible());

    } else if (evt.getSource() == ctrlAltDelButton) {
      viewer.sendCtrlAltDel();
    } else if (evt.getSource() == refreshButton) {
      viewer.refreshDisplay();
    }
	else if ( evt.getSource() == fitToWindowButton){
		viewer.toggleFitToWindow();
		fitToWindowButton.setLabel(fitToWindowButton.getLabel().equalsIgnoreCase(fitToWindowLabel) ? "Actual size" : fitToWindowLabel);
	}
  }
}

