import com.mapbox.services.api.directions.v5.DirectionsCriteria;
import com.mapbox.services.api.directions.v5.MapboxDirections;
import com.mapbox.services.api.directions.v5.models.DirectionsResponse;
import com.mapbox.services.api.directions.v5.models.DirectionsRoute;
import com.mapbox.services.commons.models.Position;
import project.constant.MapBoxAPIKeys;
import project.model.util.GeoCodingUtils;
import retrofit2.Response;

import java.util.List;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws Exception {
        MapboxDirections client = new MapboxDirections.Builder<>()
                .setAccessToken(MapBoxAPIKeys.publicToken)
                .setOrigin(Position.fromCoordinates(Objects.requireNonNull(GeoCodingUtils.getCoordinates("Туполева 24"))))
                .setDestination(Position.fromCoordinates(Objects.requireNonNull(GeoCodingUtils.getCoordinates("Крещатик 50"))))
                .setProfile(DirectionsCriteria.PROFILE_DRIVING)
                .build();
        Response<DirectionsResponse> directionsResponse = client.executeCall();
        List<DirectionsRoute> routes = directionsResponse.body().getRoutes();
        routes.stream().map(route -> route.getDistance()/1000).forEach(System.out::println);
        routes.stream().map(route -> Math.round(route.getDuration()/60)).forEach(System.out::println);
        System.out.println(directionsResponse.body().getRoutes());
    }
}