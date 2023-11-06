package PALM;

import PALM.Note;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class NoteTest {

    @Test
    public void testCreation() {
        Note testNote = new Note("name", "notes");
        assertTrue(testNote.getName().equals("name"));
        assertTrue(testNote.getNotes().equals("notes"));
    }

}
