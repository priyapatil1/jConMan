package main.options;

import main.contactfields.Contact;
import main.inputoutput.InputOutput;

import java.util.List;

public class Create implements Option {
    private final List<Contact> allContacts;
    private final InputOutput inputOutput;

    public Create(List<Contact> allContacts, InputOutput inputOutput) {
        this.allContacts = allContacts;
        this.inputOutput = inputOutput;
    }

    @Override
    public void show() {
        inputOutput.showOutput("Create a contact \n");
    }

    @Override
    public void perform() {
        Contact newContact = new Contact(inputOutput);
        newContact.setFields();
        allContacts.add(newContact);
    }
}