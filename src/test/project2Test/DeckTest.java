//package test.project2Test;
//
//import static org.junit.Assert.*;
//
//import model.Card;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import student.StuDeck;
//
//import javax.print.attribute.standard.NumberUp;
//
//// Name: Alexander Hurley
//public class DeckTest {
//
//  private StuDeck nully;
//
//  @Before
//  public void setUp() throws Exception {
//    nully = new StuDeck();
//  }
//
//  @After
//  public void tearDown() throws Exception {
//    nully = null;
//  }
//
//  @Test
//  public void reset() {
//    System.out.println("Reset method test");
//    nully.drawACard();
//    nully.reset();
//    assertEquals("shuffle deck", 180, nully.numberOfCardsRemaining());
//  }
//
//  @Test
//  public void drawACard() {
//    System.out.println("drawACard test");
//    assertEquals("deck", 180, nully.numberOfCardsRemaining());
//    nully.drawACard();
//    assertEquals("deck -1", 179, nully.numberOfCardsRemaining());
//
//    while (nully.numberOfCardsRemaining() > 0) {
//      nully.drawACard();
//    }
//
//    assertTrue("none left", nully.drawACard().equals(Card.NONE));
//
//  }
//
//  @Test
//  public void numberOfCardsRemaining() {
//    System.out.println("Running numberOfCardsRemaining() test");
//    assertEquals("200 cards left", 180, nully.numberOfCardsRemaining());
//
//    while (nully.numberOfCardsRemaining() > 100) {
//      nully.drawACard();
//    }
//    assertEquals("100 cards left", 100, nully.numberOfCardsRemaining());
//
//    while (nully.numberOfCardsRemaining() > 10) {
//      nully.drawACard();
//    }
//    assertEquals("none remaining", 10, nully.numberOfCardsRemaining());
//
//    nully.drawACard();
//    assertEquals("9 cards left", 9, nully.numberOfCardsRemaining());
//
//
//  }
//}