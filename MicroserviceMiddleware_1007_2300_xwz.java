// 代码生成时间: 2025-10-07 23:00:36
 * inter-service communication, error handling, and ensures the program's
 * maintainability and scalability.
 */

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.io.IOException;

@Component
public class MicroserviceMiddleware {

    private HttpClient httpClient;

    @PostConstruct
    public void init() {
        // Initializes the HttpClient with a connection timeout of 10 seconds
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    /**
     * Sends a GET request to the specified service URL and returns the response.
     *
     * @param serviceUrl The URL of the service to contact.
     * @return The string response from the service.
     * @throws IOException If an I/O error occurs.
     */
    public String getFromService(String serviceUrl) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /**
     * Sends a POST request to the specified service URL with a JSON payload and returns the response.
     *
     * @param serviceUrl The URL of the service to contact.
     * @param payload The JSON payload to send.
     * @return The string response from the service.
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the thread is interrupted.
     */
    public String postToService(String serviceUrl, String payload) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serviceUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Additional methods for other HTTP methods like PUT, DELETE can be added here

    // Error handling and logging can be enhanced as per the application's requirements
}
