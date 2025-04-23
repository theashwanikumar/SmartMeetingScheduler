package service;

import model.Meeting;
import java.time.LocalDateTime;
import java.util.List;

public class SchedulerService {

    public boolean isAvailable(List<Meeting> meetings, LocalDateTime start, LocalDateTime end) {
        for (Meeting m : meetings) {
            if (start.isBefore(m.getEndTime()) && end.isAfter(m.getStartTime())) {
                return false;
            }
        }
        return true;
    }

    public void addMeeting(List<Meeting> meetings, Meeting newMeeting) {
        if (isAvailable(meetings, newMeeting.getStartTime(), newMeeting.getEndTime())) {
            meetings.add(newMeeting);
            System.out.println("✅ Meeting scheduled: " + newMeeting.getTitle());
        } else {
            System.out.println("❌ Time slot is already booked!");
        }
    }

    public void listMeetings(List<Meeting> meetings) {
        if (meetings.isEmpty()) {
            System.out.println("📭 No meetings scheduled.");
        } else {
            for (Meeting m : meetings) {
                System.out.println(m);
            }
        }
    }
}
