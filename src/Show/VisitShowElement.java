package Show;

import Show.ShowVisitor;

/**
 * Interface that declare accept method to Visitor pattern.
 */
public interface VisitShowElement {
    /**
     * Declaration of method from Visitor Pattern.
     * @param visitor ShowVisitor parametr
     */
    public void accept(ShowVisitor visitor);
}
