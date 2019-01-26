package com.artace.tourism.connection;

public class DatabaseConnection {

    // Local
    public static String BASE_URL = "http:/tourismapi.000webhostapp.com/";

    //get country
    public static String COUNTRY_URL = BASE_URL+"tour/country";

    public static String CATEGORY_URL = BASE_URL+"tour/category";

    public static String TOUR_URL = BASE_URL+"tour/index";

    public static String SEARCH_TOUR_URL = BASE_URL+"tour/search";

    public static String getCountryUrl(String id) {
        return COUNTRY_URL+"/"+id;
    }

    public static String getCategoryUrl(String id) {
        return CATEGORY_URL+"/"+id;
    }
}
