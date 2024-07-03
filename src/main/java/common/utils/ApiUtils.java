package common.utils;

import com.example.credential_service.UserEntity;
import common.model.Employee;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiUtils {

    private static final String BASE_URL = "http://localhost:8080/api/employees";

    public static Response createEmployee(UserEntity user, Employee employee){
        String requestBody = JsonUtils.toJson(employee);

        if (requestBody == null) {
            // Handle the error appropriately
            return null;
        }

        Response response = given()
                .auth()
                .preemptive()
                .basic(user.getUsername(), user.getPassword())
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/employees");

        return response;
    }

    public static Response createEmployee(UserEntity userEntity, String name, String surname, String position, String dayJoined, int salary, String dateOfBirth) {
        String requestBody = String.format(
                "{ \"name\": \"%s\", \"surname\": \"%s\", \"position\": \"%s\", \"dayJoined\": \"%s\", \"salary\": %d, \"dateOfBirth\": \"%s\" }",
                name, surname, position, dayJoined, salary, dateOfBirth
        );

        return RestAssured.given()
                .auth()
                .preemptive()
                .basic(userEntity.getUsername(), userEntity.getPassword())
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(BASE_URL);
    }

    public static Response getEmployeeById(UserEntity user, String employeeId) {
        return given()
                .auth()
                .basic(user.getUsername(), user.getPassword())
                .header("Content-Type", "application/json")
                .get(BASE_URL + "/" + employeeId);
    }

    public static Response deleteEmployee(UserEntity user, String employeeId) {
        return given()
                .auth()
                .basic(user.getUsername(), user.getPassword())
                .header("Content-Type", "application/json")
                .delete(BASE_URL + "/" + employeeId);
    }
}