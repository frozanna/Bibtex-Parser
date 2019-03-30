package Show;

import Entries.*;

import java.util.ArrayList;

/**
 * ShowAuthorEntry visitor
 */
public class ShowAuthorEntryElementVisitor implements ShowVisitor {

    ArrayList<Entry> entries;
    String author;
    public ShowAuthorEntryElementVisitor(ArrayList<Entry> entries,String author){
        this.entries = entries;
        this.author = author;
    }

    @Override
    public void visit(ShowAll all) {

    }

    @Override
    public void visit(ShowAuthors authors) {

    }

    @Override
    public void visit(ShowAuthorEntries author) {
        ShowAuthorEntries.show(this.entries,this.author);
    }

    @Override
    public void visit(ShowCategory category) {

    }
}
