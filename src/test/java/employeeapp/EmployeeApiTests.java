package employeeapp;


import common.providers.DataProvider;
import common.utils.ApiUtils;
import common.utils.JsonUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

public class EmployeeApiTests extends BasicTest {

    private String createdEmployeeId;

    @Test
    @Story("GET Request")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description : Verify the list of employees is not empty")
    public void testGetAllEmployees() {
        given()
                //.filter(new AllureRestAssured())
                .auth()
                .preemptive()
                .basic(managerUser.getUsername(), managerUser.getPassword())
        .when()
                .get(BASE_URL)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }


    @Test
    @Story("POST Request")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description : Verify the creation of a new employee")
    public void testCreateEmployee() {
        createdEmployeeId = given()
                .contentType("application/json")
                .body(JsonUtils.toJson(DataProvider.getEmployee()))
                .auth()
                .preemptive()
                .basic(managerUser.getUsername(), managerUser.getPassword())
        .when()
                .post(BASE_URL)
        .then()
                .statusCode(201)
                .extract()
                .path("id");
    }

    @Test
    public void testCreateEmployeeFromApiUtils() {
        ApiUtils.createEmployee(managerUser, DataProvider.getEmployee());
    }

    @AfterEach
    public void cleanup() {
        if (createdEmployeeId != null) {
            given()
                    .auth()
                    .preemptive()
                    .basic(managerUser.getUsername(), managerUser.getPassword())
            .when()
                    .delete(BASE_URL + "/" + createdEmployeeId)
            .then()
                    .statusCode(204);
        }
    }
}