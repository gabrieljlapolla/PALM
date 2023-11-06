package PALMTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import PALM.Note;

class NoteTest {

	@Test
	void testCreation() {
		Note testNote = new Note("name", "notes");
		assertTrue(testNote.getName().equals("name"));
		assertTrue(testNote.getNotes().equals("notes"));
	}

}
