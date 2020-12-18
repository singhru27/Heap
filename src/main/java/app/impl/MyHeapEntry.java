package app.impl;

import net.datastructures.Entry;
import net.datastructures.Position;

/**
 * Represents a key/value pair to be stored in a data 
 * structure, such as a heap. Entry<K,V> is a very 
 * limited accessing interface, so you may wish to add 
 * additional methods. In particular, think about the 
 * relationship of the Entry<K,V> to its location in 
 * the heap's binary tree. All methods must run in O(1)
 * time.
 *
 * Feel free to add additional comments. 
 */

public class MyHeapEntry<K,V> implements Entry<K,V> {
	
	private K _key;
	private V _value;
	private Position <MyHeapEntry<K, V>> _node;
	

	/** 
	 * Default constructor. You may wish to modify the parameters.
	 */
	public MyHeapEntry() {
		_key = null;
		_value = null;
		_node = null;
		

	}
	
	/**
	 * @return the key stored in this entry 
	 */
	public K getKey() {
		return _key;
	}

	/** 
	 * @return the value stored in this entry 
	 */
	public V getValue() {
		return _value;
	}
	
	/*
	 * This method sets the key 
	 */
	
	public void setKey (K key) {
		_key = key;
	}

	// This method sets the value 
	
	public void setValue (V value) {
		
		_value = value;
	}
	
	// This method sets the node that contains the MyHeapEntry
	
	public void setNode (Position <MyHeapEntry<K, V>> node) {
		
		_node = node;
		
	}
	
	// This method returns the position which contains this entry
	
	public Position <MyHeapEntry<K, V>> getNode () {
		return _node;
	}
	

}
