package org.poker.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.poker.client.Card.Rank;
import org.poker.client.Card.Suit;
import org.poker.client.util.BestHandFinder;
import org.poker.client.util.PokerHand;

import com.google.common.base.Optional;
//import com.google.common.collect.Lists;


public class AIHelper {
  
  static final int trials = 10000;
  
  private static RandomCardProvider cardProvider = new AIHelper.RandomCardProvider();
  
  public static void main(String args[]) {
    Card n = null;
    List<Optional<Card>> board = new ArrayList<Optional<Card>>();
    List<Optional<Card>> holeCards = new ArrayList<Optional<Card>>();
    List<Optional<Card>> opponentHoleCards = new ArrayList<Optional<Card>>();
    
    holeCards.add(Optional.of(new Card( Suit.fromFirstLetterLowerCase("s"), Rank.fromFirstLetter("14"))));
    holeCards.add(Optional.of(new Card( Suit.fromFirstLetterLowerCase("c"), Rank.fromFirstLetter("13"))));
    
    opponentHoleCards.add(Optional.of(new Card( Suit.fromFirstLetterLowerCase("d"), Rank.fromFirstLetter("2"))));
    opponentHoleCards.add(Optional.of(new Card( Suit.fromFirstLetterLowerCase("h"), Rank.fromFirstLetter("3"))));
    
    //board.add(Optional.of(new Card( Suit.fromFirstLetterLowerCase("s"), Rank.fromFirstLetter("13"))));
    //board.add(Optional.of(new Card( Suit.fromFirstLetterLowerCase("h"), Rank.fromFirstLetter("2"))));
    //board.add(Optional.of(new Card( Suit.fromFirstLetterLowerCase("h"), Rank.fromFirstLetter("3"))));
    //board.add(Optional.of(new Card( Suit.fromFirstLetterLowerCase("s"), Rank.fromFirstLetter("13"))));
    board.add(Optional.fromNullable(n));
    board.add(Optional.fromNullable(n));
    board.add(Optional.fromNullable(n));
    board.add(Optional.fromNullable(n));
    board.add(Optional.fromNullable(n));
    
    AIHelper ai = new AIHelper();
    System.out.println(ai.monteCarloPokerSimulation(board,holeCards, opponentHoleCards));
    
  }
   
  
  public double monteCarloPokerSimulation(List<Optional<Card>> board, List<Optional<Card>> holeCards, List<Optional<Card>> opponentHoleCards  ) {
    // Remove present cards from the random card select pool
    
    return simulate(board, holeCards, opponentHoleCards);
  }
  
  public double simulate(List<Optional<Card>> board, List<Optional<Card>> holeCards, List<Optional<Card>> opponentHoleCards) {
    int winOrTies = 0;
    BestHandFinder aIfinder, opponentFinder;
    PokerHand aiHand, opponentHand;
    List<Optional<Card>> boardCopy;
    List<Optional<Card>> holeCardsCopy;
    List<Optional<Card>> opponentHoleCardsCopy;
    
    for(int i = 0 ; i < trials; i++) {
      cardProvider.removeCards(board);
      cardProvider.removeCards(holeCards);
      cardProvider.removeCards(opponentHoleCards);
      
      boardCopy = copy(board);
      holeCardsCopy = copy(holeCards);
      opponentHoleCardsCopy = copy(opponentHoleCards);
      assignCards(boardCopy, holeCardsCopy, opponentHoleCardsCopy);
      
      aIfinder = new BestHandFinder(fromOptionalToList(boardCopy), fromOptionalToList(holeCardsCopy));
      opponentFinder = new BestHandFinder(fromOptionalToList(boardCopy), fromOptionalToList(opponentHoleCardsCopy));
      aiHand = aIfinder.find();
      opponentHand = opponentFinder.find();
      if (aiHand.compareRanking(opponentHand) >= 0 ) {
        winOrTies ++;
      }
      
      // refresh the card pool for second trial
      cardProvider.refreshCardPool();
      
    }
    return (winOrTies * 1.0 / trials);
    
  }
  
  private List<Card> fromOptionalToList(List<Optional<Card>> source) {
    ArrayList<Card> cards = new ArrayList<Card>();
    for ( Optional<Card> card : source){
      cards.add(card.get());
    }
    return cards;
  }
  
  public List<Optional<Card>> copy(List<Optional<Card>> source) {
    List<Optional<Card>> c = new ArrayList<Optional<Card>>();
    for (Optional<Card> card : source ) {
      c.add(card);
    }
    return c;
  }
  
  private void assignCards(List<Optional<Card>> board, List<Optional<Card>> holeCards, List<Optional<Card>> opponentHoleCards) {
    assignCardsHelper(board);
    assignCardsHelper(holeCards);
    assignCardsHelper(opponentHoleCards);
    
  }
  
  private void assignCardsHelper(List<Optional<Card>> cards) {
    Optional<Card> card;
    for (int i = 0; i < cards.size(); i++) {
      card = cards.get(i);
      if (!card.isPresent()) {
        cards.remove(i);
        cards.add(i, cardProvider.getRandomCard());
      }
    }
  }
  
  private static class RandomCardProvider {
    private List<Optional<Card>> cardPool = new ArrayList<Optional<Card>>();
    private List<Optional<Card>> removedCards = new ArrayList<Optional<Card>>();
    private ArrayList<String> suits= new ArrayList<String>();
    
    Random r;
    
    public RandomCardProvider() {
      r = new Random();
      generateCards();
    }
    
    public void removeCards(List<Optional<Card>> cards) {
      for(int i = 0; i < cards.size(); i++) {
        if(cards.get(i).isPresent()){
          cardPool.remove(cards.get(i));
          removedCards.add(cards.get(i));
        }
      }
      return;
    }
    
    public void generateCards() {
      Optional<Card> card;
      suits.add("s");
      suits.add("c");
      suits.add("d");
      suits.add("h");
      for(String s : suits){
      for(int i = 2 ; i < 15; i++) {
        card = Optional.of(new Card(Suit.fromFirstLetterLowerCase(s), Rank.fromFirstLetter(Integer.toString(i))));
        cardPool.add(card);
      }
    } 
   }
    
  private Optional<Card> getRandomCard() {
      Optional<Card> card;
      int i = -1;
      i = r.nextInt(cardPool.size());
      card = cardPool.get(i);
      cardPool.remove(i);
      removedCards.add(card);
      return card;
    }
  
  public void refreshCardPool() {
    cardPool.addAll(removedCards);
    removedCards.clear();
  }

  }
  
   
}