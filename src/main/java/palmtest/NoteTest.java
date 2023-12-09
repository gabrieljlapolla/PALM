package palmtest;

import org.junit.Test;
import palm.Note;

import static org.junit.Assert.assertEquals;

public class NoteTest {

    @Test
    public void testCreation() {
        Note testNote = new Note("name", "notes");
        assertEquals("name", testNote.getName());
        assertEquals("notes", testNote.getNotes());
    }

}
