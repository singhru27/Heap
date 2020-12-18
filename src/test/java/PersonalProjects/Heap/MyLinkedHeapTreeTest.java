package PersonalProjects.Heap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import net.datastructures.BTNode;
import net.datastructures.EmptyTreeException;
import net.datastructures.Position;
import app.impl.*;


public class MyLinkedHeapTreeTest {

	/**
	 * A simple example of checking that the add() method adds the first element
	 * to the tree.
	 */
	@Test
	public void testAddOneElement() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(1);

		/*
		 * These are two ways of asserting the same thing Use whichever you find
		 * more convenient out of assertThat(actual, is(expected)) and
		 * assertTrue(boolean) Take a look at the JUnit docs for more assertions
		 * you might want to use.
		 */
		assertThat(tree.size(), is(1));
		assertTrue(tree.size() == 1);
	}

	/**
	 * This is an example of how to test whether an exception you expect to be
	 * thrown on a certain line of code is actually thrown. As shown, you'd
	 * simply add the expected exception right after the @Test annotation. This
	 * test will pass if the exception expected is thrown by the test and fail
	 * otherwise.
	 */
	@Test(expected = EmptyTreeException.class)
	public void testRemoveThrowsEmptyTreeException() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.remove();
	}

	/*
	 * This tests that the first element being added to the tree is being
	 * recognized as a root
	 */

	@Test
	public void testRootAdd() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(1);
		assertTrue(tree.root().element() == 1);
	}

	/*
	 * This tests that the elements are being removed and added from the tree in
	 * the correct order
	 */
	@Test
	public void testRemoveElement() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(1);
		tree.add(2);
		tree.add(3);
		tree.add(4);
		tree.add(5);
		tree.add(6);
		assertTrue(tree.remove() == 6);
		assertTrue(tree.remove() == 5);
		assertTrue(tree.remove() == 4);
		assertTrue(tree.remove() == 3);
		assertTrue(tree.remove() == 2);
		assertTrue(tree.remove() == 1);
		assertTrue(tree.size() == 0);
		assertTrue(tree.isEmpty());

	}

	/*
	 * This tests that the tree is linking properly to ensure left completeness
	 */

	@Test
	public void testLinkage() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		Position<Integer> rootNode = tree.add(1);
		Position<Integer> leftChild = tree.add(2);
		Position<Integer> rightChild = tree.add(3);
		Position<Integer> leftChild_leftChild = tree.add(4);
		Position<Integer> leftChild_rightChild = tree.add(5);
		Position<Integer> rightChild_leftChild = tree.add(6);

		assertTrue(tree.left(rootNode) == leftChild);
		assertTrue(tree.right(rootNode) == rightChild);
		assertTrue(tree.parent(leftChild) == rootNode);
		assertTrue(tree.parent(rightChild) == rootNode);
		assertTrue(tree.left(leftChild) == leftChild_leftChild);
		assertTrue(tree.right(leftChild) == leftChild_rightChild);
		assertTrue(tree.parent(leftChild_leftChild) == leftChild);
		assertTrue(tree.parent(leftChild_rightChild) == leftChild);
		assertTrue(tree.left(rightChild) == rightChild_leftChild);
		assertTrue(tree.parent(rightChild_leftChild) == rightChild);

	}

	/*
	 * This tests that the return last function is returning the proper node
	 * from the Bintree
	 */
	@Test
	public void testReturnLast() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(1);
		tree.add(2);
		BTNode<Integer> rightChild = (BTNode<Integer>) tree.add(3);
		assertTrue(tree.returnLast() == rightChild);

	}

	/*
	 * This tests that the return last function is throwing an exception when it
	 * is called on an empty tree
	 */
	@Test(expected = EmptyTreeException.class)
	public void testReturnLastThrowsEmptyTreeException (){
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.returnLast();
	}
	
}
