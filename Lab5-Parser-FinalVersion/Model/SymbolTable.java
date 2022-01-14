package Model;

public class SymbolTable {
    public int tableSize;
    public String[] hashTable;

    public SymbolTable(int size) {
        this.tableSize = size;
        this.hashTable = new String[size];
    }

    /*
     *   hashFunction(key)
     *   Function computes the hash of the elements by computing its ASCII code and returning the ASCII code % size of hashtable
     *   input: a key to hash (String)
     *   output: the hashvalue (int)
     *
     * */

    private int hashFunction(String key) {
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
            sum += key.charAt(i);
        }
        return sum % tableSize;
    }

    /*
     *   addElement(keyToBeAdded)
     *   Function adds a new key to the hashtable
     *   input: a new key to be added (String)
     *   output: a String message - "Element already exists!" if the key is already in the hash table
     *                            - "Element added on position i", otherwise
     * */
    public String addElement(String keyToBeAdded){
        if(searchElement(keyToBeAdded)!= -1){
            return "Element already exists!";
        }
        int i = hashFunction(keyToBeAdded);
        // Linear probing for the collision resolution
        while (hashTable[i] != null) {
            i++;
            if (i>= tableSize){
                i=0;
            }
        }
        hashTable[i]=keyToBeAdded;
        return "Element added on position " + i;
    }
    /*
     *   searchElement(key)
     *   Function searches for a key in the hash table and returns its position or -1 if the element is not found
     *   input: a key to search for (String)
     *   output: position (int), if element exists in the hashtable
     *           -1, otherwise
     * */
    public int searchElement(String key){
        int i = hashFunction(key);
        while (hashTable[i] != null) {
            if (hashTable[i].equals(key)) {
                return i;
            }
            if(i>= tableSize -1){
                return -1;
            }
            i++;
        }
        return -1;
    }

    public String getHashTable(){
        String stringHashTable="";
        for (int i=0;i<hashTable.length;i++){
            stringHashTable+= "   "+ i + " | " + hashTable[i] + "\n";
        }
        return stringHashTable;
    }

}