# factory
This repository is used to provide a generic type factory implementation that abides by the SOLID principles, 
namely, Dependency Inversion and Open-Closed Principle.

### Usage
This implementation uses a chain handler of "detectors" to match an object to determine which data type to create.
Based on this input type, the detectors will match the input data and create the proper instance.
```java
      Factory< String, PersistenceManager< Document > > pFactory = new Factory<>( "com.example.demo.persistence" );
      // PersistenceManager< Document > pm = pFactory.build( "file-system" );
      PersistenceManager< Document > pm = pFactory.build( "mongo" );
      pm.serialize( document );
      Document deser = pm.deserialize();
```
