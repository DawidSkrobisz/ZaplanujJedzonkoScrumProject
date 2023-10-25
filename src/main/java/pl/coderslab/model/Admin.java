package pl.coderslab.model;

import lombok.*;

@NoArgsConstructor
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

    public Admin(Integer id, String firstName, String lastName, String email, String password, int superadmin, int enable) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.superadmin = superadmin;
        this.enable = enable;
    }

    public Admin(String firstName, String lastName, String email, String password, int i, int i1) {
    }
}