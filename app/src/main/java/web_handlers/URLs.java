package web_handlers;

public class URLs {
    public static final String ROOT_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = "d010fc2ff3d1ee669b5775e7066c41bf";
    public static final String POPULAR_SERIES = ROOT_URL + "/tv/popular?api_key=" + API_KEY + "&language=en-US&page=";
    public static final String GENRES = ROOT_URL + "/genre/tv/list?api_key=" + API_KEY + "&language=en-US";
    public static final String SECOND_PART_OF_URL = "?api_key=" + API_KEY + "&language=en-US";
    public static final String SERIESCOVER = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/";

}
