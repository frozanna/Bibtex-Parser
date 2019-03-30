package Parser;

import Entries.Article;
import Entries.Book;
import Entries.Booklet;
import Entries.Conference;

import java.util.ArrayList;
import java.util.Map;
import Entries.*;
import TypeClass.*;

/**
 * Class that makes entries (Factory pattern is used here).
 */
public class EntryFactory {
    /**
     * Factory method that choose what type of entry we will create.
     * @param type      type of entry
     * @param record    String, which contains
     * @param stringMap map with records from String type
     * @return          returns parsed entry
     * @param numberOfEntry used to show exception
     * @throws IllegalArgumentException throws exception if there is no such type in BibTex
     */
    public static Entry makeEntry(String type,String record, Map<String,String> stringMap, int numberOfEntry) throws  IllegalArgumentException{
        Entry entry;
        switch (type) {
            case "ARTICLE":
                entry = new Article();
                return entryParser(entry,record,stringMap, numberOfEntry);
            case "BOOK":
                entry = new Book();
                return entryParser(entry,record,stringMap, numberOfEntry);
            case "BOOKLET":
                entry = new Booklet();
                return entryParser(entry,record,stringMap, numberOfEntry);
            case "CONFERENCE":
                entry = new Conference();
                return entryParser(entry,record,stringMap,numberOfEntry);
            case "INBOOK":
                entry = new Inbook();
                return entryParser(entry,record,stringMap,numberOfEntry);
            case "INCOLLECTION":
                entry = new Incollection();
                return entryParser(entry,record,stringMap,numberOfEntry);
            case "INPROCEEDINGS":
                entry = new Inproceedings();
                return entryParser(entry,record,stringMap, numberOfEntry);
            case "MANUAL":
                entry = new Manual();
                return entryParser(entry,record,stringMap,numberOfEntry);
            case "MASTERSTHESIS":
                entry = new Mastersthesis();
                return entryParser(entry,record,stringMap,numberOfEntry);
            case "MISC":
                entry = new Misc();
                return entryParser(entry,record,stringMap,numberOfEntry);
            case "PHDTHESIS":
                entry = new Phdthesis();
                return entryParser(entry,record,stringMap,numberOfEntry);
            case "PROCEEDINGS":
                entry = new Proceedings();
                return entryParser(entry,record,stringMap,numberOfEntry);
            case "TECHREPORT":
                entry = new Techreport();
                return entryParser(entry,record,stringMap,numberOfEntry);
            case "UNPUBLISHED":
                entry = new Unpublished();
                return entryParser(entry,record,stringMap,numberOfEntry);
            default:
                throw new IllegalArgumentException(type);
        }

    }

    /**
     * Method that actually parse entry. Also it updates whether field occured or not
     * @param entry      entry with a good type
     * @param record     String with fields from entry
     * @param stringMap  map with string type from file
     * @param numberOfEntry used to show exception
     * @return           parsed entry
     */
    public static Entry entryParser(Entry entry, String record, Map<String, String> stringMap, int numberOfEntry) {
        if(!record.contains("}")) throw new IllegalArgumentException("Entry number: "+numberOfEntry+", "+entry.getClass().getSimpleName());
        int startChar;
        int endChar;
        int iterator;
        String key;
        String value;
        String lastname;
        String firstname;
        char itrChar;
        try{
            for (iterator = 0; iterator < record.indexOf('}') && iterator+1!=record.indexOf('}'); iterator++){
                itrChar = record.toCharArray()[iterator];
                while (itrChar == ' ' || itrChar == '\n' || itrChar == '\t' || itrChar == '\r') {
                    iterator++;
                    itrChar = record.toCharArray()[iterator];
                }

                startChar = iterator;

                while (itrChar != ' ' && itrChar != '\n' && itrChar != '\t' && itrChar != '\r' && itrChar != '=' && itrChar != ',') {
                    iterator++;
                    itrChar = record.toCharArray()[iterator];
                }
                endChar = iterator;
                key = record.substring(startChar, endChar);
                if(entry.requiredFields.containsKey(key)){
                    if (entry.requiredFields.get(key) == 1)
                        throw new IllegalArgumentException("entry number: "+numberOfEntry+", "+entry.getClass().getName()+". Field: "+key);

                    entry.requiredFields.replace(key, 1);
                    for (OrAndReqFields item : entry.orAndReqFields) {
                        if (item.field1.equals(key) || item.field2.equals(key)) {
                            if (item.type) {
                                entry.requiredFields.replace(item.field1,1);
                                entry.requiredFields.replace(item.field2,1);
                            }
                            else{
                                if(item.field1.equals(key)){
                                    entry.requiredFields.replace(item.field1,1);
                                    entry.requiredFields.replace(item.field2,2);
                                }
                                else{
                                    entry.requiredFields.replace(item.field1,2);
                                    entry.requiredFields.replace(item.field2,1);
                                }
                            }
                        }
                    }
                }
                else if(entry.optionalFields.containsKey(key)){

                    if (entry.optionalFields.get(key) == 1)
                        throw new IllegalArgumentException("entry number: "+numberOfEntry+", "+entry.getClass().getName()+". Field: "+key);
                    entry.optionalFields.replace(key, 1);

                    for (OrAndReqFields item : entry.orOpFields) {
                        if (item.field1.equals(key) || item.field2.equals(key)) {
                            entry.optionalFields.replace(item.field1, 1);
                            entry.optionalFields.replace(item.field2, 1);
                        }
                    }

                }
                else{
                    iterator = keyOrIgnore(entry,key,record, iterator,numberOfEntry);
                    continue;
                }

                while(itrChar != '=' && itrChar != ','){
                    iterator++;
                    itrChar = record.toCharArray()[iterator];
                }
                iterator++;


                if (key.equals("author")) {
                    iterator = parseAuthor(entry,record, iterator,stringMap,numberOfEntry);
                }
                else iterator = parseField(entry,key,record,iterator,stringMap,numberOfEntry);

            }
            return entry;

        }catch (IllegalArgumentException e) {
            System.out.println("Bad written record: " + e);
            return null;
        }
    }

