package geonames.models;

public class Place {
    private int geonameId;
    private String name;
    private double latitude;
    private double longitude;
    private double distance;

    public Place(int geonameId, String name, double latitude, double longitude, double distance) {
        this.geonameId = geonameId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    public int getGeonameId() {
        return geonameId;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public double getDistance(){
        return distance;
    }
}
