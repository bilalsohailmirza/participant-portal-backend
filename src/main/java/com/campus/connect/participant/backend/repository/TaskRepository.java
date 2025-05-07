package com.campus.connect.participant.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import com.campus.connect.participant.backend.model.Task;

@Repository
public class TaskRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    // RowMapper as inner class
    private static class TaskRowMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task task = new Task();
            task.setTaskId(UUID.fromString(rs.getString("taskid")));
            task.setTaskName(rs.getString("taskname"));
            task.setTaskDescription(rs.getString("taskdescription"));
            task.setTaskStatus(rs.getString("taskstatus"));
            task.setCreatedBy(UUID.fromString(rs.getString("createdby")));

            String assignedToStr = rs.getString("assignedto");
            task.setAssignedTo(assignedToStr != null ? UUID.fromString(assignedToStr) : null);

            task.setDeadline(rs.getDate("deadline"));
            return task;
        }
    }

    // Create a new task
    public int createTask(Task task) {
        String sql = """
            INSERT INTO task (taskid, taskname, taskdescription, taskstatus, createdby, assignedto, deadline)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        return jdbcTemplate.update(sql,
            task.getTaskId(),
            task.getTaskName(),
            task.getTaskDescription(),
            task.getTaskStatus(),
            task.getCreatedBy(),
            task.getAssignedTo(),
            task.getDeadline()
        );
    }

    // Get all tasks created by a specific user
    public List<Task> getTasksByUserId(UUID userId) {
        String sql = "SELECT * FROM task WHERE createdby = ?";
        return jdbcTemplate.query(sql, new TaskRowMapper(), userId);
    }

    // Mark a task as completed
    public int markTaskAsComplete(UUID taskId) {
        String sql = "UPDATE task SET taskstatus = 'COMPLETED' WHERE taskid = ?";
        return jdbcTemplate.update(sql, taskId);
    }
}
