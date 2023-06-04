package geonames.controllers;

import geonames.models.Place;
import geonames.services.GeoNamesService;

import java.util.List;

public class ApplicationController {
    private GeoNamesService geoNamesService;

    public ApplicationController() {
        geoNamesService = new GeoNamesService();
    }

    public List<Place> searchPlaces(String name) {
        return geoNamesService.searchPlaces(name);
    }

    public Place getPlaceDetails(int geonameId) {
        return geoNamesService.getPlaceDetails(geonameId);
    }

    public List<Place> getNearbyPlaces(double latitude, double longitude, int radius) {
        return geoNamesService.getNearbyPlaces(latitude, longitude, radius);
    }
}
