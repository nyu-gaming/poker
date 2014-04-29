package org.poker.graphics.i18n;

import java.util.List;

import org.poker.client.Player;
import org.poker.client.PokerLogic;

import com.google.gwt.i18n.client.Messages;
import com.google.gwt.i18n.client.impl.plurals.DefaultRule_en;

public interface PokerMessages extends Messages {

  //Informational Messages
  
  @DefaultMessage("Waiting for all players to buy-in..")
  String info_waitingForBuyIn();
  
  @DefaultMessage("Enter buy-in amount")
  String info_enterBuyInAmount();
  
  
  //Error messages
  
  @DefaultMessage("Current bet is not 0")
  String err_betNotZero();
  
  @DefaultMessage("Insufficient chips")
  String err_insufficientChips();
  
  @DefaultMessage("Insufficient chips to Call")
  String err_insufficientChipsToCall();
  
  @DefaultMessage("Can''t call 0 amount")
  String err_callZero();
  
  @DefaultMessage("Please enter a valid number")
  String err_invalidNumber();
  
  @DefaultMessage("Bet cannot be less than big blind ({0})")
  String err_betLessThanBB(int bigBlind);
  
  @DefaultMessage("Invalid Raise amount")
  String err_invalidRaise();

  
  //Pot Information
  
  @DefaultMessage("Pot{0} -- Chips: {1} | Bet: {2}")
  String potInfo(int potIndex, int chips, int currentBet);
  
  @DefaultMessage("Pot{0} -- Chips: {1} | Shared by players {2,list}")
  @AlternateMessage({
    "=0", "Pot{0} -- Chips: {1}",
    "one", "Pot{0} -- Chips: {1} | Won by player {2,list}"
  })
  String winnerPotInfo(int potIndex, int chips, @PluralCount(DefaultRule_en.class) List<Player> winners);
  

  //Player Information
  
  @DefaultMessage("Chips: {0}")
  String playerChipsInfo(int chips);
  
  @DefaultMessage("Bet: {0}")
  String playerBetInfo(int bet);

}
