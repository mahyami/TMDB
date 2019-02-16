package web_handlers.interfaces;

import java.util.List;

public interface IGetSeriesList<T> {
    void getSeriesListResponse(List<T> respList, int statusCode, String statusMsg);

}
