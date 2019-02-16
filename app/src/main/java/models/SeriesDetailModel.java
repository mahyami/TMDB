package models;

import java.util.List;

public class SeriesDetailModel {
    private SeriesModel seriesModel;
    private String backDropPath;
    private String overview;
    private String firstAirDate;
    private boolean inProduction;
    private List<GenreModel> genreModels;
    private List<GenericModel> creatorModels;
    private List<GenericModel> companyModels;

    public SeriesDetailModel(SeriesModel seriesModel, String backDropPath, String overview,
                             String firstAirDate, boolean inProduction,
                             List<GenreModel> genreModels, List<GenericModel> creatorModels, List<GenericModel> companyModels) {
        this.seriesModel = seriesModel;
        this.backDropPath = backDropPath;
        this.overview = overview;
        this.firstAirDate = firstAirDate;
        this.inProduction = inProduction;
        this.genreModels = genreModels;
        this.creatorModels = creatorModels;
        this.companyModels = companyModels;
    }

    public SeriesModel getSeriesModel() {
        return seriesModel;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public List<GenericModel> getCreatorModels() {
        return creatorModels;
    }

    public List<GenericModel> getCompanyModels() {
        return companyModels;
    }

    public List<GenreModel> getGenreModels() {
        return genreModels;
    }
}
