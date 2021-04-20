package launch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StartServerTest {

    private static final int TEST_PORT = 8888;
    private static final String SERVER_ROOT = "http://localhost:" + TEST_PORT;

    private static HttpClient client = HttpClient.newHttpClient();
    private static Tomcat server;

    @BeforeAll
    public static void startServer() throws LifecycleException {
        server = Main.createServer(TEST_PORT);
        server.start();
    }

    @AfterAll
    public static void stopServer() throws LifecycleException {
        server.stop();
    }

    @Test
    public void frontPageReturnsHttp200() throws IOException, InterruptedException {
        HttpResponse<String> response = makeRequest("/");

        assertEquals(200, response.statusCode());
    }

    @Test
    public void frontPageContainsCorrectText() throws IOException, InterruptedException {
        HttpResponse<String> response = makeRequest("/");

        assertTrue(response.body().contains("Congratulations"));
    }

    @Test
    public void tomcatReturnsPagesWithUtf8Encoding() {
        HttpResponse<String> response = makeRequest("/");

        String contentType = response.headers().firstValue("Content-Type").get().toLowerCase();

        assertTrue(contentType.contains("utf-8"));
        assertTrue(contentType.contains("text/html"));
    }

    @Test
    public void nonexistingPathsReturnHttp404() throws IOException, InterruptedException {
        HttpResponse<String> response = makeRequest("/this-is-not-found");

        assertEquals(404, response.statusCode());
    }

    private static HttpResponse<String> makeRequest(String url) {
        URI uri = URI.create(SERVER_ROOT + url);

        try {
            HttpRequest request = HttpRequest.newBuilder(uri).build();
            return client.send(request, BodyHandlers.ofString());

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
