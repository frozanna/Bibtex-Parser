package Show;

import Entries.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class that shows all entries from given category.
 */
public class ShowCategory implements VisitShowElement {
    /**
     * Method that prints out all entries from chosen category from BibTex file.
     * @param entries  ArrayList of entries
     * @param category category that we searching for
     */
    static public void show(ArrayList<Entry> entries, String category){
        category = category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase();
        if (!(category.toUpperCase().equals("ARTICLE") || category.toUpperCase().equals("BOOK") || category.toUpperCase().equals("BOOKLET") || category.toUpperCase().equals("INBOOK")
                || category.toUpperCase().equals("INCOLLECTION") || category.toUpperCase().equals("INPROCEEDINGS") || category.toUpperCase().equals("MANUAL")
                || category.toUpperCase().equals("MASTERSTHESIS") || category.toUpperCase().equals("MISC") || category.toUpperCase().equals("PHDTHESIS")
                || category.toUpperCase().equals("PROCEEDINGS") || category.toUpperCase().equals("TECHREPORT") || category.toUpperCase().equals("UNPUBLISHED")
                || category.toUpperCase().equals("CONFERENCE"))){
            System.out.println(category+" - wrong entry type");
            return;
        }
        boolean thereIsENtry = false;
        Set<Entry> catEntries = new HashSet<Entry>();
        for (Entry entry : entries) {
            if (entry.getClass().getSimpleName().equals(category)) {
                catEntries.add(entry);
                thereIsENtry = true;
            }

        }
        System.out.println(category+": ");
        if (thereIsENtry) {
            for (Entry entry : catEntries) {
                if (entry == null) throw new NullPointerException();
                if (entry.key != null) System.out.println(entry.key);
                else System.out.println(entry.getClass().getSimpleName());
                System.out.println("|-----------------------------------------------------------------------------------------------|");
                for (Map.Entry<String, String> field : entry.entryMap.entrySet()) {
                    System.out.print("| ");
                    if (field.getKey().equals("author")) {
                        showAuthors(entry);
                    } else {
                        System.out.format("%-15s", field.getKey());
                        System.out.format("%-78s", " | " + field.getValue());
                        System.out.println(" |");
                    }
                    System.out.println("|-----------------------------------------------------------------------------------------------|");
                }
                System.out.println("");
            }
        }
        else {
            System.out.println("There is no entry from this category.");
            System.out.println("");
        }
    }

    /**
     * Private method to show authors separately.
     * @param entry entry from which we get authors
     */
    private static void showAuthors(Entry entry) {
        System.out.format("%-15s","authors");
        System.out.print(" | ");
        System.out.format("%-75s","* "+entry.authors.get(0).firstname+" "+entry.authors.get(0).lastname);
        System.out.print(" |");
        System.out.println("");
        for (int i = 1; i < entry.authors.size(); i++){
            System.out.format("%-17s","| ");
            System.out.print(" | ");
            System.out.format("%-75s","* "+entry.authors.get(i).firstname+" "+entry.authors.get(i).lastname);
            System.out.print(" |");
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
