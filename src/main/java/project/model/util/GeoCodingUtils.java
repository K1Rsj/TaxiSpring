package project.model.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mapbox.services.api.directions.v5.DirectionsCriteria;
import com.mapbox.services.api.directions.v5.MapboxDirections;
import com.mapbox.services.api.directions.v5.models.DirectionsResponse;
import com.mapbox.services.api.directions.v5.models.DirectionsRoute;
import com.mapbox.services.commons.models.Position;

import javafx.util.Pair;
import project.constant.MapBoxAPIKeys;
import project.model.exception.NoStreetWithSuchName;
import retrofit2.Response;

public class GeoCodingUtils {

    private static String getRequest(String url) {

        try {
            final URL obj = new URL(url);
            final HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Pair<Double, Double> getCoordinates(String address) throws UnsupportedEncodingException, NoStreetWithSuchName {
        Pair<Double, Double> coordinates = null;
        StringBuilder query = new StringBuilder();
        String[] split = address.split(" ");

        query.append("https://nominatim.openstreetmap.org/search?q=").append(URLEncoder.encode("Киев", "utf-8")).append("+");

        for (int i = 0; i < split.length; i++) {
            query.append(URLEncoder.encode(split[i], "utf-8"));
            if (i < (split.length - 1)) {
                query.append("+");
            }
        }
        query.append("&format=json&addressdetails=1");

        String queryResult = getRequest(query.toString());

        if (queryResult.equals("[]")) {
            throw new NoStreetWithSuchName(address);
        }

        Object obj = JSONValue.parse(queryResult);

        JSONArray array = (JSONArray) obj;
        if (!array.isEmpty()) {
            JSONObject jsonObject = (JSONObject) array.get(0);

            Double lon = Double.parseDouble((String)jsonObject.get("lon"));
            Double lat = Double.parseDouble((String)jsonObject.get("lat"));
            coordinates = new Pair<>(lon, lat);
        }

        return coordinates;
    }

    static List<DirectionsRoute> getRouteInformation(String departureStreet, String destinationStreet) throws NoStreetWithSuchName{
        Response<DirectionsResponse> directionsResponse;
        try {
        MapboxDirections client = new MapboxDirections.Builder<>()
                .setAccessToken(MapBoxAPIKeys.publicToken)
                .setOrigin(Position.fromCoordinates(Objects.requireNonNull(GeoCodingUtils.getCoordinates(departureStreet)).getKey(),
                        Objects.requireNonNull(GeoCodingUtils.getCoordinates(departureStreet)).getValue()))
                .setDestination(Position.fromCoordinates(Objects.requireNonNull(GeoCodingUtils.getCoordinates(destinationStreet)).getKey(),
                        Objects.requireNonNull(GeoCodingUtils.getCoordinates(destinationStreet)).getValue()))
                .setProfile(DirectionsCriteria.PROFILE_DRIVING)
                .setLanguage("uk")
                .build();
            directionsResponse = client.executeCall();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return directionsResponse.body().getRoutes();
    }

    public static void checkForStreetNamesExistence(String departureStreet, String destinationStreet) throws NoStreetWithSuchName {
        try {
            getCoordinates(departureStreet);
            getCoordinates(destinationStreet);
        } catch (NoStreetWithSuchName e) {
            throw new NoStreetWithSuchName(e.getStreetName());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
