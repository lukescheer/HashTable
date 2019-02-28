import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.Math.sqrt;


class Pair<K,V> 
{
    /*
    The Pair class is intended to store key, value pairs. It'll be helpful
    for part 1.2 of the assignment.
    */
    public String key;
    public int value;

    public Pair(String key, int value)
   {
        this.key = key;
        this.value = value;
   }
   public String getKey(){
        return (String)this.key;
   }
   public int getValue(){
       return value;
   }
   public void incrementValue(){
       int myValue = value;
       myValue += 1;
       this.value = myValue;
   }
}

/**************PART 1.2.1*******************/

public class HashTableChaining<K,V>
{
   /*
    Write your code for the hashtable with chaining here. You are allowed
    to use arraylists and linked lists.
    */
   private LinkedList[] myHashTable;
   private ArrayList<Pair> myKVPairs;
   public int capacity_of_hashtable;
   public int n = 0;

    public HashTableChaining(int capacity)
    {
        //nitialize the hashtable with capacity equal to the input capacity.

        capacity_of_hashtable = capacity;
        myHashTable = new LinkedList[capacity];
        myKVPairs = new ArrayList<Pair>();

    }
    public ArrayList getAL(){
        return myKVPairs;
    }

    public void insert(String key, int val)
    {
        //Insert the key into the hashtable if it is not already in the hashtable.
        int hashValue = hash(key);

        if(myHashTable[hashValue] == null){
            myHashTable[hashValue] = new LinkedList<String>();
            Pair thisPair = new Pair(key, val);
            myKVPairs.add(thisPair);
            myHashTable[hashValue].add(key);
            n++;
        }
        else if( !myHashTable[hashValue].contains(key) ) {
            Pair thisPair = new Pair(key, val);
            myKVPairs.add(thisPair);
            myHashTable[hashValue].add(key);
            n++;
        }
        else{
            for(Pair object: myKVPairs){
                if(object.getKey().equals(key)){
                    object.incrementValue();
                    break;
                }
            }
        }

        if( n >= 0.75 * capacity_of_hashtable){
            this.rehash();
        }
    }
    public void remove(String key)
    {
        //Remove key from the hashtable if it is present in the hashtable.
        int hashValue = hash(key);
        myHashTable[hashValue].remove(key);

        for(Pair object: myKVPairs){
            if(object.getKey().equals(key)){
                myKVPairs.remove(object);
                break;
            }
        }
    }
    public boolean contains(String key)
    {
        /* 
        Search the hashtable for key, if found return true else
        return false
        */
        int hashValue = hash(key);
        return myHashTable[hashValue].contains(key);
    }
    
    public int size()
    {
        //return the total number of keys in the hashtable.
        return n;
    }
    
    public int hash(String key)
    {
        /*
        Use Horner's rule to compute the hashval and return it.
        */
        int hashcode = 0;
        int hashvalue;
        for(int i = 0 ; i < key.length(); i++) {
            hashcode = (37 * hashcode + key.charAt(i)) % capacity_of_hashtable;
        }
        hashvalue = hashcode % capacity_of_hashtable;
        return hashvalue;
    }

    public int getVal(String key)
    {
        //return the value corresponding to the key in the hashtable.
        int val = 0;
        for(Pair object: myKVPairs){
            if(object.getKey().equals(key)){
                val = object.getValue();
                break;
            }
        }
        return val;
    }

    public int nextPrime(int input){
        int counter;
        input++;
        while(true){
            counter = 0;
            for(int i = 2; i <= sqrt(input); i ++){
                if(input % i == 0)  counter++;
            }
            if(counter == 0)
                return input;
            else{
                input++;
                continue;
            }
        }
    }

    public void rehash()
    {   /*
        Resize the hashtable such that the new size is the first prime number
        greater than two times the current size.
        For example, if current size is 5, then the new size would be 11.
        */
        int newSize = nextPrime(capacity_of_hashtable * 2);
        capacity_of_hashtable = newSize;

        int newHashValue;
        String newKey;
        LinkedList[] myNewHashTable;
        myNewHashTable = new LinkedList[newSize];
        for(int i = 0; i < myKVPairs.size(); i++){
            newKey = myKVPairs.get(i).getKey();
            newHashValue = hash(newKey);
            if(myNewHashTable[newHashValue] == null){
                myNewHashTable[newHashValue] = new LinkedList<String>();
                myNewHashTable[newHashValue].add(newKey);
            }
            else if( !myNewHashTable[newHashValue].contains(newKey) ) {
                myNewHashTable[newHashValue].add(newKey);
            }
        }
        myHashTable = myNewHashTable;


    }
    
    /**************PART 1.2*******************/

    public String[] mostFrequentStrings(String[] in)
    {
        /*
        Given an array of strings, print the five most
        frequent strings. You must use your implementation
        for hashtable with seperate chaining for this.
        */
        int value;
        String[] mostFrequent = new String[5];
        HashTableChaining htc = new HashTableChaining<String, Integer>(400);
        for(int i = 0; i < in.length; i++){
            if(in[i] != null) {
                htc.insert(in[i], 1);
            }
        }
        ArrayList<Pair> kv;
        kv = htc.getAL();
        int[] freq = new int[kv.size()];
        for(int i = 0; i < kv.size(); i++){
            freq[i] = kv.get(i).getValue();
        }
        int large[] = new int[5];
        int max;
        int index;
        for (int j = 0; j < 5; j++) {
            max = freq[0];
            index = 0;
            for (int i = 1; i < freq.length; i++) {
                if (max < freq[i]) {
                    max = freq[i];
                    index = i;
                }
            }
            large[j] = index;
            freq[index] = Integer.MIN_VALUE;
        }

        for(int i = 0; i < 5; i++){
            mostFrequent[i] = kv.get(large[i]).getKey();
        }



        return mostFrequent;

    }
    
}