    /**
     * Method that parse authors. Authors must be parsed separately, because this field is stored in a special structure.
     * @param entry         entry that we parsing
     * @param record        string with fields from entry
     * @param startChar     index of character, where field with authors starts
     * @param stringMap     map of string type from file
     * @param numberOfEntry used to show exception
     * @return              index, where we should start parsing next field
     * @throws IllegalArgumentException throws exception if field with authors is badly written
     */
    public static int parseAuthor(Entry entry, String record, int startChar, Map<String, String> stringMap,int numberOfEntry) throws IllegalArgumentException{

        int iterator = startChar;
        boolean thereIsQuotation = false;
        char itrChar = record.toCharArray()[iterator];
        while (itrChar == ' ' || itrChar == '\n' || itrChar == '\t' || itrChar == '\r' || itrChar == '\"') {
            if(itrChar == '\"') thereIsQuotation = true;
            else thereIsQuotation = false;
            iterator++;
            itrChar = record.toCharArray()[iterator];
        }


        int start = iterator;
        String authors = "";

        if(record.substring(iterator).contains("#")){
            if(record.substring(iterator).contains("#")) {
                int startFrom = start;
                StringBuilder stringBuilder = new StringBuilder(authors);
                while (thereIsQuotation || itrChar != ',') {
                    if (thereIsQuotation) {
                        while (itrChar != '\"') {
                            iterator++;
                            itrChar = record.toCharArray()[iterator];
                        }
                        stringBuilder.append(record.substring(startFrom, iterator));
                        while (itrChar != '#' && itrChar != ',') {
                            iterator++;
                            itrChar = record.toCharArray()[iterator];
                        }
                        if (itrChar == ',') break;
                        iterator++;
                        itrChar = record.toCharArray()[iterator];
                        thereIsQuotation = false;
                        while (itrChar == ' ' || itrChar == '\n' || itrChar == '\t' || itrChar == '\r' || itrChar == '\"' || itrChar == ',') {
                            if (itrChar == '\"') thereIsQuotation = true;
                            else thereIsQuotation = false;
                            iterator++;
                            itrChar = record.toCharArray()[iterator];
                        }
                        startFrom = iterator;
                    } else {
                        while (itrChar != ' ' && itrChar != '\n' && itrChar != '\t' && itrChar != '\r' && itrChar != '#' && itrChar != ',') {
                            if(itrChar=='\"')
                                throw new IllegalArgumentException("Entry number: "+numberOfEntry+", "+entry.getClass().getName()+". Field: author");
                            iterator++;
                            itrChar = record.toCharArray()[iterator];
                        }
                        String stringKey = record.substring(startFrom, iterator);
                        if(stringMap.get(stringKey) == null) throw new IllegalArgumentException("Entry number: "+numberOfEntry+", "+entry.getClass().getName()+". Field: author");
                        String value = stringMap.get(stringKey);
                        stringBuilder.append(value);
                        while (itrChar != '#' && itrChar != ',') {
                            iterator++;
                            itrChar = record.toCharArray()[iterator];
                        }
                        if (itrChar == ',') break;
                        iterator++;
                        itrChar = record.toCharArray()[iterator];
                        thereIsQuotation = false;
                        while (itrChar == ' ' || itrChar == '\n' || itrChar == '\t' || itrChar == '\r' || itrChar == '\"' || itrChar == ',') {
                            if (itrChar == '\"') thereIsQuotation = true;
                            else thereIsQuotation = false;
                            iterator++;
                            itrChar = record.toCharArray()[iterator];
                        }
                        startFrom = iterator;
                    }
                    stringBuilder.append(" ");
                }
                authors = stringBuilder.toString();
            }
        }
        else{
            if(thereIsQuotation){
                while (itrChar != '\"') {
                    iterator++;
                    itrChar = record.toCharArray()[iterator];
                }
                authors = record.substring(start,iterator);
            }
            else{
                while (itrChar != ',') {
                    if(itrChar == '=') throw new IllegalArgumentException("entry number: "+numberOfEntry+", "+entry.getClass().getName()+". Field: author");
                    iterator++;
                    itrChar = record.toCharArray()[iterator];
                }
                String stringKey = record.substring(start,iterator);
                authors = stringMap.get(stringKey);
            }
        }


        entry.entryMap.put("author",authors);

        String[] authorsArray = authors.split(" and ");
        String firstname;
        String lastname;

        for(String a : authorsArray){
            if(a.contains("|")){
                lastname = a.split("[\\s]*\\|[\\s]*")[0];
                firstname = a.split("[\\s]*\\|[\\s]*")[1];

            }
            else {
                int i;
                for(i = a.length()-1; i>=0; i--){
                    itrChar = a.toCharArray()[i];
                    if(itrChar == ' ') break;
                }
                lastname = a.substring(i+1,a.length());
                firstname = a.substring(0,i);
            }
            if (lastname.contains("}") || lastname.contains("=") || firstname.contains("}") || firstname.contains("="))
                throw new IllegalArgumentException("Entry number: "+numberOfEntry+", "+entry.getClass().getName()+". Field: authors");

            entry.authors.add(new Person(firstname,lastname));
        }

        itrChar = record.toCharArray()[iterator];
        while (itrChar != ',') {
            iterator++;
            itrChar = record.toCharArray()[iterator];
        }
        return iterator;
    }

