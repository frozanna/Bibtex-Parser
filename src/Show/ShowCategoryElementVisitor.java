package Show;

import Entries.*;

import java.util.ArrayList;

/**
 * ShowCategory visitor.
 */
public class ShowCategoryElementVisitor implements ShowVisitor{
    ArrayList<Entry> entries;
    String category;

    public ShowCategoryElementVisitor(ArrayList<Entry> entries,String category){
        this.entries = entries;
        this.category = category;
    }

    @Override
    public void visit(ShowAll all) {

    }

    @Override
    public void visit(ShowAuthors authors) {

    }

    @Override
    public void visit(ShowAuthorEntries author) {

    }

    @Override
    public void visit(ShowCategory category) {
        ShowCategory.show(this.entries,this.category);
    }
}
