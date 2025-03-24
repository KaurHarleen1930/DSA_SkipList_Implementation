import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import student.TestCase;
import student.TestableRandom;

/**
 * @author {Harleen Kaur}
 * @version {Put Something Here}
 */
public class ProblemSpecTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing here
    }


    /**
     * Read contents of a file into a string
     * 
     * @param path
     *            File name
     * @return the string
     * @throws IOException
     */
    static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }


    /**
     * Example 1: This method runs on a command sample IO file
     * You will write similar test cases
     * using different text files
     * 
     * @throws IOException
     */
    public void testPostedSample() throws IOException {
        // Setting up all the parameters
        TestableRandom.setNextBooleans(true, false, // First few inserts
            false, true, false, false, true, false, false, true, false, false,
            true, false, // Middle inserts
            false, true, false, false, true, false, false, true, false, false,
            true, false // Extra values for safety
        );
        String[] args = new String[1];
        args[0] = "P1test2.txt";

        // Invoke main method of our Graph Project
        SkipListProject.main(args);

        // Actual output from your System console
        String output = systemOut().getHistory();

        // Expected output from file
        String referenceOutput = readFile("P1test2Out.txt");
        // Compare the two outputs
// once you have implemented your project
        assertFuzzyEquals(referenceOutput, output);
    }


    /**
     * Tests the skip list implementation with a sample input file.
     * 
     * This method reads the sample input file "P1test1.txt", performs skip list
     * operations
     * based on the commands in the file, and compares the output with the
     * expected output
     * defined in "P1test1Out.txt".
     * 
     * The method sets up the TestableRandom object with specific nextInt values
     * to control
     * the levels of the nodes being inserted into the skip list.
     * 
     * @throws IOException
     *             if an I/O error occurs while reading the input or expected
     *             output files
     */

    public void testPostedSample1() throws IOException {
        // Setting up all the parameters
        boolean[] randomBools = new boolean[] { true, false, false, true, false,
            false, true, true, false, true, false,

            false, true, false, false, true, false, false

        };
        TestableRandom.setNextBooleans(randomBools);

        String[] args = new String[1];
        args[0] = "P1test1.txt";

        // Invoke main method of our Graph Project
        SkipListProject.main(args);

        // Actual output from your System console
        String output = systemOut().getHistory();

        // Expected output from file
        String referenceOutput = readFile("P1test1Out.txt");
        assertFuzzyEquals(referenceOutput, output);
    }


    /**
     * Tests the skip list implementation with a sample input file.
     * 
     * This method reads the sample input file "duplicate_test.txt", performs
     * skip list operations
     * based on the commands in the file, and compares the output with the
     * expected output
     * defined in "duplicate_testOut.txt".
     * 
     * The method sets up the TestableRandom object with specific nextInt values
     * to control
     * the levels of the nodes being inserted into the skip list.
     * 
     * @throws IOException
     *             if an I/O error occurs while reading the input or expected
     *             output files
     */

    public void testDuplicate() throws IOException {
        // Setting up all the parameters
        String[] args = new String[1];
        args[0] = "duplicate_test.txt";

        TestableRandom.setNextBooleans(false);
        // Invoke main method of our Graph Project
        SkipListProject.main(args);

        // Actual output from your System console
        String output = systemOut().getHistory();

        // Expected output from file
        String referenceOutput = readFile("duplicate_testOut.txt");

        // Compare the two outputs
        assertFuzzyEquals(referenceOutput, output);
    }


    /**
     * Example 2: This method runs on a command sample IO file
     * You will write similar test cases
     * using different text files
     * 
     * @throws IOException
     */
    public void testPostedSyntaxSample() throws IOException {
        // Setting up all the parameters
        String[] args = new String[1];
        args[0] = "SyntaxTest.txt";

        // Invoke main method of our Graph Project
        SkipListProject.main(args);

        // Actual output from your System console
        String output = systemOut().getHistory();

        // Expected output from file
        String referenceOutput = readFile("SyntaxTestOut.txt");

        // Compare the two outputs
        assertEquals(referenceOutput, output);
    }
}
