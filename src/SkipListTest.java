import java.util.ArrayList;
import java.util.Iterator;
import org.junit.After;
import org.junit.Test;

import student.TestCase;
import student.TestableRandom;

/**
 * This class tests the methods of SkipList class
 * 
 * @author Harleen Kaur
 * 
 * @version 2024-01-22
 */

public class SkipListTest extends TestCase {
    /**
     * The skip list instance used for testing.
     */
    private SkipList<String, Rectangle> sl;

    /**
     * Sets up the test fixture.
     * This method is called before each test case.
     */
    public void setUp() {
    }


    /***
     * Example 1: Test `randomLevel` method with
     * predetermined random values using `TestableRandom`
     */
    public void testRandomLevelOne() {
        TestableRandom.setNextBooleans(false);
        sl = new SkipList<String, Rectangle>();
        int randomLevelValue = sl.randomLevel();

        // This returns 1 because the first preset
        // random boolean is `false` which breaks
        // the `while condition inside the `randomLevel` method
        int expectedLevelValue = 1;

        // Compare the values
        assertEquals(expectedLevelValue, randomLevelValue);
    }


    /***
     * Example 2: Test `randomLevel` method with
     * predetermined random values using `TestableRandom`
     */
    public void testRandomLevelFour() {
        TestableRandom.setNextBooleans(true, true, false);
        sl = new SkipList<String, Rectangle>();
        int randomLevelValue = sl.randomLevel();

        // This returns 4 because the fourth preset
        // random boolean is `false` which breaks
        // the `while condition inside the `randomLevel` method
        int expectedLevelValue = 3;

        // Compare the values
        assertEquals(expectedLevelValue, randomLevelValue);
    }


    /**
     * test case for inserting single rectangle
     */
    @Test
    public void testInsertSingleRectangle() {
        TestableRandom.setNextBooleans(true, false);
        Rectangle rect = new Rectangle(10, 10, 5, 5);
        sl = new SkipList<String, Rectangle>();
        KVPair<String, Rectangle> pair = new KVPair<String, Rectangle>("ab_",
            rect);
        sl.insert(pair);

        assertEquals(1, sl.size());
    }


    /**
     * test case for inserting multiple rectangle
     */
    @Test
    public void testInsertMultipleRectangles() {
        TestableRandom.setNextBooleans(true, false, true, false, true, false);
        sl = new SkipList<String, Rectangle>();

        Rectangle rect1 = new Rectangle(10, 10, 5, 5);
        Rectangle rect2 = new Rectangle(20, 20, 5, 5);
        Rectangle rect3 = new Rectangle(30, 30, 5, 5);
        KVPair<String, Rectangle> pair1 = new KVPair<>("A", rect1);
        KVPair<String, Rectangle> pair2 = new KVPair<>("B", rect2);
        KVPair<String, Rectangle> pair3 = new KVPair<>("CA", rect3);

        sl.insert(pair1);
        sl.insert(pair2);
        sl.insert(pair3);

        assertEquals(3, sl.size());
    }


