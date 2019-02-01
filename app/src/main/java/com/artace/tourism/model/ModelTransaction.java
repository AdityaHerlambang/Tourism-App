package com.artace.tourism.model;

public class ModelTransaction {
    int id, tour_id, guest_id, adult_qty, child_qty, confirmation, country_id;
    String tour_name, denied_reason, tour_start_date, firstname, lastname, country_name, phone_number;
    double price;

    String image, short_description ,name, location;

    public ModelTransaction(int id, int tour_id, int guest_id, int adult_qty, int child_qty, int confirmation, int country_id, String tour_name, String denied_reason, String tour_start_date, String firstname, String lastname, String country_name, String phone_number) {
        this.id = id;
        this.tour_id = tour_id;
        this.guest_id = guest_id;
        this.adult_qty = adult_qty;
        this.child_qty = child_qty;
        this.confirmation = confirmation;
        this.country_id = country_id;
        this.tour_name = tour_name;
        this.denied_reason = denied_reason;
        this.tour_start_date = tour_start_date;
        this.firstname = firstname;
        this.lastname = lastname;
        this.country_name = country_name;
        this.phone_number = phone_number;
    }

    public ModelTransaction(int tour_id, String tour_start_date, double price, String image, String short_description, String name, String location) {
        this.tour_id = tour_id;
        this.tour_start_date = tour_start_date;
        this.price = price;
        this.image = image;
        this.short_description = short_description;
        this.name = name;
        this.location = location;
    }

    public ModelTransaction() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTour_id() {
        return tour_id;
    }

    public void setTour_id(int tour_id) {
        this.tour_id = tour_id;
    }

    public int getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(int guest_id) {
        this.guest_id = guest_id;
    }

    public int getAdult_qty() {
        return adult_qty;
    }

    public void setAdult_qty(int adult_qty) {
        this.adult_qty = adult_qty;
    }

    public int getChild_qty() {
        return child_qty;
    }

    public void setChild_qty(int child_qty) {
        this.child_qty = child_qty;
    }

    public int getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(int confirmation) {
        this.confirmation = confirmation;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getTour_name() {
        return tour_name;
    }

    public void setTour_name(String tour_name) {
        this.tour_name = tour_name;
    }

    public String getDenied_reason() {
        return denied_reason;
    }

    public void setDenied_reason(String denied_reason) {
        this.denied_reason = denied_reason;
    }

    public String getTour_start_date() {
        return tour_start_date;
    }

    public void setTour_start_date(String tour_start_date) {
        this.tour_start_date = tour_start_date;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
