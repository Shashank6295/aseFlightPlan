package FlightResources;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GPSCoordinate {
    private String latitude;
    private String longitude;
    private final Pattern DMS_LAT_PATTERN =
            Pattern.compile("(-?)([0-9]{1,3})�([0-5]?[0-9])'([0-5]?[0-9])\\.([0-9]{0,4})\\\"([NS])");
    private final Pattern DMS_LNG_PATTERN =
            Pattern.compile("(-?)([0-9]{1,3})�([0-5]?[0-9])'([0-5]?[0-9])\\.([0-9]{0,4})\\\"([EW])");

    public GPSCoordinate(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Double getLongitudeInDegree(){
        Matcher matcher = DMS_LNG_PATTERN.matcher(this.longitude.trim());
        if (matcher.matches()){
            double longitude = toDouble(matcher);
            if ((Math.abs(longitude) > 180)) {
                throw new NumberFormatException("Invalid longitude");
            }
            return longitude;
        }else {
            throw new NumberFormatException("Malformed DMS coordiniates");
        }
    }

    public Double getLatitudeInDegree(){
        Matcher matcher = DMS_LAT_PATTERN.matcher(this.latitude.trim());
        if (matcher.matches()){
        	
            double latitude = toDouble(matcher);
            if ((Math.abs(latitude) > 180)) {
                throw new NumberFormatException("Invalid latitude");
            }
            return latitude;
        }else {
        	//Maybe show these malformed coordinates?
            throw new NumberFormatException("Malformed DMS coordiniates");
        }
    }

    public Double getLatitudeInRadian(){
        return this.getLatitudeInDegree() * (Math.PI / 180);
    }

    public Double getLongitudeInRadian(){
        return this.getLongitudeInDegree() * (Math.PI / 180);
    }

    private Double toDouble(Matcher matcher){
        int sign = "".equals(matcher.group(1)) ? 1 : -1;
        double degrees = Double.parseDouble(matcher.group(2));
        double minutes = Double.parseDouble(matcher.group(3));
        double seconds = Double.parseDouble(matcher.group(4));
        int direction = "NE".contains(matcher.group(5)) ? 1 : -1;

        return sign * direction * (degrees + minutes / 60 + seconds / 3600 );
    }
}