    /**
     * test case for searching single element
     */
    /**
     * Tests searching in a comprehensive manner including:
     * - Single element search
     * - Multiple elements with same key
     * - Middle element search
     * - Non-existent key search
     * - Case sensitivity
     * - Search after removal
     * - Null key search
     */
    @Test
    public void testSearchComprehensive() {
        // Setting predictable random behavior for level generation
        TestableRandom.setNextBooleans(true, false, false);
        sl = new SkipList<String, Rectangle>();
        // Test 1: Single element search
        Rectangle r1 = new Rectangle(0, 0, 5, 5);
        sl.insert(new KVPair<>("A", r1));
        ArrayList<KVPair<String, Rectangle>> results = sl.search("A");
        assertEquals(1, results.size());
        assertEquals("A", results.get(0).getKey());
        assertEquals(r1, results.get(0).getValue());

        // Test 2: Multiple elements with same key
        TestableRandom.setNextBooleans(false, false);
        Rectangle r2 = new Rectangle(10, 10, 5, 5);
        Rectangle r3 = new Rectangle(20, 20, 5, 5);
        sl.insert(new KVPair<>("A", r2));
        sl.insert(new KVPair<>("A", r3));
        results = sl.search("A");
        assertEquals(3, results.size());
        assertEquals(r3, results.get(0).getValue());
        assertEquals(r2, results.get(1).getValue());
        assertEquals(r1, results.get(2).getValue());

        // Test 3: Search in middle of list
        Rectangle r4 = new Rectangle(30, 30, 5, 5);
        sl.insert(new KVPair<>("C", r4));
        Rectangle r5 = new Rectangle(40, 40, 5, 5);
        sl.insert(new KVPair<>("B", r5));
        results = sl.search("B");
        assertEquals(1, results.size());
        assertEquals("B", results.get(0).getKey());
        assertEquals(r5, results.get(0).getValue());

        // Test 4: Search non-existent key
        results = sl.search("D");
        assertTrue(results.isEmpty());

        // Test 5: Case sensitivity
        results = sl.search("a");
        assertTrue("Search should be case sensitive", results.isEmpty());

        // Test 6: Search after removal
        sl.remove("A");
        results = sl.search("A");
        assertEquals(2, results.size()); // Should still find 2 remaining As

    }


    /**
     * Tests search functionality with a single element in the list
     * Verifies that:
     * - Element can be found
     * - Correct key-value pair is returned
     * - Size of result is correct
     */
    @Test
    public void testSearchOneElement() {
        TestableRandom.setNextBooleans(false, false);
        sl = new SkipList<String, Rectangle>();
        Rectangle r1 = new Rectangle(0, 0, 5, 5);
        sl.insert(new KVPair<>("XYZ", r1));

        ArrayList<KVPair<String, Rectangle>> results = sl.search("XYZ");
        assertEquals(1, results.size());
        assertEquals("XYZ", results.get(0).getKey());
        assertEquals(r1, results.get(0).getValue());
    }


    /**
     * Tests searching for multiple elements with the same key
     * Verifies that:
     * - All elements with same key are returned
     * - Elements are returned in insertion order
     * - Result size matches number of insertions
     */
    @Test
    public void testSearchMultipleElements() {
        TestableRandom.setNextBooleans(false, false);
        sl = new SkipList<String, Rectangle>();
        Rectangle r11 = new Rectangle(10, 10, 5, 5);
        Rectangle r21 = new Rectangle(10, 10, 5, 5);
        Rectangle r31 = new Rectangle(20, 20, 5, 5);

        sl.insert(new KVPair<>("A", r11));
        sl.insert(new KVPair<>("A", r21));
        sl.insert(new KVPair<>("B", r31));

        ArrayList<KVPair<String, Rectangle>> results = sl.search("A");
        assertEquals(2, results.size());
        assertEquals(r11, results.get(0).getValue());
        assertEquals(r21, results.get(1).getValue());
    }


    /**
     * Tests searching for a key that doesn't exist in the list
     * Verifies that:
     * - Empty ArrayList is returned
     * - Size of result is 0
     */
    @Test
    public void testSearchKeyNotPresent() {
        TestableRandom.setNextBooleans(false, false);
        sl = new SkipList<String, Rectangle>();
        Rectangle r1 = new Rectangle(0, 0, 5, 5);
        sl.insert(new KVPair<>("FGH", r1));

        ArrayList<KVPair<String, Rectangle>> results = sl.search("B");
        assertTrue(results.isEmpty());
        assertEquals(0, results.size());
    }


