package app.impl;

import java.util.Comparator;
import net.datastructures.CompleteBinaryTree;
import net.datastructures.DefaultComparator;
import net.datastructures.EmptyPriorityQueueException;
import net.datastructures.Entry;
import net.datastructures.InvalidEntryException;
import net.datastructures.InvalidKeyException;
import net.datastructures.Position;
import net.datastructures.AdaptablePriorityQueue;
import support.heap.HeapWrapper;

/**
 * An implementation of an adaptable priority queue by means of a heap. Be
 * certain that your running times match those specified in the program
 * documentation, and remember that the running time of a "called" method sets
 * the minimum running time of the "calling" method. Feel free to add additional
 * comments.
 */

public class MyHeap<K, V> implements HeapWrapper<K, V>, AdaptablePriorityQueue<K, V> {

	// This the underlying data structure of your heap
	private MyLinkedHeapTree<MyHeapEntry<K, V>> _tree;
	private Comparator<K> _comparator;

	/**
	 * Creates an empty heap with the given comparator.
	 * 
	 * @param the
	 *            comparator to be used for heap keys
	 */
	public MyHeap(Comparator<K> comparator) {
		_tree = new MyLinkedHeapTree<>();
		this.setComparator(comparator);

	}

	/**
	 * Sets the comparator used for comparing items in the heap to the
	 * comparator passed in.
	 * 
	 * @param comparator,
	 *            the comparator to be used for heap keys
	 * @throws IllegalStateException
	 *             if priority queue is not empty
	 * @throws IllegalArgumentException
	 *             if null comparator is passed in
	 */
	public void setComparator(Comparator<K> comparator) throws IllegalStateException, IllegalArgumentException {

		// throwing an exception if the comparator is null
		if (comparator == null) {
			throw new IllegalArgumentException();
		}

		// throwing the exception if the queue isn't empty

		if (this.isEmpty() == false) {
			throw new IllegalStateException();
		}

		_comparator = comparator;
	}

	/**
	 * Returns a CompleteBinaryTree that will allow the visualizer access to
	 * private members, shattering encapsulation, but allowing visualization of
	 * the heap. This is the only method needed to satisfy HeapWrapper interface
	 * implementation.
	 *
	 * Do not modify or call this method. It is solely necessary for the
	 * visualizer to work properly.
	 * 
	 * @return the underlying binary tree on which the heap is based
	 */
	public CompleteBinaryTree<MyHeapEntry<K, V>> getTree() {
		return _tree;
	}

	/**
	 * Returns the size of the heap. This method must run in O(1) time.
	 *
	 * @return an int representing the number of entries stored
	 */
	public int size() {
		return _tree.size();
	}

	/**
	 * Returns whether the heap is empty. This method must run in O(1) time.
	 * 
	 * @return true if the heap is empty; false otherwise
	 */
	public boolean isEmpty() {
		return _tree.isEmpty();
	}

	/**
	 * Returns but does not remove the entry with minimum key. This method must
	 * run in O(1) time.
	 * 
	 * @return the entry with the minimum key in the heap
	 * @throws EmptyPriorityQueueException
	 *             if the heap is empty
	 */
	public Entry<K, V> min() throws EmptyPriorityQueueException {
		if (_tree.isEmpty()) {
			throw new EmptyPriorityQueueException("Heap is Empty");
		}

		return _tree.root().element();
	}

	/**
	 * Inserts a key-value pair and returns the entry created. This method must
	 * run in O(log n) time.
	 *
	 * @param key
	 *            to be used as the key the heap is sorting with
	 * @param value
	 *            stored with the associated key in the heap
	 * @return the entry created using the key/value parameters
	 * @throws InvalidKeyException
	 *             if the key is not suitable for this heap
	 */
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {

		// This throws an exception if the key entered is a null value
		if (key == null) {
			throw new InvalidKeyException("Must Enter Suitable Key");
		}

		// This catches any exceptions thrown by the comparator if the key is an
		// invalid type

		try {
			_comparator.compare(key, key);
		} catch (ClassCastException e) {
			throw new InvalidKeyException("Must Enter Suitable Key");
		}

		/*
		 * This creates an entry with the designated key, value pair. It then
		 * adds this entry to the LinkedHeapTree, and calls upheap to ensure
		 * correct placement of the entry
		 */

		MyHeapEntry<K, V> entry = new MyHeapEntry<>();
		entry.setKey(key);
		entry.setValue(value);
		Position<MyHeapEntry<K, V>> entry_node = _tree.add(entry);
		entry.setNode(entry_node);
		this.upHeap(entry);
		return entry;
	}

