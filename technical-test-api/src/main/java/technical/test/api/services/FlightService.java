package technical.test.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import technical.test.api.record.FlightRecord;
import technical.test.api.repository.FlightRepository;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public Flux<FlightRecord> getFlights(String origin, String destination, String sortPrice, int page, int size) {

        Sort sort = "desc".equalsIgnoreCase(sortPrice)
                ? Sort.by("price").descending()
                : Sort.by("price").ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        if (origin != null && destination != null) {
            return flightRepository.findByOriginAndDestination(origin, destination, pageable);
        } else if (origin != null) {
            return flightRepository.findByOrigin(origin, pageable);
        } else if (destination != null) {
            return flightRepository.findByDestination(destination, pageable);
        } else {
            return flightRepository.findAllBy(pageable);
        }
    }

    public Mono<FlightRecord> saveFlight(FlightRecord flightRecord) {
        return flightRepository.save(flightRecord);
    }
}