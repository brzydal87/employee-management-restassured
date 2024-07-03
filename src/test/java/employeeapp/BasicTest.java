package employeeapp;

import com.example.credential_service.UserEntity;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class BasicTest {

    private static final String CREDENTIAL_SERVICE_URL = "http://localhost:8081/api/users/";
    protected static final String BASE_URL = "http://localhost:8080/api/employees"; // Adjust as per your application's base URL
    protected static UserEntity managerUser;
    protected static UserEntity regularUser;
    private static RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        restTemplate = new RestTemplate();
        managerUser = getCredentialsByRole("MANAGER");
        regularUser = getCredentialsByRole("REGULAR");
    }

    private UserEntity getCredentialsByRole(String role) {
        ResponseEntity<UserEntity> response = restTemplate.getForEntity(CREDENTIAL_SERVICE_URL + role, UserEntity.class);
        return response.getBody();
    }
}
