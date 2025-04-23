import model.Meeting;
import model.User;
import service.MeetingStorage;
import service.SchedulerService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SchedulerService scheduler = new SchedulerService();
        // List<Meeting> meetings = new ArrayList<>();
        List<Meeting> meetings = MeetingStorage.loadMeetings();

        User admin = new User("admin", "admin123", true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (true) {
            System.out.println("\n====== Smart Meeting Scheduler ======");
            System.out.println("1. Schedule a Meeting");
            System.out.println("2. Remove a Meeting");
            System.out.println("3. View All Meetings");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume leftover newline

            if (choice == 1) {
                System.out.print("Enter Meeting Title: ");
                String title = scanner.nextLine().trim();
                if (title.isEmpty()) {
                    System.out.println("⚠️ Title cannot be empty.");
                    continue;
                }

                LocalDateTime startTime, endTime;
                try {
                    System.out.print("Enter Start Time (yyyy-MM-dd HH:mm): ");
                    String start = scanner.nextLine();
                    startTime = LocalDateTime.parse(start, formatter);

                    System.out.print("Enter End Time (yyyy-MM-dd HH:mm): ");
                    String end = scanner.nextLine();
                    endTime = LocalDateTime.parse(end, formatter);

                    if (endTime.isBefore(startTime) || endTime.equals(startTime)) {
                        System.out.println("⚠️ End time must be after start time.");
                        continue;
                    }

                    Meeting meeting = new Meeting(title, startTime, endTime, admin);
                    scheduler.addMeeting(meetings, meeting);
                    MeetingStorage.saveMeetings(meetings);

                } catch (Exception e) {
                    System.out.println("❌ Invalid date/time format. Please try again.");
                }

            } else if (choice == 2) {
                System.out.println("Enter the number of the meeting to remove:");
                for (int i = 0; i < meetings.size(); i++) {
                    System.out.println((i + 1) + ". " + meetings.get(i));
                }
                int index = scanner.nextInt();
                scanner.nextLine(); // Clear newline

                if (index >= 1 && index <= meetings.size()) {
                    meetings.remove(index - 1);
                    MeetingStorage.saveMeetings(meetings); // Save the updated list
                    System.out.println("Meeting removed successfully.");
                } else {
                    System.out.println("Invalid meeting number.");
                }
                break;

            } else if (choice == 3) {
                scheduler.listMeetings(meetings);
            } else if (choice == 4) {
                System.out.println("👋 Exiting... Have a productive day!");
                break;
            } else {
                System.out.println("❗ Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
