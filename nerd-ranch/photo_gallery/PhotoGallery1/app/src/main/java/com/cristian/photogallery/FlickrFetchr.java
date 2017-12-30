package com.cristian.photogallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class FlickrFetchr {

    private static final String TAG = "alltest";

    private static final String API_KEY = "e6f91b864fc7f0b75b171ebd2be05b59";

    public byte[] getUrlBytes(String urlSpec) throws IOException {

        URL url = new URL(urlSpec);

        //calls openConnection() to create a connection object pointed at the URL
        /*
            URL.openConnection() returns a URLConnection , but because you are
            connecting to an http URL, you can cast it to HttpURLConnection.
            This gives you HTTP-specific interfaces for working with request methods,
            response codes, streaming methods, and more.
        */
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            //connect to your endpoint
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }

            /*
                Once you create your URL and open a connection, you call read()
                repeatedly until your connection runs out of data.
                The InputStream will yield bytes as they are available.
            */

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<GalleryItem> fetchItems() {

        List<GalleryItem> items = new ArrayList<>();

        try {

            /*  Here you use a Uri.Builder to build the complete URL for your Flickr API request.
                Uri.Builder is a convenience class for creating properly escaped parameterized
                URLs. Uri.Builder.appendQueryParameter(String, String)
                will automatically escape query strings for you.
            */
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }

        return items;
    }

    private void parseItems(List<GalleryItem> items, JSONObject jsonBody)
            throws IOException, JSONException
    {
        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");
        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);
            GalleryItem item = new GalleryItem();
            item.setId(photoJsonObject.getString("id"));
            item.setCaption(photoJsonObject.getString("title"));

            //ignore images that do not have an image url
            if (!photoJsonObject.has("url_s")) {
                continue;
            }

            item.setUrl(photoJsonObject.getString("url_s"));
            items.add(item);
        }
    }

}

/*

{
    "photos":
        {
            "page":1,
            "pages":10,
            "perpage":100,
            "total":1000,
            "photo":[
                {
                    "id":"24519121847",
                    "owner":"159383925@N04",
                    "secret":"9780bcf69d",
                    "server":"4727",
                    "farm":5,
                    "title":"Scattered Protests",
                    "ispublic":1,
                    "isfriend":0,
                    "isfamily":0
                },
                {"
                    id":"24519122897",
                    "owner":"145188035@N07",
                    "secret":"59e50b42a9",
                    "server":"4738",
                    "farm":5,
                    "title":"Foxit PhantomPDF Business 9.0.1.1049",
                    "ispublic":1,
                    "isfriend":0,
                    "isfamily":0,
                    "url_s":"https:\/\/farm5.staticflickr.com\/4738\/24519122897_59e50b42a9_m.jpg",
                    "height_s":"240",
                    "width_s":"240"
                }
            ]
        },
        "stat" : "ok"
}
 */