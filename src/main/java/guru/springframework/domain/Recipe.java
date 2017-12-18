package guru.springframework.domain;

import guru.springframework.domain.enums.Dificulty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
@Entity
public class Recipe {

    //Special type to relational database.
    //Not suported by oracle prior to 12.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookingTime;
    private String source;

    public Recipe() {
    }

    @Lob //Need more than 255
    private String directions;

    //Difficulty dificulty
    @Lob
    private Byte[] image;

    //Cascade makes Recipe to own this.
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    //Save relationship in the child class
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    //Save as ordinal or string | Ordinal is the default
    //Try to use string to avoid data issues when changing enums.
    @Enumerated(value = EnumType.STRING)
    private Dificulty dificulty;

    //Just need to add in one. Used mapped by in the other method
    @ManyToMany
    @JoinTable(name = "recipe_category",
        joinColumns = @JoinColumn(name = "recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void addNotes(Notes notes) {
        notes.setRecipe(this);
        this.notes = notes;
    }

    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

}
