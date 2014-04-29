package org.poker.graphics.i18n;

import com.google.gwt.i18n.client.Constants;

public interface PokerConstants extends Constants {
  
  @DefaultStringValue("Buy-in")
  String buyIn();
  
  @DefaultStringValue("Fold")
  String fold();
  
  @DefaultStringValue("Check")
  String check();
  
  @DefaultStringValue("Call")
  String call();
  
  @DefaultStringValue("Bet")
  String bet();
  
  @DefaultStringValue("Raise")
  String raise();
  
  @DefaultStringValue("All In")
  String allIn();  

}
