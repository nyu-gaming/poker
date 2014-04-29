package org.poker.graphics;

import java.util.List;

import org.poker.client.Player;

import com.google.gwt.i18n.client.Messages;

public interface PokerMessages extends Messages {
  
  @DefaultMessage("Waiting for all players to buy-in..")
  String waitingForBuyIn();
  
  @DefaultMessage("Enter buy-in amount")
  String enterBuyInAmount();
  
  @DefaultMessage("Pot{0} -- Chips: {1} | Bet: {2}")
  String potInfo(int potIndex, int chips, int currentBet);
  
  @DefaultMessage("Pot{0} -- Chips: {1} | Shared by players {2,list}")
  @AlternateMessage({
    "=0", "Pot{0} -- Chips: {1}",
    "one", "Pot{0} -- Chips: {1} | Won by player {2,list}"
  })
  String winnerPotInfo(int potIndex, int chips, @PluralCount List<Player> winners);

}
