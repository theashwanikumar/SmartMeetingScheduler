import model.Meeting;
import model.User;
import service.SchedulerService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SchedulerService scheduler = new SchedulerService();
        List<Meeting> meetings = new ArrayList<>();

        User admin = new User("admin", "admin123", true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (true) {
            System.out.println("\n====== Smart Meeting Scheduler ======");
            System.out.println("1. Schedule a Meeting");
            System.out.println("2. View All Meetings");
            System.out.println("3. Exit");
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

                } catch (Exception e) {
                    System.out.println("❌ Invalid date/time format. Please try again.");
                }

            } else if (choice == 2) {
                scheduler.listMeetings(meetings);
            } else if (choice == 3) {
                System.out.println("👋 Exiting... Have a productive day!");
                break;
            } else {
                System.out.println("❗ Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
