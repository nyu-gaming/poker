package org.poker.client;

import java.util.List;

import org.game_api.GameApi.Operation;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class AILogic extends AbstractPokerLogicBase {
  
  private PokerLogic pokerLogic = new PokerLogic();
  
  public List<Operation> decideMove(PokerState state, PokerState lastState, List<String> playerIds) {
    
    // lastState cannot be null because AI will not have first move
    
    PokerMove previousMove = state.getPreviousMove();
    int aiIndex = state.getWhoseMove().ordinal();
    int opponentIndex = aiIndex == 1 ? 0 : 1;
    
    List<Integer> playerChips = state.getPlayerChips();
    int aiChips = playerChips.get(aiIndex);
    List<Integer> playerBets = state.getPlayerBets();
    
    BettingRound currentRound = state.getCurrentRound();
    BettingRound lastRound = lastState == null ? null : lastState.getCurrentRound();
    boolean isNewRound = lastRound == currentRound;
    
    int potAmount = calculateTotalPotAmount(state);
    int requiredBet = calculateLastRequiredBet(state);
    int currentBet = playerBets.get(aiIndex);
    int amountToCall = requiredBet - currentBet;
    
    List<Optional<Card>> aiHand = getCards(state.getHoleCards().get(aiIndex), state.getCards());
    List<Optional<Card>> opponentHand = getCards(state.getHoleCards().get(opponentIndex), state.getCards());
    List<Optional<Card>> board = getCards(state.getBoard(), state.getCards());
    AIHelper ai = new AIHelper();
    double winChance = ai.getWinningChance(board, aiHand, opponentHand ); 
    
    if (amountToCall == 0) {
      // Check or Bet?
      if (winChance >= 0.5) {
        makeBetMove(state, playerIds, aiChips, BIG_BLIND * 3);
      }
      else {
        pokerLogic.doCheckMove(state, playerIds);
      }
    }
    else {
      // Fold, Call or Raise?
      double riskToRewardRatio = amountToCall / potAmount;
      
      if (winChance >= 0.75) {
        if (riskToRewardRatio <= 0.3) {
          // Raise
          makeRaiseMove(state, playerIds, aiChips, requiredBet*3 - currentBet);
        }
        else {
          // Call
          makeCallMove(state, playerIds, aiChips, amountToCall);
        }
      }
      else if (winChance >= 0.5) {
        if (riskToRewardRatio <= 0.15) {
          // Raise
          makeRaiseMove(state, playerIds, aiChips, requiredBet*3 - currentBet);
        }
        else if (riskToRewardRatio <= 0.65) {
          // Call
          makeCallMove(state, playerIds, aiChips, amountToCall);
        }
        else {
          pokerLogic.doFoldMove(state, playerIds);
        }
      }
      else {
        if (riskToRewardRatio <= 0.3) {
          // Call
          makeCallMove(state, playerIds, aiChips, amountToCall); 
        }
        else {
          // Fold
          pokerLogic.doFoldMove(state, playerIds);
        }
      }
    }

    return null;
  }

  private void makeCallMove(PokerState state, List<String> playerIds, int aiChips, int amountToCall) {
    if (aiChips >= amountToCall) {
      pokerLogic.doCallMove(state, playerIds, amountToCall);
    }
    else {
      //All-in
      pokerLogic.doRaiseMove(state, playerIds, aiChips);
    }
  }
  
  private void makeBetMove(PokerState state, List<String> playerIds, int aiChips, int betAmount) {
    if (aiChips >= betAmount) {
      pokerLogic.doBetMove(state, playerIds, betAmount);
    }
    else {
      //All-in
      pokerLogic.doBetMove(state, playerIds, aiChips);
    }
  }
  
  private void makeRaiseMove(PokerState state, List<String> playerIds, int aiChips, int additionalAmount) {
    if (aiChips >= additionalAmount) {
      pokerLogic.doRaiseMove(state, playerIds, additionalAmount);
    }
    else {
      //All-in
      pokerLogic.doRaiseMove(state, playerIds, aiChips);
    }
  }
  
  private List<Optional<Card>> getCards(List<Integer> cardIndices, ImmutableList<Optional<Card>> deck) {
    List<Optional<Card>> cards = Lists.newArrayList();
    for (Integer cardIndex : cardIndices) {
      cards.add(Optional.of(deck.get(cardIndex).get()));
    }
    return cards;
  }

}
