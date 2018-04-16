package test.project2Test;

import static org.junit.Assert.*;

import model.Space;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import student.StuSpace;

// name: Alexander Hurley
public class SpaceTest {
  private Space testSpace1;
  private Space testSpace2;
  private Space testSpace3;

  @Before
  public void setUp(){
    testSpace1 = new StuSpace(3,2);
    testSpace2 = new StuSpace(2, 4);
    testSpace3 = new StuSpace(2,4);
  }

  @Test
  public void getRow() {
    System.out.println("Running getRow() test");
    assertEquals("not correct row", 3, testSpace1.getRow());
    assertEquals("not correct row", 2, testSpace2.getRow());
    assertEquals("not correct row", 2, testSpace3.getRow());
  }

  @Test
  public void getCol() {
    System.out.println("Running getCol() test");
    assertEquals("not correct col", 2, testSpace1.getCol());
    assertEquals("not correct col", 4, testSpace2.getCol());
    assertEquals("not correct col", 4, testSpace3.getCol());
  }

  @Test
  public void collocated() {
    System.out.println("Running collocated() test");
    assertEquals("Space1 not on same space as Space2", false, testSpace1.collocated(testSpace2));
    assertEquals("Space2 same as Space3", true, testSpace2.collocated(testSpace3));
  }

  @After
  public void tearDown(){
    testSpace1 = null;
    testSpace2 = null;
    testSpace3 = null;
  }
}