import static java.lang.Math.sqrt;

/* Quadratic Probing */
	public class QuadraticProbing<AnyType>
	{
		private static final int DEFAULT_TABLE_SIZE = 13;
		private HashEntry<AnyType> [ ] array; // The array of elements
		public int size;
		public int n;
	
	public static class HashEntry<AnyType>
	{
		/* Initialize the entries here. You can write a constructor for the same */
		public AnyType  element; 
		public boolean isActive;  // For Lazy deletion

		public HashEntry(AnyType element){
			this.element = element;
			isActive = true;
		}
		public void changeElement(AnyType element){
			this.element = element;
			isActive = true;
		}
		public String toString()
		{
			if(this.element!=null)
				return (String) element;
			else
				return "NULL";
		}
	}


/* Construct the hash table */
	public QuadraticProbing( )
	{
		this( DEFAULT_TABLE_SIZE );
	}

/* Construct the hash table */

	public QuadraticProbing( int size )
	{
		/* allocate memory to hash table */
		array = (HashEntry<AnyType>[]) new HashEntry[size];
		this.size = size;
		n = 0;
	}


/* Return true if currentPos exists and is active - Lazy Deletion*/
	public boolean isActive(int position)
	{
		return array[position].isActive;
	}
	

/* Find an item in the hash table. */
	public boolean contains( AnyType x )
	{
		/* returns the active status of key in hash table */
		int index;
		int firstInactive = -1;
		int hashValue = hash( (x.toString()), size);
		for(int i = 0; i < size; i++){
			index = (i * i + hashValue) % size;
			if(array[index] == null){
				return false;
			}
			if(!array[index].isActive && firstInactive == -1){
				firstInactive = index;
			}
			if(array[index].isActive && array[index].toString().equals(x.toString())){
				if(firstInactive != -1){
					array[index].isActive = false;
					array[firstInactive].changeElement(x);
				}
				return true;
			}
		}
		return false;
	}


/* Insert into the Hash Table */
	
	public void insert( AnyType x )
	{
		/* Insert an element */
		int hashValue = hash( (x.toString()), size);
		int index;
		for(int i = 0; i < size; i++){
			index = (i * i + hashValue) % size;
			if(array[index] == null){
				array[index] = new HashEntry(x);
				n++;
				break;
			}
			if(array[index].isActive && array[index].toString().equals(x.toString())){
				break;
			}
			else{
				if(!array[index].isActive){
					array[index].changeElement(x);
					n++;
					break;
				}
			}
		}
	}


/* Remove from the hash table. */
	
	public void remove( AnyType x )	
	{
		/* Lazy Deletion*/
		for(int i = 0; i < size; i++){
			if(array[i] != null && array[i].isActive && array[i].toString().equals(x.toString())){
				array[i].isActive = false;
				n--;
				break;
			}
		}
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
/* Rehashing for quadratic probing hash table */
	private void rehash( )
	{
		int newSize = nextPrime(size * 2);
		AnyType x;
		n=0;

		HashEntry<AnyType>[] array;
		array = new HashEntry[newSize];
		for(int j = 0; j < size; j++){
			if(array[j] != null && array[j].isActive) {
				x = array[j].element;
				int hashValue = hash( (x.toString()), size);
				int index;
				for(int i = 0; i < size; i++){
					index = (i * i + hashValue) % size;
					if(array[index] == null){
						array[index] = new HashEntry<AnyType>(x);
						n++;
						break;
					}
					else{
						if(!array[index].isActive){
							array[index].changeElement(x);
							n++;
							break;
						}
					}
				}
			}
		}
		size = newSize;
		this.array = array;

	}
	

/* Hash Function */
	public int hash( String key, int tableSize )
	{
		/**  Make sure to type cast "AnyType"  to string 
		before calling this method - ex: if "x" is of "AnyType", 
		you should invoke this function as hash((x.toString()), tableSize) */

		int capacity_of_hashtable = tableSize;
		int hashcode = 0;
		int hashvalue;
		for(int i = 0 ; i < key.length(); i++) {
			hashcode = (37 * hashcode + key.charAt(i)) % capacity_of_hashtable;
		}
		hashvalue = hashcode % capacity_of_hashtable;
		return hashvalue;
		/* Compute the hash code*/
	}

	public int probe(AnyType x)
	{
		/* Return the number of probes encountered for a key */
		int index;
		int firstInactive = -1;
		int hashValue = hash( (x.toString()), size);
		for(int i = 0; i < size; i++){
			index = (i * i + hashValue) % size;
			if(array[index] == null){
				return i;
			}
			if(!array[index].isActive){
				firstInactive = index;
			}
			if(array[index].isActive && array[index].toString().equals(x.toString())){
				return i;
			}
		}
		return 1;
	}
	
}

