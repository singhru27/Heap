package PersonalProjects.Heap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import net.datastructures.CompleteBinaryTree;
import net.datastructures.EmptyPriorityQueueException;
import net.datastructures.InvalidKeyException;
import net.datastructures.Position;
import app.impl.*;

/**
 * This class can be used to test the functionality of your MyHeap
 * implementation. You will find a few examples to guide you through the syntax
 * of writing test cases. Each test case uses its own heap instance to ensure
 * that the test cases are independent of each other. All of the given examples
 * should pass once you've implemented your heap.
 * 
 *
 * The annotation @Test before each test case is JUnit syntax. It basically lets
 * the compiler know that this is a unit test method. Use this annotation for
 * *every* test method. This class is also like any other java class, so should
 * you need to add private helper methods to use in your tests, you can do so,
 * simply without the @Test annotation. The general framework of a test case is:
 * - Name the test method descriptively, mentioning what is being tested (it is
 * ok to have slightly verbose method names here) - Set-up the program state
 * (ex: instantiate a heap and insert K,V pairs into it) - Use assertions to
 * validate that the progam is in the state you expect it to be
 * 
 * We've given you four example of test cases below that should help you
 * understand syntax and the general structure of tests.
 */

public class MyHeapTest {

	/**
	 * A simple test to ensure that insert() works.
	 */
	@Test
	public void testInsertOneElement() {
		// set-up
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(1, "A");

		// Assert that your data structure is consistent using
		// assertThat(actual, is(expected))
		assertThat(heap.size(), is(1));
		assertThat(heap.min().getKey(), is(1));
	}

	/**
	 * This is an example to check that the order of the heap is sorted as per
	 * the keys by comparing a list of the actual and expected keys.
	 */
	@Test
	public void testRemoveMinHeapOrderUsingList() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(11, "A");
		heap.insert(13, "B");
		heap.insert(64, "C");
		heap.insert(16, "D");
		heap.insert(44, "E");

		// the expected ordering that keys come in
		List<Integer> expectedKeys = Arrays.asList(11, 13, 16, 44, 64);

		// the actual ordering of keys in the heap
		List<Integer> actualKeys = new ArrayList<Integer>();
		while (!heap.isEmpty()) {
			actualKeys.add(heap.removeMin().getKey());
		}

