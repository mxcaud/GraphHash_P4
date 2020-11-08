import java.util.Map;
import java.util.Set;

import java.util.Collection; //for returning in the values() function only

//If you uncomment the import to ArrayList below, the grader will manually
//look to make sure you are not using it anywhere else... if you use it
//somewhere else you will get 0pts on the entire project (not a joke).

//uncomment the line below ONLY if you are implementing values().
//import java.util.ArrayList; //for returning in the values() function only

/**
 * Class ThreeTenHashMap represents object key value pair elements with chaining.
 * @param <K> key for an object.
 * @param <V> value for an object.
 */
class ThreeTenHashMap<K,V> implements Map<K,V> {
	//********************************************************************************
	//   DO NOT EDIT ANYTHING IN THIS SECTION (except to add the JavaDocs)
	//********************************************************************************

	//you must use this storage for the hash table
	//and you may not alter this variable's name, type, etc.
	private Node<K,V>[] storage;

	//you must use to track the current number of elements
	//and you may not alter this variable's name, type, etc.
	private int numElements = 0;

	//********************************************************************************
	//   YOUR CODE GOES IN THIS SECTION
	//********************************************************************************

	private int originalSize;

	@SuppressWarnings("unchecked")
	public ThreeTenHashMap(int size) {
		//Create a hash table where the size of the storage is
		//the provided size (number of "slots" in the table)
		//You may assume size is >= 1

		//Remember... if you want an array of ClassWithGeneric<V>, the following format ___SHOULD NOT___ be used:
		//         ClassWithGeneric<V>[] items = (ClassWithGeneric<V>[]) new Object[10];
		//instead, use this format:
		//         ClassWithGeneric<V>[] items = (ClassWithGeneric<V>[]) new ClassWithGeneric[10];
		storage = (Node<K,V>[]) new Node[size];
		originalSize = size;
	}

	@SuppressWarnings("unchecked")
	public void clear() {
		//the table should return to the original size it had
		//when constructed
		//O(1)
	}

	public boolean isEmpty() {
		//O(1)
		return size()==0;
	}

	public int getSlots() {
		//return how many "slots" are in the table
		//O(1)
		return -1;
	}

	public int size() {
		//return the number of elements in the table
		//O(1)
		return numElements;
	}

	public V get(Object key) {
		//Given a key, return the value from the table.

		//If the value is not in the table, return null.

		//Worst case: O(n), Average case: O(1)

		return null;
	}

	public Set<K> keySet() {
		//O(n+m) or better, where n is the size and m is the
		//number of slots

		//Hint: you aren't allowed to import
		//anything, but a ThreeTenHashSet is a Set

		return null;
	}

	public V remove(Object key) {
		//Remove the given key (and associated value)
		//from the table. Return the value removed.
		//If the value is not in the table, return null.

		//Hint: Remember there are no tombstones for
		//separate chaining! Don't leave empty nodes!

		//Worst case: O(n), Average case: O(1)

		return null;
	}

	private V putNoExpand(K key, V value) {
		//Place value v at the location of key k.
		//Use separate chaining if that location is in use.
		//You may assume both k and v will not be null.
		//This method does NOT handle issues with the load,
		//that is handled by put(K,V) in the provided section
		//The return value is the same as specified by put()
		//(see the Map interface).

		//Hint 1: Make a TableEntry to store in storage
		//and use the absolute value of k.hashCode() for
		//the location of the entry.

		//Here's something you may want to know about Math's
		//absolute value method:
		//	"If the argument is equal to the value of Integer.MIN_VALUE,
		//  the most negative representable int value, the result is
		//  that same value, which is negative." -Oracle Docs

		//Hint 2: Remember that you're dealing with an array
		//of linked lists in this part of the project, not
		//an array of table entries.

		//If the key already exists in the table
		//replace the current value with v.

		//If the key does not exist in the table, add
		//the new node to the _end_ of the linked list.

		//This method is used by the provided put() and
		//rehash() methods, so if those aren't working,
		//look here!

		//Worst case: O(n) where n is the number
		//of items in the list, NOT O(m) where m
		//is the number of slots, and NOT O(n+m)
		return null;
	}

