package com.example.weddingelements_android.model;

public class Review {
    private String reviewId;
    private String name;
    private String email;
    private String review;
    private int score;
    private String advertisementId;

    public Review(String reviewId, String name, String email, String review, int score, String advertisementId) {
        this.reviewId = reviewId;
        this.name = name;
        this.email = email;
        this.review = review;
        this.score = score;
        this.advertisementId = advertisementId;
    }

    public Review() {
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(String advertisementId) {
        this.advertisementId = advertisementId;
    }
}
