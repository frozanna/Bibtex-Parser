package Show;

import Entries.*;
import TypeClass.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that shows every author.
 */
public class ShowAuthors implements VisitShowElement {
    /**
     * Method that prints out all authors from BibTex file entries.
     * @param entries ArrayList of entries
     */
    static public void show(ArrayList<Entry> entries){
        System.out.println("");
        System.out.println("AUTHORS: ");

        Set<Person> authors= new HashSet<Person>();
        for(Entry entry : entries){
            for(Person p : entry.authors){
                authors.add(p);
            }
        }

        for(Person a : authors){
            System.out.println("* "+a.firstname+" "+a.lastname);
        }
    }

    /**
     * Method used in Visitor pattern.
     * @param visitor visitor parametr
     */

    @Override
    public void accept(ShowVisitor visitor) {
        visitor.visit(this);
    }

}
