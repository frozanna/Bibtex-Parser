package Entries;

import TypeClass.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Article class.
 */
public class Article extends Entry {
    public Article(){
        this.requiredFields = new HashMap<String,Integer>();
        this.requiredFields.put("author",0);
        this.requiredFields.put("title",0);
        this.requiredFields.put("journal",0);
        this.requiredFields.put("year",0);

        this.optionalFields = new HashMap<String, Integer>();
        this.optionalFields.put("volume",0);
        this.optionalFields.put("number",0);
        this.optionalFields.put("pages",0);
        this.optionalFields.put("month",0);
        this.optionalFields.put("note",0);
        this.optionalFields.put("crossref",0);

        this.entryMap = new HashMap<String,String>();
        this.orAndReqFields = new ArrayList<OrAndReqFields>();
        this.orOpFields = new ArrayList<TypeClass.OrAndReqFields>();
        this.authors = new ArrayList<Person>();
    }
}
