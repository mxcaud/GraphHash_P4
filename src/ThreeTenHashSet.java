import java.util.Collection;
import java.util.Set;
import java.util.Iterator;

//You need to add javadocs to this class.
//You need to submit this file for grading.
//If you don't submit this for grading we will use
//a vanialla version which doesn't have javadocs.
//This will mean that your code won't pass the style checker.

//Remember that for interface methods with existing documentation
//(such as the java.util.Set interface), the documentation is already
//written, you just need to resuse that (don't copy-and-paste it,
//use inheritdoc).

/**
 * Whatever this.
 * @param <E> as param.
 */
class ThreeTenHashSet<E> implements Set<E> {
	//********************************************************************************
	//   YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
	// These are some methods we didn't write for you, but you could write.
	// if you need/want them for building your graph. We will not test
	// (or grade) these methods.
	//********************************************************************************

	/**
	 * Unused method, removeAll boolean.
	 * @param c type.
	 * @return none.
	 */
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unused method, keep everything boolean.
	 * @param c type.
	 * @return none.
	 */
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unused method, move everything into an Array.
	 * @param a type.
	 * @param <T> type.
	 * @return none.
	 */
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unused method, add everything.
	 * @param c type.
	 * @return none.
	 */
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unused method, contains all same.
	 * @param c type.
	 * @return none.
	 */
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unused method, if equals.
	 * @param o type.
	 * @return none.
	 */
	public boolean equals(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Unused method, gets hashCode.
	 * @return none.
	 */
	public int hashCode() {
		throw new UnsupportedOperationException();
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to add the JavaDocs)
	// We will grade these methods to make sure they still work, so don't break them.
	//********************************************************************************

	/**
	 * Storage for hashmap elemental pairs.
	 */
	private ThreeTenHashMap<E,E> storage = new ThreeTenHashMap<>(5);

	/**
	 * Add a pair to the hashmap.
	 * @param e element.
	 * @return storage element pair.
	 */
	public boolean add(E e) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.put(e, e) == null;
	}

	/**
	 * Clear the hashmaps storage.
	 */
	public void clear() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		storage = new ThreeTenHashMap<>(5);
	}

	/**
	 * Check if element in storage contains passed object.
	 * @param o object we are trying to locate in the storage.
	 * @return boolean value if found of true.
	 */
	public boolean contains(Object o) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.get(o) != null;
	}

	/**
	 * Checks if size is empty.
	 * @return boolean value of true if empty.
	 */
	public boolean isEmpty() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return size() == 0;
	}

	/**
	 * Remove element in storage.
	 * @param o object to try to remove in storage.
	 * @return Removed object assuming it was not null as boolean value true.
	 */
	public boolean remove(Object o) {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.remove(o) != null;
	}

	/**
	 * Size of the storage, counting by element.
	 * @return size of storage as an integer.
	 */
	public int size() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.size();
	}

	/**
	 * Move table entry object to storage as an array. Until size of both maps are equal.
	 * @return array.
	 */
	public Object[] toArray() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		ThreeTenHashMap.TableEntry[] arr = storage.toArray();
		Object[] ret = new Object[arr.length];
		for(int i = 0; i < arr.length; i++) {
			ret[i] = arr[i].key;
		}
		return ret;
	}

	/**
	 * Convert storage object to a string.
	 * @return string value of storage element.
	 */
	public String toString(){
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return storage.toString();
	}

	/**
	 * Iterate through storage objects within an array.
	 * Check next value and hasNext when invoked.
	 * @return value iterator from invocation.
	 */
	public Iterator<E> iterator() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		return new Iterator<>() {
			private Object[] values = toArray();
			private int currentLoc = 0;
			
			@SuppressWarnings("unchecked")
			public E next() {
				return (E) values[currentLoc++];
			}
			
			public boolean hasNext() {
				return currentLoc != values.length;
			}
		};
	}
}