		// check that the actual ordering matches the expected ordering by using
		// one assert
		// Note that assertThat(actual, is(expected)), when used on lists/
		// arrays, also checks that the
		// ordering is the same.
		assertThat(actualKeys, is(expectedKeys));
	}

	/**
	 * This is an example of testing heap ordering by ensuring that the min key
	 * is always at the root by checking it explicitly each time, using multiple
	 * asserts rather than a list.
	 */
	@Test
	public void testRemoveMinHeapOrder() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(11, "A");
		heap.insert(13, "B");
		heap.insert(64, "C");
		heap.insert(16, "D");
		heap.insert(44, "E");

		// test the heap ordering by asserting on all elements
		assertThat(heap.removeMin().getKey(), is(11));
		assertThat(heap.removeMin().getKey(), is(13));
		assertThat(heap.removeMin().getKey(), is(16));
		assertThat(heap.removeMin().getKey(), is(44));
		assertThat(heap.removeMin().getKey(), is(64));
	}

	/**
	 * This is an example of how to test whether an exception you expect to be
	 * thrown on a certain line of code is actually thrown. As shown, you'd
	 * simply add the expected exception right after the @Test annotation. This
	 * test will pass if the exception expected is thrown by the test and fail
	 * otherwise.
	 * 
	 * Here, we're checking to see if an IllegalStateException is being
	 * correctly thrown after we try to call setComparator on a non-empty heap.
	 */
	@Test(expected = IllegalStateException.class)
	public void testSetComparatorThrowsIllegalStateException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(1, "A");
		heap.setComparator(new IntegerComparator());
	}

	/**
	 * TODO: add your tests below! Think of edge cases and testing for
	 * exceptions (if applicable) for insert, remove, min, removeMin, size and
	 * your helper methods (if applicable).
	 */

	/*
	 * This comprehensively tests that the size method is returning the correct
	 * size of the underlying heap
	 */

	@Test
	public void testSize() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		assertTrue(heap.size() == 0);
		MyHeapEntry<Integer, String> entry = (MyHeapEntry<Integer, String>) heap.insert(1, "test");
		assertTrue(heap.size() == 1);
		heap.insert(2, "test");
		assertTrue(heap.size() == 2);
		heap.insert(3, "test");
		assertTrue(heap.size() == 3);
		heap.removeMin();
		assertTrue(heap.size() == 2);
		heap.remove(entry);
		assertTrue(heap.size() == 1);
	}

	/*
	 * This tests that the heap is correctly returning whether it is empty or
	 * not
	 */
	@Test
	public void testIsEmpty() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		assertTrue(heap.isEmpty());
		heap.insert(1, "test");
		assertTrue(heap.isEmpty() == false);
		heap.removeMin();
		assertTrue(heap.isEmpty());
		MyHeapEntry<Integer, String> entry = (MyHeapEntry<Integer, String>) heap.insert(1, "test");
		heap.remove(entry);
		assertTrue(heap.isEmpty());
	}

	/*
	 * This tests that the Heap correctly raises an exception when the min
	 * method is called on an empty Heap
	 */

	@Test(expected = EmptyPriorityQueueException.class)
	public void testMinThrowsEmptyPriorityQueueException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.min();

	}
	/*
	 * This tests that the Heap is correctly returning the minimum element,
	 * regardless of how many additional entries are added or removed in that
	 * heap
	 */

	@Test
	public void testMin() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(3, "test");
		assertTrue(heap.min().getKey() == 3);
		heap.insert(4, "test");
		assertTrue(heap.min().getKey() == 3);
		heap.insert(1, "test");
		assertTrue(heap.min().getKey() == 1);
		heap.removeMin();
		assertTrue(heap.min().getKey() == 3);
	}

	/*
	 * This tests that an exception is properly thrown when adding entries with
	 * null keys
	 */

	@Test(expected = InvalidKeyException.class)
	public void testInsertThrowsInvalidKeyException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(null, "asdf");
	}

	/*
	 * This tests that the data structure remains consistent as multiple items
	 * are added into the heap.
	 */

	@Test
	public void testMultipleInsert() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		MyHeapEntry<Integer, String> entry = (MyHeapEntry<Integer, String>) heap.insert(1, "test");
		Position<MyHeapEntry<Integer, String>> positionNode = entry.getNode();
		assertTrue(heap.getTree().root() == positionNode);
		heap.insert(2, "test");
		assertTrue(heap.getTree().root() == positionNode);
	}

	/*
	 * This tests that removeMin throws an exception when it is called on an
	 * empty tree
	 */

	@Test(expected = EmptyPriorityQueueException.class)
	public void testRemoveMinThrowsEmptyPriorityQueueException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.removeMin();
	}

	/*
	 * This tests that removeMin works when there is only one element in the
	 * Heap
	 */

	@Test
	public void testOneElementRemoveMin() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(1, "test");
		assertTrue(heap.removeMin().getKey() == 1);
	}

	/*
	 * This tests that remove min works with multiple elements in the list
	 */

	@Test
	public void testMultipleElementRemoveMin() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		heap.insert(1, "asdf");
		heap.insert(2, "asdf");
		heap.insert(3, "asdf");
		heap.insert(4, "asdf");
		heap.insert(5, "asdf");
		assertTrue(heap.removeMin().getKey() == 1);
		assertTrue(heap.removeMin().getKey() == 2);
		assertTrue(heap.removeMin().getKey() == 3);
		assertTrue(heap.removeMin().getKey() == 4);
		assertTrue(heap.removeMin().getKey() == 5);

	}

	/*
	 * This tests that the remove method works when there is one element, and
	 * when there are multiple elements in the tree
	 */

	@Test
	public void testRemove() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		MyHeapEntry<Integer, String> rootEntry = (MyHeapEntry<Integer, String>) heap.insert(1, "asdf");
		MyHeapEntry<Integer, String> leftChildEntry = (MyHeapEntry<Integer, String>) heap.insert(2, "asdf");
		MyHeapEntry<Integer, String> rightChildEntry = (MyHeapEntry<Integer, String>) heap.insert(3, "asdf");
		assertTrue(heap.remove(rootEntry) == rootEntry);
		assertTrue(heap.remove(leftChildEntry) == leftChildEntry);
		assertTrue(heap.remove(rightChildEntry) == rightChildEntry);

	}

	/*
	 * This tests that the replaceKey method correctly throws an exception when
	 * a null key is used
	 */
	@Test(expected = InvalidKeyException.class)
	public void testReplaceKeyThrowsException() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		MyHeapEntry<Integer, String> rootEntry = (MyHeapEntry<Integer, String>) heap.insert(1, "asdf");
		heap.replaceKey(rootEntry, null);
	}

	/*
	 * This tests that the replaceKey is correctly replacing the key for
	 * multiple elements
	 */
	@Test
	public void testReplaceKeyMultipleElements() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		MyHeapEntry<Integer, String> rootEntry = (MyHeapEntry<Integer, String>) heap.insert(1, "asdf");
		MyHeapEntry<Integer, String> leftChildEntry = (MyHeapEntry<Integer, String>) heap.insert(2, "asdf");
		heap.replaceKey(rootEntry, 2);
		heap.replaceKey(leftChildEntry, 1);
		assertTrue(rootEntry.getKey() == 2);
		assertTrue(leftChildEntry.getKey() == 1);
	}

	/*
	 * This tests that the replaceKey function is correctly upheaping and
	 * downheaping elements
	 */
	
	@Test
	public void testReplaceKeyDownheapUpheap() {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		MyHeapEntry<Integer, String> rootEntry = (MyHeapEntry<Integer, String>) heap.insert(1, "asdf");
		MyHeapEntry<Integer, String> leftChildEntry = (MyHeapEntry<Integer, String>) heap.insert(2, "asdf");
		MyHeapEntry<Integer, String> rightChildEntry = (MyHeapEntry<Integer, String>) heap.insert(3, "asdf");
		heap.replaceKey(rootEntry, 3);
		heap.replaceKey(leftChildEntry, 2);
		heap.replaceKey(rightChildEntry, 1);
		assertTrue(heap.removeMin() == rightChildEntry);
		assertTrue(heap.removeMin() == leftChildEntry);
		assertTrue(heap.removeMin() == rootEntry);
	}

	/*
	 * This tests that the replaceValue method is correctly returning old Values
	 * and replacing the values of the given position nodes
	 */
	@Test 
	public void testReplaceValue () {
		MyHeap<Integer, String> heap = new MyHeap<Integer, String>(new IntegerComparator());
		MyHeapEntry<Integer, String> rootEntry = (MyHeapEntry<Integer, String>) heap.insert(1, "root");
		MyHeapEntry<Integer, String> leftChildEntry = (MyHeapEntry<Integer, String>) heap.insert(2, "leftchild");
		MyHeapEntry<Integer, String> rightChildEntry = (MyHeapEntry<Integer, String>) heap.insert(3, "rightchild");
		assertTrue (heap.replaceValue(rootEntry, "root1") == "root");
		assertTrue (rootEntry.getValue() == "root1");
		assertTrue (heap.replaceValue(leftChildEntry, "leftchild1") == "leftchild");
		assertTrue (leftChildEntry.getValue() == "leftchild1");
		assertTrue (heap.replaceValue(rightChildEntry, "rightchild1") == "rightchild");
		assertTrue (rightChildEntry.getValue() == "rightchild1");
		
		
	}
}
