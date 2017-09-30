import java.util.Scanner;

public class Testing {

  public static void main(String[] args) {
    //initialize a default hashmap of size 100
    FixedHashMap<String> fixedMap = new FixedHashMap<>(100);
    //initialize a scanner to get user input
    Scanner scan = new Scanner(System.in);
    String input = "";

    //The below while loop handles user input for the hashmap
    System.out.println("Initialized a default fixedhashmap with size 100");
    while(input != "e") {
      System.out.print("\nPlease enter what command you would like to do (enter `h` for more info): ");
      input = scan.next();
      String key = "";
      String value = "";
      boolean status = false;

      switch(input) {
        case "set":
          System.out.print("Please enter a key: ");
          key = scan.next();
          System.out.print("Please enter a value: ");
          value = scan.next();
          status = fixedMap.set(key, value);
          if(status) {
            System.out.println("Successfully added key " + key + " and value " + value);
          } else {
            System.out.println("The hashmap is already at max capacity.");
          }
          break;
        case "delete":
          System.out.print("Enter a key to delete: ");
          key = scan.next();
          if(fixedMap.delete(key) != null) {
            System.out.println("Successfully deleted " + key);
          } else {
            System.out.println("Delete failed");
          }
          break;
        case "get":
          System.out.print("Enter a key to get: ");
          key = scan.next();
          value = fixedMap.get(key);
          if(value != null) {
            System.out.println("Value: " + value);
          } else {
            System.out.println("Key: " + key + " does not exist.");
          }
          break;
        case "new":
          System.out.print("Enter the size of the new hashmap: ");
          int size = scan.nextInt();
          fixedMap = new FixedHashMap<String>(size);
          System.out.println("Successfully initialized a new hashmap of size " + size);
          break;
        case "load":
          System.out.println("The current load on the system is: " + fixedMap.load());
          break;
        case "test":
          runAllTests();
          System.out.println("Ran all tests");
          break;
        default:
          System.out.println(
          "List of commands: \n" +
          "h: help \n" +
          "set: insert a new element into the hashmap \n" +
          "get: get an element from the hashmap \n" +
          "delete: delete an element from the hashmap \n" +
          "load: get the current load of the hashmap \n" +
          "new: creates a new hashmap (String keys and values) \n" +
          "test: runs the test functions \n");
      }
    }

    //close the scanner
    scan.close();

  }


  /* Testing Functions */
  public static void runAllTests() {
    //a few sample tests written to test the FixedHashMap
    //it might be easier to use the commands above to test ^
    testInsertFailure();
    testInsertOverwrite();
    testStress();
  }

  public static void testInsertFailure() {
    FixedHashMap<String> fixedMap = new FixedHashMap<>(5);
    fixedMap.set("1", "testString1");
    fixedMap.set("2", "testString2");
    fixedMap.set("3", "testString3");
    fixedMap.set("4", "testString4");
    fixedMap.set("5", "testString5");
    if(fixedMap.set("6", "hello2")) {
      throw new IllegalStateException("The hashmap should have not inserted another element");
    }
  }

  public static void testInsertOverwrite() {
    FixedHashMap<String> fixedMap = new FixedHashMap<>(5);
    fixedMap.set("hi", "hello");
    fixedMap.set("hi2", "hello2");
    fixedMap.set("hi3", "hello3");
    fixedMap.set("hi4", "hello4");
    fixedMap.set("hi5", "hello4");
    fixedMap.set("hi4", "not_hello");

    if(fixedMap.get("hi4") != "not_hello") {
      throw new IllegalStateException("The hashmap should have overwritten the previous element");
    }
  }

  public static void testStress() {
    FixedHashMap<Integer> fixedMap = new FixedHashMap<>(100);
    for(int i = 0; i < 100; i++) {
      if(fixedMap.set("input" + i, i) == false) {
        throw new IllegalStateException("The hashmap insert failed.");
      }
    }

    for(int i = 0; i < 100; i++) {
      Integer value = fixedMap.get("input" + i);
      if(value == null || value != i) {
        throw new IllegalStateException("The hashmap returned the wrong value");
      }
    }
  }

}
