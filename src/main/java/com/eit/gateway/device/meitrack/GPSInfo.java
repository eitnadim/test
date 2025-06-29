package com.eit.gateway.device.meitrack;

import java.util.Date;

public class GPSInfo {

    private double latitude;
    private double longitude;
    private int speed;
    private float heading;
    private int satellites;
    private boolean valid;
    private Date timestamp;
    
    // Additional parameters for Meitrack P88L protocol
    private double hdop;              // Horizontal Dilution of Precision
    private double altitude;          // Altitude in meters
    private int course;              // Course/Direction in degrees (0-359)
    private int satelliteCount;      // Alternative satellite count field
    private boolean gpsFixed;        // GPS fix status
    private double accuracy;         // GPS accuracy
    private String fixType;          // GPS fix type (2D/3D)

    // Constructors
    public GPSInfo() {
        this.fixType = "No Fix";
    }

    // Getters and setters for original fields
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }

    public float getHeading() { return heading; }
    public void setHeading(float heading) { this.heading = heading; }

    public int getSatellites() { return satellites; }
    public void setSatellites(int satellites) { this.satellites = satellites; }

    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

    // Getters and setters for additional parameters
    public double getHdop() { return hdop; }
    public void setHdop(double hdop) { this.hdop = hdop; }

    public double getAltitude() { return altitude; }
    public void setAltitude(double altitude) { this.altitude = altitude; }

    public int getCourse() { return course; }
    public void setCourse(int course) { 
        this.course = course;
        // Also update heading for compatibility
        this.heading = (float) course;
    }

    public int getSatelliteCount() { return satelliteCount; }
    public void setSatelliteCount(int satelliteCount) { 
        this.satelliteCount = satelliteCount;
        // Also update satellites for compatibility
        this.satellites = satelliteCount;
    }

    public boolean isGpsFixed() { return gpsFixed; }
    public void setGpsFixed(boolean gpsFixed) { 
        this.gpsFixed = gpsFixed;
        // Also update valid for compatibility
        this.valid = gpsFixed;
    }

    public double getAccuracy() { return accuracy; }
    public void setAccuracy(double accuracy) { this.accuracy = accuracy; }

    public String getFixType() { return fixType; }
    public void setFixType(String fixType) { this.fixType = fixType; }

    // Utility methods
    public boolean hasValidCoordinates() {
        return latitude != 0 && longitude != 0 && valid && 
               latitude >= -90 && latitude <= 90 && 
               longitude >= -180 && longitude <= 180;
    }

    public boolean hasGoodSignal() {
        return satellites >= 4 && hdop > 0 && hdop < 5.0;
    }

    public String getFormattedCoordinates() {
        if (!hasValidCoordinates()) {
            return "Invalid coordinates";
        }
        return String.format("%.6f, %.6f", latitude, longitude);
    }

    public double getSpeedInMph() {
        return speed * 0.621371; // Convert km/h to mph
    }

    public double getSpeedInMps() {
        return speed * 0.277778; // Convert km/h to m/s
    }

    public double getSpeedInKnots() {
        return speed * 0.539957; // Convert km/h to knots
    }

    public String getCardinalDirection() {
        if (course < 0 || course > 360) return "Unknown";
        
        String[] directions = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE",
                              "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
        int index = (int) Math.round(course / 22.5) % 16;
        return directions[index];
    }

    public String getDetailedDirection() {
        if (course < 0 || course > 360) return "Unknown direction";
        
        String cardinal = getCardinalDirection();
        return String.format("%s (%d°)", cardinal, course);
    }

    public String getGPSQuality() {
        if (!valid) return "No Fix";
        
        if (satellites >= 8 && hdop <= 2.0) return "Excellent";
        else if (satellites >= 6 && hdop <= 3.0) return "Good";
        else if (satellites >= 4 && hdop <= 5.0) return "Fair";
        else if (satellites >= 3) return "Poor";
        else return "Very Poor";
    }

    public boolean isMoving() {
        return speed > 2; // Consider moving if speed > 2 km/h
    }

    public boolean isStationary() {
        return speed <= 2;
    }

    public String getAltitudeFormatted() {
        if (altitude == 0) return "0 m";
        return String.format("%.0f m", altitude);
    }

    public String getSpeedFormatted() {
        return String.format("%d km/h (%.1f mph)", speed, getSpeedInMph());
    }

    public boolean isIndoors() {
        // Rough estimation: low satellite count and high HDOP might indicate indoor location
        return satellites < 4 && hdop > 10.0;
    }

    public boolean hasRecentFix() {
        if (timestamp == null) return false;
        
        long currentTime = System.currentTimeMillis();
        long fixTime = timestamp.getTime();
        long timeDiff = currentTime - fixTime;
        
        // Consider fix recent if it's less than 5 minutes old
        return timeDiff < (5 * 60 * 1000);
    }

    public String getTimeSinceLastFix() {
        if (timestamp == null) return "Never";
        
        long currentTime = System.currentTimeMillis();
        long fixTime = timestamp.getTime();
        long timeDiff = currentTime - fixTime;
        
        if (timeDiff < 60000) { // Less than 1 minute
            return (timeDiff / 1000) + " seconds ago";
        } else if (timeDiff < 3600000) { // Less than 1 hour
            return (timeDiff / 60000) + " minutes ago";
        } else {
            return (timeDiff / 3600000) + " hours ago";
        }
    }

    public double getDistanceFrom(double otherLat, double otherLon) {
        if (!hasValidCoordinates()) return -1;
        
        // Haversine formula to calculate distance between two points
        double R = 6371; // Earth radius in km
        double dLat = Math.toRadians(otherLat - latitude);
        double dLon = Math.toRadians(otherLon - longitude);
        
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(otherLat)) *
                   Math.sin(dLon/2) * Math.sin(dLon/2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c; // Distance in km
    }

    public String getCoordinatesInDMS() {
        if (!hasValidCoordinates()) return "Invalid coordinates";
        
        return String.format("%s %s", 
            convertToDMS(latitude, true), 
            convertToDMS(longitude, false));
    }

    private String convertToDMS(double coordinate, boolean isLatitude) {
        String direction;
        if (isLatitude) {
            direction = coordinate >= 0 ? "N" : "S";
        } else {
            direction = coordinate >= 0 ? "E" : "W";
        }
        
        coordinate = Math.abs(coordinate);
        int degrees = (int) coordinate;
        coordinate = (coordinate - degrees) * 60;
        int minutes = (int) coordinate;
        double seconds = (coordinate - minutes) * 60;
        
        return String.format("%d°%d'%.2f\"%s", degrees, minutes, seconds, direction);
    }

    @Override
    public String toString() {
        return "GPSInfo{" +
                "coordinates=" + getFormattedCoordinates() +
                ", speed=" + getSpeedFormatted() +
                ", direction=" + getDetailedDirection() +
                ", altitude=" + getAltitudeFormatted() +
                ", satellites=" + satellites +
                ", hdop=" + String.format("%.1f", hdop) +
                ", quality=" + getGPSQuality() +
                ", fixType='" + fixType + '\'' +
                ", valid=" + valid +
                ", timestamp=" + (timestamp != null ? getTimeSinceLastFix() : "null") +
                '}';
    }

    public String toDetailedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== GPS Information ===\n");
        
        sb.append("Coordinates: ").append(getFormattedCoordinates()).append("\n");
        sb.append("Coordinates (DMS): ").append(getCoordinatesInDMS()).append("\n");
        sb.append("Speed: ").append(getSpeedFormatted()).append("\n");
        sb.append("Direction: ").append(getDetailedDirection()).append("\n");
        sb.append("Altitude: ").append(getAltitudeFormatted()).append("\n");
        
        sb.append("\n--- Signal Quality ---\n");
        sb.append("Satellites: ").append(satellites).append("\n");
        sb.append("HDOP: ").append(String.format("%.1f", hdop)).append("\n");
        sb.append("Accuracy: ").append(String.format("%.1f", accuracy)).append("\n");
        sb.append("Fix Type: ").append(fixType).append("\n");
        sb.append("GPS Quality: ").append(getGPSQuality()).append("\n");
        
        sb.append("\n--- Status ---\n");
        sb.append("Valid: ").append(valid).append("\n");
        sb.append("Fixed: ").append(gpsFixed).append("\n");
        sb.append("Moving: ").append(isMoving()).append("\n");
        sb.append("Recent Fix: ").append(hasRecentFix()).append("\n");
        sb.append("Last Fix: ").append(getTimeSinceLastFix()).append("\n");
        
        return sb.toString();
    }
}