    /**
     * Method that parse fields.
     * @param entry          entry that we parsing
     * @param key            key of field that we're parsing
     * @param record        string with fields from entry
     * @param startChar     index of character, where field with authors starts
     * @param stringMap     map of string type from file
     * @param numberOfEntry used to show exception
     * @return              index, where we should start parsing next field
     * @throws IllegalArgumentException throws exception if field with authors is badly written
     */
    public static int parseField(Entry entry, String key, String record, int startChar, Map<String, String> stringMap,int numberOfEntry) throws IllegalArgumentException{

        int iterator = startChar;
        boolean thereIsQuotation = false;
        char itrChar = record.toCharArray()[iterator];
        while (itrChar == ' ' || itrChar == '\n' || itrChar == '\t' || itrChar == '\r' || itrChar == '\"') {
            if(itrChar == '\"') thereIsQuotation = true;
            else thereIsQuotation = false;
            iterator++;
            itrChar = record.toCharArray()[iterator];
        }


        int start = iterator;
        String value = "";

        if(record.substring(iterator).contains("#")){
            if(record.substring(iterator).contains("#")) {
                int startFrom = start;
                StringBuilder stringBuilder = new StringBuilder(value);
                while (thereIsQuotation || itrChar != ',') {
                    if (thereIsQuotation) {
                        while (itrChar != '\"') {
                            iterator++;
                            itrChar = record.toCharArray()[iterator];
                        }
                        stringBuilder.append(record.substring(startFrom, iterator));
                        while (itrChar != '#' && itrChar != ',') {
                            iterator++;
                            itrChar = record.toCharArray()[iterator];
                        }
                        if (itrChar == ',') break;
                        iterator++;
                        itrChar = record.toCharArray()[iterator];
                        thereIsQuotation = false;
                        while (itrChar == ' ' || itrChar == '\n' || itrChar == '\t' || itrChar == '\r' || itrChar == '\"' || itrChar == ',') {
                            if (itrChar == '\"') thereIsQuotation = true;
                            else thereIsQuotation = false;
                            iterator++;
                            itrChar = record.toCharArray()[iterator];
                        }
                        startFrom = iterator;
                    } else {
                        while (itrChar != ' ' && itrChar != '\n' && itrChar != '\t' && itrChar != '\r' && itrChar != '#' && itrChar != ',') {
                            if(itrChar == '\"') throw new IllegalArgumentException("Entry number: "+numberOfEntry+", "+entry.getClass().getName()+". Field: "+key);
                            iterator++;
                            itrChar = record.toCharArray()[iterator];
                        }
                        String stringKey = record.substring(startFrom, iterator);
                        if(stringMap.get(stringKey) == null) throw new IllegalArgumentException("Entry number: "+numberOfEntry+", "+entry.getClass().getName()+". Field: "+key);
                        String valueString = stringMap.get(stringKey);
                        stringBuilder.append(valueString);
                        while (itrChar != '#' && itrChar != ',') {
                            iterator++;
                            itrChar = record.toCharArray()[iterator];
                        }
                        if (itrChar == ',') break;
                        iterator++;
                        itrChar = record.toCharArray()[iterator];
                        thereIsQuotation = false;
                        while (itrChar == ' ' || itrChar == '\n' || itrChar == '\t' || itrChar == '\r' || itrChar == '\"' || itrChar == ',') {
                            if (itrChar == '\"') thereIsQuotation = true;
                            else thereIsQuotation = false;
                            iterator++;
                            itrChar = record.toCharArray()[iterator];
                        }
                        startFrom = iterator;
                    }
                    stringBuilder.append(" ");
                }
                value = stringBuilder.toString();
            }
        }
        else{
            if(thereIsQuotation){
                while (itrChar != '\"') {
                    iterator++;
                    itrChar = record.toCharArray()[iterator];
                }
                value = record.substring(start,iterator);
            }
            else{
                while (itrChar != ',') {
                    if(itrChar=='\"')
                        throw new IllegalArgumentException("Entry number: "+numberOfEntry+", "+entry.getClass().getName()+". Field: "+key);
                    iterator++;
                    itrChar = record.toCharArray()[iterator];
                }
                String stringKey = record.substring(start,iterator);
                value = stringMap.get(stringKey);
            }
        }
        if(value.contains("=") || value.contains("}"))
            throw new IllegalArgumentException("Entry number: "+numberOfEntry+", "+entry.getClass().getName()+". Field: "+key+", value: "+value);
        entry.entryMap.put(key,value);
        itrChar = record.toCharArray()[iterator];
        while (itrChar != ',') {
            if(itrChar == '=') if(itrChar == '=') throw new IllegalArgumentException("entry number: "+numberOfEntry+", "+entry.getClass().getName()+". Field: "+key);
            iterator++;
            itrChar = record.toCharArray()[iterator];
        }
        return iterator;
    }

