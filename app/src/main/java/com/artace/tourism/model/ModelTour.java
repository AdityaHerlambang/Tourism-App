package com.artace.tourism.model;

public class ModelTour {

    String id, country_id, tour_provider_id;
    String name, location;
    String short_description, overview, activity;
    int minimum_person;
    String preparation;
    String image;
    String location_latitude, location_longitude;
    String nearest_airport;
    String country;
    String customStatus;
    String category, status_popularity;
    int verified_by_admin;
    double adult_price, child_price;
    int max_capacity;
    int duration_hour;
    int duration_day;

    public ModelTour() {
    }

    public ModelTour(String id, String country_id, String tour_provider_id, String name, String location, String short_description, String overview, String activity, int minimum_person, String preparation, String image, String location_latitude, String location_longitude, String nearest_airport, String status_popularity, int verified_by_admin, double adult_price, double child_price, int max_capacity, int duration_hour, int duration_day) {
        this.id = id;
        this.country_id = country_id;
        this.tour_provider_id = tour_provider_id;
        this.name = name;
        this.location = location;
        this.short_description = short_description;
        this.overview = overview;
        this.activity = activity;
        this.minimum_person = minimum_person;
        this.preparation = preparation;
        this.image = image;
        this.location_latitude = location_latitude;
        this.location_longitude = location_longitude;
        this.nearest_airport = nearest_airport;
        this.status_popularity = status_popularity;
        this.verified_by_admin = verified_by_admin;
        this.adult_price = adult_price;
        this.child_price = child_price;
        this.max_capacity = max_capacity;
        this.duration_hour = duration_hour;
        this.duration_day = duration_day;
    }

    public String getStatus_popularity() {
        return status_popularity;
    }

    public void setStatus_popularity(String status_popularity) {
        this.status_popularity = status_popularity;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getTour_provider_id() {
        return tour_provider_id;
    }

    public void setTour_provider_id(String tour_provider_id) {
        this.tour_provider_id = tour_provider_id;
    }

    public String getCustomStatus() {
        return customStatus;
    }

    public void setCustomStatus(String customStatus) {
        this.customStatus = customStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getMinimum_person() {
        return minimum_person;
    }

    public void setMinimum_person(int minimum_person) {
        this.minimum_person = minimum_person;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation_latitude() {
        return location_latitude;
    }

    public void setLocation_latitude(String location_latitude) {
        this.location_latitude = location_latitude;
    }

    public String getLocation_longitude() {
        return location_longitude;
    }

    public void setLocation_longitude(String location_longitude) {
        this.location_longitude = location_longitude;
    }

    public String getNearest_airport() {
        return nearest_airport;
    }

    public void setNearest_airport(String nearest_airport) {
        this.nearest_airport = nearest_airport;
    }

    public int getVerified_by_admin() {
        return verified_by_admin;
    }

    public void setVerified_by_admin(int verified_by_admin) {
        this.verified_by_admin = verified_by_admin;
    }

    public double getAdult_price() {
        return adult_price;
    }

    public void setAdult_price(double adult_price) {
        this.adult_price = adult_price;
    }

    public double getChild_price() {
        return child_price;
    }

    public void setChild_price(double child_price) {
        this.child_price = child_price;
    }

    public int getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }

    public int getDuration_hour() {
        return duration_hour;
    }

    public void setDuration_hour(int duration_hour) {
        this.duration_hour = duration_hour;
    }

    public int getDuration_day() {
        return duration_day;
    }

    public void setDuration_day(int duration_day) {
        this.duration_day = duration_day;
    }
}
