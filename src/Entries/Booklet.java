package Entries;

import java.util.ArrayList;
import java.util.HashMap;
import TypeClass.*;

/**
 * Booklet class.
 */
public class Booklet extends Entry {
    public Booklet() {
        this.requiredFields = new HashMap<String, Integer>();
        this.requiredFields.put("title", 0);

        this.optionalFields = new HashMap<String, Integer>();
        this.optionalFields.put("author", 0);
        this.optionalFields.put("howpublished", 0);
        this.optionalFields.put("address", 0);
        this.optionalFields.put("month", 0);
        this.optionalFields.put("year", 0);
        this.optionalFields.put("note", 0);
        this.optionalFields.put("crossref",0);


        this.orAndReqFields = new ArrayList<OrAndReqFields>();
        this.orOpFields = new ArrayList<OrAndReqFields>();

        this.entryMap = new HashMap<String, String>();
        this.authors = new ArrayList<Person>();
    }
}
