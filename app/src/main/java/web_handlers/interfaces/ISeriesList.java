package web_handlers.interfaces;

import java.util.List;

public interface ISeriesList<T> {
    void seriesListResponse(List<T> reportList, int statusCode, String statusMsg);

}
