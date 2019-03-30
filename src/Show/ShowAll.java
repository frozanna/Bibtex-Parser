package Show;

import Entries.Entry;

import java.util.ArrayList;
import java.util.Map;

/**
 * Class that shows all entries.
 */
public class ShowAll implements VisitShowElement {
    /**
     * Method that prints out all BibTex file entries.
     * @param entries   ArrayList of entries
     */
    public static void show(ArrayList<Entry> entries){
        try{
            for(Entry entry : entries){
                if(entry == null) throw new NullPointerException();
                if(entry.key != null) System.out.println(entry.getClass().getSimpleName()+" ("+entry.key+")");
                else System.out.println(entry.getClass().getName());
                System.out.println("|-----------------------------------------------------------------------------------------------|");
                for (Map.Entry<String, String> field : entry.entryMap.entrySet()) {
                    System.out.print("| ");
                    if (field.getKey().equals("author")) {
                        showAuthors(entry);
                    } else {
                        System.out.format("%-15s",field.getKey());
                        System.out.format("%-78s"," | " + field.getValue());
                        System.out.println(" |");
                    }
                    System.out.println("|-----------------------------------------------------------------------------------------------|");
                }
                System.out.println("");
            }

        }catch(NullPointerException e){
            System.out.println("Problem with: " + e);
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
