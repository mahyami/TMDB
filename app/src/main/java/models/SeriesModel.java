package models;

import java.util.List;

public class SeriesModel {
    private int id;
    private String name;
    private List<Integer> genres;
    private String origLang;
    private String posterPath;
    private Double voteAvg;

    public SeriesModel(int id, String name, List<Integer> genres, String origLang, String posterPath,
                       Double voteAvg) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.origLang = origLang;
        this.posterPath = posterPath;
        this.voteAvg = voteAvg;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getGenres() {
        return genres;
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


}
