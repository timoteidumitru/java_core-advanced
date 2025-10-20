## Java Core

### OOP Concepts:
- **Encapsulation**: Bundling data and methods together, restricting direct access to them.
  - *Use case*: In *Entity* and *DTO* classes.
- **Inheritance**: Creating new classes by inheriting attributes and behaviors from existing ones.
  - *Use case*: In *Service* or *DAO* classes.
- **Polymorphism**: Allowing different classes to be treated through the same interface, with varied behaviors.
  - *Use case*: When *Overloading* or *Overriding* methods, with Interfaces in general.
- **Abstraction**: Simplifying complex systems by exposing only essential features, hiding unnecessary details.
  - *Use case*: Widely used in method overriding, interface implementations, working with collections, dependency injection.

### Exceptions/Errors and their order:
- **Error**(unchecked):
  - OutOfMemoryError 
  - StackOverflowError
- **Exception**(checked):
  - **RuntimeException**(unchecked):
    - NullPointerException
    - IllegalArgumentException
    - ArithmeticException
  - **Other Exceptions**(Checked) - named as: "compile time exception"
    - ClassNotFoundException
    - IOException
    - FileNotFoundException
    - SQLException

### Final, Finally and Finalize:
- **Final** is a keyword, used for fields and methods in a class
- **Finally** is a code block, used for cleanup purposes(streams) replaced by try with resource approach
- **Finalize** is a method used by JVM for cleanup of unused objects

### Equals and Hashcode
- **Hashcode**: used to generate unique or reference number of an object through which we can easily identify uniqueness of an object.
- **Equals**: used to compare objects based on its content.

### Strings immutability
- creation:
  - object creation: `String s1 = new String("javaString");` -> heap then go to SCP
  - literals: `String s2 = "javaString"; -> SCP(string constant pool area)`
- check their hashCode and use same value from SCP:
  - `s1.intern().hashCode==s2.hashCode()`
- check value from SCP(string constant pool area)
  - `s1.intern()`
- why strings are immutable?
  - allow existence of SCP, saves heap memory
  - allow safe multithreading
  - hashCode cached at time creation, no need to be recalculated later
  - for security purpose like database connexion, credentials

### Strings mutability
- using StringBuffer and StringBuilder with their `.append()` method

### Collections
- List -> will allow store duplicate values, work on index. Used for frequently access the elements by index.
  - ArrayList -> internally uses dynamic array to store elements. Slow to manipulate elements but great for storing and accessing.
  - LinkedList -> internally use double linked list to store elements. Great for manipulation of elements.
  - CopyOnWriteArrayList
- Set -> will not allow store duplicate values, work base on hash. Used when design a collection of distinct elements.
  - HasSet -> by default will store unique values only if primitive data type is used, if used to store custom class need to override hashCode() and equals() methods.
  - LinkedHasSet
  - TreeSet
  - CopyOnWriteArraySet
- Map -> Store key value pairs of data.
  - HasMap 
  - LinkedHasMap
  - TreeMap
  - ConcurrentHasMap

### Comparable and Comparator
- Comparable:
  - Defines natural ordering: Implements the compareTo() method to define the default sorting order of objects.
  -  Single sorting sequence: The class implementing Comparable can only have one sorting logic.
- Comparator:
  - Customizable ordering: Implements the compare() method to define multiple sorting sequences, allowing custom ordering logic.
  - Separate from the class: Comparator is usually implemented in a separate class, enabling different sorting strategies for the same objects.

### Fail fast and Fail safe
- Fail Fast:
  - Immediate failure detection: Fail-Fast iterators throw ConcurrentModificationException as soon as they detect structural modification during iteration.
  - Not thread-safe: Fail-Fast iterators do not operate on a clone of the collection, making them unsafe for concurrent modifications without external synchronization(Ex: ArrayList, HashMap and Vector)
- Fail Safe:
  - No immediate failure: Fail-Safe iterators operate on a cloned copy of the collection, so they don't throw exceptions on structural modifications during iteration.
  - Thread-safe: Fail-Safe iterators are generally safe for concurrent modifications but may not reflect real-time changes in the underlying collection(Ex: CopyOnWriteArrayList, CopyOnWriteArraySet and ConcurrentHashMap)

### Functional Interfaces
- **Function** -> Accepts input and returns a result.
  - **Input**: Takes an argument.
  - **Output**: Returns a result.
  - **Operation**: Applies a function.
- **Predicate** -> Evaluates input and returns true or false.
  - **Input**: Takes an argument.
  - **Output**: Returns a boolean.
  - **Operation**: Evaluates a condition.
- **Consumer** -> Performs action on input without returning result.
  - **Input**: Takes an argument.
  - **Output**: No return value.
  - **Operation**: Performs an action.
- **Supplier** -> Provides output without requiring any input.
  - **Input**: Takes no arguments.
  - **Output**: Returns a result.
  - **Operation**: Supplies a value.

### Multithreading
- Using extend to **Thread** class or implement **Runnable** interface
  - When implement **Runnable** interface we must override `run()` method.
  - To start a thread we use `.start()` method.
  - We use `synchronized()` along with `notify()` methods to synchronize threads.
  - Method `.shutdown()` is used to close a thread.

### Miscellaneous
- **interfaces in java7 and java8**: 
  - **Java 7**:
    - Interfaces can only have abstract methods.
    - Cannot have any method implementations.
    - Can only contain public, static, and final variables.
  - **Java 8**:
    - Introduced default methods with implementations.
    - Allowed static and default methods in functional interfaces but need to implement them.
    - Enabled the use of lambda expressions for functional interfaces. 
- **application performance monitor**
  - **actuator**: Use endpoints like `/actuator/health`, `/actuator/metrics`, and `/actuator/httptrace` to monitor the health, performance metrics, and HTTP request details of application.
  - **monitoring tools**: Connect the Actuator metrics to external monitoring systems like **Prometheus** and **Grafana** for enhanced monitoring and visualization.

