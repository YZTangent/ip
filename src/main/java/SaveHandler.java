import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class SaveHandler {

    private final String SAVE_FILE_DIR = System.getProperty("user.dir") + "/save.txt";

    private final File SAVE_FILE = new File(SAVE_FILE_DIR);

    public void init() throws DaveException {
        try {
            SAVE_FILE.createNewFile();
        } catch (IOException e) {
            throw new DaveInitFileException();
        }
        if (!SAVE_FILE.canRead() || !SAVE_FILE.canWrite()) {
            throw new DaveException("Oh no! You do not have permission to read and/or write to your saved files :(");
        }
    }

    // Adapted from https://stackoverflow.com/questions/16111496/java-how-can-i-write-my-arraylist-to-a-file-and-read-load-that-file-to-the
    public void save(TaskList tasks) throws DaveException {
        try{
            FileOutputStream file_output = new FileOutputStream(SAVE_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(file_output);
            oos.writeObject(tasks);
            file_output.close();
        } catch (IOException e) {
//            throw new DaveException("Oh no! An error has been encountered while saving!");
            throw new DaveException("save" + e.toString());
        }
    }

    // Adapted from https://stackoverflow.com/questions/16111496/java-how-can-i-write-my-arraylist-to-a-file-and-read-load-that-file-to-the
    public TaskList load() throws DaveException {
        if (SAVE_FILE.length() != 0) {
            try{
                FileInputStream fin= new FileInputStream (SAVE_FILE);
                ObjectInputStream ois = new ObjectInputStream(fin);
                TaskList tasks = (TaskList) ois.readObject();
                fin.close();
                return tasks;
            } catch (IOException | ClassNotFoundException e) {
//            throw new DaveException("Oh no! An error has been encountered while loading!");
                throw new DaveException("load" + e.toString());
            }
        } else {
            return new TaskList();
        }
    }
}
