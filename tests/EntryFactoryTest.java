import TypeClass.*;
import Entries.*;
import Parser.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test methods from EntryFactory.
 */
class EntryFactoryTest {

    @Test
    void parseAuthor() {
        Entry entry = new Article();
        String record = "\"FirstName LastName\",";
        int startChar = 0;
        Map<String, String> stringMap = new LinkedHashMap<String, String>();
        assertEquals(EntryFactory.parseAuthor(entry,record,startChar,stringMap,0),20);
        assertEquals(entry.authors.get(0).firstname,"FirstName");
        assertEquals(entry.authors.get(0).lastname,"LastName");
        stringMap.put("TEST","TestName TestSurname");
        String record1 = "TEST,";
        assertEquals(EntryFactory.parseAuthor(entry,record1,startChar,stringMap,0),4);
        assertEquals(entry.authors.get(1).firstname,"TestName");
        assertEquals(entry.authors.get(1).lastname,"TestSurname");
        String record2 = "\"Lastname2 | Firstname2\",";
        assertEquals(EntryFactory.parseAuthor(entry,record2,startChar,stringMap,0),24);
        assertEquals(entry.authors.get(2).firstname,"Firstname2");
        assertEquals(entry.authors.get(2).lastname,"Lastname2");
        String record3 = "\"NamePer1 SurPer1 and NamePer2 SurPer2\",";
        assertEquals(EntryFactory.parseAuthor(entry,record3,startChar,stringMap,0),39);
        assertEquals(entry.authors.get(3).firstname,"NamePer1");
        assertEquals(entry.authors.get(3).lastname,"SurPer1");
        assertEquals(entry.authors.get(4).firstname,"NamePer2");
        assertEquals(entry.authors.get(4).lastname,"SurPer2");
        String record4 = "\"SurPer1 | NamePer1 and SurPer2 | NamePer2\",";
        assertEquals(EntryFactory.parseAuthor(entry,record4,startChar,stringMap,0),43);
        assertEquals(entry.authors.get(5).firstname,"NamePer1");
        assertEquals(entry.authors.get(5).lastname,"SurPer1");
        assertEquals(entry.authors.get(6).firstname,"NamePer2");
        assertEquals(entry.authors.get(6).lastname,"SurPer2");
        stringMap.put("TEST1","TestName TestSurname and");
        String record5 = "TEST1 # \"TestName1 TestSurname1\",";
        assertEquals(EntryFactory.parseAuthor(entry,record5,startChar,stringMap,0),32);
        assertEquals(entry.authors.get(7).firstname,"TestName");
        assertEquals(entry.authors.get(7).lastname,"TestSurname");
        assertEquals(entry.authors.get(8).firstname,"TestName1");
        assertEquals(entry.authors.get(8).lastname,"TestSurname1");
    }

    @Test
    void parseField() {
        Entry entry = new Article();
        int startChar = 0;
        Map<String, String> stringMap = new LinkedHashMap<String, String>();
        String key = "test";
        String record = "\"Test\",";
        assertEquals(EntryFactory.parseField(entry,key,record,startChar,stringMap,0),6);
        assertEquals(entry.entryMap.get(key),"Test");
        String record1 = "     \"Test\"       ,";
        assertEquals(EntryFactory.parseField(entry,"record1",record1,startChar,stringMap,0),18);
        assertTrue(entry.entryMap.containsKey("record1"));
        assertFalse(entry.entryMap.containsKey("record2"));
        assertEquals(entry.entryMap.get("record1"),"Test");
        stringMap.put("TEST","This is a test");
        String record2 = "TEST,";
        assertEquals(EntryFactory.parseField(entry,"record2",record2,startChar,stringMap,0),4);
        assertTrue(entry.entryMap.containsKey("record2"));
        assertEquals(entry.entryMap.get("record2"),"This is a test");


        stringMap.put("TEST1","Test");
        String record3 = "TEST1 # \"Test2\",";
        assertEquals(EntryFactory.parseField(entry,"record3",record3,startChar,stringMap,0),15);
        assertTrue(entry.entryMap.containsKey("record3"));
        assertEquals(entry.entryMap.get("record3"),"Test Test2");
        String record4 = "\"Test2\" # TEST1,";
        assertEquals(EntryFactory.parseField(entry,"record4",record4,startChar,stringMap,0),15);
        assertTrue(entry.entryMap.containsKey("record4"));
        assertEquals(entry.entryMap.get("record4"),"Test2 Test");
        stringMap.put("TEST3","Test3");
        String record5 = "TEST1#TEST3,";
        assertEquals(EntryFactory.parseField(entry,"record5",record5,startChar,stringMap,0),11);
        assertTrue(entry.entryMap.containsKey("record5"));
        assertEquals(entry.entryMap.get("record5"),"Test Test3");
        String record6 = "TEST1         #      \"Test2\"           #          TEST3,";
        assertEquals(EntryFactory.parseField(entry,"record6",record6,startChar,stringMap,0),55);
        assertTrue(entry.entryMap.containsKey("record6"));
        assertEquals(entry.entryMap.get("record6"),"Test Test2 Test3");
        String record7 = "\"TEST\",";
        assertEquals(EntryFactory.parseField(entry,"record7",record7,startChar,stringMap,0),6);
        assertTrue(entry.entryMap.containsKey("record7"));
        assertEquals(entry.entryMap.get("record7"),"TEST");


    }

