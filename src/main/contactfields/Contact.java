package main.contactfields;

import main.inputoutput.InputOutput;

import java.util.Arrays;
import java.util.List;

public class Contact {
    private final List<Field> fields;
    private final InputOutput inputOutput;
    private FirstName firstName;
    private LastName lastName;
    private Email email;
    private HomeAddress homeAddress;

    public Contact(InputOutput inputOutput) {
        this.inputOutput = inputOutput;
        this.firstName = new FirstName(inputOutput);
        this.lastName = new LastName(inputOutput);
        this.email = new Email(inputOutput);
        this.homeAddress = new HomeAddress(inputOutput);
        this.fields = Arrays.asList(firstName, lastName, email, homeAddress);
    }

    public void setFields() {
        for (Field field : fields) {
            inputOutput.showOutput(field.showFieldName());
            field.set();
        }
    }

    public String showFields() {
        String list = "";
        for (Field field : fields) {
            list +=  field.showFieldName() + field.show() + "\n";
        }
        return list;
    }

    public String getName() {
        return firstName.show() + " " + lastName.show();
    }

    public String getEmail() {
        return email.show();
    }

}