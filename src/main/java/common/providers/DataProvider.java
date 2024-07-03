package common.providers;

import common.model.Employee;

import static common.utils.DataFaker.*;

public class DataProvider {

    public static Employee getEmployee() {
        return Employee.builder()
                .name(getName())
                .surname(getLastName())
                .position(getPosition())
                .dayJoined("2024-06-30") //add random date
                .dateOfBirth("1990-01-01")//add random date
                .salary(getRandomNumber(1000, 100000))
                .build();
    }
}
