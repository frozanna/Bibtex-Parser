package TypeClass;

/**
 * Class that contains information about each person (last name and first name).
 */
public class Person {
    /**
     * First name of person.
     */
    public String firstname;
    /**
     * Last name of person.
     */
    public String lastname;

    public Person (String firstname, String lastname){
        this.lastname = lastname;
        this.firstname = firstname;
    }

    /**
     * Overwritten equals method to compare Person
     * @param obj   object that we compare to Person
     * @return      true if object is equal to Person, otherwise false
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Person)) return false;
        Person p = (Person) obj;
        if(this.lastname.equals(p.lastname) && this.firstname.equals(p.firstname)) return true;
        return false;
    }

    /**
     * Overwritten hashCode method to hash Person.
     * @return  hash code from person
     */
    @Override
    public int hashCode(){
        int hash = 0;
        int hashFName = 0;
        int hashLName =0;
        for(char c : this.firstname.toCharArray()){
            hashFName+=c;
        }
        hashFName*=7;
        for(char c : this.lastname.toCharArray()){
            hashLName+=c;
        }
        hashLName*=3;
        hash = (hashFName+hashLName)%22273;
        return hash;
    }

}
