package com.example.laboratoirejeuastre;

public class Planet {

    public static final int STANDARD_SIZE = 100;
    private int id;
    private String name;
    private int size;
    private String status;
    private String image;
    private float x;
    private float y;

    public Planet() {
    }

    public Planet( String name, int size, String status, String image, float x, float y) {

        this.name = name;
        this.size = size;
        this.status = status;
        this.image = image;
        this.x = x;
        this.y = y;
    }
    public Planet(int id, String name, int size, String status, String image, float x, float y) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.status = status;
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
