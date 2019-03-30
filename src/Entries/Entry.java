package Entries;

import java.util.ArrayList;
import java.util.Map;

/**
 * Class that is superclass to all types of entries. Contains initialization of required maps, ArrayLists and key
 * In requiredFields (value - Integer) means:
 *      0 - there is no such field in object yet
 *      1 - there is such field
 *      2 - this field may be, but it isn't necessarily
 * In optionalFields (value - Integer) means:
 *      0 - there is no such field in object yet
 *      1 - there is such field
 */

public class Entry {

    /**
     * Map that contains fields from record.
     */
    public Map<String, String> entryMap;
    /**
     * ArrayList of authors.
     */
    public ArrayList<TypeClass.Person> authors;
    /**
     * Citation key.
     */
    public String key;
    /**
     * Required fields - specified for each type of record.
     */
    public Map<String, Integer> requiredFields;
    /**
     * Optional fields - specified for each type of record.
     */
    public Map<String, Integer> optionalFields;
    /**
     * ArrayList that contains information about 'and/or', 'or' required fields.
     */
    public ArrayList<TypeClass.OrAndReqFields> orAndReqFields;
    /**
     * ArrayLisy that contains information about 'or' optional fields.
     */
    public ArrayList<TypeClass.OrAndReqFields> orOpFields;

}

