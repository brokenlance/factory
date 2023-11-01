# factory
This repository is used to provide a generic type factory implementation that abides by the SOLID principles, 
namely, Dependency Inversion and Open-Closed Principle.

### Usage
This implementation uses a chain handler of "detectors" to match an object to determine which data type to create.
Based on this input type, the detectors will match the input data and create the proper instance.

In this example, the detector is matching String types, though these could be Config objects, etc., to determine
which type to build, in this case a PersistenceManager< T > of type Document:

```java
      Factory< String, PersistenceManager< Document > > pFactory = new Factory<>( "com.example.demo.persistence" );
      PersistenceManager< Document > pm = pFactory.build( "mongo" );
      pm.serialize( document );
```

In this case, the "file-system" detector should match and create its datatype:

```java
      Factory< String, PersistenceManager< Document > > pFactory = new Factory<>( "com.example.demo.persistence" );
      PersistenceManager< Document > pm = pFactory.build( "file-system" );
      pm.serialize( document );
```
