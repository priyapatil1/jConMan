package conMan;

import java.io.File;

public interface FileType {
    void importContacts();
    void saveContacts();
    File getFile();
}