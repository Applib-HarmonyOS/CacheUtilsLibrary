package com.lifeofcoding.sample;

/**
 * Created by Wesley Lin on 9/6/15.
 */
public class MyClass {
    private String text;
    private int id;
    private boolean isIdZero;

    /**
     * Constructor.
     *
     * @param text text parameter
     * @param id   id parameter
     */
    public MyClass(String text, int id) {
        this.text = text;
        this.id = id;
        this.isIdZero = id == 0;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIdZero() {
        return isIdZero;
    }

    public void setIsIdZero(boolean isIdZero) {
        this.isIdZero = isIdZero;
    }

    @Override
    public String toString() {
        return "{text: " + text + ", id: " + id + ", isIdZero: " + isIdZero + "}";
    }

}
