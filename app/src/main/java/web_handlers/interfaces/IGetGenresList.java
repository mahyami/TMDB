package web_handlers.interfaces;

import java.util.List;

public interface IGetGenresList<T> {
    void getGenreListResponse(List<T> respList, int statusCode, String statusMsg);

}
