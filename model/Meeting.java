package model;

import java.time.LocalDateTime;
import java.io.Serializable;

public class Meeting implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private User organizer;

    public Meeting(String title, LocalDateTime startTime, LocalDateTime endTime, User organizer) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.organizer = organizer;
    }

    public String getTitle() { return title; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public User getOrganizer() { return organizer; }

    @Override
    public String toString() {
        return "📅 " + title + " | " + startTime + " → " + endTime + " | Organizer: " + organizer.getUsername();
    }
}
