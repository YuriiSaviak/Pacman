package util;

import model.Record;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class LeaderboardProcessor {
    private static final String path = "resources/leaderboard.ser";

    public static void writeRecords(List<Record> recordList) {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(path))) {
            stream.writeObject(recordList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Record> readRecords() {
        File fileRecords = new File(path);

        if (fileRecords.length() > 0)
            try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(path))) {
                return (List<Record>) stream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        return new ArrayList<>();
    }
}
