public record GeoBox(GeoPoint northeast, GeoPoint southwest) {
    public GeoBox {
        if (northeast.latitude() < -80 || northeast.latitude() > 80 || southwest.latitude() < -80 || southwest.latitude() > 80) {
            throw new IllegalArgumentException("Invalid latitude");
        }
        if (Math.abs(northeast.latitude() - southwest.latitude()) > 30) {
            throw new IllegalArgumentException("Invalid vertical span");
        }
        if (Math.abs(northeast.longitude() - southwest.longitude()) > 30) {
            throw new IllegalArgumentException("Invalid horizontal span");
        }
        if (northeast.latitude() < southwest.latitude() || northeast.longitude() < southwest.longitude()) {
            throw new IllegalArgumentException("Invalid corners");
        }
    }

    public GeoPoint getCenter() {
        double centerLatitude = (northeast.latitude() + southwest.latitude()) / 2;
        double centerLongitude = (northeast.longitude() + southwest.longitude()) / 2;
        if (Math.abs(northeast.longitude() - southwest.longitude()) > 180) {
            centerLongitude += (centerLongitude < 0) ? 180 : -180;
        }
        return new GeoPoint(centerLatitude, centerLongitude);
    }

    public GeoPoint getNorthwest() {
        return new GeoPoint(northeast.latitude(), southwest.longitude());
    }

    public GeoPoint getSoutheast() {
        return new GeoPoint(southwest.latitude(), northeast.longitude());
    }
}

public class GeoBox {
    private final GeoPoint neCorner;
    private final GeoPoint swCorner;

    public GeoBox(GeoPoint neCorner, GeoPoint swCorner) {
        validateCorners(neCorner, swCorner);
        this.neCorner = neCorner;
        this.swCorner = swCorner;
    }

    private void validateCorners(GeoPoint neCorner, GeoPoint swCorner) {
        if (neCorner.latitude() <= swCorner.latitude()) {
            throw new IllegalArgumentException("Invalid corners: NE corner must be northeast of SW corner");
        }
        double latSpan = neCorner.latitude() - swCorner.latitude();
        double lonSpan = neCorner.longitude() > swCorner.longitude() ?
                         neCorner.longitude() - swCorner.longitude() :
                         (neCorner.longitude() + 360) - swCorner.longitude();

        if (latSpan > 30 || lonSpan > 30 || neCorner.latitude() > 80 || swCorner.latitude() < -80) {
            throw new IllegalArgumentException("Invalid GeoBox: Latitude and longitude spans exceed limits");
        }
    }
    public GeoPoint getCenter() {
        double centerLat = (neCorner.latitude() + swCorner.latitude()) / 2;
        double centerLon = (neCorner.longitude() + swCorner.longitude() +
                            (neCorner.longitude() < swCorner.longitude() ? 360 : 0)) / 2;
        centerLon = (centerLon > 180) ? centerLon - 360 : centerLon;
        return new GeoPoint(centerLat, centerLon);
    }
}


import java.util.ArrayList;
import java.util.List;

public class Street {
    private GeoBox boundingBox;
    private String name;
    private int length;

    public Street(GeoBox boundingBox, String name, int length) {
        this.boundingBox = boundingBox;
        this.name = name;
        this.length = length;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class City {
    private String name;
    private int population;
    private List<Street> streets;

    public City(String name, int population) {
        this.name = name;
        this.population = population;
        this.streets = new ArrayList<>();
    }

    public void updatePopulation(int population) {
        this.population = population;
    }

    public void addStreet(Street street) {
        this.streets.add(street);
    }
}

public class Address {
    private Street street;
    private City city;
    private String addressNumber;

    public Address(Street street, City city, String addressNumber) {
        this.street = street;
        this.city = city;
        this.addressNumber = addressNumber;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s", street, city, addressNumber);
    }
}