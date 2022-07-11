package com.example.madcamp_week2.weather;

public class Fashion {
    private String Top;
    private String Top_color;
    private String Bottom;
    private String Bottom_color;
    private String Accessory;
    private String Accessory_color;
    private String Outer;
    private String Outer_color;

    public Fashion(String Top, String Top_color, String Bottom, String Bottom_color, String Accessory, String Accessory_color, String Outer, String Outer_color){
        this.Top = Top;
        this.Top_color = Top_color;
        this.Bottom = Bottom;
        this.Bottom_color = Bottom_color;
        this.Accessory = Accessory;
        this.Accessory_color = Accessory_color;
        this.Outer = Outer;
        this.Outer_color = Outer_color;
    }

    public String getAccessory() {
        return Accessory;
    }

    public String getAccessory_color() {
        return Accessory_color;
    }

    public String getBottom() {
        return Bottom;
    }

    public String getBottom_color() {
        return Bottom_color;
    }

    public String getOuter() {
        return Outer;
    }

    public String getOuter_color() {
        return Outer_color;
    }

    public String getTop() {
        return Top;
    }

    public String getTop_color() {
        return Top_color;
    }

    public void setAccessory(String accessory) {
        Accessory = accessory;
    }

    public void setAccessory_color(String accessory_color) {
        Accessory_color = accessory_color;
    }

    public void setBottom(String bottom) {
        Bottom = bottom;
    }

    public void setBottom_color(String bottom_color) {
        Bottom_color = bottom_color;
    }

    public void setOuter(String outer) {
        Outer = outer;
    }

    public void setOuter_color(String outer_color) {
        Outer_color = outer_color;
    }

    public void setTop(String top) {
        Top = top;
    }

    public void setTop_color(String top_color) {
        Top_color = top_color;
    }
}
