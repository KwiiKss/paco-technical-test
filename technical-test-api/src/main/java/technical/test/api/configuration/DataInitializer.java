package technical.test.api.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import technical.test.api.record.AirportRecord;
import technical.test.api.record.FlightRecord;
import technical.test.api.repository.AirportRepository;
import technical.test.api.repository.FlightRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    @Override
    public void run(String... args) {
        airportRepository.count()
                .flatMap(count -> {
                    if (count == 0) {
                        System.err.println("Saving airports...");
                        return airportRepository.saveAll(generateAirports()).then();
                    }
                    return Mono.empty();
                })
                .then(
                        flightRepository.count()
                                .flatMap(count -> {
                                    if (count == 0) {
                                        System.err.println("Saving flights...");
                                        return flightRepository.saveAll(generateFlights()).then();
                                    }
                                    return Mono.empty();
                                })
                )
                .doOnError(Throwable::printStackTrace)
                .block();
    }

    private List<AirportRecord> generateAirports() {
        return List.of(
                AirportRecord.builder().iata("LAX").name("Los Angeles Airport").country("US").build(),
                AirportRecord.builder().iata("JFK").name("John F. Kennedy International Airport").country("US").build(),
                AirportRecord.builder().iata("ORD").name("O'Hare International Airport").country("US").build(),
                AirportRecord.builder().iata("SFO").name("San Francisco International Airport").country("US").build(),
                AirportRecord.builder().iata("LHR").name("London Heathrow Airport").country("UK").build(),
                AirportRecord.builder().iata("CDG").name("Charles de Gaulle Airport").country("France").build(),
                AirportRecord.builder().iata("HND").name("Haneda Airport").country("Japan").build(),
                AirportRecord.builder().iata("SYD").name("Sydney Kingsford-Smith Airport").country("Australia").build(),
                AirportRecord.builder().iata("PEK").name("Beijing Capital International Airport").country("China").build(),
                AirportRecord.builder().iata("DXB").name("Dubai International Airport").country("UAE").build(),
                AirportRecord.builder().iata("AMS").name("Amsterdam Airport Schiphol").country("Netherlands").build(),
                AirportRecord.builder().iata("FRA").name("Frankfurt Airport").country("Germany").build(),
                AirportRecord.builder().iata("MIA").name("Miami International Airport").country("US").build(),
                AirportRecord.builder().iata("NRT").name("Narita International Airport").country("Japan").build(),
                AirportRecord.builder().iata("ICN").name("Incheon International Airport").country("South Korea").build(),
                AirportRecord.builder().iata("YYZ").name("Toronto Pearson International Airport").country("Canada").build(),
                AirportRecord.builder().iata("MEX").name("Mexico City International Airport").country("Mexico").build(),
                AirportRecord.builder().iata("IST").name("Istanbul Airport").country("Turkey").build(),
                AirportRecord.builder().iata("LIS").name("Lisbon Airport").country("Portugal").build(),
                AirportRecord.builder().iata("KUL").name("Kuala Lumpur International Airport").country("Malaysia").build(),
                AirportRecord.builder().iata("BKK").name("Suvarnabhumi Airport").country("Thailand").build(),
                AirportRecord.builder().iata("CPT").name("Cape Town International Airport").country("South Africa").build()
        );
    }

    private List<FlightRecord> generateFlights() {
        return List.of(
                FlightRecord.builder().id(UUID.randomUUID()).origin("LAX").destination("PEK").image("https://www.hdwallpapers.in/download/high_resolution_flying_eagle_4k_8k_hd-3840x2160.jpg").departure(LocalDateTime.of(2023, 12, 9, 17, 0)).arrival(LocalDateTime.of(2023, 12, 10, 5, 0)).price(125.5).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("JFK").destination("LHR").image("https://wallpaperswide.com/download/aircraft_in_flight-wallpaper-2560x1600.jpg").departure(LocalDateTime.of(2023, 11, 15, 8, 30)).arrival(LocalDateTime.of(2023, 11, 15, 19, 45)).price(335.2).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("ORD").destination("CDG").image("https://skytraxratings.com/airports/chicago-ohare-airport-rating").departure(LocalDateTime.of(2023, 11, 20, 12, 15)).arrival(LocalDateTime.of(2023, 11, 20, 22, 30)).price(90.4).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("SFO").destination("HND").image("https://wallpapersmug.com/download/3840x2400/9a232e/airplane-take-off.jpg").departure(LocalDateTime.of(2023, 11, 25, 6, 45)).arrival(LocalDateTime.of(2023, 11, 25, 19, 10)).price(177.5).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("LHR").destination("AMS").image("https://www.pixel4k.com/wp-content/uploads/2019/01/airplane-fly-through-clouds-4k_1547938000.jpg").departure(LocalDateTime.of(2023, 12, 1, 10, 0)).arrival(LocalDateTime.of(2023, 12, 1, 16, 20)).price(189.9).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("HND").destination("SYD").image("https://images.pexels.com/photos/912050/pexels-photo-912050.jpeg?cs=srgb&dl=pexels-ahmed-muntasir-912050.jpg&fm=jpg").departure(LocalDateTime.of(2023, 12, 8, 9, 0)).arrival(LocalDateTime.of(2023, 12, 9, 0, 30)).price(89.5).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("SYD").destination("LAX").image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQsoC0JAnuaasjR8riSfIEHn0UXglok65vCcw&usqp=CAU").departure(LocalDateTime.of(2023, 12, 12, 18, 50)).arrival(LocalDateTime.of(2023, 12, 12, 9, 20)).price(78.12).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("PEK").destination("DXB").image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZl_oBM6fMFUXNv07GxIbGXWg_K2Jcgav0mw&usqp=CAU").departure(LocalDateTime.of(2023, 12, 17, 20, 15)).arrival(LocalDateTime.of(2023, 12, 18, 6, 40)).price(125.29).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("DXB").destination("IST").image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSA-GRBEaSch-EbKdP-RliWtLNfyOIBsbD-LQ&usqp=CAU").departure(LocalDateTime.of(2023, 12, 22, 14, 30)).arrival(LocalDateTime.of(2023, 12, 22, 18, 55)).price(165.5).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("AMS").destination("MIA").image("https://wallpapercave.com/wp/wp9362238.jpg").departure(LocalDateTime.of(2023, 12, 28, 16, 0)).arrival(LocalDateTime.of(2023, 12, 28, 22, 25)).price(49.24).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("MIA").destination("SYD").image("https://www.hdwallpapers.in/download/flying_aeroplane_on_light_blue_sky_4k_5k_hd_light_blue-3840x2160.jpg").departure(LocalDateTime.of(2023, 11, 15, 10, 30)).arrival(LocalDateTime.of(2023, 11, 15, 23, 45)).price(523.2).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("NRT").destination("LAX").image("https://wallpapercrafter.com/sizes/2560x1440/15076-aircraft-wing-flight-aerial-view-mountains-snow-4k.jpg").departure(LocalDateTime.of(2023, 11, 20, 14, 45)).arrival(LocalDateTime.of(2023, 11, 21, 2, 30)).price(432.5).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("ICN").destination("JFK").image("https://wallpapersmug.com/download/3840x2400/03b268/storm-airplane-on-fire-4k.jpg").departure(LocalDateTime.of(2023, 11, 25, 6, 15)).arrival(LocalDateTime.of(2023, 11, 25, 17, 20)).price(123.2).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("YYZ").destination("ORD").image("https://www.wallpapersshare.com/img/big/flying-bird-owl-4k.jpg").departure(LocalDateTime.of(2023, 12, 1, 8, 0)).arrival(LocalDateTime.of(2023, 12, 1, 11, 30)).price(990.5).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("MEX").destination("LHR").image("https://www.wallpapers13.com/wp-content/uploads/2019/06/Bird-in-flight-wings-Macaws-long-tailed-Parrots-Colorful-Birds-4k-ultra-hd-1610-desktop-backgrounds-for-pc-mac-laptop-tablet-mobile-phone.jpg").departure(LocalDateTime.of(2023, 12, 5, 9, 0)).arrival(LocalDateTime.of(2023, 12, 5, 18, 45)).price(21).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("IST").destination("SFO").image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRWcF76RuaEZjR-USu_oWIGz8MBUaQbBz3CGQ&usqp=CAU").departure(LocalDateTime.of(2023, 12, 8, 13, 30)).arrival(LocalDateTime.of(2023, 12, 9, 4, 10)).price(210.12).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("LIS").destination("CDG").image("https://wallpapercave.com/wp/wp9361775.jpg").departure(LocalDateTime.of(2023, 12, 12, 11, 0)).arrival(LocalDateTime.of(2023, 12, 12, 14, 40)).price(490).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("KUL").destination("AMS").image("https://images.alphacoders.com/738/738037.jpg").departure(LocalDateTime.of(2023, 12, 17, 16, 30)).arrival(LocalDateTime.of(2023, 12, 17, 23, 55)).price(125.4).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("BKK").destination("PEK").image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSUrIpyL-1uM9noiBLnH-z8NFi-j9GvvF-IBQ&usqp=CAU").departure(LocalDateTime.of(2023, 12, 22, 5, 45)).arrival(LocalDateTime.of(2023, 12, 22, 12, 20)).price(909.3).build(),
                FlightRecord.builder().id(UUID.randomUUID()).origin("CPT").destination("DXB").image("https://skytraxratings.com/airports/chicago-ohare-airport-rating").departure(LocalDateTime.of(2023, 12, 28, 19, 15)).arrival(LocalDateTime.of(2023, 12, 29, 2, 30)).price(306.5).build()
        );
    }
}
