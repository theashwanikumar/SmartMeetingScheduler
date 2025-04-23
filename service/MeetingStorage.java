package service;

import model.Meeting;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MeetingStorage {
    private static final String FOLDER_NAME = ".smartmeetings";
    private static final String FILE_NAME = "meetings.ser";

    public static List<Meeting> loadMeetings() {
        List<Meeting> meetings = new ArrayList<>();
        try {
            String home = System.getProperty("user.home");
            File appFolder = new File(home + File.separator + FOLDER_NAME);
            if (!appFolder.exists()) appFolder.mkdirs();

            File saveFile = new File(appFolder, FILE_NAME);
            if (saveFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
                    meetings = (List<Meeting>) ois.readObject();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return meetings;
    }

    public static void saveMeetings(List<Meeting> meetings) {
        try {
            String home = System.getProperty("user.home");
            File appFolder = new File(home + File.separator + FOLDER_NAME);
            if (!appFolder.exists()) appFolder.mkdirs();

            File saveFile = new File(appFolder, FILE_NAME);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
                oos.writeObject(meetings);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
