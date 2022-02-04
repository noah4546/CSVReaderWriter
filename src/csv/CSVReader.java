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

    /**
     * Create a new CSV Reader with a file path to the file to read
     * 
     * @param filePath file path of csv document
     */
    public CSVReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Start the reader
     * 
     * @return true if reader started successfully
     */
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

    /**
     * Get the Headers for the file
     * 
     * @return array of headers
     */
    public String[] getHeaders() {
        return this.headers;
    }

    /**
     * Get the next line from the csv file
     * 
     * @return array of next line
     */
    public String[] getNextLine() {
        try {
            String line = reader.readLine();

            return line == null ? null : line.split(",");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Close the reader
     * 
     * @return true if closed successfully
     */
    public boolean closeReader() {
        try {
            reader.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}