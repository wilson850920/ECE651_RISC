package edu.duke.ece651.risc.common;

import java.io.Serializable;

/**
 * Represents a Pair of objects (used in upgrade map)
 */
public class Pair<T> implements Serializable {
  private T first;
  private T second;

  /**
   * Constructs a Pair of two objects of type T
   *
   * @param T first is the first object
   * @param T second is the second object
   */
  public Pair(T first, T second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Gets the first object in the pair
   *
   * @return T is the first object in the pair
   */
  public T getFirst() {
    return this.first;
  }

  /**
   * Gets the second object in the pair
   *
   * @return T is the second object in the pair
   */
  public T getSecond() {
    return this.second;
  }
  
}
