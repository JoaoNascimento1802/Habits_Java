package sesi.habits.dto;

import sesi.habits.model.Habit;

import java.util.List;
import java.util.stream.Collectors;

public class HabitDto {
    private Long id;
    private String title;
    private String description;
    private List<TaskDto> tasks;

    public HabitDto(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public HabitDto(Habit habit) {
        this.id = habit.getId();
        this.title = habit.getTitle();
        this.description = habit.getDescription();
        if (habit.getTasks() != null) {
            this.tasks = habit.getTasks().stream()
                    .map(task -> new TaskDto(task))
                    .collect(Collectors.toList());
        }
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<TaskDto> getTasks() { return tasks; }
    public void setTasks(List<TaskDto> tasks) { this.tasks = tasks; }
}