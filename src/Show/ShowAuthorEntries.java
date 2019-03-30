package Show;

import Entries.*;
import TypeClass.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class that shows all entries from given author.
 */
public class ShowAuthorEntries implements VisitShowElement {
    /**
     * Method that shows entries from given author.
     * @param entries ArrayList of entries from BibTex
     * @param author Author that we searching for
     */
    static public void show(ArrayList<Entry> entries, String author) {
        author = author.substring(0, 1).toUpperCase() + author.substring(1).toLowerCase();
        System.out.println(author + ": ");
        boolean thereIsENtry = false;
        Set<Entry> authorsEntries = new HashSet<Entry>();
        for (Entry entry : entries) {
            if (!(entry.authors == null)) {
                for (Person a : entry.authors) {
                    if (a.lastname.equals(author)) {
                        authorsEntries.add(entry);
                        thereIsENtry = true;
                    }
                }
            }

        }
        if (thereIsENtry) {
            for (Entry entry : authorsEntries) {
                if(entry.key != null) System.out.println(entry.getClass().getSimpleName()+" ("+entry.key+")");
                System.out.println("|-----------------------------------------------------------------------------------------------|");
                for (Map.Entry<String, String> field : entry.entryMap.entrySet()) {
                    if (field.getKey().equals("author")) {
                        continue;
                    } else {
                        System.out.print("| ");
                        System.out.format("%-15s", field.getKey());
                        System.out.format("%-78s", " | " + field.getValue());
                        System.out.println(" |");
                    }
                    System.out.println("|-----------------------------------------------------------------------------------------------|");
                }
                System.out.println("");
            }
        }
        else{
            System.out.println("This author has no records in this file.");
            System.out.println("");
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
