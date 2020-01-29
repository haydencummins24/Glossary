import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program to create a glossary from an input list.
 *
 * @author Hayden Cummins
 *
 * @version 20191119
 */
public final class Glossary {
    /**
     * Overrides compare method to implement Comparator<String> and make it sort
     * alphabetically.
     *
     * @implements Comparator<String>
     *
     * @overrides compare
     */
    private static class StringSort implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    /**
     * Private no-argument constructor to prevent instantiation of this utility
     * class.
     */
    private Glossary() {
    }

    /**
     * Adds hyperlinks to a provided definition given a map to reference terms
     * against.
     *
     * @param mref
     *            map containing all terms and definitions
     * @param definition
     *            string containing the definition for a term
     * @param folderName
     *            string containing the name of the folder in which the files
     *            will be stored
     * @return definition
     */
    public static String addLinks(Map<String, String> mref, String definition,
            String folderName) {
        /**
         * set up iterator to loop through terms and check whether or not they
         * exist within the definition
         */
        for (Pair<String, String> s : mref) {
            //if the definition has such a term we need to add a link
            if (definition.contains(s.key())) {
                //determine the location of the term
                int termLoc = definition.indexOf(s.key());
                //split the term into two pieces, excluding the term itself
                String firstHalf = definition.substring(0, termLoc);
                String secondHalf = definition
                        .substring((termLoc + s.key().length()));
                //add the term back into the definition, but as an html link
                String term = definition.substring(termLoc,
                        termLoc + s.key().length());
                term = " <a href=\"" + s.key()
                        + ".html\" style=\"color: #fb3f00; text-decoration: none;\">"
                        + s.key() + "</a>";
                definition = firstHalf + term + secondHalf;
            }
        }
        //return finished definition
        return definition;
    }

    /**
     * Creates html page for a glossary term.
     *
     * @param mref
     *            map containing all terms and definitions
     * @param term
     *            string containing a term
     * @param definition
     *            string containing the definition for a term
     * @param folderName
     *            string containing the name of the folder in which the files
     *            will be stored
     */
    public static void termPage(Map<String, String> mref, String term,
            String definition, String folderName) {

        SimpleWriter fileOut = new SimpleWriter1L(
                folderName + "/" + term + ".html");

        //Set Up Base Template
        //add term to top left of page(red, italicized, large)

        //begin html

        fileOut.println("<html>");
        fileOut.println("<head>");
        fileOut.println(
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\" />");
        fileOut.println("<title>Glossary Index</title>");
        fileOut.println(" </head>");
        fileOut.println("<body>");
        fileOut.println("<div class=\"row\">");
        fileOut.println("  <div class=\"column\">");
        fileOut.println("  </div>");
        fileOut.println("  <div class=\"column\">");
        fileOut.println("  <style>");
        fileOut.println("  body{");
        fileOut.println("  background-color: #101010;");
        fileOut.println("  }");
        fileOut.println("  </style>");
        //Header
        fileOut.println("  <h1><font color=\"red\"><b><i>" + term
                + "</i></b></font></h1>");
        fileOut.println("  <ul><font color=\"white\">");
        definition = addLinks(mref, definition, folderName);
        fileOut.println("   <li>" + definition + "</li>");

        //closing tags
        fileOut.println("  </ul>");
        fileOut.println("  </div>");
        fileOut.println("  <div class=\"column\">");
        fileOut.println(
                "  <body><font color=\"white\"><b><a href=\"index.html\" style=\"color: #fb3f00; text-decoration: none;\">Return to index</a></b></body>");
        fileOut.println("  </div>");
        fileOut.println("</div>");
        fileOut.println("</body>");
        fileOut.println("</html>");
        //close fileOut
        fileOut.close();

    }

