package employeeapp;

import common.providers.DataProvider;
import common.utils.JsonUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.equalTo;

public class UnprivilegedUserTests extends BasicTest {

    @Test
    public void testGetAllEmployees() {
        given()
                .auth()
                .preemptive()
                .basic(regularUser.getUsername(), regularUser.getPassword())
        .when()
                .get(BASE_URL)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testCreateEmployee() {
        given()
                .auth()
                .preemptive()
                .basic(regularUser.getUsername(), regularUser.getPassword())
                .contentType(ContentType.JSON)
                .body(JsonUtils.toJson(DataProvider.getEmployee()))
        .when()
                .post(BASE_URL)
        .then()
                .statusCode(403)
                .body("error", equalTo("Forbidden"));
    }
}
