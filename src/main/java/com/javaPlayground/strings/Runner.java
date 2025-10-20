package com.javaPlayground.strings;

public class Runner {
    /*
        - A string in Java is an immutable sequence of characters used to represent text.
        - Immutable: Strings can't be changed after creation; any modification creates a new string.
        - String Pool: Java stores string literals in a special memory area to optimize memory use.
        - Concatenation: Strings can be joined using +, but StringBuilder is more efficient for frequent changes.
        - Methods: The String class provides methods like substring(), replace(), and toUpperCase() for manipulation.
        - Equality Check: Use .equals() to compare string contents, as == checks reference equality.
        - Mutable Alternatives: StringBuilder and StringBuffer are mutable classes for handling strings.
    */

    public static void main(String[] args) {
        // Instantiating a string using the constructor, which creates a new String object
        String usingConstructor = new String("immutable"); // Heap (new object), "immutable" is in SCP

        // Creating an immutable string using template literals
        String immutable = "immutable"; // SCP (String Constant Pool)

        // Concatenating "string" to the immutable string, resulting in a new String object
        String newStr = immutable.concat(" string");

        // Printing the original immutable string, which remains unchanged
        System.out.println(immutable); // "immutable"

        // Comparing the interned original string with the new string for content equality
        System.out.println(immutable.intern().equals(newStr)); // false

        // Printing the interned version of the new string, which may add it to the string pool if not already present
        System.out.println(newStr.intern()); // "immutable string"

        // Creating a mutable StringBuilder object with the value "mutable"
        StringBuilder mutable = new StringBuilder("mutable");

        // Appending " string" to the mutable StringBuilder object, modifying its value
        mutable.append(" string");

        // Printing the content of the mutable StringBuilder
        System.out.println(mutable); // "mutable string"
    }

}
