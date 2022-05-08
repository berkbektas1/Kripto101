package com.example.kripto101.Models;

public class SliderItem {

    //Here you can use String variable to store url
    //If you want to load image from the internet
    // name
    //referans linknin eklenmesi lazım
    private String image, link;

    public SliderItem() {
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
