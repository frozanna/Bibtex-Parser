package Entries;

import TypeClass.Person;

import java.util.ArrayList;
import java.util.HashMap;
import TypeClass.*;

/**
 * Techreport class.
 */
public class Techreport extends Entry {
    public Techreport() {
        this.requiredFields = new HashMap<String, Integer>();
        this.requiredFields.put("author", 0);
        this.requiredFields.put("title", 0);
        this.requiredFields.put("institution", 0);
        this.requiredFields.put("year", 0);

        this.optionalFields = new HashMap<String, Integer>();
        this.optionalFields.put("type", 0);
        this.optionalFields.put("number", 0);
        this.optionalFields.put("address", 0);
        this.optionalFields.put("month", 0);
        this.optionalFields.put("note", 0);
        this.optionalFields.put("crossref",0);


        this.orAndReqFields = new ArrayList<OrAndReqFields>();
        this.orOpFields = new ArrayList<OrAndReqFields>();

        this.entryMap = new HashMap<String, String>();
        this.authors = new ArrayList<Person>();
    }
}
