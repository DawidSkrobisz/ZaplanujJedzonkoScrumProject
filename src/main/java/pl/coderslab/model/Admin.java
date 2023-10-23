package pl.coderslab.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Admin {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int superadmin;
    private int enable;
}