package Parser;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Entries.*;

/**
 * Main BibTex parser.
 */
public class BibTexParser {

    private static EntryFactory factory;
    private BibTexParser(EntryFactory factory){
        this.factory = factory;
    }
    /**
     * Main parser. Splits file to entries.
     * @param reader buffer reader from file
     * @return       ArrayList of parsed entries from bile
     */
    public static ArrayList<Entry> parse(BufferedReader reader){
        try {

            char readerChar = (char)reader.read();
            while(readerChar != '@') {
                readerChar = (char)reader.read();
            }
            String read = null;

            StringBuilder stringBuilder = new StringBuilder("");
            stringBuilder.append("@");
            while ((read = reader.readLine())!= null) {
                stringBuilder.append(read);
            }
            String readedFile = stringBuilder.toString();


            ArrayList<String> splitedFile = new ArrayList<String>();
            Pattern pattern = Pattern.compile("@.*?\\}");
            Matcher matcher = pattern.matcher(readedFile);
            while(matcher.find()) {
                String s = matcher.group();
                s = s.substring(1);
                splitedFile.add(s);
            }

            return parseEntries(splitedFile);

        } catch (IOException e) {
            System.out.println("There was a problem with file: " + e);
            return null;
        } finally {
            try {reader.close();
            } catch (Exception e) {
                System.out.println("Can't close the file: " + e);
            }
        }
    }

    /**
     * Method, that parse each entry/string record.
     * @param splitedFile string made from file
     * @return            arraylist of entries
     */
    private static ArrayList<Entry> parseEntries(ArrayList<String> splitedFile) {
        String type;
        ArrayList<Entry> parsedBibTex = new ArrayList<Entry>();
        Map<String, String> stringMap = new LinkedHashMap<String, String>();
        String[] typeAndRecord = null;

        int numberOfEntry =0;
        for (String entry : splitedFile) {
            try {
                typeAndRecord = entry.split("\\{");
                type = typeAndRecord[0];
                if (type.equals("ARTICLE") || type.equals("BOOK") || type.equals("BOOKLET") || type.equals("INBOOK")
                        || type.equals("INCOLLECTION") || type.equals("INPROCEEDINGS") || type.equals("MANUAL")
                        || type.equals("MASTERSTHESIS") || type.equals("MISC") || type.equals("PHDTHESIS")
                        || type.equals("PROCEEDINGS") || type.equals("TECHREPORT") || type.equals("UNPUBLISHED")
                        || type.equals("CONFERENCE")) {
                    numberOfEntry++;
                    parsedBibTex.add(factory.makeEntry(type, typeAndRecord[1], stringMap,numberOfEntry));

                } else if (type.equals("STRING")) StringParser.stringAdd(stringMap, typeAndRecord[1]);
                else if (type.equals("preamble") || type.equals("comment")) continue;
                else throw new NoSuchElementException(type);
            }catch (NoSuchElementException e) {
                System.out.println("Wrong type of record: " + e);
            }
            catch (IllegalArgumentException e) {
                System.out.println("Wrong record: " + e);
            }

        }

        ArrayList<Entry> checkedParsedBibTex = new ArrayList<Entry>();
        EntryFactory.checkCrossRef(parsedBibTex);

        for(Entry entry : parsedBibTex){
            if(!checkIfCorrect(entry)){
                String message;
                if(entry == null){
                    message = "Bad written record.";
                }
                else{
                    message = "Lack of fields. Entry "+entry.getClass().getSimpleName()+" ("+entry.key+").";
                }
                System.out.println(message);
            }
            else checkedParsedBibTex.add(entry);
        }
        return checkedParsedBibTex;
    }

    /**
     * Method that check if entry is correct (if it contains all fields from requiredFields
     * @param entry     entry that we checking
     * @return      returns true if entry has all required files
     */
    public static boolean checkIfCorrect(Entry entry){
        if(entry == null) return false;
        if(entry.key == null) return false;
        for (Map.Entry<String, Integer> key : entry.requiredFields.entrySet())
        {
            if(key.getValue() == 0) return false;
        }
        return true;
    }
}
