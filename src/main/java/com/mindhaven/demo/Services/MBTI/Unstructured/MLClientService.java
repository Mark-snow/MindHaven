package com.mindhaven.demo.Services.MBTI.Unstructured;

import java.time.Duration;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.mindhaven.demo.Entities.MBTIAxis;
import com.mindhaven.demo.Exceptions.MLServiceException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MLClientService {
    
    private final WebClient mlWebClient;

    private static final Logger log = LoggerFactory.getLogger(MLClientService.class);

    public Map<MBTIAxis, Double> predictProbabilities(String text) {
        try {
            MLResponseDTO response = mlWebClient.post()
                .uri("/predict")  // Your ML API endpoint
                .bodyValue(new MLRequestDTO(text))
                .retrieve()
                .onStatus(
                    status -> !status.is2xxSuccessful(),
                    clientResponse -> handleError(clientResponse)
                )
                .bodyToMono(MLResponseDTO.class)
                .block(Duration.ofSeconds(10));  // Timeout

            return parseProbabilities(response);
            
        } catch (WebClientResponseException e) {
            log.error("ML API error: {}", e.getResponseBodyAsString());
            throw new MLServiceException("Failed to call ML API");
        } catch (Exception e) {
            log.error("Unexpected error calling ML API", e);
            throw new MLServiceException("Service unavailable");
        }
    }

    private Mono<? extends Throwable> handleError(ClientResponse response) {
        return response.bodyToMono(String.class)
            .flatMap(body -> Mono.error(new MLServiceException(
                "ML API error: " + response.statusCode() + " - " + body
            )));
    }

    private Map<MBTIAxis, Double> parseProbabilities(MLResponseDTO response) {
        // Convert ML API response to your domain model
        return Map.of(
            MBTIAxis.E_I, response.getProbabilities().get("E/I").get("I"),
            MBTIAxis.S_N, response.getProbabilities().get("S/N").get("N"),
            MBTIAxis.T_F, response.getProbabilities().get("T/F").get("F"),
            MBTIAxis.J_P, response.getProbabilities().get("J/P").get("P")
        );
    }
}
