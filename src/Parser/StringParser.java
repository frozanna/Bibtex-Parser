package Parser;

import java.util.Map;

/**
 * String type parser.
 */
public class StringParser {

    /**
     * Method that adds to map of string type from file new records
     * @param stringMap     Map to which we are adding
     * @param record        Record that we adding
     */
    public static void stringAdd(Map<String,String> stringMap, String record) {
        int startChar;
        int endChar;
        int iterator = 0;

        String key;
        String value;

        try{
            char itrChar = record.toCharArray()[0];
            while( itrChar == ' ' || itrChar == '\n' || itrChar == '\t' || itrChar == '\r'){
                iterator++;
                itrChar = record.toCharArray()[iterator];
            }

            startChar = iterator;

            while (itrChar != ' ' && itrChar != '\n' && itrChar != '\t' && itrChar != '\r' && itrChar != '='){
                iterator++;
                itrChar = record.toCharArray()[iterator];
            }
            endChar = iterator;
            key = record.substring(startChar,endChar);

            if(stringMap.containsKey(key)) throw new IllegalArgumentException(key);
            while(itrChar != '"'){
                itrChar = record.toCharArray()[iterator];
                iterator++;
            }
            startChar = iterator;

            itrChar = record.toCharArray()[iterator++];
            while(itrChar != '"'){
                itrChar = record.toCharArray()[iterator];
                iterator++;
            }
            endChar = iterator-1;
            value = record.substring(startChar,endChar);

            stringMap.put(key,value);

        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("String is bad written: "+e);
        }catch (IllegalArgumentException e){
            System.out.println("There is a string like this already: "+e);
        }
    }
}
