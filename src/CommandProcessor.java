/**
 * The purpose of this class is to parse a text file into its appropriate, line
 * by line commands for the format specified in the project spec.
 * 
 * @author Harleen Kaur
 * 
 * @version 2024-01-22
 */
public class CommandProcessor {

    // the database object to manipulate the
    // commands that the command processor
    // feeds to it
    private Database data;

    /**
     * The constructor for the command processor requires a database instance to
     * exist, so the only constructor takes a database class object to feed
     * commands to.
     */
    public CommandProcessor() {
        data = new Database();
    }


    /**
     * This method parses keywords in the line and calls methods in the
     * database as required. Each line command will be specified by one of the
     * keywords to perform the actions.
     * These actions are performed on specified objects and include insert,
     * remove,
     * regionsearch, search, and dump. If the command in the file line is not
     * one of these, an appropriate message will be written in the console. This
     * processor method is called for each line in the file. Note that the
     * methods called will themselves write to the console, this method does
     * not, only calling methods that do.
     * 
     * @param line
     *            a single line from the text file
     */
    public void processor(String line) {
        // converts the string of the line into an
        // array of its space (" ") delimited elements
        String[] arr = line.split("\\s{1,}");
        String command = arr[0]; // the command will be the first of these
                                 // elements
        // calls the insert function and passes the correct
        // parameters by converting the string integers into
        // their Integer equivalent, trimming the whitespace
        if (command.equals("insert")) {
            if (arr.length < 6) {
                System.out.println("Rectangle rejected: ");
                return;
            }
            if (!arr[1].matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
                throw new IllegalArgumentException("Invalid name format");
            }

            try {
                Rectangle rectangle = new Rectangle(Integer.parseInt(arr[2]),
                    Integer.parseInt(arr[3]), Integer.parseInt(arr[4]), Integer
                        .parseInt(arr[5]));

                KVPair<String, Rectangle> pair = new KVPair<>(arr[1],
                    rectangle);
                data.insert(pair);
            }
            catch (NumberFormatException e) {
                System.out.println("Rectangle rejected: ");
            }
        }
        // calls the appropriate remove method based on the
        // number of white space delimited strings in the line
        else if (command.equals("remove")) {
            // checks the number of white space delimited strings in the line
            int numParam = arr.length - 1;
            if (numParam == 1) {
                // Calls remove by name
                data.remove(arr[1]);

            }
            else if (numParam == 4) {
                // Calls remove by coordinate, converting string
                // integers into their Integer equivalent minus whitespace
                data.remove(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]),
                    Integer.parseInt(arr[3]), Integer.parseInt(arr[4]));
            }

        }
        else if (command.equals("regionsearch")) {
            // calls the regionsearch method for a set of coordinates
            // the string integers in the line will be trimmed of whitespace
            if (arr.length == 5) {
                data.regionsearch(Integer.parseInt(arr[1]), Integer.parseInt(
                    arr[2]), Integer.parseInt(arr[3]), Integer.parseInt(
                        arr[4]));
            }

        }
        else if (command.equals("intersections")) {
            // calls the intersections method, no parameters to be passed
            // (see the intersections JavaDoc in the Database class for more
            // information)
            data.intersections();
        }
        else if (command.equals("search")) {
            // calls the search method for a name of object
            if (arr.length != 2) {
                System.out.println("unrecognized command search" + line
                    .substring(arr[0].length()));
                return;
            }
            data.search(arr[1]);
        }
        else if (command.equals("dump")) {
            // calls the dump method for the database, takes no parameters
            // (see the dump() JavaDoc in the Database class for more
            // information)
            data.dump();
        }
        else {
            // the first white space delimited string in the line is not
            // one of the commands which can manipulate the database,
            // a message will be written to the console
            System.out.println("Unrecognized command.");
        }
    }

}
