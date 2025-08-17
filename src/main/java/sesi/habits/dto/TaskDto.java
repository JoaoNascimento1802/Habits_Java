package sesi.habits.dto;

import sesi.habits.habits_enum.TaskStatus;
import sesi.habits.model.Task;

import java.time.LocalDate;

public class TaskDto {
    private Long id;
    private LocalDate taskDate;
    private TaskStatus status;
    private HabitDto habit;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.taskDate = task.getTaskDate();
        this.status = task.getStatus();
        if (task.getHabit() != null) {
            this.habit = new HabitDto(task.getHabit().getId(), task.getHabit().getTitle(), task.getHabit().getDescription());
        }
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getTaskDate() { return taskDate; }
    public void setTaskDate(LocalDate taskDate) { this.taskDate = taskDate; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public HabitDto getHabit() { return habit; }
    public void setHabit(HabitDto habit) { this.habit = habit; }
}
