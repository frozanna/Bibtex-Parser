import org.junit.Before;
import org.junit.jupiter.api.Test;
import TypeClass.Person;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test Person class methods.
 */
class PersonTest {

    @Test
    void equals() {
        Person p1 = new Person("test","test");
        Person p2 = new Person("test","test");
        assertTrue(p1.equals(p1));
        assertFalse(p1.equals("TestString"));
        assertTrue(p1.equals(p2));
        p2.lastname = "test2";
        assertFalse(p1.equals(p2));
    }

    @Test
    void hashCodeTest() {
        Person p1 = new Person("test","test");
        Person p2 = new Person("test","test");
        assertTrue(p1.hashCode() == p1.hashCode());
        assertTrue(p1.hashCode() == p2.hashCode());
        p2.lastname = "test2";
        assertFalse(p1.hashCode() == p2.hashCode());
    }
}