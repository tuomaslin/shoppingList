package testserver;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import launch.Main;

/**
 * Utility class for starting and stopping an embedded Tomcat server and making
 * requests to it. See IndexServletTest for example usage.
 * 
 * @author T. Havulinna
 */
public class TestServer {

    private static final int TEST_PORT = 8888;
    private static final String SERVER_ROOT = "http://localhost:" + TEST_PORT;

    private static Tomcat server;
    private static HttpClient client = HttpClient.newHttpClient();

    /**
     * Creates and starts an embedded Tomcat server that can be called in JUnit
     * tests.
     */
    public static void start() {
        try {
            if (server == null) {
                server = Main.createServer(TEST_PORT);
            }

            server.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stops the embedded tests. This should be called after a test suite has been
     * executed.
     */
    public static void stop() {
        try {
            server.stop();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Makes a GET request to the given path in the embedded Tomcat server.
     * 
     * @param path String such as "/" or "/todos/1.json"
     * @return HttpResponse<String> containing the headers and body of the response
     */
    public static HttpResponse<String> get(String path) {
        URI uri = URI.create(SERVER_ROOT + path);

        try {
            HttpRequest request = HttpRequest.newBuilder(uri).build();
            return client.send(request, BodyHandlers.ofString());

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
