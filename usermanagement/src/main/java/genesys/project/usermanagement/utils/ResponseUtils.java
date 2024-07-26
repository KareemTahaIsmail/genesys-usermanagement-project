package genesys.project.usermanagement.utils;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class ResponseUtils {
    public static Map<String, String> returnErrorResponse(String message) {
        return Map.of("error", message);
    }

    public static Map<String, Boolean> returnLoginResponse(boolean emailExists, boolean passwordMatches) {
        return Map.of("emailExists", emailExists, "passwordMatches", passwordMatches);
    }

    public static Map<String, Boolean> returnSignUpResponse(boolean userCreated, boolean userAlreadyExists) {
        return Map.of("userCreated", userCreated, "userAlreadyExists", userAlreadyExists);
    }

}
