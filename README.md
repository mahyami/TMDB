# TMDB
This is an application for fetching top series from TMDB API's.

## API's used
For accessing TMDB API's, you have to register for an account and get an API key. Without that, nothing can be done.
```
https://developers.themoviedb.org/3/tv/get-tv-details
https://developers.themoviedb.org/3/genres/get-tv-list
https://developers.themoviedb.org/3/tv/get-similar-tv-shows
```
All the pictures are available on the URL below
```
https://image.tmdb.org/t/p/w600_and_h900_bestv2/{picture_id}
```
## Libraries used
No complicated libraries where used for this project. Here is a list of few that have been used.
```
implementation 'com.android.support:cardview-v7:27.1.1'
implementation 'com.android.volley:volley:1.1.0'
implementation 'com.github.bumptech.glide:glide:4.9.0'
```
### Some Notes
All of the requests from the application to the server are handled under 
```
main/java/web_handlers/VolleyHandler
```
All activitiew are in the location below
```
main/java/com/swapcard/tmdb
```

If you need more clarification please contact me :)
