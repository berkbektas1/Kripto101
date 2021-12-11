package com.example.kripto101.Models;

public class EducationsModel {
    private String name;
    private String description;
    private Integer imageEdu;

    public EducationsModel() {
    }

    public EducationsModel(String name, String description, Integer imageEdu) {
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

    public Integer getImageEdu() {
        return imageEdu;
    }
}
