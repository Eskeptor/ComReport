package javabasic;

import java.io.*;

public class ScoreDAO {
    static String filenName = "D:\\Project\\ComReport\\score.dat";
    public static void save(final Scores _scores) throws Exception {

        FileOutputStream outputStream = new FileOutputStream(filenName);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(_scores);
        objectOutputStream.flush();
        objectOutputStream.close();
        outputStream.close();
    }

    public static Scores load() throws Exception {
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(filenName);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Scores scores = (Scores)objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
            return scores;
        } catch (FileNotFoundException e) {
            File scoreFile = new File(filenName);
            scoreFile.createNewFile();
        }
        return null;
    }
}