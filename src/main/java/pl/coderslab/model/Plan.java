package pl.coderslab.model;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Plan {
    private Integer id;
    private String name;
    private String description;
    private Date created;
}