package test.project2Test;

import static org.junit.Assert.*;

import model.Card;
import model.Deck;
import model.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import student.StuDeck;
import student.StuPair;

// name: alexander Hurley
public class PairTest {

  private StuPair pair;

  @Before
  public void setUp() throws Exception {
    pair = new StuPair(Card.RED, Card.GREEN);

  }

  @After
  public void tearDown() throws Exception {
    pair = null;
  }

  @Test
  public void getSecondCard() {
    System.out.println(" getFirstCard test");
    assertEquals("Green", Card.GREEN, pair.getSecondCard());
  }

  @Test
  public void getFirstCard() {
    System.out.println("getFirstCard test");
    assertEquals("Red", Card.RED, pair.getFirstCard());
    System.out.println("wanna dieeeeee");
  }

}