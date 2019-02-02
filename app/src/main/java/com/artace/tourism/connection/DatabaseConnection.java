package com.artace.tourism.connection;

public class DatabaseConnection {

    // GLOBAL
    public static String BASE_URL = "http://tourismapi.000webhostapp.com/";
//    public static String BASE_URL = "http://192.168.88.9:8000/";

    //Local
//    public static String BASE_URL = "http://192.168.43.48:8000/";

//    public static String BASE_URL = "http://10.10.22.199:8000/";

    //get country
    public static String COUNTRY_URL = BASE_URL+"tour/country";

    public static String CATEGORY_URL = BASE_URL+"tour/category";

    public static String POPULAR_COUNTRY_URL = BASE_URL+"tour/popularcountry";

    public static String POPULAR_CATEGORY_URL = BASE_URL+"tour/popularcategory";

    public static String TOUR_URL = BASE_URL+"tour/alltour";

    public static String INDEX_TOUR_URL = BASE_URL+"tour/index";

    public static String SEARCH_TOUR_URL = BASE_URL+"tour/search";

    public static String SEARCH_POPULAR_TOUR_URL = BASE_URL+"tour/search";
    public static String TOUR_DETAIL = BASE_URL+"tour/detail";
    public static String ALL_TOUR = BASE_URL+"tour/alltour";
    public static String CONFIRMATION_TRAVELER = BASE_URL+"provider/confirmation";

    public static String PROFILE_PROVIDER = BASE_URL+"provider/profile";

    public static String TOUR_PROVIDER = BASE_URL+"provider/tour";

    public static String TREVELER_PROVIDER = BASE_URL+"provider/treveler";

    public static String TREVELER_PER_TOUR_PROVIDER = BASE_URL+"provider/tourtreveler";

    public static String LOGIN = BASE_URL+"login";

    public static String registerTraveller = BASE_URL+"login/registerguest";
    public static String registerProvider= BASE_URL+"login/registerprovider";

    public static String makeBooking = BASE_URL+"booking/makebooking";

    public static String bookedTour = BASE_URL+"tour/booked";

    public static String CREATE_TOUR = BASE_URL+"provider/createtour";
    public static String UPDATE_TOUR = BASE_URL+"provider/updatetour";
    public static String DELETE_TOUR = BASE_URL+"provider/deletetour";

    public static String uploadImage = "http://ruangbudayadevelopment.000webhostapp.com/tourism/upload.php";


    public static String getProfileProvider(String id) {
        return PROFILE_PROVIDER+"/"+id;
    }

    public static String getTourProvider(String id) {
        return TOUR_PROVIDER+"/"+id;
    }

    public static String getTrevelerProvider(String id) {
        return TREVELER_PROVIDER+"/"+id;
    }

    public static String getTrevelerPerTourProvider(String id) {
        return TREVELER_PER_TOUR_PROVIDER+"/"+id;
    }

    public static String getCreateTour() {
        return CREATE_TOUR;
    }

    public static String getUpdateTour(String id) {
        return UPDATE_TOUR+"/"+id;
    }

    public static String getDeleteTour(String id) {
        return DELETE_TOUR+"/"+id;
    }

    public static String getConfirmationTraveler() {
        return CONFIRMATION_TRAVELER;
    }

    public static String getTourUrl() {
        return TOUR_URL;
    }

    public static String getCountryUrl(String id) {
        return COUNTRY_URL+"/"+id;
    }

    public static String getCategoryUrl(String id) {
        return CATEGORY_URL+"/"+id;
    }

    public static String getPopularCountryUrl(String id) {
        return POPULAR_COUNTRY_URL+"/"+id;
    }

    public static String getPopularCategoryUrl(String id) {
        return POPULAR_CATEGORY_URL+"/"+id;
    }

    public static String allCountries = BASE_URL+"country/allcountry";

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getAllCountries() {
        return allCountries;
    }

    public static String getSearchTourUrl() {
        return SEARCH_TOUR_URL;
    }

    public static String getSearchPopularTourUrl() {
        return SEARCH_POPULAR_TOUR_URL;
    }

    public static String getIndexTourUrl() {
        return INDEX_TOUR_URL;
    }

    public static String getTourDetail() {
        return TOUR_DETAIL;
    }

    public static String getAllTour() {
        return ALL_TOUR;
    }

    public static String getLOGIN() {
        return LOGIN;
    }

    public static String getRegisterTraveller() {
        return registerTraveller;
    }

    public static String getMakeBooking() {
        return makeBooking;
    }

    public static String getBookedTour() {
        return bookedTour;
    }

    public static String getRegisterProvider() {
        return registerProvider;
    }

    public static String getUploadImage() {
        return uploadImage;
    }
}
