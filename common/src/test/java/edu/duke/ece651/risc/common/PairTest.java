package edu.duke.ece651.risc.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PairTest {

  // Tests basic Pair Class functionality with Integer
  @Test
  public void test_int_pair() {
    Pair<Integer> p = new Pair<Integer>(2, 3);
    assertEquals(2, p.getFirst());
    assertEquals(3, p.getSecond());
  }

  // Tests basic Pair Class functionality with String
  @Test
  public void test_string_pair() {
    Pair<String> p = new Pair<String>("Hello", "World");
    assertEquals("Hello", p.getFirst());
    assertEquals("World", p.getSecond());
  }

}
