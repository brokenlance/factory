# factory
This repository is used to provide a generic type factory implementation that abides by the SOLID principles, 
namely, Dependency Inversion and Open-Closed Principle.

### Usage
This implementation uses a chain handler of "detectors" to match an object to determine which data type to create.
Based on this input type, the detectors will match the input data and create the proper instance.

In this example, the detector is matching String types, though these could be Config objects, etc., to determine
which type to build, in this case a PersistenceManager< T > of type Document:

```java
      Factory< String, PersistenceManager< Document > > pFactory = new Factory<>( "org.brokenlance.persistence" );
      PersistenceManager< Document > pm = pFactory.build( "mongo" );
      pm.serialize( document );
```

In this case, the "file-system" detector should match and create its datatype:

```java
      Factory< String, PersistenceManager< Document > > pFactory = new Factory<>( "org.brokenlance.persistence" );
      PersistenceManager< Document > pm = pFactory.build( "file-system" );
      pm.serialize( document );
```

### Structure
The following diagram exhibits the class hierarchy for the factory framework:
                                                                                                                            
                            +--------------------+                                                                          
                            |                    |                                                                          
                            |   Factory< P, T >  |                                                                             
                            |--------------------|                                                                          
                            | + T: build( P )    |                                                                          
                            |                    |                                                                          
                            +--------------------+                                                                          
                                      | 1                                                                                    
                                      |                                                                                     
                                      v 1..*                                                                                    
                            +---------------------+                                                                          
                            |                     |  Using Java reflection, concrete instances of this interface             
                            |  Detector< P, T >   |  are injected into the Factory class above so that during                                
                            |---------------------|  runtime, the first Detector whose Predicate::test method                
                            | + boolean: test( p )|  matches has its Supplier::get method invoked to create the        
                            | + T:       get()    |  actual type T for this Factory.                                         
                            |                     |                                                                          
                            +---------------------+                                                                          
                                      |                                                                                     
                                     +++                                                                                    
                                    ++ ++                                                                                   
                                   ++   ++                                                                                  
      +------------------+        ++     ++        +-----------------+                                                      
      |                  |       ++       ++       |                 |                                                      
      |  Predicate< P >  |<------+         +------>|  Supplier< T >  |                                                                                 
      |                  |extends          extends |                 |                                                      
      +------------------+                         +-----------------+                                                      
                                                                                                                            
                                                                                                                            
                                                                                                                            
                                                                                                                            
                                                                                                                            
                                                                                                                            
                                                                                                                            