    /**
     * Method that assings key to entry or simply ignore field if it isn't a required or optional field.
     * @param entry          entry that we parsing
     * @param key            possible key of that entry
     * @param record         string with fields from entry
     * @param startChar      index of character, where field with authors starts
     * @param numberOfEntry  used to show exception
     * @return               index, where we should start parsing next field
     */
    public static int keyOrIgnore(Entry entry, String key, String record, int startChar,int numberOfEntry){
        int iterator = startChar;

        char itrChar = record.toCharArray()[iterator];
        while (itrChar != ',') {
            iterator++;
            itrChar = record.toCharArray()[iterator];
        }
        if(!record.substring(startChar,iterator).contains("=")) entry.key = key;
        int countEq = record.substring(startChar,iterator).length()-record.substring(startChar,iterator).replaceAll("=","").length();
        if(countEq>1) throw new IllegalArgumentException("entry number: "+numberOfEntry+", "+entry.getClass().getName()+". Field: "+key);
        return iterator;
    }


    /**
     * Method that supports cross-reference entries.
     * @param entries   ArrayList of parsed entries from file
     */

    public static void checkCrossRef(ArrayList<Entry> entries) {
        for(Entry entry : entries){
            if(entry==null) continue;
            if(entry.entryMap.containsKey("crossref")){
                Entry crossRef;
                for(Entry entryRef : entries){
                    if(entryRef == null) continue;
                    if(entryRef.key == null) throw new IllegalArgumentException(entry.getClass().getSimpleName()+". No cross-ref key.");
                    if(entryRef.key.equals(entry.entryMap.get("crossref"))){
                        for (Map.Entry<String, Integer> field : entry.requiredFields.entrySet()) {
                            try{
                                if(field.getValue() == 0 || field.getValue() == 2){
                                    if(field.getKey().equals("author")){
                                        if(entryRef.authors == null)   throw new NullPointerException("There is no required field in "+entry.getClass().getSimpleName()+" ("+entry.key+"). Field: authors");
                                        for(Person p : entryRef.authors){
                                           entry.authors.add(p);
                                        }
                                        entry.requiredFields.replace(field.getKey(), 1);
                                    }
                                    else{
                                        if(entryRef.entryMap.get(field.getKey()) == null) throw new NullPointerException("There is no required field in "+entry.getClass().getSimpleName()+" ("+entry.key+"). Field: "+field.getKey());
                                        entry.entryMap.put(field.getKey(),entryRef.entryMap.get(field.getKey()));
                                        entry.requiredFields.replace(field.getKey(), 1);
                                    }
                                }
                            }
                            catch(NullPointerException e){
                                System.out.println(e);
                            }

                        }
                        break;
                    }
                }
            }
        }
    }
}
