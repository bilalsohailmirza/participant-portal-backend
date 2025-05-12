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
import com.campus.connect.participant.backend.payload.request.TaskWithTeamDTO;
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
            task.setTeamId(rs.getString("team_id") != null ? UUID.fromString(rs.getString("team_id")) : null);
            task.setSocietyId(rs.getString("society_id") != null ? UUID.fromString(rs.getString("society_id")) : null);
            return task;
        }
    }

    public static class TaskWithTeamDTORowMapper implements RowMapper<TaskWithTeamDTO> {
        @Override
        public TaskWithTeamDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            TaskWithTeamDTO dto = new TaskWithTeamDTO();
            dto.setTaskId(UUID.fromString(rs.getString("taskid")));
            dto.setTaskName(rs.getString("taskname"));
            dto.setTaskDescription(rs.getString("taskdescription"));
            dto.setTaskStatus(rs.getString("taskstatus"));
            dto.setCreatedBy(UUID.fromString(rs.getString("createdby")));
            dto.setAssignedTo(UUID.fromString(rs.getString("assignedto")));
            dto.setDeadline(rs.getTimestamp("deadline"));
            dto.setTeamId(UUID.fromString(rs.getString("team_id")));
            dto.setSocietyId(UUID.fromString(rs.getString("society_id")));
            dto.setTeamName(rs.getString("team_name"));
            return dto;
        }
    }


    // Create a new task
    public int createTask(Task task) {
        String sql = """
            INSERT INTO task (taskid, taskname, taskdescription, taskstatus, createdby, assignedto, deadline, team_id, society_id)
            VALUES (?, ?, ?, ?, ?, ?, ?,?,?)
        """;
        return jdbcTemplate.update(sql,
            task.getTaskId(),
            task.getTaskName(),
            task.getTaskDescription(),
            task.getTaskStatus(),
            task.getCreatedBy(),
            task.getAssignedTo(),
            task.getDeadline(),
            task.getTeamId(),
            task.getSocietyId()
        );
    }

    // Get all tasks created by a specific user
    public List<Task> getTasksByUserId(UUID userId) {
        String sql = "SELECT * FROM task WHERE createdby = ?";
        return jdbcTemplate.query(sql, new TaskRowMapper(), userId);
    }


    public List<Task> getTasksAssignedByUserId(UUID userId) {
        String sql = "SELECT * FROM task WHERE assignedto = ?";
        return jdbcTemplate.query(sql, new TaskRowMapper(), userId);
    }

    // Mark a task as completed
    public int markTaskAsComplete(UUID taskId) {
        String sql = "UPDATE task SET taskstatus = 'COMPLETED' WHERE taskid = ?";
        return jdbcTemplate.update(sql, taskId);
    }

    //add a function to update the status of task
    public int updateTaskStatus(UUID taskId, String status) {
        String sql = "UPDATE task SET taskstatus = ? WHERE taskid = ?";
        return jdbcTemplate.update(sql, status, taskId);
    }

    public List<Task> getAllTasks() {
        String sql = "SELECT * FROM task";
        return jdbcTemplate.query(sql, new TaskRowMapper());
    }

        public List<TaskWithTeamDTO> getAllTasksBySocietyId(UUID societyId) {
            String sql = "SELECT t1.taskid, t1.taskname, t1.taskdescription, t1.taskstatus,t1.createdby, t1.assignedto, t1.deadline, t1.team_id, t1.society_id, t2.name AS team_name FROM task t1 JOIN team t2 ON t1.team_id = t2.id WHERE t1.society_id = ?";
            
            return jdbcTemplate.query(sql, new TaskWithTeamDTORowMapper(), societyId);
        }
}
