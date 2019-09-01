package helper;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Helper {
    public static String currentDir = System.getProperty("user.dir") + "\\chromedriver.exe";
    public static Logger logger = Logger.getLogger("MyLog");
    public static FileHandler fh;
    private static String directoryName = "logs";
    private static String fileName = "AllLogs.log";

    public static void addToAllLogs() throws IOException {
        if (new File(System.getProperty("user.dir") + "\\logs\\LastLog.log".replace('\\', '/')).isFile()) {
            File file = new File(directoryName + "/" + fileName);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\logs\\LastLog.log".replace('\\', '/')), "UTF-8"));
                String strRead = null;
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);

                while( (strRead = br.readLine()) != null) {
                    bw.write(strRead);
                    bw.newLine();
                }
                bw.write( " \n");
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        } else {

            System.out.println("\033[31m ERROR: File LastLog.log not found");
        }
    }



    public static void checkLog() throws IOException {

        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler(System.getProperty("user.dir") + "\\logs\\LastLog.log".replace('\\', '/'));

            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
           // logger.info("My first loging");

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
