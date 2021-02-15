package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListIndexedCollectionTest {

    static Collection col = new LinkedListIndexedCollection();

    @BeforeEach
    public void setUp () {
        col.add("Ivo");
        col.add("Ana");
        col.add("Jasna");
    }

    @Test
    public void testCreateElementsGetter() {
        ElementsGetter getter = col.createElementsGetter();

        assertTrue(getter.hasNextElement());
        assertEquals("Ivo", getter.getNextElement());
        assertTrue(getter.hasNextElement());
        assertEquals("Ana", getter.getNextElement());
        assertTrue(getter.hasNextElement());
        assertEquals("Jasna", getter.getNextElement());
        assertFalse(getter.hasNextElement());
        assertThrows(NoSuchElementException.class, () -> getter.getNextElement());
    }

    @Test
    public void testConcurrentModifying() {
        ElementsGetter getter = col.createElementsGetter();
        assertEquals("Ivo", getter.getNextElement());
        assertEquals("Ana", getter.getNextElement());
        col.clear();
        assertThrows(ConcurrentModificationException.class, () -> getter.getNextElement());
    }

    @Test
    public void testHasNextElement() {
        ElementsGetter getter = col.createElementsGetter();
        assertTrue(getter.hasNextElement());
        assertTrue(getter.hasNextElement());
        assertTrue(getter.hasNextElement());
        assertTrue(getter.hasNextElement());
        assertTrue(getter.hasNextElement());
        assertEquals("Ivo", getter.getNextElement());
        assertTrue(getter.hasNextElement());
        assertTrue(getter.hasNextElement());
        assertEquals("Ana", getter.getNextElement());
        assertTrue(getter.hasNextElement());
        assertTrue(getter.hasNextElement());
        assertTrue(getter.hasNextElement());
        assertEquals("Jasna", getter.getNextElement());
        assertFalse(getter.hasNextElement());
        assertFalse(getter.hasNextElement());
    }

    @Test
    public void testGetNextElement() {
        ElementsGetter getter = col.createElementsGetter();
        assertEquals("Ivo", getter.getNextElement());
        assertEquals("Ana", getter.getNextElement());
        assertEquals("Jasna", getter.getNextElement());
        assertThrows(NoSuchElementException.class, () -> getter.getNextElement());
    }

    @Test
    public void testMultipleGetters() {
        ElementsGetter getter1 = col.createElementsGetter();
        ElementsGetter getter2 = col.createElementsGetter();

        assertEquals("Ivo", getter1.getNextElement());
        assertEquals("Ana", getter1.getNextElement());
        assertEquals("Ivo", getter2.getNextElement());
        assertEquals("Jasna", getter1.getNextElement());
        assertEquals("Ana", getter2.getNextElement());

    }

    @Test
    public void testMultipleCollectionsAndMultipleGetters() {
        Collection col1 = new LinkedListIndexedCollection();
        Collection col2 = new LinkedListIndexedCollection();
        col1.add("Ivo");
        col1.add("Ana");
        col1.add("Jasna");
        col2.add("Jasmina");
        col2.add("Štefanija");
        col2.add("Karmela");
        ElementsGetter getter1 = col1.createElementsGetter();
        ElementsGetter getter2 = col1.createElementsGetter();
        ElementsGetter getter3 = col2.createElementsGetter();

        assertEquals("Ivo", getter1.getNextElement());
        assertEquals("Ana", getter1.getNextElement());
        assertEquals("Ivo", getter2.getNextElement());
        assertEquals("Jasmina", getter3.getNextElement());
        assertEquals("Štefanija", getter3.getNextElement());
    }

    @Test
    public void testProcessRemaining() {
        ElementsGetter getter = col.createElementsGetter();
        getter.getNextElement();
        getter.processRemaining(System.out::println);
    }
}