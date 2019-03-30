import Parser.BibTexParser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import Entries.*;
import java.io.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test some methods from BibTexParser class.
 */
class BibTexParserTest {

    @Test
    void parse(){


        String str = "There is a text.\n" +
                "\n" +
                "\n" +
                "@STRING{TEST = \"Test Year\"}\n" +
                "\n" +
                "\n" +
                "There is a text.\n" +
                "\n" +
                "@ARTICLE{\n" +
                "   test,\n" +
                "   author = \"TestName TestSurname\",\n" +
                "   title = \"Test Title\",\n" +
                "   journal = \"Test Journal\",\n" +
                "   year = TEST,\n" +
                "}\n" +
                "\n" +
                "@ARTICLE1{\n" +
                "   test1,\n" +
                "   author = \"TestName TestSurname\",\n" +
                "   title = \"Bad Entry Test\",\n" +
                "   journal = \"TestJournal\",\n" +
                "   year = \"Test Year\",\n" +
                "}\n";

        InputStream is = new ByteArrayInputStream(str.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ArrayList<Entry> file = BibTexParser.parse(reader);

        assertTrue(file.size() == 1);
        assertTrue(file.get(0) instanceof Article);
        assertEquals(file.get(0).key, "test");
        assertEquals(file.get(0).entryMap.get("year"),"Test Year");
        assertEquals(file.get(0).entryMap.get("title"),"Test Title");
    }

    @Test
    void checkIfCorrect() {
        Entry badEntry = new Article();
        badEntry.key = "test";
        assertFalse(BibTexParser.checkIfCorrect(badEntry));
        Entry goodEntry = new Misc();
        goodEntry.key = "test";
        assertTrue(BibTexParser.checkIfCorrect(goodEntry));
        Entry badEntry2 = new Misc();
        assertFalse(BibTexParser.checkIfCorrect(badEntry2));
    }
}