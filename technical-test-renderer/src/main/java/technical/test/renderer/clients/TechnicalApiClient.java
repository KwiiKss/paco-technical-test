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

import java.util.Optional;

@Component
@Slf4j
public class TechnicalApiClient {

    private final TechnicalApiProperties technicalApiProperties;
    private final WebClient webClient;

    public TechnicalApiClient(TechnicalApiProperties technicalApiProperties, final WebClient.Builder webClientBuilder) {
        this.technicalApiProperties = technicalApiProperties;
        this.webClient = webClientBuilder.baseUrl(technicalApiProperties.getUrl()).build();
    }

    public Flux<FlightViewModel> getFlights(String origin, String destination, String sortPrice, int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flight")
                        .queryParamIfPresent("origin", Optional.ofNullable(origin).filter(s -> !s.isEmpty()))
                        .queryParamIfPresent("destination", Optional.ofNullable(destination).filter(s -> !s.isEmpty()))
                        .queryParam("sortPrice", sortPrice)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
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
