package org.brokenlance.factory;

public class TestDetector implements Detector< String, String >
{ 
   /**
    * @param TYPE
    * @return TYPE
    */
   public TestDetector()
   {
   }

   /**
    * @param TYPE
    * @return TYPE
    */
   @Override
   public String get()
   {
      return "Hello World";
   }

   /**
    * @param TYPE
    * @return TYPE
    */
   @Override
   public boolean test( String data )
   {
      return data.startsWith( "hi" );
   }
}
