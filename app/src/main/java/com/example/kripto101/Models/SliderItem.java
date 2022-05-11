package com.example.kripto101.Models;

public class SliderItem {
    private String image, link;

    public SliderItem(){

    }
    public SliderItem(String image, String link)
    {
        this.image = image;
        this.link = link;
    }

    public String getImage(){
        return image;
    }

    public String getLink() {
        return link;
    }


}
