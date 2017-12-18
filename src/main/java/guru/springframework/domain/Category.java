package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipes"}) //Need to do that in order to avoid equals and hashcode StackOverflow problem
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    //Set the relationship you want
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}
