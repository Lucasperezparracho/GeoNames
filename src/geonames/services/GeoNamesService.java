package geonames.services;

import geonames.models.Place;
import geonames.utils.HttpService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GeoNamesService {

    private HttpService httpService;
    private final String username = "luquinhan03";

    public GeoNamesService() {
        httpService = new HttpService();
    }

    public List<Place> searchPlaces(String query) {
        List<Place> places = new ArrayList<>();

        try {
            String urlString = "http://api.geonames.org/searchJSON?q=" + query + "&maxRows=10&username=" + username;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String output;
            StringBuilder responseBuilder = new StringBuilder();
            while ((output = br.readLine()) != null) {
                responseBuilder.append(output);
            }

            JSONObject response = new JSONObject(responseBuilder.toString());
            JSONArray geonames = response.getJSONArray("geonames");
            for (int i = 0; i < geonames.length(); i++) {
                JSONObject placeJson = geonames.getJSONObject(i);
                int geonameId = placeJson.getInt("geonameId");
                String name = placeJson.getString("name");
                double lat = placeJson.getDouble("lat");
                double lng = placeJson.getDouble("lng");
                Place place = new Place(geonameId, name, lat, lng,0);
                places.add(place);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return places;
    }

    public Place getPlaceDetails(int geonameId) {
        try {
            String urlString = "http://api.geonames.org/getJSON?geonameId=" + geonameId + "&username=" + username;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder responseBuilder = new StringBuilder();
            while ((output = br.readLine()) != null) {
                responseBuilder.append(output);
            }

            JSONObject response = new JSONObject(responseBuilder.toString());
            String name = response.getString("name");
            double lat = response.getDouble("lat");
            double lng = response.getDouble("lng");

            conn.disconnect();

            return new Place(geonameId, name, lat, lng,0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Place> getNearbyPlaces(double latitude, double longitude, int radius) {
        List<Place> places = new ArrayList<>();

        try {
            String urlString = "http://api.geonames.org/findNearbyJSON?lat=" + latitude + "&lng=" + longitude + "&radius=" + radius + "&username=" + username;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder responseBuilder = new StringBuilder();
            while ((output = br.readLine()) != null) {
                responseBuilder.append(output);
            }

            JSONObject response = new JSONObject(responseBuilder.toString());
            JSONArray geonames = response.getJSONArray("geonames");
            for (int i = 0; i < geonames.length(); i++) {
                JSONObject placeObject = geonames.getJSONObject(i);
                int geonameId = placeObject.getInt("geonameId");
                String name = placeObject.getString("name");
                double lat = placeObject.getDouble("lat");
                double lng = placeObject.getDouble("lng");
                double distance = calculateDistance(latitude, longitude, lat, lng);
                Place place = new Place(geonameId, name, lat, lng, distance);
                places.add(place);
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return places;
    }

    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371; // Radio de la Tierra en kilómetros
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c * 1000; // Distancia en metros
        return distance;
    }

}