    @Test
    void keyOrIgnore() {
        Entry entry = new Article();
        int startChar = 0;
        String key = "key";
        String record = ", etc.";
        assertEquals(EntryFactory.keyOrIgnore(entry,key,record,startChar,0),0);
        assertEquals(entry.key,"key");

        String record1 = "= something, etc.";
        Entry entry1 = new Article();
        assertEquals(EntryFactory.keyOrIgnore(entry1,key,record1,startChar,0),11);
        assertEquals(entry1.key,null);

    }


    @Test
    void makeEntry(){
        String record = "test,\n" +
                "   author = \"Test | Test\",\n" +
                "   title = \"TestTitle\",\n" +
                "   year = \"TestYear\",\n" +
                "   note = \"TestNote\",\n" +
                "   editor = \"TestIgnoreField\",\n" +
                "}";
        Entry entry = new Misc();
        Map<String, String> stringMap = new LinkedHashMap<String, String>();
        Entry parsedEntry = EntryFactory.entryParser(entry,record,stringMap,0);
        assertEquals(parsedEntry.key,"test");
        assertTrue(entry.entryMap.containsKey("author"));
        assertTrue(entry.entryMap.containsKey("title"));
        assertTrue(entry.entryMap.containsKey("year"));
        assertTrue(entry.entryMap.containsKey("note"));
        assertFalse(entry.entryMap.containsKey("editor"));
        assertFalse(entry.entryMap.containsKey("editor"));

        assertEquals(entry.entryMap.get("title"),"TestTitle");
        assertEquals(entry.entryMap.get("year"),"TestYear");
        assertEquals(entry.entryMap.get("note"),"TestNote");

        String record1 = "test1,\n" +
                "   title = \"TestTitle\"\n" +
                "   note = \"TestNote\",\n" +
                "}";


        Entry badEntry = new Misc();
        Entry parsedBadEntry = EntryFactory.entryParser(badEntry,record1,stringMap,0);
        assertEquals(parsedBadEntry,null);

        String record2 = "test1,\n" +
                "   title = \"TestTitle,\n" +
                "   note = \"TestNote\",\n" +
                "}";


        Entry badEntry2 = new Misc();
        Entry parsedBadEntry2 = EntryFactory.entryParser(badEntry2,record2,stringMap,0);
        assertEquals(parsedBadEntry2,null);
    }

    @Test
    void checkCrossRef(){
        Entry entry = new Unpublished();

        entry.key = "test";
        entry.entryMap.put("authors","");
        entry.entryMap.put("crossref","testRef");
        entry.requiredFields.replace("authors",1);
        Entry entryCR = new Unpublished();
        entryCR.key = "testRef";
        entryCR.entryMap.put("authors","author");
        entryCR.entryMap.put("title","title");
        entryCR.entryMap.put("note","note");
        entryCR.entryMap.put("year","year");
        ArrayList<Entry> list = new ArrayList<Entry>();
        list.add(entry);
        list.add(entryCR);

        EntryFactory.checkCrossRef(list);
        assertTrue(entry.entryMap.containsKey("title"));
        assertEquals(entry.entryMap.get("authors"),"");
        assertEquals(entry.entryMap.get("title"),"title");
        assertEquals(entry.entryMap.get("note"),"note");
        assertFalse(entry.entryMap.containsKey("year"));
    }

}