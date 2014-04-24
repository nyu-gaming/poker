package org.poker.graphics;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.googlecode.mgwt.ui.client.dialog.PopinDialog;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class PopupEnterValue extends PopinDialog {
  public interface ValueEntered {
    void setValue(int value);
  }
  
  final MTextBox txtValue;
  Button btnEnter;
  
  public PopupEnterValue(String text, final ValueEntered valueEntered) {
    //super(false, true);
    //setText(text);
    
	txtValue = new MTextBox();
    btnEnter = new Button("Buy-in");
    btnEnter.addTapHandler(new TapHandler() {
      @Override
      public void onTap(TapEvent event) {
        hide();
        valueEntered.setValue(Integer.parseInt(txtValue.getText()));
      }
    });
    HorizontalPanel panel = new HorizontalPanel();
    panel.add(txtValue);
    panel.add(btnEnter);
    add(panel);
  }
  
  @Override
  public void center() {
    super.center();
    txtValue.setFocus(true);
    //btnEnter.setFocus(true);
  }

}
