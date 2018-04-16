package test.project2Test;

import static org.junit.Assert.*;

import model.Baron;
import model.Orientation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import student.StuRoute;
import student.StuStation;
import student.StuTrack;


//name: alexander HURLEY


public class TrackTest {

  private StuTrack testTrack1;
  private StuTrack testTrack2;
  private StuStation testStation1;
  private StuStation testStation2;
  private StuStation testStation3;
  private StuRoute troute1;
  private StuRoute troute2;

  @Before
  public void setUp() {
    testStation1 = new StuStation(0, 0, 0,"Station1");
    testStation2 = new StuStation(2, 2, 0,"Station2");
    testStation3 = new StuStation(1, 0, 2,"Station3");

    troute1 = new StuRoute(testStation1, testStation2, Baron.UNCLAIMED);
    troute2 = new StuRoute(testStation1, testStation3, Baron.UNCLAIMED);

    testTrack1 = new StuTrack(troute1, 0, 1);
    testTrack2 = new StuTrack(troute2, 1, 0);

  }

  @After
  public void tearDown() {
    testStation1 = null;
    testStation2 = null;
    testStation3 = null;
    testTrack1 = null;
    testTrack2 = null;
  }

  @Test
  public void getOrientation() {
    System.out.println("getOrt test");
    assertEquals("incorrect ort", Orientation.VERTICAL, testTrack1.getOrientation());

    assertEquals("incorrect ort", Orientation.HORIZONTAL, testTrack2.getOrientation());
  }

  @Test
  public void getRoute() {
    System.out.println("getRoute test");

    assertEquals("incorrect", troute1, testTrack1.getRoute());

    assertEquals("incorrect", troute2, testTrack2.getRoute());
  }

  @Test
  public void getBaron() {
    System.out.println("getBaron test");
    assertEquals("Wrong owner", Baron.UNCLAIMED, testTrack1.getBaron());

    assertEquals("Wrong owner", Baron.UNCLAIMED, testTrack2.getBaron());
  }


}