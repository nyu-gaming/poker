package org.poker.client;

import java.util.List;

import org.game_api.GameApi;
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
    //GameApi.ContainerConnector.alert("got win chance "+winChance);
    //GameApi.ContainerConnector.alert("amount to call" + amountToCall);
    if (amountToCall == 0) {
      // Check or Bet?
      if (winChance >= 0.5) {
        if(currentBet == 0) {
          //GameApi.ContainerConnector.alert("making bet move" + BIG_BLIND*3);
          return makeBetMove(state, playerIds, aiChips, BIG_BLIND * 3);
        }
        else{
          //GameApi.ContainerConnector.alert("making raise move" + BIG_BLIND*3);
          return makeRaiseMove(state, playerIds, aiChips, BIG_BLIND * 3 - currentBet);
        }
      }
      else {
        //GameApi.ContainerConnector.alert("making check move 1");
        return pokerLogic.doCheckMove(state, playerIds);
      }
    }
    else {
      // Fold, Call or Raise?
      double riskToRewardRatio = amountToCall / potAmount;
      
      if (winChance >= 0.75) {
        if (riskToRewardRatio <= 0.3) {
          // Raise
          //GameApi.ContainerConnector.alert("making raise move 1");
          return makeRaiseMove(state, playerIds, aiChips, requiredBet*3 - currentBet);
        }
        else {
          // Call
          //GameApi.ContainerConnector.alert("making call move 2");
          return makeCallMove(state, playerIds, aiChips, amountToCall);
        }
      }
      else if (winChance >= 0.5) {
        if (riskToRewardRatio <= 0.15) {
          // Raise
          //GameApi.ContainerConnector.alert("making raise move 3");
          return makeRaiseMove(state, playerIds, aiChips, requiredBet*3 - currentBet);
        }
        else if (riskToRewardRatio <= 0.65) {
          // Call
          //GameApi.ContainerConnector.alert("making call move 4");
          return makeCallMove(state, playerIds, aiChips, amountToCall);
        }
        else {
          //GameApi.ContainerConnector.alert("making fold move 5");
          return pokerLogic.doFoldMove(state, playerIds);
        }
      }
      else {
        if (riskToRewardRatio <= 0.3) {
          // Call
          //GameApi.ContainerConnector.alert("making call move 6");
          return makeCallMove(state, playerIds, aiChips, amountToCall); 
        }
        else {
          // Fold
          //GameApi.ContainerConnector.alert("making fold move 7");
          return pokerLogic.doFoldMove(state, playerIds);
        }
      }
    }

  }

  private List<Operation> makeCallMove(PokerState state, List<String> playerIds, int aiChips, int amountToCall) {
    if (aiChips >= amountToCall) {
      return pokerLogic.doCallMove(state, playerIds, amountToCall);
    }
    else {
      //All-in
      return pokerLogic.doRaiseMove(state, playerIds, aiChips);
    }
  }
  
  private List<Operation> makeBetMove(PokerState state, List<String> playerIds, int aiChips, int betAmount) {
    if (aiChips >= betAmount) {
      return pokerLogic.doBetMove(state, playerIds, betAmount);
    }
    else {
      //All-in
      return pokerLogic.doBetMove(state, playerIds, aiChips);
    }
  }
  
  private List<Operation> makeRaiseMove(PokerState state, List<String> playerIds, int aiChips, int additionalAmount) {
    if (aiChips >= additionalAmount) {
      return pokerLogic.doRaiseMove(state, playerIds, additionalAmount);
    }
    else {
      //All-in
      return pokerLogic.doRaiseMove(state, playerIds, aiChips);
    }
  }
  
  private List<Optional<Card>> getCards(List<Integer> cardIndices, ImmutableList<Optional<Card>> deck) {
    Card a = null;
    List<Optional<Card>> cards = Lists.newArrayList();
    for (Integer cardIndex : cardIndices) {
      if(deck.get(cardIndex).isPresent()){
        cards.add(Optional.of(deck.get(cardIndex).get()));
      }else {
        cards.add(Optional.fromNullable(a));
      }
    }
    return cards;
  }

}
