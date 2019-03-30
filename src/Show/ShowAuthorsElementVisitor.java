package Show;

import java.util.ArrayList;

/**
 * ShowAuthor visitor.
 */
public class ShowAuthorsElementVisitor implements ShowVisitor {

    ArrayList entries;
    public ShowAuthorsElementVisitor(ArrayList entries){
        this.entries = entries;
    }


    @Override
    public void visit(ShowAll all) {    }

    @Override
    public void visit(ShowAuthors authors) {
        ShowAuthors.show(this.entries);
    }

    @Override
    public void visit(ShowAuthorEntries author) {
    }

    @Override
    public void visit(ShowCategory category) {
    }
}
