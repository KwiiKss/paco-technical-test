package technical.test.renderer.facades;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.services.FlightService;
import technical.test.renderer.viewmodels.FlightForm;
import technical.test.renderer.viewmodels.FlightViewModel;

@Component
public class FlightFacade {

    private final FlightService flightService;

    public FlightFacade(FlightService flightService) {
        this.flightService = flightService;
    }

    public Flux<FlightViewModel> getFlights(String origin, String destination, String sortPrice, int page, int size) {
        return this.flightService.getFlights(origin, destination, sortPrice, page, size);
    }

    public Mono<FlightViewModel> createFlight(FlightForm flightForm) {
        return this.flightService.createFlight(flightForm);
    }
}
