import java.util.List;

import csv.CSVReader;
import csv.PathReader;

public class App {

    public static void main(String[] args) throws Exception {
    
        PathReader pathReader = new PathReader("/Files/test.csv");

        String[] head = {"motor"};

        boolean match = pathReader.headersMatch(head);


        while (true) {
            double[] values = pathReader.getData();

            if (values == null) break;

            long time = pathReader.getElapsedTime();

            System.out.println(String.format("%s || %s", String.valueOf(time), doubleArrayToString(values)));
        }


        /*for (String item : reader.getHeaders()) {
            System.out.println(item);
        }*/

    }

    public static String doubleArrayToString(double[] input) {
        String output = "[";

        for (int i = 0; i < input.length; i++) {
            if (i == input.length - 1) {
                output += String.format("%s]", String.valueOf(input[i]));
            } else {
                output += String.format("%s, ", String.valueOf(input[i]));
            }
        }

        return output;
    }
}
