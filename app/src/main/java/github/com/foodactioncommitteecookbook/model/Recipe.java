package github.com.foodactioncommitteecookbook.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Model class for Recipes.
 */
public class Recipe {

    @SerializedName("id")
    private int id;

    @SerializedName("addedDate")
    private Date addedDate;

    @SerializedName("updatedDate")
    private Date updatedDate;

    @SerializedName("title")
    private String title;

    @SerializedName("searchItems")
    private String searchItems;

    @SerializedName("category")
    private String category;

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

    public Date getAddedDate() {
        return addedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getSearchItems() {
        return searchItems;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getSeason() {
        return season;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public static class Ingredient {
        @SerializedName("amount")
        private String amount;

        @SerializedName("ingredient")
        private String ingredient;

        public String getAmount() {
            return amount;
        }

        public String getIngredient() {
            return ingredient;
        }
    }

    public static class Direction {
        @SerializedName("direction")
        private String direction;

        public String getDirection() {
            return direction;
        }
    }

    public static class Note {
        @SerializedName("note")
        private String note;

        public String getNote() {
            return note;
        }
    }
}