	/**
	 * Change floating point to an absolute value.
	 * @param absNum number to pass and convert.
	 * @return absolute converted value.
	 */
	private int absVal(int absNum){
		int abs;
		if (absNum < 0) abs = -absNum;
		else abs = absNum;
		return abs;
	}

	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------

	/**
	 * Tester class for ThreeTenHashMap.
	 * Tests if values are being added properly and compare size.
	 * @param args map.
	 */
	public static void main(String[] args) {
		//main method for testing, edit as much as you want
		ThreeTenHashMap<String,String> st1 = new ThreeTenHashMap<>(10);
		ThreeTenHashMap<String,Integer> st2 = new ThreeTenHashMap<>(5);

		st1.put("a","apple");
		st1.put("b","banana");
		st1.put("banana","b");
		st1.put("b","butter");

		if(st1.toString().equals("a:apple\nbanana:b\nb:butter") && st1.toStringDebug().equals("[0]: null\n[1]: null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: [a:apple]->[banana:b]->null\n[8]: [b:butter]->null\n[9]: null")) {
			System.out.println("Yay 1");
		}

		if(st1.getSlots() == 10 && st1.size() == 3 && st1.get("a").equals("apple")) {
			System.out.println("Yay 2");
		}

		st2.rehash(1);
		st2.put("a",1);
		st2.put("b",2);

		if(st2.toString().equals("b:2\na:1") && st2.toStringDebug().equals("[0]: [b:2]->null\n[1]: [a:1]->null")
				&& st2.put("e",3) == null && st2.put("y",4) == null &&
				st2.toString().equals("a:1\ne:3\ny:4\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: [a:1]->[e:3]->[y:4]->null\n[2]: [b:2]->null\n[3]: null")) {
			System.out.println("Yay 3");
		}

		if(st2.remove("e").equals(3) && st2.rehash(8) == true &&
				st2.size() == 3 && st2.getSlots() == 8 &&
				st2.toString().equals("a:1\ny:4\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: [a:1]->[y:4]->null\n[2]: [b:2]->null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: null")) {
			System.out.println("Yay 4");
		}

		ThreeTenHashMap<String,String> st3 = new ThreeTenHashMap<>(2);
		st3.put("a","a");
		st3.remove("a");

		if(st3.toString().equals("") && st3.toStringDebug().equals("[0]: null\n[1]: null")) {
			st3.put("a","a");
			if(st3.toString().equals("a:a") && st3.toStringDebug().equals("[0]: null\n[1]: [a:a]->null")) {
				System.out.println("Yay 5");
			}
		}

		//This is NOT all the testing you need... several methods are not
		//being tested here! Some method return types aren't being tested
		//either. You need to write your own tests!

		//Also, try this and see if it works:
		//ThreeTenHashMap<Integer,Integer> st4 = new ThreeTenHashMap<>(10);
		//st4.put(Integer.MIN_VALUE, Integer.MIN_VALUE);
	}

	//********************************************************************************
	//   YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
	// These are some methods we didn't write for you, but you could write,
	// if you need/want them for building your graph. We will not test
	// (or grade) these methods.
	//********************************************************************************

