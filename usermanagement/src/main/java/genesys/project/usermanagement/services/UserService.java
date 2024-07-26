package genesys.project.usermanagement.services;

import genesys.project.usermanagement.entities.User;
import genesys.project.usermanagement.repos.UserRepository;
import genesys.project.usermanagement.utils.LoginRequest;
import genesys.project.usermanagement.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // UserRepository to handle DB operations
    @Autowired
    private UserRepository userRepository;

    // Checks if any required fields (name, email, password) are missing or empty
    private static boolean missingRequiredFields(User user) {
        return (user.getPassword() == null || user.getPassword().isEmpty()) ||
                (user.getEmail() == null || user.getEmail().isEmpty()) ||
                (user.getName() == null || user.getName().isEmpty());
    }

    // Checks if a user exists by email
    public boolean userExists(String email) {
        try {
            return userRepository.existsById(email.toLowerCase());
        } catch (Exception e) {
            logger.error("Error checking if user exists with email: {}", email, e);
            throw new RuntimeException("Error checking user existence", e);
        }
    }

    // Creates a new user
    public Map createUser(User user) {
        try {
            // Check if the user already exists
            if (userExists(user.getEmail().toLowerCase())) {
                return ResponseUtils.returnSignUpResponse(false, true);
            }

            // Check for missing required fields
            if (missingRequiredFields(user)) {
                return ResponseUtils.returnErrorResponse("Missing or empty required field; name, email, or password");
            }
            // Always use lowercase emails to avoid case-sensitive login bugs
            user.setEmail(user.getEmail().toLowerCase());
            // Set lastLogin to current date and time
            user.setLastLogin(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            // Save the new user
            userRepository.save(user);
            return ResponseUtils.returnSignUpResponse(true, false);
        } catch (Exception e) {
            logger.error("Error creating user with email: {}", user.getEmail(), e);
            throw new RuntimeException("Error creating user", e);
        }
    }

    // Fetches a user by email
    public Optional<User> getUser(String email) {
        logger.info("Looking for user with email: {}", email);
        try {
            Optional<User> user = userRepository.findById(email.toLowerCase());
            if (user.isPresent()) {
                logger.info("User found: {}", user.get());
            } else {
                logger.warn("No user found with email: {}", email);
            }
            return user;
        } catch (Exception e) {
            logger.error("Error fetching user with email: {}", email, e);
            throw new RuntimeException("Error fetching user", e);
        }
    }

    // Logs in a user
    public Map<String, Boolean> login(LoginRequest loginRequest) {
        try {
            User user = validateUser(loginRequest.getEmail().toLowerCase(), loginRequest.getPassword());
            boolean emailExists = userExists(loginRequest.getEmail());
            boolean passwordMatches = user != null;

            if (emailExists && passwordMatches) {
                user.setLastLogin(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                userRepository.save(user);
            }
            return ResponseUtils.returnLoginResponse(emailExists, passwordMatches);
        } catch (Exception e) {
            logger.error("Error updating last login for user with email: {}", loginRequest.getEmail(), e);
            throw new RuntimeException("Error updating last login", e);
        }
    }

    // Lists all users
    public Iterable<User> listUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            logger.error("Error listing users", e);
            throw new RuntimeException("Error listing users", e);
        }
    }

    // Deletes a user by email
    public void deleteUser(String email) {
        try {
            userRepository.deleteById(email.toLowerCase());
        } catch (Exception e) {
            logger.error("Error deleting user with email: {}", email, e);
            throw new RuntimeException("Error deleting user", e);
        }
    }

    // Updates a user by email
    public User updateUser(String email, User userUpdates) {
        try {
            // Fetch the existing user
            Optional<User> existingUserOpt = userRepository.findById(email.toLowerCase());

            if (existingUserOpt.isPresent()) {
                User existingUser = existingUserOpt.get();

                // Update name/password fields if they are not null or empty
                if (userUpdates.getName() != null && !userUpdates.getName().isEmpty()) {
                    existingUser.setName(userUpdates.getName());
                }
                if (userUpdates.getPassword() != null && !userUpdates.getPassword().isEmpty()) {
                    existingUser.setPassword(userUpdates.getPassword());
                }

                // Save the updated user
                return userRepository.save(existingUser);
            } else {
                throw new RuntimeException("User not found with email: " + email);
            }
        } catch (Exception e) {
            logger.error("Error updating user with email: {}", email, e);
            throw new RuntimeException("Error updating user", e);
        }
    }

    // Validates a user's email and password
    public User validateUser(String email, String password) {
        try {
            Optional<User> userOpt = userRepository.findById(email.toLowerCase());
            User user = userOpt.orElse(null);
            return Objects.nonNull(user) && user.getPassword().equals(password) ? user : null;
        } catch (Exception e) {
            logger.error("Error validating user with email: {}", email, e);
            throw new RuntimeException("Error validating user", e);
        }
    }
}
