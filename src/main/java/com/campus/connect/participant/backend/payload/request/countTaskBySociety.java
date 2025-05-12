package com.campus.connect.participant.backend.payload.request;

//select society_id, count(*),s1.name from task t1 inner join society s1 on t1.society_id = s1.id group by(society_id,s1.name);

public class countTaskBySociety {
    private int society_id;
    private String name;
    private int count;

    public int getSociety_id() {
        return society_id;
    }

    public void setSociety_id(int society_id) {
        this.society_id = society_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}