	/**
	 * Removes and returns the entry with the minimum key. This method must run
	 * in O(log n) time.
	 * 
	 * @return the entry with the with the minimum key, now removed
	 * @throws EmptyPriorityQueueException
	 *             if the heap is empty
	 */
	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		// Throwing exception if the method is called on an empty Heap
		if (this.isEmpty()) {
			throw new EmptyPriorityQueueException("Cannot get minimum of an empty Heap");
		}
		
		// This handles the edge case in which the tree only has 1 element
		if (_tree.size() == 1) {
			return _tree.remove();
		}

		// Setting the local variables to keep track of variables and nodes
		Position<MyHeapEntry<K, V>> rootNode = _tree.root();
		MyHeapEntry<K, V> minimumEntry = rootNode.element();
		MyHeapEntry<K, V> lastEntry = _tree.returnLast().element();
		Position<MyHeapEntry<K, V>> lastNode = lastEntry.getNode();

		// Swapping out the entries of the root node and the last node in the
		// heap
		_tree.replace(rootNode, lastEntry);
		_tree.replace(lastNode, minimumEntry);

		// Setting the container nodes of the entries
		lastEntry.setNode(rootNode);
		minimumEntry.setNode(lastNode);

		// Removing the last node of the heap, and downheaping the lastEntry
		_tree.remove();
		this.downHeap(lastEntry);

