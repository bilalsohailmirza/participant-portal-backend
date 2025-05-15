ALTER TABLE task
ADD COLUMN team_id UUID;

ALTER TABLE task
ADD COLUMN society_id UUID;

ALTER TABLE task
ADD CONSTRAINT fk_team_id FOREIGN KEY (team_id) REFERENCES team(id);

ALTER TABLE task
ADD CONSTRAINT fk_society_id FOREIGN KEY (society_id) REFERENCES society(id);
