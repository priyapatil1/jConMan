package test;

import conMan.ConMan;
import conMan.ContactList;
import conMan.FileType;
import conMan.contactfields.Contact;
import conMan.inputoutput.ConsoleIO;
import conMan.inputoutput.InputOutput;
import conMan.options.Option;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConManTest {
    private ConsoleIO consoleIO;
    private ContactList importedContacts;
    private ContactList contactList;
    private FakeFile fakeFile;
    private FakeExit exitOption;
    private ConMan conMan;

    ByteArrayOutputStream recordedOutput = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(recordedOutput);

    @Before
    public void setUp() {
        importedContacts = createdImportedContacts();
        contactList = new ContactList();
        consoleIO = new ConsoleIO(new ByteArrayInputStream(("1\nMaya\nPatil\n123@gmail.com\n1 Cedar Way\n123\n" +
                                                            "1\nSam\nSmith\n234@gmail.com\n2 Cedar Way\n123\n" +
                                                            "2\nN\n4\n5\n").getBytes()), out);
        fakeFile = new FakeFile(consoleIO, contactList, importedContacts);
        exitOption = new FakeExit(consoleIO, contactList, fakeFile);
        conMan = new ConMan(consoleIO, exitOption, fakeFile, contactList);
    }

    @Test
    public void showsInitialGreeting() throws IOException {
        conMan.showGreeting();
        assertEquals("Welcome to ConMan! \n" +
                     "Please choose from the following options: \n", recordedOutput.toString());
    }

    @Test
    public void showsUserOptions() {
        conMan.showOptionTitles();
        assertEquals("1) Create a contact \n" +
                     "2) Read a contact's details \n" +
                     "3) Update a contact's details \n" +
                     "4) Delete a contact \n" +
                     "5) ExitConMan conMan.ConMan \n", recordedOutput.toString());
    }

    @Test
    public void formatsImportedAndCreatedContactsToList() {
        ConMan conMan = new ConMan(consoleIO, exitOption, fakeFile, contactList);
        conMan.menuLoop();
        assertEquals("1) Priya Patil\n" +
                     "2) Sarah Smith\n" +
                     "3) Maya Patil\n" +
                     "4) Sam Smith\n", conMan.listAllNames());
    }

    @Test
    public void canReadDetailsOfFirstContactInListByEntering2() {
        InputOutput consoleIO = new ConsoleIO(new ByteArrayInputStream("2\nN\n1\n5\nY\n".getBytes()), out);
        ConMan conMan = new ConMan(consoleIO, exitOption, fakeFile, contactList);
        conMan.menuLoop();
        assertTrue(recordedOutput.toString().contains("First Name: Priya\n" +
                                                      "Last Name: Patil\n" +
                                                      "Email: 123@gmail.com\n" +
                                                      "Home Address: 2 Cedar Way\n" +
                                                      "Phone Number: 123\n\n\n"));
    }

    @Test
    public void canUpdateTheFirstNameOfACreatedContactByEntering3() {
        InputOutput consoleIO = new ConsoleIO(new ByteArrayInputStream(("1\nMaya\nPatil\n123@gmail.com\n1 Cedar Way\n123\n" +
                                                                        "1\nSam\nSmith\n234@gmail.com\n2 Cedar Way\n123\n" +
                                                                        "3\n4\nBeth\n\n\n\n\n2\nN\n4\n5\n").getBytes()), out);
        ConMan conMan = new ConMan(consoleIO, exitOption, fakeFile, contactList);
        conMan.menuLoop();
        assertTrue(recordedOutput.toString().contains("First Name: Beth\n" +
                                                      "Last Name: Smith\n" +
                                                      "Email: 234@gmail.com\n" +
                                                      "Home Address: 2 Cedar Way\n" +
                                                      "Phone Number: 123\n\n\n"));
    }

    @Test
    public void canDeleteFirstCreatedContactByEntering4() {
        InputOutput consoleIO = new ConsoleIO(new ByteArrayInputStream(("4\n1\nY\n2\nN\n1\n5\n").getBytes()), out);
        ConMan conMan = new ConMan(consoleIO, exitOption, fakeFile, contactList);
        conMan.menuLoop();
        assertTrue(recordedOutput.toString().contains("Deleting Contact..."));
    }

    @Test
    public void ensuresAValidDigitIsEnteredWhenChoosingAnOption() {
        InputOutput consoleIO = new ConsoleIO(new ByteArrayInputStream(("a\nb\n1\n").getBytes()), out);
        ConMan conMan = new ConMan(consoleIO, exitOption, fakeFile, contactList);
        conMan.menuChoice();
        assertTrue(recordedOutput.toString().contains("Please enter a valid number: "));
    }

    @Test
    public void conManLoopsThroughOptionsTillUserChoosesToExit() {
        FileType fakeFile = new FakeFile(consoleIO, contactList, importedContacts);
        Option exitOption = new FakeExit(consoleIO, contactList, fakeFile);
        InputOutput consoleIO = new ConsoleIO(new ByteArrayInputStream(("1\nSarah\nSmith\n234@gmail.com\n1 Cedar Way\n120-123-123\n" +
                                                                        "1\nPriya\nPatil\n123@gmail.com\n2 Cedar Way\n789-123-123\n" +
                                                                        "4\n1\nY\n5\n").getBytes()), out);
        ConMan conMan = new ConMan(consoleIO, exitOption, fakeFile, contactList);
        conMan.menuLoop();
        assertTrue(recordedOutput.toString().contains("Delete a contact \n"));
    }

    @Test
    public void conManDisplaysPromptsToTheUserDuringTheMenuLoop() throws IOException {
        ConMan conMan = new ConMan(consoleIO, exitOption, fakeFile, contactList);
        conMan.menuLoop();
        assertTrue(recordedOutput.toString().contains("1) Create a contact \n" +
                                                      "2) Read a contact's details \n" +
                                                      "3) Update a contact's details \n" +
                                                      "4) Delete a contact \n" +
                                                      "5) ExitConMan conMan.ConMan \n"));
    }

    private ContactList createdImportedContacts() {
        ContactList newList = new ContactList();
        Contact priya = new Contact("Priya", "Patil", "123@gmail.com", "2 Cedar Way", "123", consoleIO);
        Contact sarah = new Contact("Sarah", "Smith", "234@gmail.com", "3 Cedar Way", "123", consoleIO);
        priya.setExisting();
        sarah.setExisting();
        newList.addContact(priya);
        newList.addContact(sarah);
        return newList;
    }
}
