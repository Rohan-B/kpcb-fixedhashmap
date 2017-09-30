/*
Fixed HashMap Class
Description: Initializes a HashMap with a fixed size
Author: Rohan Bhargava
*/

public class FixedHashMap<T extends Object> {
  private Object[] storage;
  private String[] keys;
  private int size;
  private int count;

  /*Constructor to initialize the hashmap with the given size */
  public FixedHashMap(int size) {
    storage = new Object[size];
    keys = new String[size];
    this.size = size;
    this.count = 0;
  }

  /*---------------------------------------------------------------------------
  Name:           set
  Description     Set the key to correspond to the given value
  Parameters:     String key - key to use
                  T value - value to set key equal to
  Returns:        true if the operation was successful
                  false if there are hashmap is at max capacity already
  ---------------------------------------------------------------------------*/
  public boolean set(String key, T value) {
    //find a spot where this key can go
    int insertLoc = findSpot(key);

    //if the hashmap is full then return false as set cannot be completed
    if(keys[insertLoc] != null && !key.equals(keys[insertLoc])) {
      return false;
    }

    //increment count of items in the map
    if(keys[insertLoc] == null) {
      count++;
    }

    keys[insertLoc] = key;
    storage[insertLoc] = value;
    return true;
  }

  /*---------------------------------------------------------------------------
  Name:           get
  Description     Get the value to correspond to the given key
  Parameters:     String key - key to use
  Returns:        value the key corresponds to, null if not a valid key
  ---------------------------------------------------------------------------*/
  public T get(String key) {
    int location = findSpot(key);

    //return null if this key is not valid
    if(!key.equals(keys[location])) {
      return null;
    }

    return (T) storage[location];
  }

  /*---------------------------------------------------------------------------
  Name:           delete
  Description     delete the key value pair from storage
  Parameters:     String key - key to use
  Returns:        the T object stored at the key deleted
  ---------------------------------------------------------------------------*/
  public T delete(String key) {
    int location = findSpot(key);
    Object retVal = storage[location];

    //if there is nothing stored under that key return null
    if(!key.equals(keys[location])) {
      return null;
    }

    //reset all storage values to null
    if(retVal != null) {
      storage[location] = null;
      keys[location] = null;
      count--;
    }
    return (T) retVal;

  }

  /*---------------------------------------------------------------------------
  Name:           load
  Description     Return the load on the current hashmap
  Parameters:     None
  Returns:        float  - current load
  ---------------------------------------------------------------------------*/
  public float load() {
    if(size == 0.0) {
      return 0;
    }

    return (float)count / (float)(size * 1.0);
  }

  /*---------------------------------------------------------------------------
  Name:           findSpot
  Description     Finds the current location or the next possible location for
                  the given key
  Parameters:     String key - key to use
  Returns:        true if the operation was successful
                  false if there are hashmap is at max capacity already
  ---------------------------------------------------------------------------*/
  private int findSpot(String key) {
    int count = 0;
    // Convert the key into an int using built in hash functions
    int starting = Math.abs(key.hashCode() % size);
    //find the first empty spot or the spot that this key is in
    while(keys[starting] != null && count <= size) {
      if(key.equals(keys[starting])) {
        return starting;
      }
      starting++;
      starting %= size;
      //make sure we don't loop forever
      count++;
    }

    //return this spot
    return starting;
  }
}
