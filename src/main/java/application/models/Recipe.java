package application.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private ChefUser author;

    private String recipeName;

    private String description;

    @Enumerated(EnumType.STRING)
    private FoodType type;

    private boolean isVegan;

    // ha ManyToMany, akkor csak egyik oldalon lehet mappedBy
    //@ManyToMany(mappedBy = "favoriteRecipes")
    @Transient
    private List<ChefUser> likedBy;

    public Recipe() {
        likedBy = new ArrayList<>();
    }

    public Recipe(long id, ChefUser author, String recipeName,
                  String description, FoodType type, boolean isVegan) {
        this();
        this.id = id;
        this.author = author;
        this.recipeName = recipeName;
        this.description = description;
        this.type = type;
        this.isVegan = isVegan;
    }

    public Recipe(long id, ChefUser author, String recipeName,
                  String description, FoodType type, boolean isVegan,
                  List<ChefUser> likedBy) {
        this(id, author, recipeName, description, type, isVegan);
        this.likedBy = likedBy;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FoodType getType() {
        return type;
    }

    public void setType(FoodType type) {
        this.type = type;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public ChefUser getAuthor() {
        return author;
    }

    public void setAuthor(ChefUser author) {
        this.author = author;
    }

    public List<ChefUser> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<ChefUser> likedBy) {
        this.likedBy = likedBy;
    }

}