    /**
     * Tests searching for elements in middle of the list
     * Verifies that:
     * - Middle element can be found
     * - Correct key-value pair is returned
     * - Search traverses levels correctly
     */
    @Test
    public void testSearchMiddleElement() {
        TestableRandom.setNextBooleans(false, false);
        sl = new SkipList<String, Rectangle>();
        Rectangle rm1 = new Rectangle(0, 0, 5, 5);
        Rectangle rm2 = new Rectangle(10, 10, 5, 5);
        Rectangle rm3 = new Rectangle(20, 20, 5, 5);

        sl.insert(new KVPair<>("dws", rm1));
        sl.insert(new KVPair<>("dd", rm2));
        sl.insert(new KVPair<>("ssd", rm3));

        ArrayList<KVPair<String, Rectangle>> results = sl.search("dd");
        assertEquals(1, results.size());
        assertEquals("dd", results.get(0).getKey());
        assertEquals(rm2, results.get(0).getValue());
    }


    /**
     * Tests searching in an empty list
     * Verifies that:
     * - Empty ArrayList is returned
     * - Size of result is 0
     */
    @Test
    public void testSearchEmptyList() {
        sl = new SkipList<String, Rectangle>();
        ArrayList<KVPair<String, Rectangle>> results = sl.search("A");
        assertTrue(results.isEmpty());
        assertEquals(0, results.size());
    }


    /**
     * Tests case sensitivity in search
     * Verifies that:
     * - Search is case sensitive
     * - Different case returns empty result
     */
    @Test
    public void testSearchCaseSensitive() {
        TestableRandom.setNextBooleans(false, false);
        sl = new SkipList<String, Rectangle>();
        Rectangle r1 = new Rectangle(0, 0, 5, 5);
        sl.insert(new KVPair<>("A", r1));

        ArrayList<KVPair<String, Rectangle>> results = sl.search("a");
        assertTrue("Search should be case sensitive", results.isEmpty());
        assertEquals(0, results.size());
    }


    /**
     * Tests searching with multiple levels in the skip list
     * Verifies that:
     * - Search works correctly with different level nodes
     * - Level traversal is working properly
     */
    @Test
    public void testSearchWithMultipleLevels() {
        // Create nodes with different levels
        sl = new SkipList<String, Rectangle>();
        TestableRandom.setNextBooleans(true, true, false); // Level 2
        Rectangle r1 = new Rectangle(0, 0, 5, 5);
        sl.insert(new KVPair<>("A", r1));

        TestableRandom.setNextBooleans(true, false); // Level 1
        Rectangle r2 = new Rectangle(10, 10, 5, 5);
        sl.insert(new KVPair<>("B", r2));

        TestableRandom.setNextBooleans(false); // Level 0
        Rectangle r3 = new Rectangle(20, 20, 5, 5);
        sl.insert(new KVPair<>("C", r3));

        ArrayList<KVPair<String, Rectangle>> results = sl.search("B");
        assertEquals(1, results.size());
        assertEquals(r2, results.get(0).getValue());
    }


    /**
     * test case for removing element from empty list
     */
    @Test
    public void testRemoveFromEmptyList() {
        sl = new SkipList<String, Rectangle>();
        assertNull(sl.remove("A"));
        assertEquals(0, sl.size());
    }


    /**
     * test case for removing single element from list
     */
    @Test
    public void testRemoveSingleElement() {
        TestableRandom.setNextBooleans(true, false, true, false);
        sl = new SkipList<String, Rectangle>();
        Rectangle rect = new Rectangle(0, 0, 5, 5);
        KVPair<String, Rectangle> pair = new KVPair<>("A", rect);

        sl.insert(pair);
        assertEquals("Size should be 1 after insertion", 1, sl.size());

        ArrayList<KVPair<String, Rectangle>> searchResult = sl.search("A");
        assertFalse("Search result should not be empty", searchResult
            .isEmpty());
        assertEquals("Search should find one element", 1, searchResult.size());

        KVPair<String, Rectangle> removed = sl.remove("A");

        assertNotNull("Removed element should not be null", removed);
        assertEquals("Removed key should be 'A'", "A", removed.getKey());
        assertEquals("Rectangle should match", rect, removed.getValue());
        assertEquals("Size should be 0 after removal", 0, sl.size());

        searchResult = sl.search("A");
        assertTrue("Search result should be empty after removal", searchResult
            .isEmpty());
    }


