package technical.test.renderer.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.representation.AirportRepresentation;
import technical.test.api.representation.FlightRepresentation;
import technical.test.renderer.properties.TechnicalApiProperties;
import technical.test.renderer.viewmodels.FlightForm;
import technical.test.renderer.viewmodels.FlightViewModel;

@Component
@Slf4j
public class TechnicalApiClient {

    private final TechnicalApiProperties technicalApiProperties;
    private final WebClient webClient;

    public TechnicalApiClient(TechnicalApiProperties technicalApiProperties, final WebClient.Builder webClientBuilder) {
        this.technicalApiProperties = technicalApiProperties;
        this.webClient = webClientBuilder.build();
    }

    public Flux<FlightViewModel> getFlights() {
        return webClient
                .get()
                .uri(technicalApiProperties.getUrl() + technicalApiProperties.getFlightPath())
                .retrieve()
                .bodyToFlux(FlightViewModel.class);
    }

    public Mono<FlightViewModel> createFlight(FlightForm form) {
        FlightRepresentation apiRequest = new FlightRepresentation();
        apiRequest.setDeparture(form.getDeparture());
        apiRequest.setArrival(form.getArrival());
        apiRequest.setPrice(form.getPrice());
        apiRequest.setImage(form.getImage());

        AirportRepresentation origin = new AirportRepresentation();
        origin.setIata(form.getOrigin());
        apiRequest.setOrigin(origin);

        AirportRepresentation dest = new AirportRepresentation();
        dest.setIata(form.getDestination());
        apiRequest.setDestination(dest);

        return webClient.post()
                .uri(technicalApiProperties.getUrl() + "/flight/admin/flights")
                .bodyValue(apiRequest)
                .retrieve()
                .bodyToMono(FlightViewModel.class);
    }
}
