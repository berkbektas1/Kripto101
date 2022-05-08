package com.example.kripto101.Models;

public class EducationsModel {
    private String name, description, imageEdu;

    public EducationsModel() {
    }

    public EducationsModel(String name, String description, String imageEdu) {
        this.name = name;
        this.description = description;
        this.imageEdu = imageEdu;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageEdu() {
        return imageEdu;
    }
}
