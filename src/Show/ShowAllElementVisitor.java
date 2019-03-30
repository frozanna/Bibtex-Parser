package Show;

import Entries.*;

import java.util.ArrayList;

/**
 * ShowAll visitor.
 */
public class ShowAllElementVisitor implements ShowVisitor{

    ArrayList<Entry> entries;
    public ShowAllElementVisitor(ArrayList<Entry> entries){
        this.entries = entries;
    }
    @Override
    public void visit(ShowAll all) {
        ShowAll.show(this.entries);
    }

    @Override
    public void visit(ShowAuthors all) {
    }

    @Override
    public void visit(ShowAuthorEntries author) {

    }

    @Override
    public void visit(ShowCategory category) {

    }
}
