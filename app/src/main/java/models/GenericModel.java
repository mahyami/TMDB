package models;

public class GenericModel {
    private String img;
    private String name;

    public GenericModel(String img, String name) {
        this.img = img;
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public String getName() {
        return name;
    }
}
