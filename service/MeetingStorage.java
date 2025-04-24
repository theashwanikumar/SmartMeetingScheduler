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
            if (!appFolder.exists())
                appFolder.mkdirs();

            File saveFile = new File(appFolder, FILE_NAME);

            // Delete the old file if it exists
            if (saveFile.exists()) {
                saveFile.delete(); // Delete the old serialized file
                System.out.println("Old meetings file deleted.");
            }
            
            //load new meetings
            if (saveFile.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFile))) {
                    Object obj = ois.readObject(); // Read object as raw Object

                    // Check if the object is of type List<Meeting>
                    if (obj instanceof List<?>) {
                        List<?> tempList = (List<?>) obj;
                        // Check if the list contains only Meeting objects
                        if (!tempList.isEmpty() && tempList.get(0) instanceof Meeting) {
                            @SuppressWarnings("unchecked") // Safe unchecked cast after verification
                            List<Meeting> castedMeetings = (List<Meeting>) tempList;
                            meetings = castedMeetings; // Assign to meetings
                        } else {
                            System.out.println("Error: The saved data does not contain Meeting objects.");
                        }
                    } else {
                        System.out.println("Error: The saved data is not of type List<Meeting>.");
                    }
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
            if (!appFolder.exists())
                appFolder.mkdirs();

            File saveFile = new File(appFolder, FILE_NAME);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile))) {
                oos.writeObject(meetings);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
