package service;

import model.Meeting;

import java.time.LocalDateTime;
import java.util.List;

public class SchedulerService {

    public void addMeeting(List<Meeting> meetings, Meeting newMeeting) {
        if (hasClash(meetings, newMeeting)) {
            System.out.println("❌ Time clash! You already have a meeting scheduled during this time.");
            return;
        }
        meetings.add(newMeeting);
        System.out.println("✅ Meeting scheduled successfully!");
    }

    public void listMeetings(List<Meeting> meetings) {
        if (meetings.isEmpty()) {
            System.out.println("📭 No meetings scheduled yet.");
            return;
        }
        for (Meeting m : meetings) {
            System.out.println(m);
        }
    }

    private boolean hasClash(List<Meeting> meetings, Meeting newMeeting) {
        LocalDateTime newStart = newMeeting.getStartTime();
        LocalDateTime newEnd = newMeeting.getEndTime();

        for (Meeting existing : meetings) {
            LocalDateTime existingStart = existing.getStartTime();
            LocalDateTime existingEnd = existing.getEndTime();

            // Check if the new meeting overlaps with an existing one
            if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
                return true;
            }
        }
        return false;
    }
}
