package TypeClass;

/**
 * Class that contains information about X or Y fields and X and/or Y fields.
 */

public class OrAndReqFields {
    /**
     * First field.
     */
    public String field1;
    /**
     * Second field.
     */
    public String field2;
    /**
     * true - Or, false - AndOr
     */
    public boolean type;
    public OrAndReqFields(String field1, String field2,boolean type){
        this.field1 = field1;
        this.field2 = field2;
        this.type = type;

    }
}
