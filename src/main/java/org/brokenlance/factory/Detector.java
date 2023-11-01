package org.brokenlance.factory;

import java.io.Serializable;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This interface uses generic types P and T to define what data type it uses
 * to match and what data type it creates.
 * For instance, a concrete class defined as the following will search String 
 * data types to a String data type.
 * {@code public class StringDetector implements Detector< String, String > }
 * Then the class would override the Predicate< String >::test method:
 * {@code 
 *  @Override
 *  public boolean test( String data )
 *  {
 *     return data.startsWith( "hi" );
 *  } }
 *  If this matches (returns true), then the Supplier< String >::get method will be called
 *  by the Factory class:
 * {@code 
 *  @Override
 *  public String get()
 *  {
 *     return "Hello World";
 *  } }
 */
public interface Detector< P, T > extends Predicate< P >, Supplier< T >, Serializable {}
