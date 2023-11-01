package org.brokenlance.factory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import static org.reflections.scanners.Scanners.SubTypes;

/**
 * This class functions as a Factory builder that uses two generic types P and T, 
 * which define the type, P, used to determine which detector/builder to use to build
 * the return type T.
 * This implementation uses reflection, namely, the org.reflections library, to find
 * the detectors that are used to determine which data type to build.
 * The Detector< P, T > interface is used by this Factory pattern.
 */
@Slf4j
public class Factory< P, T > implements Serializable
{
   private List< Detector< P, T > > detectors = new ArrayList<>();

   /**
    *  Default constructor.
    */
   public Factory()
   {
   }

   /**
    * Primary constructor.
    * We can also filter the reflections library search path by adding a filter to prevent searching in certain dirs:
    *   filterInputsBy( new FilterBuilder().excludePackage( "package" ) );
    */
   public Factory( String packageName )
   {
      Reflections reflection = new Reflections( new ConfigurationBuilder().forPackage( packageName )
                                                                          .setScanners( Scanners.values() ) );

      for( Class< ? > clazz : reflection.get( SubTypes.of( Detector.class ).asClass() ) )
      {
         try
         {
            detectors.add( (Detector< P, T >) clazz.getDeclaredConstructor().newInstance() );
         }
         catch( NoSuchMethodException e )
         {
            log.error( "Unable to find detector constructor for class: {}", clazz );
         }
         catch( InstantiationException e )
         {
            log.error( "Unable to instantiate class: {}", clazz );
         }
         catch( IllegalAccessException e )
         {
            log.error( "Unable to access class: {}", clazz );
         }
         catch( InvocationTargetException e )
         {
            log.error( "Unable to invoke target class: {}", clazz );
         }
      }

      log.debug( "detectors: {}", detectors );
   }

   /**
    * @param P The parameter for which the detector is checking for a match.
    * @return T The data type that the factory builder will create.
    */
   public T build( P parameter )
   {
      Detector< P, T > detector = detectors.parallelStream().filter( d -> d.test( parameter ) ).findFirst().orElse( null );

      log.debug( "found detector: {}", detector );

      return detector == null ? null : detector.get();
   }
}
