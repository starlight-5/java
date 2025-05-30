import java.time.LocalDateTime;

public class ScheduleEvent {
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ScheduleEvent(String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }

    @Override
    public String toString() {
        return "[" + startTime.toLocalTime() + " - " + endTime.toLocalTime() + "] " + title;
    }
}