    /**
     * test case for removing element from list by value
     */
    @Test
    public void testRemoveByValue() {
        TestableRandom.setNextBooleans(true, false);
        sl = new SkipList<String, Rectangle>();
        Rectangle rect = new Rectangle(0, 0, 5, 10);
        KVPair<String, Rectangle> pair = new KVPair<>("ABC", rect);
        sl.insert(pair);

        KVPair<String, Rectangle> removed = sl.removeByValue(rect);
        assertNotNull(removed);
        assertEquals(rect, removed.getValue());
        assertEquals(0, sl.size());
    }


    /**
     * test case for removing element from list value which is not present
     */
    @Test
    public void testRemoveByValueNotPresent() {
        TestableRandom.setNextBooleans(true, false);
        sl = new SkipList<String, Rectangle>();
        Rectangle rect = new Rectangle(0, 0, 5, 10);
        Rectangle rect1 = new Rectangle(0, 0, 5, 11);
        KVPair<String, Rectangle> pair = new KVPair<>("ABC", rect);
        sl.insert(pair);

        assertNull(sl.removeByValue(rect1));
        assertEquals(1, sl.size());
    }


    /**
     * test case for removing element from list by value with duplicates
     */
    @Test
    public void testRemoveByValueWithDuplicates() {
        TestableRandom.setNextBooleans(true, false, true, false);
        sl = new SkipList<String, Rectangle>();
        Rectangle rect1 = new Rectangle(0, 0, 10, 51);
        Rectangle rect2 = new Rectangle(0, 0, 10, 51);

        sl.insert(new KVPair<>("AA", rect1));
        sl.insert(new KVPair<>("BA", rect2));

        KVPair<String, Rectangle> removed = sl.removeByValue(rect1);
        assertNotNull(removed);
        assertEquals(1, sl.size());
    }

// @Test
// public void testRemoveMultipleLevel() {
// sl = new SkipList<String, Rectangle>();
// for (int i = 0; i < 10; i++) {
// sl.insert(new KVPair<>(String.valueOf(2 * i), new Rectangle(i * 11,
// i * 11, 5, 5)));
// }
//
// KVPair<String, Rectangle> removed = sl.remove("10");
// assertEquals("10", removed.getKey());
// }


    /**
     * test case for removing null element from list
     */
    @Test
    public void testRemoveNull() {
        sl = new SkipList<String, Rectangle>();
        assertNull(sl.remove(null));
        assertNull(sl.removeByValue(null));
    }


    /**
     * test case for removing double values
     */
    @Test
    public void testDoubleRemoval() {
        // Create your SkipList instance
        SkipList<String, Rectangle> list = new SkipList<>();
        TestableRandom.setNextBooleans(true, false);
        // Insert the rectangle that's causing issues
        String name = "r14";
        Rectangle rect = new Rectangle(120, 117, 93, 706);
        KVPair<String, Rectangle> pair = new KVPair<>(name, rect);
        list.insert(pair);

        // First verify the insertion
        System.out.println("After insertion - List size: " + list.size());

        // First removal using removeByValue
        KVPair<String, Rectangle> removedByValue = list.removeByValue(rect);
        System.out.println("After removeByValue:");
        System.out.println("Removed pair: " + (removedByValue != null
            ? removedByValue.getKey() + ", " + removedByValue.getValue()
            : "null"));
        System.out.println("List size: " + list.size());

        // Try to find the pair after first removal
        ArrayList<KVPair<String, Rectangle>> found = list.search(name);
        System.out.println("Search after first removal: " + (found != null
            ? "Found"
            : "Not found"));

        // Second removal using remove(key)
        KVPair<String, Rectangle> removedByKey = list.remove(name);
        System.out.println("After remove(key):");
        System.out.println("Removed pair: " + (removedByKey != null
            ? removedByKey.getKey() + ", " + removedByKey.getValue()
            : "null"));
        System.out.println("List size: " + list.size());

        // Verify final state
        found = list.search(name);
        System.out.println("Final search: " + (found != null
            ? "Found"
            : "Not found"));
    }

}
