package csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    private BufferedReader reader;
    private File file;

    private String filePath;
    private String[] headers;

    public CSVReader(String filePath) {
        this.filePath = filePath;
    }

    public boolean startReader() {
        this.file = new File(filePath);

        if (!this.file.exists()) return false;
        
        try {
            FileReader fileReader = new FileReader(filePath);
            reader = new BufferedReader(fileReader);

            headers = reader.readLine().split(",");

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[] getHeaders() {
        return this.headers;
    }

    public String[] getNextLine() {
        try {
            String line = reader.readLine();

            return line == null ? null : line.split(",");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}