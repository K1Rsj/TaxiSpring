package project.model.util;


import com.mapbox.services.api.directions.v5.DirectionsCriteria;
import com.mapbox.services.api.directions.v5.MapboxDirections;
import com.mapbox.services.api.directions.v5.models.DirectionsResponse;
import com.mapbox.services.api.directions.v5.models.DirectionsRoute;
import com.mapbox.services.commons.models.Position;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import project.constant.MapBoxAPIKeys;
import project.model.exception.NoStreetWithSuchName;
import retrofit2.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

public class GeoCodingUtils {

    private static String getRequest(String url) {

        try {
            final URL obj = new URL(url);
            final HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

//        if (con.getResponseCode() != 200) {
//            System.out.println("rip");
//            return null;
//        }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            return response.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static double[] getCoordinates(String address) throws UnsupportedEncodingException, NoStreetWithSuchName {
        double[] coordinates = new double[2];
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
        System.out.println(query);

        String queryResult = null;
        try {
            queryResult = getRequest(query.toString());
            System.out.println(queryResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (queryResult == null || queryResult.equals("[]")) {
            throw new NoStreetWithSuchName(address);
        }

        Object obj = JSONValue.parse(queryResult);

        JSONArray array = (JSONArray) obj;
        if (array.size() > 0) {
            JSONObject jsonObject = (JSONObject) array.get(0);

            String lon = (String) jsonObject.get("lon");
            String lat = (String) jsonObject.get("lat");
            System.out.println("lon=" + lon);
            System.out.println("lat=" + lat);
            coordinates[0] = Double.parseDouble(lon);
            coordinates[1] = Double.parseDouble(lat);
        }

        return coordinates;
    }

    public static List<DirectionsRoute> getRouteInformation(String departureStreet, String destinationStreet) throws NoStreetWithSuchName, IOException {
        MapboxDirections client = new MapboxDirections.Builder<>()
                .setAccessToken(MapBoxAPIKeys.publicToken)
                .setOrigin(Position.fromCoordinates(Objects.requireNonNull(GeoCodingUtils.getCoordinates(departureStreet))))
                .setDestination(Position.fromCoordinates(Objects.requireNonNull(GeoCodingUtils.getCoordinates(destinationStreet))))
                .setProfile(DirectionsCriteria.PROFILE_DRIVING)
                .build();
        Response<DirectionsResponse> directionsResponse = client.executeCall();
        List<DirectionsRoute> routes = directionsResponse.body().getRoutes();
        routes.stream().map(route -> route.getDistance() / 1000).forEach(System.out::println);
        routes.stream().map(route -> Math.round(route.getDuration() / 60)).forEach(System.out::println);
        System.out.println(directionsResponse.body().getRoutes());

        return routes;
    }
}