	/**
	 * Shows all values in the hashmap.
	 * @return value colleciton.
	 */
	public Collection<V> values() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Put all the element pairs into the hashmap.
	 * @param m unkown usage.
	 */
	public void	putAll(Map<? extends K,? extends V> m) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Check if value is contained in the hashmap.
	 * @param value to check object.
	 * @return boolean value true if value could be found.
	 */
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unused, sets map entry to initial set in front.
	 * @return stuff.
	 */
	public Set<Map.Entry<K,V>> entrySet() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unused boolean check if object is equal to another.
	 * @param o object to compare.
	 * @return boolean true if objects are equal.
	 */
	public boolean equals(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unused integer value of hash key.
	 * @return the hash key.
	 */
	public int hashCode() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unused boolean for getting value of key.
	 * @param key value.
	 * @return value.
	 */
	public boolean containsKey(Object key) {
		throw new UnsupportedOperationException();
	}

	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
	// We will check these things to make sure they still work, so don't break them.
	//********************************************************************************

	/**
	 * Node class with object that has a key value pair. Including next node in list.
	 * @param <K> key of object pair.
	 * @param <V> value of object pair.
	 */
	//THIS CLASS IS PROVIDED, DO NOT CHANGE IT
	public static class Node<K,V> {
		public TableEntry<K,V> entry;
		public Node<K,V> next;

		/**
		 * Setter entry to entry for Node.
		 * @param entry value.
		 */
		public Node(TableEntry<K,V> entry) {
			this.entry = entry;
		}

		/**
		 * Nodes constructor.
		 * @param entry object pair table.
		 * @param next object pair Node next.
		 */
		public Node(TableEntry<K,V> entry, Node<K,V> next) {
			this(entry);
			this.next = next;
		}

		/**
		 * Show object as a string value delimited by colon per object of Node.
		 * @return string value of object.
		 */
		public String toString() {
			return "[" + entry.toString() + "]->";
		}
	}

	/**
	 * A static class that has a table entry which contains a key value pair.
	 * @param <K> key object.
	 * @param <V> value object.
	 */
	//THIS CLASS IS PROVIDED, DO NOT CHANGE IT
	public static class TableEntry<K,V> {
		public K key;
		public V value;

		/**
		 * Constructor for TableEntry.
		 * @param key object.
		 * @param value object.
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Show object as a string value delimited by colon per object.
		 * @return string value of object.
		 */
		public String toString() {
			return key.toString()+":"+value.toString();
		}
	}

	/**
	 * Convert table entry to array, iterating through table while element is not null.
	 * @return collection of array elements.
	 */
	public TableEntry[] toArray(){
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT

		TableEntry[] collection = new TableEntry[this.numElements];
		int index = 0;
		for(int i = 0; i < storage.length; i++) {
			if(storage[i] != null) {
				Node<K,V> curr = storage[i];
				while(curr != null) {
					collection[index] = curr.entry;
					index++;
					curr = curr.next;
				}
			}
		}
		return collection;
	}

	/**
	 * Prints everything inside of the hashtable without debug null verbose print.
	 * @return elements inside the hashtable in use excluding debug features.
	 */
	public String toString() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT

		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			Node<K,V> curr = storage[i];
			if(curr == null) continue;

			while(curr != null) {
				s.append(curr.entry.toString());
				s.append("\n");
				curr = curr.next;
			}
		}
		return s.toString().trim();
	}

	/**
	 * Prints everything inside of the hashtable.
	 * @return elements inside the hashtable in use.
	 */
	public String toStringDebug() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT

		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			Node<K,V> curr = storage[i];

			s.append("[" + i + "]: ");
			while(curr != null) {
				s.append(curr.toString());
				curr = curr.next;
			}
			s.append("null\n");
		}
		return s.toString().trim();
	}

	/**
	 * Rehash the hashtable to the provided size.
	 * @param size of hashtable.
	 * @return boolean value true.
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int size) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT

		if(size < 1) return false;

		Node<K,V>[] oldTable = storage;
		storage = (Node<K,V>[]) new Node[size];
		numElements = 0;

		for(Node<K,V> node : oldTable) {
			while(node != null) {
				putNoExpand(node.entry.key, node.entry.value);
				node = node.next;
			}
		}

		return true;
	}

	/**
	 * Place key value pair after assigning to put without expanding table.
	 * When the number of elements exceeds to storage size increase by two.
	 * @param key key value.
	 * @param value value.
	 * @return KV pair.
	 */
	public V put(K key, V value) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT

		V ret = putNoExpand(key, value);
		while((numElements/(double)storage.length) >= 2) {
			rehash(storage.length*2);
		}
		return ret;
	}
}