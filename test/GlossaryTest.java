import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class GlossaryTest {

    @Test
    public void addLinksTest() {
        SimpleWriter out = new SimpleWriter1L();
        Map<String, String> mref = new Map1L<>();
        String test5 = "a sequence of words that gives meaning to a term";
        String test6 = "definition";
        String test3 = "a list of difficult or specialized terms, with their definitions,\r\n"
                + "usually near the end of a book";
        String test4 = "glossary";
        String test1 = "a word whose definition is in a glossary";
        String test2 = "term";
        mref.add(test2, test1);
        mref.add(test4, test3);
        mref.add(test6, test5);
        String folder = "data";
        test1 = Glossary.addLinks(mref, test1, folder);
        out.print(test1);
        /**
         * Should print as shown below
         *
         * a word whose
         * <a href="definition.html" style="color: #fb3f00; text-decoration:
         * none;">definition</a> is in a
         * <a href="glossary.html" style="color: #fb3f00; text-decoration:
         * none;">glossary</a>"
         */
        out.println();
        out.println();
        out.close();
    }

    @Test
    public void alphabetizeTest() {
        SimpleWriter out = new SimpleWriter1L();
        Map<String, String> mref = new Map1L<>();
        String test5 = "a sequence of words that gives meaning to a term";
        String test6 = "definition";
        String test3 = "a list of difficult or specialized terms, with their definitions,\r\n"
                + "usually near the end of a book";
        String test4 = "glossary";
        String test1 = "a word whose definition is in a glossary";
        String test2 = "term";
        mref.add(test2, test1);
        mref.add(test4, test3);
        mref.add(test6, test5);
        Queue<String> glossaryterms = Glossary.alphabetize(mref);
        out.println(glossaryterms.toString());
        /**
         * Should Print "<definition,glossary,term>"
         */
        out.println();
        out.close();
    }

    @Test
    public void linesFromInputTest() {
        SimpleWriter out = new SimpleWriter1L();
        SimpleWriter outFile = new SimpleWriter1L("data/text");
        outFile.print("meaning\n"
                + "something that one wishes to convey, especially by language\n"
                + "\n" + "term\n" + "a word whose definition is in a glossary\n"
                + "\n" + "word\n"
                + "a string of characters in a language, which has at least one character\n"
                + "\n" + "definition\n"
                + "a sequence of words that gives meaning to a term\n" + "\n");
        SimpleReader inFile = new SimpleReader1L("data/text");

        Map<String, String> glossary = Glossary.linesFromInput(inFile);
        out.println(glossary.toString());
        /**
         * Should Print -- {(meaning,something that one wishes to convey,
         * especially by language),(term,a word whose definition is in a
         * glossary),(definition,a sequence of words that gives meaning to a
         * term),(word,a string of characters in a language, which has at least
         * one character)}
         */
        out.println();
        out.close();
    }

    @Test
    public void termPageTest() {
        SimpleWriter out = new SimpleWriter1L();
        Map<String, String> mref = new Map1L<>();
        String test5 = "a sequence of words that gives meaning to a term";
        String test6 = "definition";
        String test3 = "a list of difficult or specialized terms, with their definitions,\r\n"
                + "usually near the end of a book";
        String test4 = "glossary";
        String test1 = "a word whose definition is in a glossary";
        String test2 = "term";
        mref.add(test2, test1);
        mref.add(test4, test3);
        mref.add(test6, test5);
        String folder = "data";
        Glossary.termPage(mref, test6, test5, folder);

        /**
         * Should Write To File And Create Working HTML -- Should Print
         * (true,"data/definition.html",[contents])
         */
        SimpleWriter tempFile = new SimpleWriter1L("data/definition.html");
        out.println(tempFile.toString());
        out.println();
        tempFile.close();
        out.close();
    }

    @Test
    public void indexPageTest() {
        SimpleWriter out = new SimpleWriter1L();
        Map<String, String> mref = new Map1L<>();
        String test5 = "a sequence of words that gives meaning to a term";
        String test6 = "definition";
        String test3 = "a list of difficult or specialized terms, with their definitions,\r\n"
                + "usually near the end of a book";
        String test4 = "glossary";
        String test1 = "a word whose definition is in a glossary";
        String test2 = "term";
        mref.add(test2, test1);
        mref.add(test4, test3);
        mref.add(test6, test5);
        String folder = "data";
        Glossary.indexpage(mref, folder);

        /**
         * Should Write To File And Create Working HTML -- Should Print
         * (true,"data/index.html",[contents])
         */
        SimpleWriter tempFile = new SimpleWriter1L("data/index.html");
        out.println(tempFile.toString());
        out.println();
        tempFile.close();
        out.close();
    }

}