		return minimumEntry;
	}

	/**
	 * Removes and returns the given entry from the heap. This method must run
	 * in O(log n) time.
	 *
	 * @param entry
	 *            to be removed from the heap
	 * @return the entry specified for removal by the parameter, now removed
	 * @throws InvalidEntryException
	 *             if the entry cannot be removed from this heap
	 */
	public Entry<K, V> remove(Entry<K, V> entry) throws InvalidEntryException {

		
		// This handles the edge case in which the entry is the only element in the tree
		if (_tree.size() == 1) {
			return _tree.remove();
		}
		
		MyHeapEntry<K, V> checkedEntry = this.checkAndConvertEntry(entry);
	
		Position<MyHeapEntry<K, V>> entryNode = checkedEntry.getNode();
		Position<MyHeapEntry<K, V>> lastNode = _tree.returnLast();
		MyHeapEntry<K, V> lastEntry = lastNode.element();
		
		// Swapping out the removed entry with the entry contained in the last
		// node
		_tree.replace(lastNode, checkedEntry);
		_tree.replace(entryNode, lastEntry);
		checkedEntry.setNode(lastNode);
		lastEntry.setNode(entryNode);
		
		// Removing the last element in the heap
		_tree.remove();

		// Checking positional validity and down/upheaping accordingly
		if (this.checkUpValidity(lastEntry) == false) {
			this.upHeap(lastEntry);
		}

		if (this.checkDownValidity(lastEntry) == false) {
			this.downHeap(lastEntry);
		}
		return checkedEntry;
		

	}

	/**
	 * Replaces the key of the given entry. This method must run in O(log n)
	 * time.
	 *
	 * @param entry
	 *            within which the key will be replaced
	 * @param key
	 *            to replace the existing key in the entry
	 * @return the old key formerly associated with the entry
	 * @throws InvalidEntryException
	 *             if the entry is invalid
	 * @throws InvalidKeyException
	 *             if the key is invalid
	 */
	public K replaceKey(Entry<K, V> entry, K key) throws InvalidEntryException, InvalidKeyException {
		MyHeapEntry<K, V> checkedEntry = this.checkAndConvertEntry(entry);

		// This throws an exception if the key entered is a null value
		if (key == null) {
			throw new InvalidKeyException("Must Enter Suitable Key");
		}

		// This catches any exceptions thrown by the comparator if the key is an
		// invalid type

		try {
			_comparator.compare(key, key);
		} catch (ClassCastException e) {
			throw new InvalidKeyException("Must Enter Suitable Key");
		}

		K oldKey = checkedEntry.getKey();
		checkedEntry.setKey(key);

		if (this.checkUpValidity(checkedEntry) == false) {
			this.upHeap(checkedEntry);
		}

		if (this.checkDownValidity(checkedEntry) == false) {
			this.downHeap(checkedEntry);
		}

		return oldKey;
	}

	/**
	 * Replaces the value of the given entry. This method must run in O(1) time.
	 *
	 * @param entry
	 *            within which the value will be replaced
	 * @param value
	 *            to replace the existing value in the entry
	 * @return the old value formerly associated with the entry
	 * @throws InvalidEntryException
	 *             if the entry cannot have its value replaced
	 */
	public V replaceValue(Entry<K, V> entry, V value) throws InvalidEntryException {
		MyHeapEntry<K, V> checkedEntry = this.checkAndConvertEntry(entry);

		V oldValue = checkedEntry.getValue();
		checkedEntry.setValue(value);
		return oldValue;
	}

	/**
	 * Determines whether a given entry is valid and converts it to a
	 * MyHeapEntry. Don't change this method.
	 *
	 * @param entry
	 *            to be checked for validity with respect to the heap
	 * @return the entry cast as a MyHeapEntry if considered valid
	 *
	 * @throws InvalidEntryException
	 *             if the entry is not of the proper class
	 */
	public MyHeapEntry<K, V> checkAndConvertEntry(Entry<K, V> entry) throws InvalidEntryException {
		if (entry == null || !(entry instanceof MyHeapEntry)) {
			throw new InvalidEntryException("Invalid entry");
		}
		return (MyHeapEntry<K, V>) entry;
	}

	/*
	 * This method is used to determine if an entry is properly placed below its
	 * parent. If the parent node contains an entry which has a larger key, the
	 * method returns false.
	 * 
	 * @ param: The entry being checked for upward movement validity
	 * @ return: A boolean checking if the entry is correctly positioned relative to entries above it
	 */

	private boolean checkUpValidity(MyHeapEntry<K, V> entryObject) {

		MyHeapEntry<K, V> entry = entryObject;
		Position<MyHeapEntry<K, V>> entryNode = entry.getNode();

		// The method returns true if the node containing the entry is a root
		// node
		if (_tree.isRoot(entryNode)) {
			return true;
		}

		// This creates local variables that track the keys of the parent node
		// and the current node
		Position<MyHeapEntry<K, V>> parentNode = _tree.parent(entryNode);
		K parentKey = parentNode.element().getKey();
		K entryKey = entryNode.element().getKey();

		if (_comparator.compare(parentKey, entryKey) <= 0) {
			return true;
		}

		return false;
	}

	/*
	 * This helper method is used to determine if an entry is properly placed
	 * above its children. If the children nodes contain entries which have
	 * smaller keys, the method returns false
	 * 
	 * @ param The entry that is being checked for downward placement validity
	 * @ return A boolean checking if the entry is correctly positioned relative to entries below it
	 */

	private boolean checkDownValidity(MyHeapEntry<K, V> entryObject) {
		MyHeapEntry<K, V> entry = entryObject;
		Position<MyHeapEntry<K, V>> entryNode = entry.getNode();
		K entryKey = entry.getKey();

		// Returns true if the given entry has no children
		if (_tree.hasLeft(entryNode) == false && _tree.hasRight(entryNode) == false) {
			return true;
		}

		// Returns true if the node has two children, and both have bigger keys
		if (_tree.hasLeft(entryNode) && _tree.hasRight(entryNode)) {
			Position<MyHeapEntry<K, V>> leftChild = _tree.left(entryNode);
			MyHeapEntry<K, V> leftChildElement = leftChild.element();
			K leftChildKey = leftChildElement.getKey();
			Position<MyHeapEntry<K, V>> rightChild = _tree.right(entryNode);
			MyHeapEntry<K, V> rightChildElement = rightChild.element();
			K rightChildKey = rightChildElement.getKey();

			if (_comparator.compare(entryKey, leftChildKey) <= 0 && _comparator.compare(entryKey, rightChildKey) <= 0) {

				return true;
	
			} else {
				return false;
			}
		} else {

		// Returns true if the node only has a left child, and this left child has a bigger key
			Position<MyHeapEntry<K, V>> leftChild = _tree.left(entryNode);
			MyHeapEntry<K, V> leftChildElement = leftChild.element();
			K leftChildKey = leftChildElement.getKey();

			if (_comparator.compare(entryKey, leftChildKey) <= 0) {
				return true;
		
			} else {
				return false;
			}
		}

		
	}

	/*
	 * This method is used to upheap Entries up the heap, if they have keys that
	 * are smaller than the key of its parent
	 * 
	 * @param: The entry that is to be upheaped
	 */

	private void upHeap(MyHeapEntry<K, V> entryObject) {
		MyHeapEntry<K, V> entry = entryObject;

		while (this.checkUpValidity(entry) == false) {
			Position<MyHeapEntry<K, V>> entryNode = entry.getNode();
			Position<MyHeapEntry<K, V>> parentNode = _tree.parent(entry.getNode());
			MyHeapEntry<K, V> parentEntry = parentNode.element();
			_tree.replace(entryNode, parentNode.element());
			_tree.replace(parentNode, entry);
			entry.setNode(parentNode);
			parentEntry.setNode(entryNode);

		}
	}

	/*
	 * This method is used to downheap entries down the heap, if they have keys
	 * that are larger than the key of its children.
	 * 
	 * @ param: The entry that is to be downheaped
	 */

	private void downHeap(MyHeapEntry<K, V> entryObject) {

		MyHeapEntry<K, V> entry = entryObject;

		// The downheap procedure is carried out as long as the DownValidity
		// method returns false

		while (this.checkDownValidity(entry) == false) {
			/*
			 * This handles the case in which there are two children for the
			 * entry. Local variables are not assigned prior to the entry of the
			 * if statements to avoid nullpointer exceptions, in the case where
			 * the entry does not have a right or left child
			 */
			Position<MyHeapEntry<K, V>> entryNode = entry.getNode();

			if (_tree.hasLeft(entryNode) && _tree.hasRight(entryNode)) {
				Position<MyHeapEntry<K, V>> leftChild = _tree.left(entryNode);
				Position<MyHeapEntry<K, V>> rightChild = _tree.right(entryNode);
				MyHeapEntry<K, V> leftChildEntry = leftChild.element();
				MyHeapEntry<K, V> rightChildEntry = rightChild.element();
				K leftChildKey = leftChildEntry.getKey();
				K rightChildKey = rightChildEntry.getKey();

				// Swapping out elements for the left child if the left child is
				// smaller than the right child
				if (_comparator.compare(leftChildKey, rightChildKey) <= 0) {
					_tree.replace(entryNode, leftChildEntry);
					_tree.replace(leftChild, entry);
					entry.setNode(leftChild);
					leftChildEntry.setNode(entryNode);
					// Swapping out elements for the right child if the right
					// child is smaller than the left child

				} else {
					_tree.replace(entryNode, rightChildEntry);
					_tree.replace(rightChild, entry);
					entry.setNode(rightChild);
					rightChildEntry.setNode(entryNode);
				}
				continue;
			}

			/*
			 * This handles the case in which there is only a left child.
			 */

			if (_tree.hasLeft(entryNode)) {
				Position<MyHeapEntry<K, V>> leftChild = _tree.left(entryNode);
				MyHeapEntry<K, V> leftChildEntry = leftChild.element();
				_tree.replace(entryNode, leftChildEntry);
				_tree.replace(leftChild, entry);
				entry.setNode(leftChild);
				leftChildEntry.setNode(entryNode);

				/*
				 * This handles the case in which there is only a right child
				 */
			} else {
				Position<MyHeapEntry<K, V>> rightChild = _tree.right(entryNode);
				MyHeapEntry<K, V> rightChildEntry = rightChild.element();
				_tree.replace(entryNode, rightChildEntry);
				_tree.replace(rightChild, entry);
				entry.setNode(rightChild);
				rightChildEntry.setNode(entryNode);

			}

		}

	}

}
