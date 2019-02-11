package models;

public class SeriesDetailModel {
    private String backDropPath;
    private Double voteAvg;
    private String firstAirDate;
    private int voteCount;
    private String overview;
    private String origName;

    public SeriesDetailModel(String backDropPath, Double voteAvg, String firstAirDate,
                             int voteCount, String overview, String origName) {
        this.backDropPath = backDropPath;
        this.voteAvg = voteAvg;
        this.firstAirDate = firstAirDate;
        this.voteCount = voteCount;
        this.overview = overview;
        this.origName = origName;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public Double getVoteAvg() {
        return voteAvg;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getOverview() {
        return overview;
    }

    public String getOrigName() {
        return origName;
    }
}
