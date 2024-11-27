package com.campus.connect.participant.backend.model;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;
import java.util.UUID;

@Table("participant")
public class Participant {

    @Id
    private UUID id;

    private UUID user_id;

    private String name;

    public UUID getId()
    {
        return this.id;
    }

    public UUID getUserId()
    {
        return this.user_id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setUserId(UUID user_id)
    {
        this.user_id = user_id;
    }

}
