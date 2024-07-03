package common.model;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String name;
    private String surname;
    private String position;
    private String dayJoined;
    private int salary;
    private String dateOfBirth;
}
