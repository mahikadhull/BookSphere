
package dao;

import exception.DataAccessException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileStorageDAO {

    private static final String DATA_FOLDER = "database";

    // Save application data
    public void saveData(String fileName, Serializable data)
            throws DataAccessException {

        try {
            File folder = new File(DATA_FOLDER);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(folder, fileName);

            ObjectOutputStream output =
                    new ObjectOutputStream(new FileOutputStream(file));

            output.writeObject(data);
            output.close();

        } catch (Exception e) {
            throw new DataAccessException(
                    "Error while saving data: " + e.getMessage(), e);
        }
    }

    // Load application data
    public <T> T loadData(String fileName, Class<T> type)
            throws DataAccessException {

        File file = new File(DATA_FOLDER, fileName);

        if (!file.exists()) {
            throw new DataAccessException("Data file not found: " + fileName);
        }

        try {
            ObjectInputStream input =
                    new ObjectInputStream(new FileInputStream(file));

            Object data = input.readObject();
            input.close();

            return type.cast(data);

        } catch (Exception e) {
            throw new DataAccessException(
                    "Error while loading data: " + e.getMessage(), e);
        }
    }

    // Check whether a data file exists
    public boolean fileExists(String fileName) {
        File file = new File(DATA_FOLDER, fileName);
        return file.exists();
    }
}