    /**
     * Creates HTML index page containing all terms and links to their
     * definitions.
     *
     * @param mref
     *            map containing all terms and definitions
     * @param folderName
     *            name of the folder in which all files will be stored
     *
     */
    public static void indexpage(Map<String, String> mref, String folderName) {

        //prep to output to file
        SimpleWriter fileOut = new SimpleWriter1L(folderName + "/index.html");

        //begin html

        fileOut.println("<html>");
        fileOut.println("<head>");
        fileOut.println(
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\" />");
        fileOut.println("<title>Glossary Index</title>");
        fileOut.println(" </head>");
        fileOut.println("<body>");
        fileOut.println("<div class=\"row\">");
        fileOut.println("  <div class=\"column\">");
        fileOut.println("  </div>");
        fileOut.println("  <div class=\"column\">");
        fileOut.println("  <style>");
        fileOut.println("  body{");
        fileOut.println("  background-color: #101010;");
        fileOut.println("  }");
        fileOut.println("  </style>");
        //Header
        fileOut.println("  <h1><font color=\"red\">Glossary</font></h1>");
        fileOut.println("  <ul><font color=\"white\">");

        //alphabetize and set up queue
        Queue<String> terms = alphabetize(mref);
        //while loop to set up definition links
        for (Pair<String, String> s : mref) {
            //set up page
            termPage(mref, s.key(), s.value(), folderName);
        }
        for (String st : terms) {
            //Bulleted list format
            //pass in name as hyperlink to respective HTML file
            fileOut.println("   <li><a href=\"" + st
                    + ".html\" style=\"color: #fb3f00; text-decoration: none;\">"
                    + st + "</a></li>");
        }
        //closing tags
        fileOut.println("  </ul>");
        fileOut.println("  </div>");
        fileOut.println("  <div class=\"column\">");
        fileOut.println("  </div>");
        fileOut.println("</div>");
        fileOut.println("</body>");
        fileOut.println("</html>");

        //close fileOut
        fileOut.close();

    }

    /**
     * Returns the map of all individual terms and definitions read as lines
     * from {@code input}.
     *
     * @param input
     *            source of strings, one per line
     * @return map of lines read from {@code input}
     * @requires input.is_open
     * @ensures <pre>
     * input.is_open
     * </pre>
     */
    public static Map<String, String> linesFromInput(SimpleReader input) {
        assert input != null : "Violation of: input is not null";
        assert input.isOpen() : "Violation of: input.is_open";
        //initialize map to fill and return
        Map<String, String> m = new Map1L<>();
        //String for term(key of map)
        String term = "";
        //String for definition(value of map)
        String definition = "";
        //while input still can be given
        while (!input.atEOS()) {
            //read in term, as it will always be the first in the block of text
            term = input.nextLine();
            //reset definition as loop will continuously overwrite it
            definition = "";
            //get first line of definition
            definition = input.nextLine();
            //set test string to check if next line is empty
            String tester = input.nextLine();
            //while the next line is not empty loop through
            while (!tester.equals("")) {
                //with each successive loop, add the value of tester to definition
                definition = definition + tester;
                //read in tester once again to compare
                tester = input.nextLine();

            }
            //add finished definition and term to map
            m.add(term, definition);
        }

        //return filled map
        return m;
    }

    /**
     * Alphabetizes the map according to term(key).
     *
     * @param mref
     *            map to be alphabetized
     * @return glossaryterms
     * @requires mref.isEmpty() == false
     *
     */
    public static Queue<String> alphabetize(Map<String, String> mref) {
        //create queue to be filled with terms
        Queue<String> glossaryterms = new Queue2<>();
        //for loop using iterator to populate glossaryterms with all the keys(terms)
        for (Pair<String, String> s : mref) {
            //enqueue to add
            glossaryterms.enqueue(s.key());
        }
        //comparator
        Comparator<String> alpheb = new StringSort();
        //sort
        glossaryterms.sort(alpheb);
        //return terms
        return glossaryterms;
    }

    /**
     * Given a file name (relative to the path where the application is running)
     * that contains fragments of a single original source text, one fragment
     * per line, outputs to stdout the result of trying to reassemble the
     * original text from those fragments using a "greedy assembler". The
     * result, if reassembly is complete, might be the original text; but this
     * might not happen because a greedy assembler can make a mistake and end up
     * predicting the fragments were from a string other than the true original
     * source text. It can also end up with two or more fragments that are
     * mutually non-overlapping, in which case it outputs the remaining
     * fragments, appropriately labelled.
     *
     * @param args
     *            Command-line arguments: not used
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Input file with terms and definitions: ");
        String inputFileName = in.nextLine();
        SimpleReader inFile = new SimpleReader1L(inputFileName);
        /*
         * Get glossary items from input file
         */
        Map<String, String> glossary = linesFromInput(inFile);
        /*
         * Close inFile; we're done with it
         */
        inFile.close();
        //write to index page, and in turn write
        indexpage(glossary, "data");

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}