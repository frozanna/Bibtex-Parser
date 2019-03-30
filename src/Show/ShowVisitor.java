package Show;

/**
 * Main visitor in Visitor pattern.
 */
public interface ShowVisitor {
    /**
     * Declaration of method used in Visitor pattern.
     * @param all parametr for Visitor pattern
     */
    public void visit(ShowAll all);
    /**
     * Declaration of method used in Visitor pattern.
     * @param authors parametr for Visitor pattern
     */
    public void visit(ShowAuthors authors);
    /**
     * Declaration of method used in Visitor pattern.
     * @param author parametr for Visitor pattern
     */
    public void visit(ShowAuthorEntries author);
    /**
     * Declaration of method used in Visitor pattern.
     * @param category parametr for Visitor pattern
     */
    public void visit(ShowCategory category);
}
