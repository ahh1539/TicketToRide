package test.project2Test;

import static org.junit.Assert.*;

import model.Station;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import student.StuStation;

//name: alexander HURLEY


public class StationTest {
  private Station tStation1;
  private Station tStation3;
  private Station tStation2;


  @Before
  public void setUp(){
    tStation1 = new StuStation(0,1,2,"Station1");
    tStation2 = new StuStation(1,2, 2,"Station2");
    tStation3 = new StuStation(2,1,2,"Station3");
  }
  @Test
  public void getName() {
    System.out.println("getName test");

    assertEquals("incorrect name", "Station1", tStation1.getName());

    assertEquals("incorrect namee", "Station2", tStation2.getName());
  }

  @Test
  public void collocated() {
    System.out.println("Running collocated() test");
    assertEquals("station one isnt same as station2", false, tStation1.collocated(tStation2));
    assertEquals("station one and 3 are the same", true, tStation1.collocated(tStation3));
  }

  @Test
  public void getRow() {
    System.out.println("getRow test");
    assertEquals("incorrect col", 1, tStation1.getRow());
    assertEquals("incorrect col", 2, tStation2.getRow());
  }

  @Test
  public void getCol() {
    System.out.println("getCol test");
    assertEquals("incorrft column", 2, tStation1.getCol());
    assertEquals("incorrect column", 2, tStation2.getCol());
  }


  @After
  public void tearDown(){
    tStation1 = null;
    tStation2 = null;
    tStation3 = null;
  }
}