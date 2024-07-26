package genesys.project.usermanagement.controllers;

import genesys.project.usermanagement.entities.User;
import genesys.project.usermanagement.services.UserService;
import genesys.project.usermanagement.utils.LoginRequest;
import genesys.project.usermanagement.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    // Endpoint to create a new user
    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        try {
            // Attempts to create a new user
            Map result = userService.createUser(user);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Logs an error message if user creation fails
            logger.error("Error creating user with email: {}", user.getEmail(), e);
            return ResponseEntity.status(500)
                    .body(ResponseUtils.returnSignUpResponse(false, false));
        }
    }

    // Endpoint to fetch a user by email
    @GetMapping("/{email}")
    public ResponseEntity<Object> getUser(@PathVariable String email) {
        logger.info("Fetching user with email: {}", email);
        try {
            // Attempts to retrieve the user
            Optional<User> user = userService.getUser(email);
            if (user.isPresent()) {
                logger.info("User found: {}", user.get());
                return ResponseEntity.ok(user.get());
            } else {
                String errorMessage = String.format("The email %s does not exist!", email);
                logger.warn(errorMessage);
                return ResponseEntity.status(404).body(ResponseUtils.returnErrorResponse(errorMessage));
            }
        } catch (Exception e) {
            // Logs an error message if fetching the user fails
            logger.error("Error fetching user with email: {}", email, e);
            return ResponseEntity.status(500).body(ResponseUtils.returnErrorResponse("Internal Server Error"));
        }
    }

    // Endpoint to list all users
    @GetMapping
    public ResponseEntity<Iterable<User>> getUsers() {
        try {
            // Attempts to fetch all users
            Iterable<User> users = userService.listUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            // Logs an error message if fetching users fails
            logger.error("Error listing users", e);
            return ResponseEntity.status(500).build();
        }
    }

    // Endpoint to delete a user by email
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        try {
            // Attempts to delete the user
            userService.deleteUser(email);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Logs an error message if deleting the user fails
            logger.error("Error deleting user with email: {}", email, e);
            return ResponseEntity.status(500).build();
        }
    }

    // Endpoint to update a user by email
    @PutMapping("/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User userUpdates) {
        try {
            // Attempts to update the user
            User updatedUser = userService.updateUser(email, userUpdates);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            // Logs an error message if updating the user fails
            logger.error("Error updating user with email: {}", email, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Boolean>> userLogin(@RequestBody LoginRequest loginRequest) {
        try {
            // Attempts update user details after logging in
            Map<String, Boolean> response = userService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Logs an error message if login fails
            logger.error("Error updating last login for email: {}", loginRequest.getEmail(), e);
            return ResponseEntity.status(500)
                    .body(ResponseUtils.returnLoginResponse(false, false));
        }
    }
}
