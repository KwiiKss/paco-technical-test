package technical.test.renderer.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.renderer.clients.TechnicalApiClient;
import technical.test.renderer.viewmodels.FlightForm;
import technical.test.renderer.viewmodels.FlightViewModel;

@Service
public class FlightService {
    private final TechnicalApiClient technicalApiClient;

    public FlightService(TechnicalApiClient technicalApiClient) {
        this.technicalApiClient = technicalApiClient;
    }

    public Flux<FlightViewModel> getFlights(String origin, String destination, String sortPrice, int page, int size) {
        return this.technicalApiClient.getFlights(origin, destination, sortPrice, page, size);
    }
    public Mono<FlightViewModel> createFlight(FlightForm flightForm) {
        return this.technicalApiClient.createFlight(flightForm);
    }
}
