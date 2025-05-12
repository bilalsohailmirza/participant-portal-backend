//select team_id,count(*), t2.name from task t1 inner join team t2 on t1.team_id = t2.id group by(team_id,t2.name);

package com.campus.connect.participant.backend.payload.request;
import java.util.UUID;

public class countTaskByTeam {

    private String teamName;
    private UUID teamId;
    private int taskCount;

    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public UUID getTeamId() {
        return teamId;
    }
    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }
    public int getTaskCount() {
        return taskCount;
    }
    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

}
