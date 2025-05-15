package com.campus.connect.participant.backend.repository;

import com.campus.connect.participant.backend.model.Organizer;
import com.campus.connect.participant.backend.model.Participant;
import com.campus.connect.participant.backend.model.User;
import com.campus.connect.participant.backend.payload.response.CompetitionResponse;
import com.campus.connect.participant.backend.payload.response.EventResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import com.campus.connect.participant.backend.security.services.CustomUserDetails;
import com.campus.connect.participant.backend.repository.ParticipantRepository;
import java.net.Authenticator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired ParticipantRepository participantRepository;

    private final RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(UUID.fromString(rs.getString("id")));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setOauth(rs.getString("oauth"));  // Update field
            user.setAccessToken(rs.getString("access_token"));  // Update field
            user.setRefreshToken(rs.getString("refresh_token"));  // Update field
            user.setProfilePic(rs.getString("profilepic"));  // Update field
            user.setRole(rs.getString("role"));
            return user;
        }
    };

    // Create a new user
    public User createUser(User user) {
        user.setId(UUID.randomUUID());
        String sql = "INSERT INTO \"user\" (id, email, password, oauth, access_token, refresh_token, profilepic, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getEmail(), user.getPassword(), user.getOauth(),
                user.getAccessToken(), user.getRefreshToken(), user.getProfilePic(), user.getRole());
        return user;
    }

    // Find a user by ID
    public Optional<User> findById(UUID id) {
        String sql = "SELECT * FROM \"user\" WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, rowMapper, id);
        return users.stream().findFirst();
    }

    // Get all users
    public List<User> findAll() {
        String sql = "SELECT * FROM \"user\"";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // Update a user by ID
    public int updateUser(UUID id, User user) {
        String sql = "UPDATE \"user\" SET email = ?, password = ?, oauth = ?, access_token = ?, " +
                "refresh_token = ?, profilepic = ?, role = ? WHERE id = ?";
        return jdbcTemplate.update(sql, user.getEmail(), user.getPassword(), user.getOauth(),
                user.getAccessToken(), user.getRefreshToken(), user.getProfilePic(), user.getRole(), id);
    }

    // Delete a user by ID
    public int deleteUser(UUID id) {
        String sql = "DELETE FROM \"user\" WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Find a user by email
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM \"user\" WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, rowMapper, email);
        return users.stream().findFirst();
    }

    public Participant getUserDetails()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("Unauthenticated user"); // Handle unauthenticated requests
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();

        Optional<User> optionalUser = findByEmail(userEmail);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("User not found");
        }
    
        // Retrieve the actual User object
        User loggedUser = optionalUser.get();
        System.out.println(loggedUser);
        // Extract the user's ID
        UUID user_id = loggedUser.getId();

        System.out.println(user_id);
        
        Optional<Participant> participant = participantRepository.getParticipantByUserId(user_id);

        return participant.orElseGet(() -> new Participant());

    }


    public String createParticipation(String full_name, String phone, Boolean Student, String organization, UUID activity_id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "Unauthenticated user"; // Handle unauthenticated requests
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();

        Optional<User> optionalUser = findByEmail(userEmail);
        if (optionalUser.isEmpty()) {
            return "User not found";
        }
    
        // Retrieve the actual User object
        User loggedUser = optionalUser.get();
    
        // Extract the user's ID
        UUID user_id = loggedUser.getId();
        // UUID user_id = UUID.randomUUID();
        UUID participantId = UUID.randomUUID();
        System.out.println(participantId);
        Participant participant = new Participant();

        participant.setEmail(userEmail);
        participant.setFullName(full_name);
        participant.setId(participantId);
        participant.setOrganization(organization);
        participant.setPhone(phone);
        participant.setStudent(Student);
        participant.setUserId(user_id);
        
        boolean var = participantRepository.createOneParticipant(participant);
        System.out.println("activity_id");
        System.out.println(activity_id); 
        participantRepository.associateWithActivity(participantId, activity_id);

        if(var)
        {
            return "Participant Created Successfully";
        }
        else
        {
            return "Unsuccessful";
        }

    }

   public ResponseEntity<List<CompetitionResponse>> getCompetitions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("Unauthenticated user"); // Handle unauthenticated requests
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();

        Optional<User> optionalUser = findByEmail(userEmail);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body(null);  // Return 404 if user not found
        }

        User loggedUser = optionalUser.get();
        UUID userId = loggedUser.getId();

        String sql = """
                SELECT 
                    c.id AS competition_id, 
                    c.name AS competition_name, 
                    c.description AS competition_description, 
                    c.date AS competition_date, 
                    c.time AS competition_time, 
                    c.rule_book AS competition_rule_book, 
                    a.id AS activity_id, 
                    a.name AS activity_name 
                FROM 
                    competition c 
                INNER JOIN 
                    "Activities" a ON a.id = c.activity_id 
                INNER JOIN 
                    "participant_Activities" p ON c.activity_id = p.activity_id 
                INNER JOIN 
                    participant pp ON pp.id = p.participant_id 
                WHERE 
                    pp.user_id = ?
            """;

        // Execute the query and map the result to CompetitionResponse
        List<CompetitionResponse> competitions = jdbcTemplate.query(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setObject(1, userId);  // Set the user ID parameter
                return ps;
            },
            (rs, rowNum) -> {
                CompetitionResponse response = new CompetitionResponse();
                response.setCompetitionId(rs.getString("competition_id"));
                response.setCompetitionName(rs.getString("competition_name"));
                response.setCompetitionDescription(rs.getString("competition_description"));
                response.setCompetitionDate(rs.getDate("competition_date"));
                response.setCompetitionTime(rs.getTime("competition_time"));
                response.setCompetitionRuleBook(rs.getString("competition_rule_book"));
                response.setActivityId(rs.getString("activity_id"));
                response.setActivityName(rs.getString("activity_name"));
                return response;
            }
        );

        // Return the list of competitions with a 200 OK status
        return ResponseEntity.ok(competitions);
    }


    public ResponseEntity<List<EventResponse>> getEvents() {
    // Check for authentication
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
        throw new SecurityException("Unauthenticated user"); // Handle unauthenticated requests
    }

    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
    String userEmail = userDetails.getUsername();

    // Find the user by email
    Optional<User> optionalUser = findByEmail(userEmail);
    if (optionalUser.isEmpty()) {
        return ResponseEntity.status(404).body(null);  // Return 404 if user not found
    }

    User loggedUser = optionalUser.get();
    UUID userId = loggedUser.getId();

    // SQL query to fetch events with all columns
    String sql = """
            SELECT 
                e.id AS event_id, 
                e.name AS event_name, 
                e.description AS event_description, 
                e.about AS event_about, 
                e.image AS event_image,
                e.fee AS event_fee, 
                e.logo AS event_logo, 
                e.cover AS event_cover, 
                e.date AS event_date, 
                e.time AS event_time, 
                e.society_id AS event_society_id, 
                e.activity_id AS event_activity_id, 
                a.name AS activity_name
            FROM 
                event e 
            INNER JOIN 
                "Activities" a ON a.id = e.activity_id
            INNER JOIN 
                "participant_Activities" p ON e.activity_id = p.activity_id
            INNER JOIN 
                participant pp ON pp.id = p.participant_id
            WHERE 
                pp.user_id = ?
        """;

    // Execute the query and map the result to EventResponse
    List<EventResponse> events = jdbcTemplate.query(
        connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, userId);  // Set the user ID parameter
            return ps;
        },
        (rs, rowNum) -> {
            EventResponse response = new EventResponse();
            response.setEventId(rs.getString("event_id"));
            response.setEventName(rs.getString("event_name"));
            response.setEventDescription(rs.getString("event_description"));
            response.setEventAbout(rs.getString("event_about"));
            response.setEventImage(rs.getString("event_image"));
            response.setEventFee(rs.getInt("event_fee"));
            response.setEventLogo(rs.getString("event_logo"));
            response.setEventCover(rs.getString("event_cover"));
            response.setEventDate(rs.getDate("event_date"));
            response.setEventTime(rs.getTime("event_time"));
            response.setEventSocietyId(rs.getString("event_society_id"));
            response.setEventActivityId(rs.getString("event_activity_id"));
            response.setActivityName(rs.getString("activity_name"));
            return response;
        }
    );

    // Return the list of events with a 200 OK status
    return ResponseEntity.ok(events);
}

}
