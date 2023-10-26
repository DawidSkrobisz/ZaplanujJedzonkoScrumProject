package pl.coderslab.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DayName {

    private int id;
    private String name;
    private Integer display_order;
}