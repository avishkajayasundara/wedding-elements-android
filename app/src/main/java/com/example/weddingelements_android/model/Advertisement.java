package com.example.weddingelements_android.model;

import java.io.Serializable;
import java.util.Date;

public class Advertisement implements Serializable {
    private String advertisementId;
    private String title;
    private String description;
    private String image;
    private String businessOwner;
    private Date publishedDate;
    private String category;
    private Double startingPrice;
    private Float score;
    private int numberOfReviews;

    public Advertisement(String advertisementId, String title, String description, String image, String businessOwner, Date publishedDate, String category, Double startingPrice, Float score, int numberOfReviews) {
        this.advertisementId = advertisementId;
        this.title = title;
        this.description = description;
        this.image = image;
        this.businessOwner = businessOwner;
        this.publishedDate = publishedDate;
        this.category = category;
        this.startingPrice = startingPrice;
        this.score = score;
        this.numberOfReviews = numberOfReviews;
    }

    public Advertisement() {
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public String getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(String advertisementId) {
        this.advertisementId = advertisementId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBusinessOwner() {
        return businessOwner;
    }

    public void setBusinessOwner(String businessOwner) {
        this.businessOwner = businessOwner;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(Double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
