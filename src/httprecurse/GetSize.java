package httprecurse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.math.*;
import java.io.PrintWriter;
import java.io.*;
import java.util.ArrayList;

public class GetSize {

    public static void run(ArrayList<String> args) throws Exception {
        double current = 0;
        double currentmb = 0;
        double totalmb = 0;
        ArrayList<String> fSizes = new ArrayList<String>();

        for (int i = 1; i <= args.size(); i++) {
            //double progress = i / v;
            //System.out.println("progress: " + progress + "    " + i + " out of " + v);
            current = getSize(args.get(i - 1));
            currentmb = current / 1000000;
            totalmb = currentmb + totalmb;
            //System.out.println("Total Megabytes: " + totalmb + "    Current Filesize: " + currentmb);
            fSizes.add(String.valueOf(currentmb));

        }
        System.out.println("Total MB: " + totalmb);
        System.out.println("Total GB: " + (totalmb/new Double(1000)));
        writeToFile(fSizes);

    }

    private static int getSize(String con) throws Exception {
        URL cons = new URL(con);
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) cons.openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            return -1;
        } finally {
            conn.disconnect();
        }
    }

    public static void writeToFile(ArrayList<String> input) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileOutputStream("L:\\file.txt"));
            for (int i = 0; i < input.size(); i++) {
                printWriter.println(input.get(i));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
}