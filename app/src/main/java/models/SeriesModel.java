package models;

import java.util.List;

public class SeriesModel {
    private int id;
    private String name;
    private List<String> genres;
    private List<String> countryOrigin;
    private String origLang;
    private String posterPath;
    private Double voteAvg;
    private SeriesDetailModel details;

    public SeriesModel(int id, String name, List<String> genres,
                       List<String> countryOrigin, String origLang, String posterPath,
                       Double voteAvg, SeriesDetailModel details) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.countryOrigin = countryOrigin;
        this.origLang = origLang;
        this.posterPath = posterPath;
        this.voteAvg = voteAvg;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getCountryOrigin() {
        return countryOrigin;
    }

    public String getOrigLang() {
        return origLang;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public Double getVoteAvg() {
        return voteAvg;
    }

    public SeriesDetailModel getDetails() {
        return details;
    }
}
