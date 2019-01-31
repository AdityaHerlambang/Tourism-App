package com.artace.tourism.connection;

public class DatabaseConnection {

    // GLOBAL
//    public static String BASE_URL = "http://tourismapi.000webhostapp.com/";

    //Local
    public static String BASE_URL = "http://192.168.43.48:8000/";

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
}
