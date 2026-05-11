package technical.test.api.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.mapper.AirportMapper;
import technical.test.api.mapper.FlightMapper;
import technical.test.api.record.AirportRecord;
import technical.test.api.record.FlightRecord;
import technical.test.api.representation.FlightRepresentation;
import technical.test.api.services.AirportService;
import technical.test.api.services.FlightService;

@Component
@RequiredArgsConstructor
public class FlightFacade {

    private final FlightService flightService;
    private final AirportService airportService;
    private final FlightMapper flightMapper;
    private final AirportMapper airportMapper;

    public Flux<FlightRepresentation> getAllFlights() {
        return flightService.getAllFlights()
                .flatMap(this::enrichFlight);
    }

    public Mono<FlightRepresentation> createFlight(FlightRepresentation flightRepresentation) {

        FlightRecord flightRecord = flightMapper.convert(flightRepresentation);

        if (flightRecord.getId() == null) {
            flightRecord.setId(java.util.UUID.randomUUID());
        }

        return flightService.saveFlight(flightRecord)
                .flatMap(this::enrichFlight);
    }

    private Mono<FlightRepresentation> enrichFlight(FlightRecord flightRecord) {

        return airportService.findByIataCode(flightRecord.getOrigin())
                .zipWith(airportService.findByIataCode(flightRecord.getDestination()))
                .map(tuple -> {

                    AirportRecord origin = tuple.getT1();
                    AirportRecord destination = tuple.getT2();

                    FlightRepresentation response = flightMapper.convert(flightRecord);
                    response.setOrigin(airportMapper.convert(origin));
                    response.setDestination(airportMapper.convert(destination));

                    return response;
                });
    }
}
