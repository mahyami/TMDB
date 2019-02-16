package web_handlers.interfaces;

import models.SeriesDetailModel;

public interface IGetSeriesDetail<T> {
    void getSeriesDetailResponse(SeriesDetailModel resp, int statusCode, String statusMsg);
}
