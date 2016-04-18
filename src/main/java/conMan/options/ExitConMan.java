package conMan.options;

import conMan.inputoutput.InputOutput;
import fileTypes.FileType;

public class ExitConMan implements Option, Exit {
    private final FileType file;
    private InputOutput console;

    public ExitConMan(InputOutput console, FileType file) {
        this.console = console;
        this.file = file;
    }

    @Override
    public void show() {
        console.showOutput("ExitConMan ConMan \n");
    }

    @Override
    public void perform() {
        file.saveContacts();
        System.exit(0);
    }
}
