package models;

import java.util.List;

public class SeriesDetailModel {
    private SeriesModel seriesModel;
    private String backDropPath;
    private String overview;
    private List<GenreModel> genreModels;
    private List<GenericModel> creatorModels;
    private List<GenericModel> companyModels;

    public SeriesDetailModel(SeriesModel seriesModel, String backDropPath, String overview,
                             List<GenreModel> genreModels, List<GenericModel> creatorModels,
                             List<GenericModel> companyModels) {
        this.seriesModel = seriesModel;
        this.backDropPath = backDropPath;
        this.overview = overview;
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
