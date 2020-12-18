package app.impl;

import net.datastructures.CompleteBinaryTree;
import net.datastructures.EmptyTreeException;
import net.datastructures.LinkedBinaryTree;
import net.datastructures.NodeDeque;
import net.datastructures.Position;

/**
 * An implementation of a complete binary tree by means of a linked structure
 * (LinkedBinaryTree). The LinkedBinaryTree class takes care of most of the
 * mechanics of modifying the tree (you should read through the NDS4
 * documentation in order to fully understand how this class works. There's a
 * link on the website), but you will need to think about how to implement a
 * CompleteBinaryTree such that additions and removals operate *only* on the
 * last node (hint: think about other useful data structures). You must also
 * ensure that you do not violate the assignment runtime requirements when
 * deciding how you will track nodes within the tree.
 * 
 */

public class MyLinkedHeapTree<E> extends LinkedBinaryTree<E> implements CompleteBinaryTree<E> {

	private NodeDeque<Position<E>> _deque;

	/**
	 * Default constructor. The tree begins empty.
	 */
	public MyLinkedHeapTree() {

		_deque = new NodeDeque<>();
	}

	/**
	 * Adds an element to the tree just after the last node. Returns the newly
	 * created position for the element.
	 *
	 * Note: You don't need to instantiate a new Position<E> as a local
	 * variable. Look at the NDS4 documentation for LinkedBinaryTree for how to
	 * add a new Position<E> to the tree.
	 * 
	 * This method must run in constant O(1) worst-case time.
	 * 
	 * @param element
	 *            to be added to the tree as the new last node
	 * @return the Position of the newly inserted element
	 */

	@Override
	public Position<E> add(E element) {

		// This adds a root, and adds the new node to the back of the deque if
		// the deque is empty
		if (_deque.isEmpty()) {
			Position<E> newNode = this.addRoot(element);
			_deque.addLast(newNode);
			return newNode;

			// This handles the case in which the deque is not empty
		} else {
			Position<E> parent = _deque.getFirst();

			// This handles the case in which the parent has no children
			if (this.hasLeft(parent) == false) {
				Position<E> newNode = this.insertLeft(parent, element);
				_deque.addLast(newNode);
				return newNode;

			} else {

				// This handles the case in which the parent has 1 child
				// already. The new element is added, and the parent is removed
				// from the deque
				Position<E> newNode = this.insertRight(parent, element);
				_deque.removeFirst();
				_deque.addLast(newNode);
				return newNode;
			}

		}

	}

	/**
	 * Removes and returns the element stored in the last node of the tree.
	 * 
	 * This method must run in constant O(1) worst-case time.
	 * 
	 * @return the element formerly stored in the last node (prior to its
	 *         removal)
	 * @throws EmptyTreeException
	 *             if the tree is empty and no last node exists
	 */
	@Override
	public E remove() throws EmptyTreeException {

		if (_deque.isEmpty()) {
			throw new EmptyTreeException("Can't remove elements from empty tree");
		}

		Position<E> removedNode = _deque.removeLast();

		// If the removedNode is not a root, this if statement checks if the
		// node was a right child
		if (this.isRoot(removedNode) == false) {
			Position<E> parent = this.parent(removedNode);

			// This adds the parent back into the deque, if the node being
			// removed is a right child
			if (this.hasRight(parent)) {
				_deque.addFirst(parent);
			}

		}

		// This removes the last node from the tree and returns the element
		// stored
		this.remove(removedNode);
		return removedNode.element();

	}

	/*
	 * This method returns the position node from the end of the deque, but does
	 * not remove it
	 * 
	 * @return the element stored at the last position in the deque
	 * 
	 * @throws EmptyTreeException if the tree is empty and no last node exists
	 */

	public Position<E> returnLast() {
		if (_deque.isEmpty()) {
			throw new EmptyTreeException("Can't remove elements from empty tree");
		}
		return _deque.getLast();
	}

}
