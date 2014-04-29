package org.poker.graphics;

import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.ui.client.dialog.DialogPanel;
import com.googlecode.mgwt.ui.client.dialog.PopinDialog;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.MTextBox;

public class PopupEnterValue extends PopinDialog {
  public interface ValueEntered {
    void setValue(int value);
  }
  
  final MTextBox txtValue;
  Button btnEnter;
  
  public PopupEnterValue(String text, final ValueEntered valueEntered) {
    
    txtValue = new MTextBox();
    btnEnter = new Button(PokerGraphics.pokerConstants.buyIn());
    
    DialogPanel dialogPanel = new DialogPanel();
    HorizontalPanel panel = new HorizontalPanel();
    dialogPanel.getContent().add(panel);
    
    panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    panel.add(txtValue);
    panel.add(btnEnter);
    
    dialogPanel.getDialogTitle().setText(text);
    dialogPanel.showOkButton(false);
    dialogPanel.showCancelButton(false);
    dialogPanel.addStyleName("buyin_popup");
    
    btnEnter.addTapHandler(new TapHandler() {
      @Override
      public void onTap(TapEvent event) {
        hide();
        valueEntered.setValue(Integer.parseInt(txtValue.getText()));
      }
    });
    
    add(dialogPanel);
  }
  
  @Override
  public void center() {
    super.center();
    //txtValue.setFocus(true);
  }

}
