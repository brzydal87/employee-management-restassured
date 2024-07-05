package employeeapp;

import app.credential_service.UserEntity;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class BasicTest {

    private static final String CREDENTIAL_SERVICE_URL = "http://localhost:8081/api/users/";
    protected static final String BASE_URL = "http://localhost:8088/api/employees"; // Adjust as per your application's base URL
    protected static UserEntity managerUser;
    protected static UserEntity regularUser;
    private static RestTemplate restTemplate;

    @BeforeAll
    public static void set(){
        RestAssured.filters(new AllureRestAssured());
    }
    
    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        //RestAssured.port = 8080;
        restTemplate = new RestTemplate();
        managerUser = getCredentialsByRole("MANAGER");
        regularUser = getCredentialsByRole("REGULAR");
        RestAssured.filters(new AllureRestAssured());

    }

    private UserEntity getCredentialsByRole(String role) {
        ResponseEntity<UserEntity> response = restTemplate.getForEntity(CREDENTIAL_SERVICE_URL + role, UserEntity.class);
        return response.getBody();
    }
}
