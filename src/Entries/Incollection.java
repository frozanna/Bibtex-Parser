package Entries;

import java.util.ArrayList;
import java.util.HashMap;
import TypeClass.*;

/**
 * Incollection class.
 */
public class Incollection extends Entry {
    public Incollection() {
        this.requiredFields = new HashMap<String, Integer>();
        this.requiredFields.put("author", 0);
        this.requiredFields.put("title", 0);
        this.requiredFields.put("booktitle", 0);
        this.requiredFields.put("publisher", 0);
        this.requiredFields.put("year", 0);

        this.optionalFields = new HashMap<String, Integer>();
        this.optionalFields.put("editor", 0);
        this.optionalFields.put("volume", 0);
        this.optionalFields.put("number", 0);
        this.optionalFields.put("series", 0);
        this.optionalFields.put("type", 0);
        this.optionalFields.put("address", 0);
        this.optionalFields.put("edition", 0);
        this.optionalFields.put("month", 0);
        this.optionalFields.put("note", 0);
        this.optionalFields.put("crossref",0);


        this.orAndReqFields = new ArrayList<OrAndReqFields>();

        this.orOpFields = new ArrayList<OrAndReqFields>();
        this.orOpFields.add(new OrAndReqFields("volume", "number", true));

        this.entryMap = new HashMap<String, String>();
        this.authors = new ArrayList<Person>();
    }
}
