package org.poker.client;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractPokerLogicBase {
  
  public static final int SMALL_BLIND = 100;
  public static final int BIG_BLIND = 200;
  public static final int DEALER_INDEX = 0;

  protected static final String[] P = {"P0", "P1", "P2", "P3", "P4", "P5", "P6", "P7", "P8"};
  protected static final String C = "C";
  
  protected static final String PREVIOUS_MOVE = "previousMove";
  protected static final String PREVIOUS_MOVE_ALL_IN = "previousMoveAllIn";
  protected static final String NUMBER_OF_PLAYERS = "numberOfPlayers";
  protected static final String WHOSE_MOVE = "whoseMove";
  protected static final String CURRENT_BETTER = "currentBetter";
  protected static final String CURRENT_ROUND = "currentRound";
  protected static final String PLAYERS_IN_HAND = "playersInHand";
  protected static final String BOARD = "board";
  protected static final String HOLE_CARDS = "holeCards";
  protected static final String PLAYER_BETS = "playerBets";
  protected static final String PLAYER_CHIPS = "playerChips";
  protected static final String POTS = "pots";
  protected static final String CHIPS = "chips";
  protected static final String CURRENT_POT_BET = "currentPotBet";
  protected static final String PLAYERS_IN_POT = "playersInPot";
  
  protected void check(boolean val, Object... debugArguments) {
    if (!val) {
      throw new RuntimeException("We have a hacker! debugArguments="
          + Arrays.toString(debugArguments));
    }
  }
  
  protected int calculateLastRequiredBet(PokerState lastState) {
    List<Pot> pots = lastState.getPots();
    int totalRequiredBet = 0;
    for(Pot pot : pots) {
      totalRequiredBet += pot.getCurrentPotBet();
    }
    return totalRequiredBet;
  }
  
  protected int calculateTotalPotAmount(PokerState state) {
    int amount = 0;
    if (state != null) {
      for (Pot pot : state.getPots()) {
        amount += pot.getChips();
      }
    }
    return amount;
  }

}
