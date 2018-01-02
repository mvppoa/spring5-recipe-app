package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.domain.enums.Dificulty;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes(){

        log.debug("Start adding recipes");

        List<Recipe> recipes = new ArrayList<>(2);

        UnitOfMeasure eachUnitOfMeasure = getUomByDescription("Each");
        UnitOfMeasure tableSpoonUnitOfMeasure = getUomByDescription("Tablespoon");
        UnitOfMeasure teaSpoonUnitOfMeasure = getUomByDescription("Teaspoon");
        UnitOfMeasure dashUnitOfMeasure = getUomByDescription("Dash");
//        UnitOfMeasure pintUnitOfMeasure = getUomByDescription("Pint");
//        UnitOfMeasure cupsUnitOfMeasure = getUomByDescription("Cup");

        Category americanCategory = getCategoryByDescription("American");
        //Category mexicanCategory = getCategoryByDescription("Mexican");

        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookingTime(0);
        guacRecipe.setServing(4);
        guacRecipe.setDificulty(Dificulty.EASY);
        guacRecipe.setSource(30);
        guacRecipe.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacRecipe.setDirections("Shit pickle");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("My notees");
        guacRecipe.addNotes(guacNotes);

        guacRecipe.addIngredient(new Ingredient("Ripe avocados", new BigDecimal(2), eachUnitOfMeasure))
                .addIngredient(new Ingredient("Kosher salt", new BigDecimal(.5), teaSpoonUnitOfMeasure))
                .addIngredient(new Ingredient("Fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUnitOfMeasure))
                .addIngredient(new Ingredient("Minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUnitOfMeasure))
                .addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUnitOfMeasure))
                .addIngredient(new Ingredient("Freshly grated black pepper", new BigDecimal(2), dashUnitOfMeasure))
                .addIngredient(new Ingredient("Ripe tomato, seeds and pulp removed, chopped", new BigDecimal(2), eachUnitOfMeasure));

        guacRecipe.getCategories().add(americanCategory);
        recipes.add(guacRecipe);

        log.debug("Added recipes");

        return recipes;

    }

    private UnitOfMeasure getUomByDescription(String uom) {
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByUom(uom);
        if(!unitOfMeasureOptional.isPresent()){
            throw  new RuntimeException("Excpected UOM Not Found");
        }
        return unitOfMeasureOptional.get();
    }


    private Category getCategoryByDescription(String category) {
        Optional<Category> categoryOptional = categoryRepository.findByDescription(category);
        if(!categoryOptional.isPresent()){
            throw  new RuntimeException("Excpected UOM Not Found");
        }
        return categoryOptional.get();
    }

}
