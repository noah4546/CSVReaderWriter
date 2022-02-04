package csv;

import java.nio.channels.Pipe;

public class PathReader {

    private CSVReader csvReader;

    private long startTime;
    private long elapsedTime;
    private boolean running;

    private TimeValuePair<Long, double[]> current;
    private TimeValuePair<Long, double[]> next;
    
    public PathReader(String filePath) {
        this.csvReader = new CSVReader(filePath);
        this.csvReader.startReader();
    }

    public boolean headersMatch(String[] headers) {
        int offset = 0;

        for (int i = 0; i < headers.length; i++) {
            if (i == 0 && !headers[0].equals(csvReader.getHeaders()[0])) offset = 1;
            if (!headers[i].equals(csvReader.getHeaders()[i+offset])) return false;
        }

        return true;
    }

    public double[] getData() {
        if (!this.running) initData();

        elapsedTime = System.currentTimeMillis() - startTime;

        if (next.getTime() >= elapsedTime) {
            current = next;
            next = convertToData(csvReader.getNextLine());
            if (next == null) return null;
        }

        return current.getValue();
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    private void initData() {
        this.startTime = System.currentTimeMillis();
        this.running = true;

        this.current = convertToData(csvReader.getNextLine());
        this.next = convertToData(csvReader.getNextLine());
    }

    private TimeValuePair<Long, double[]> convertToData(String[] row) {
        long time;

        try {
            time = Long.parseLong(row[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        double[] output = new double[row.length-1];

        for (int i = 1; i < row.length; i++) {
            String item = row[i];
            try {
                double value = Double.parseDouble(item);
                output[i-1] = value;
            } catch (Exception e) {
                System.err.println("Reader couldn't parse the double");
                e.printStackTrace();
                return null;
            }
        }
        
        return new TimeValuePair<Long,double[]>(time, output);
    }


    private class TimeValuePair<T1,T2> {
        private T1 time;
        private T2 value;

        public TimeValuePair(T1 time, T2 value) {
            this.time = time;
            this.value = value;
        }

        public T1 getTime() { return this.time; }
        public T2 getValue() { return this.value; }
    }
}
