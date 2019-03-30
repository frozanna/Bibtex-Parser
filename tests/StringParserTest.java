import Parser.StringParser;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class to test StringParser.
 */
class StringParserTest {

    @Test
    void stringAdd() {
        Map<String, String> stringMap = new LinkedHashMap<String, String>();
        String record = "TEST=\"This is a test\"";
        StringParser.stringAdd(stringMap,record);
        Assert.assertTrue(stringMap.containsKey("TEST"));
        Assert.assertEquals(stringMap.get("TEST"),"This is a test");

        record = "                 TEST2      =          \"This is a second test\"            ";
        StringParser.stringAdd(stringMap,record);
        Assert.assertTrue(stringMap.containsKey("TEST2"));
        Assert.assertEquals(stringMap.get("TEST2"),"This is a second test");

    }
}