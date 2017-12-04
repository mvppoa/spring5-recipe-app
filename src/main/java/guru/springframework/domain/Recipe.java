package guru.springframework.domain;

import guru.springframework.domain.enums.Dificulty;

import javax.persistence.*;
import java.util.Set;

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
    private String directions;
    //Difficulty dificulty
    @Lob
    private Byte[] image;

    //Cascade makes Recipe to own this.
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    //Save relationship in the child class
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients;

    //Save as ordinal or string | Ordinal is the default
    //Try to use string to avoid data issues when changing enums.
    @Enumerated(value = EnumType.STRING)
    private Dificulty dificulty;

    //Just need to add in one. Used mapped by in the other method
    @ManyToMany
    @JoinTable(name = "recipe_category",
        joinColumns = @JoinColumn(name = "recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    public Recipe() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(Integer cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Dificulty getDificulty() {
        return dificulty;
    }

    public void setDificulty(Dificulty dificulty) {
        this.dificulty = dificulty;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
