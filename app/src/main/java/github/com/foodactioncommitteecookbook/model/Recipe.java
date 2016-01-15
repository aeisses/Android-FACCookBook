package github.com.foodactioncommitteecookbook.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Model class for Recipes.
 */
public class Recipe implements Serializable {

  @SerializedName("id")
  private int id;

  @SerializedName("addedDate")
  private Date addedDate;

  @SerializedName("updatedDate")
  private Date updatedDate;

  @SerializedName("title")
  private String title;

  @SerializedName("searchItems")
  private List<String> searchItems;

  @SerializedName("category")
  private List<String> category;

  @SerializedName("type")
  private String type;

  @SerializedName("season")
  private String season;

  @SerializedName("ingredients")
  private List<Ingredient> ingredients;

  @SerializedName("directions")
  private List<Direction> directions;

  @SerializedName("notes")
  private List<Note> notes;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getAddedDate() {
    return addedDate;
  }

  public void setAddedDate(Date addedDate) {
    this.addedDate = addedDate;
  }

  public Date getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = updatedDate;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<String> getSearchItems() {
    if (searchItems == null) {
      searchItems = new ArrayList<>();
    }
    return searchItems;
  }

  public void addSearchItems(String searchItem) {
    if (this.searchItems == null) {
      this.searchItems = new ArrayList<>();
    }
    this.searchItems.add(searchItem);
  }

  public List<String> getCategories() {
    if (category == null) {
      category = new ArrayList<>();
    }
    return category;
  }

  public void addCategory(String category) {
    if (this.category == null) {
      this.category = new ArrayList<>();
    }
    this.category.add(category);
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSeason() {
    return season;
  }

  public void setSeason(String season) {
    this.season = season;
  }

  public List<Ingredient> getIngredients() {
    if (ingredients == null) {
      ingredients = new ArrayList<>();
    }
    return ingredients;
  }

  public void addIngredient(Ingredient ingredient) {
    if (this.ingredients == null) {
      this.ingredients = new ArrayList<>();
    }
    this.ingredients.add(ingredient);
  }

  public List<Direction> getDirections() {
    if (directions == null) {
      directions = new ArrayList<>();
    }
    return directions;
  }

  public void addDirection(Direction direction) {
    if (this.directions == null) {
      this.directions = new ArrayList<>();
    }
    this.directions.add(direction);
  }

  public List<Note> getNotes() {
    if (notes == null) {
      notes = new ArrayList<>();
    }
    return notes;
  }

  public void addNote(Note note) {
    if (this.notes == null) {
      this.notes = new ArrayList<>();
    }
    this.notes.add(note);
  }

  public static class Ingredient {
    @SerializedName("amount")
    private String amount;

    @SerializedName("ingredient")
    private String ingredient;

    public String getAmount() {
      return amount;
    }

    public void setAmount(String amount) {
      this.amount = amount;
    }

    public String getIngredient() {
      return ingredient;
    }

    public void setIngredient(String ingredient) {
      this.ingredient = ingredient;
    }
  }

  public static class Direction {
    @SerializedName("direction")
    private String direction;

    public String getDirection() {
      return direction;
    }

    public void setDirection(String direction) {
      this.direction = direction;
    }
  }

  public static class Note {
    @SerializedName("note")
    private String note;

    public String getNote() {
      return note;
    }

    public void setNote(String note) {
      this.note = note;
    }
  }
}
