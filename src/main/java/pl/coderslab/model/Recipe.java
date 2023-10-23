package pl.coderslab.model;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Recipe {
    private int id;
    private String name;
    private String ingredients;
    private String description;
    private Date created;
    private Date updated;
    private int preparation_time;
    private String preparation;
}