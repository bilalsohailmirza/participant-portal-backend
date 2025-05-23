package com.campus.connect.participant.backend.controller;

import com.campus.connect.participant.backend.model.Organizer;
import com.campus.connect.participant.backend.model.Participant;
import com.campus.connect.participant.backend.model.User;
import com.campus.connect.participant.backend.repository.OrganizerRepository;
import com.campus.connect.participant.backend.repository.OrganizerTeamRepository;
import com.campus.connect.participant.backend.repository.UserRepository;
import com.campus.connect.participant.backend.repository.UserRepository;
import com.campus.connect.participant.backend.payload.request.SignupRequest;
import com.campus.connect.participant.backend.payload.request.LoginRequest;
import com.campus.connect.participant.backend.payload.request.ParticipantRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.campus.connect.participant.backend.security.services.CustomUserDetails;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import com.campus.connect.participant.backend.security.jwt.JwtUtils;
import com.campus.connect.participant.backend.payload.response.CompetitionResponse;
import com.campus.connect.participant.backend.payload.response.EventResponse;
import com.campus.connect.participant.backend.payload.response.JwtResponse;
import com.campus.connect.participant.backend.repository.SocietyRepository;
import java.time.LocalDate;
import com.campus.connect.participant.backend.model.Organizer_society;
import com.campus.connect.participant.backend.model.Organizer_team;
import com.campus.connect.participant.backend.model.Organizer_society;
import com.campus.connect.participant.backend.model.Society;
import com.campus.connect.participant.backend.repository.Society_OrganizerRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private SocietyRepository societyRepository;

    @Autowired
    private Society_OrganizerRepository society_organizerRepository;

    @Autowired
    private OrganizerTeamRepository organizerTeamRepository;
    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update a user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User userDetails) {
        int rowsAffected = userRepository.updateUser(id, userDetails);
        if (rowsAffected > 0) {
            userDetails.setId(id);
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        int rowsAffected = userRepository.deleteUser(id);
        if (rowsAffected > 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/email")
    public ResponseEntity<?> GetEmail(@RequestParam String email) {
        System.err.print("in req");
        Optional<User> response = userRepository.findByEmail(email);

        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }


    @PostMapping("/signup")
    public ResponseEntity<?> signIn(@RequestBody SignupRequest signupRequest) {

        System.out.println("Signup Request:??????????????????? " + signupRequest);
        Optional<User> response = userRepository.findByEmail(signupRequest.getEmail());

        if (response.isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email already exists.");
        } else {
            User user = new User();
        System.out.println("User: " + user);    
            user.setEmail(signupRequest.getEmail());
            String hashedPassword = new BCryptPasswordEncoder().encode(signupRequest.getPassword());

            user.setPassword(hashedPassword);
            user.setRole(signupRequest.getRole());
            System.out.println("User>>>>>>>>>>>: " + user);    
            userRepository.createUser(user);
            System.out.println(signupRequest.getRole() + ">>>>>>>>>>>>>>");

            if (signupRequest.getRole().equals("organizer")) {
                String societyName = signupRequest.getSociety_name();
                Society society = societyRepository.getSocietyByName(societyName);

                System.out.println(societyName);
                System.out.println("-------------------");
                System.out.println(society);
                if (society == null) {
                    
                    return ResponseEntity.badRequest().body("Error: Society does not exist.");
                }

                System.out.println("Organizer");
                Organizer organizer = new Organizer();
                organizer.setRole(signupRequest.getOrganizer_role());
                organizer.setUserId(user.getId());
                organizer.setFullName(signupRequest.getFull_name());
                Organizer newOrganizer = organizerRepository.createOrganizer(organizer);

                society_organizerRepository.InsertSocietyOrganizer(newOrganizer.getId(), society.getId());
                organizerTeamRepository.InsertOrganizerTeam(signupRequest.getTeam_name(), newOrganizer.getId(), society.getId());
                
                System.out.println(organizer);
                return ResponseEntity.status(HttpStatus.CREATED).body(
                        Map.of(
                                "message", "organizer successfully registered.",
                                "user", organizer));
                                
            }
            // return ResponseEntity.ok().body(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of(
                            "message", "User successfully registered.",
                            "user", user));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());

        if (user.isPresent()) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            // if (roles.contains("participant")) {

                if(roles.contains("organizer"))
                {   
                    Organizer organizer = organizerRepository.getOrganizerByUserId(user.get().getId());

                    Organizer_society society_organizer_details = society_organizerRepository.getSocietyByOrganizerId(organizer.getId());
                    Society society = societyRepository.getSocietyById(society_organizer_details.getSocietyId());
                    
                    Organizer_team org = organizerTeamRepository.getOrganizerTeamByOrganizerId(organizer.getId());
                    
                    //translate this in java
                    System.out.println("Organizer: " + org);
                    
                    return ResponseEntity.ok().body(
                            Map.of(
                                    "message", "User successfully logged in.",
                                    "user", user,
                                    "organizer", organizer,
                                    "society", society,
                                    "team", org,
                                    "token", jwt)
                    );
                }

                return ResponseEntity.ok(new JwtResponse(jwt,
                        userDetails.getUsername(),
                        roles));
            // }

            // else
            // {   
            //     Organizer organizer = organizerRepository.getOrganizerByUserId(user.get().getId());
            //     return ResponseEntity.ok(
            //             Map.of(
            //                     "message", "User successfully logged in.",
            //                     "user", organizer,
            //                     "token", jwt)
            //     );
            // }
        }

        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of(
                            "message", "Email does not exist"));
        }
    }

    @PostMapping("/participants")
    public ResponseEntity<?> createParticipant(@RequestBody ParticipantRequest participantRequest) {
        try {
            // Call createParticipation to handle the participant creation logic
            String result = userRepository.createParticipation(
                    participantRequest.getFullName(),
                    participantRequest.getPhone(),
                    participantRequest.isStudent(),
                    participantRequest.getOrganization(),
                    participantRequest.getActivityId());

            if (result.contains("Successfully")) {
                return ResponseEntity.status(HttpStatus.CREATED).body(
                        Map.of("message", result));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Map.of("message", result));
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Failed to create participant", "details", ex.getMessage()));
        }
    }

    @GetMapping("/getPersonalData")
    public ResponseEntity<Participant> getPersonalData() {
        Participant participant = userRepository.getUserDetails();

        // Return the participant object in the HTTP response
        return ResponseEntity.ok(participant);
    }

    @GetMapping("/getCompetitions")
    public ResponseEntity<List<CompetitionResponse>> getCompetitions() {
        return userRepository.getCompetitions();
    }

    @GetMapping("/getEvents")
    public ResponseEntity<List<EventResponse>> getEvents() {
        return userRepository.getEvents();
    }

}