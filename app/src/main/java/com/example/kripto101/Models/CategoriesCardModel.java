package com.example.kripto101.Models;

public class CategoriesCardModel {
    Integer eduCategoriesImage;
    String eduCategoriesTitle;


    public CategoriesCardModel(Integer eduCategoriesImage, String eduCategoriesTitle) {
        this.eduCategoriesImage = eduCategoriesImage;
        this.eduCategoriesTitle = eduCategoriesTitle;
    }

    public Integer getEduCategoriesImage() {
        return eduCategoriesImage;
    }

    public String getEduCategoriesTitle() {
        return eduCategoriesTitle;
    }
}
