package test.project2Test;

import static org.junit.Assert.*;

import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import student.StuRoute;
import student.StuSpace;
import student.StuStation;

//name: alexander HURLEY


public class RouteTest {

  private StuStation start;
  private StuStation testdest1;
  private StuStation testdest2;
  private Route tRoute1;
  private Route tRoute2;

  @Before
  public void setUp() {
    start = new StuStation(0, 1, 0,"OriginStation");
    testdest1 = new StuStation(0, 3, 0,"DestinationStation");
    testdest2= new StuStation(0, 1, 3, "DestinationStation2");
    tRoute1 = new StuRoute(start, testdest1, Baron.UNCLAIMED);
    tRoute2= new StuRoute(start, testdest2, Baron.UNCLAIMED);
  }

  @After
  public void tearDown() {
    start = null;
    testdest1 = null;
    tRoute1 = null;
  }

  @Test
  public void getOrigin() {
    System.out.println("getOrigin test");
    assertEquals("Wrong start", start, tRoute1.getOrigin());
  }

  @Test
  public void getTracks() {
    System.out.println("getTracks test");
    assertEquals("incorrect col", 0, tRoute1.getTracks().get(0).getCol());
    assertEquals("incorrect row", 2, tRoute1.getTracks().get(0).getRow());
  }

  @Test
  public void getDestination() {
    System.out.println("getDestination test");
    assertEquals("Wrong ort", testdest1, tRoute1.getDestination());
  }

  @Test
  public void getOrientation() {
    System.out.println("getOrientation test");
    assertEquals("incorrect ort", Orientation.VERTICAL, tRoute1.getOrientation());
  }


  @Test
  public void includesCoordinate() {
    System.out.println("includesCoodinates test");
    assertTrue("Expected true", tRoute1.includesCoordinate(new StuSpace(1, 0)));

    assertTrue("Expected true", tRoute1.includesCoordinate(new StuSpace(3, 0)));

    assertFalse("Expected false", tRoute1.includesCoordinate(new StuSpace(2, 1)));

    assertTrue("Expected true", tRoute2.includesCoordinate(new StuSpace(1, 0)));

    assertTrue("Expected true", tRoute2.includesCoordinate(new StuSpace(1, 2)));

    assertFalse("Expected false", tRoute2.includesCoordinate(new StuSpace(2, 1)));
  }

  @Test
  public void getLength() {
    System.out.println("getLength test");
    assertEquals("incorrect size", 1, tRoute1.getLength());
  }

  @Test
  public void getPointValue() {
    System.out.println("getPointValue test");
    assertEquals("incorrect amount of points", 1, tRoute1.getPointValue());

  }



  @Test
  public void getBaron() {
    System.out.println("getBaron test");
    assertEquals("epected unclaimed", Baron.UNCLAIMED, tRoute1.getBaron());
  }

  @Test
  public void claim() {
    System.out.println("claim test");
    assertEquals("Route not claimed", Baron.UNCLAIMED, tRoute1.getBaron());
    assertTrue("Route availible", tRoute1.claim(Baron.BLUE));
    assertEquals("Route route claimed blue", Baron.BLUE, tRoute1.getBaron());
    assertEquals("Track claimed", Baron.BLUE,
        tRoute1.getTracks().get(0).getBaron());
    assertFalse("Route not availible", tRoute1.claim(Baron.RED));

  }
}