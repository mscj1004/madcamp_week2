package com.example.madcamp_week2;

public class Shopping {
    private String Title;
    private String Price;
    private String Link;

    public Shopping(String Title, String Price, String Link){
        this.Title = Title;
        this.Price = Price;
        this.Link = Link;
    }

    public String getLink() {
        return Link;
    }

    public String getPrice() {
        return Price;
    }

    public String getTitle() {
        return Title;
    }
}
