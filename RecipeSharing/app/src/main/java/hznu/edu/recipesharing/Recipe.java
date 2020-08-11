package hznu.edu.recipesharing;

public class Recipe {
    String recipe_id = null;
    String recipe_content = null;
    String recipe_name = null;
    String recipe_writer = null;
    String recipe_tag = null;
    String recipe_editdate = null;
    String recipe_image = null;

    public Recipe(){}

    public Recipe(String recipe_id, String recipe_content,String recipe_name,String recipe_writer,String recipe_tag,String recipe_editdate,String recipe_image){
        this.recipe_id = recipe_id;
        this.recipe_content = recipe_content;
        this.recipe_name = recipe_name;
        this.recipe_writer = recipe_writer;
        this.recipe_tag = recipe_tag;
        this.recipe_editdate = recipe_editdate;
        this.recipe_image = recipe_image;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public void setRecipe_content(String recipe_content) {
        this.recipe_content = recipe_content;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public void setRecipe_writer(String recipe_writer) {
        this.recipe_writer = recipe_writer;
    }

    public void setRecipe_tag(String recipe_tag) {
        this.recipe_tag = recipe_tag;
    }

    public void setRecipe_editdate(String recipe_editdate) {
        this.recipe_editdate = recipe_editdate;
    }

    public void setRecipe_image(String recipe_image) {
        this.recipe_image = recipe_image;
    }

    public String getRecipe_id(){
        return this.recipe_id;
    }

    public String getRecipe_content() {
        return recipe_content;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public String getRecipe_writer() {
        return recipe_writer;
    }

    public String getRecipe_tag() {
        return recipe_tag;
    }

    public String getRecipe_editdate() {
        return recipe_editdate;
    }

    public String getRecipe_image() {
        return recipe_image;
    }
}
