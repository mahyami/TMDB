package models;

public class GenreModel {
    private int id;
    private String value;


    public GenreModel(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
