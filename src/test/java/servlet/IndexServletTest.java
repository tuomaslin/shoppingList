package servlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.http.HttpResponse;

import org.apache.catalina.LifecycleException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import testserver.TestServer;

public class IndexServletTest {

    @BeforeAll
    public static void startServer() throws LifecycleException {
        TestServer.start();
    }

    @AfterAll
    public static void stopServer() throws LifecycleException {
        TestServer.stop();
    }

    @Test
    public void frontPageReturnsHttp200() {
        HttpResponse<String> response = TestServer.get("/");

        assertEquals(200, response.statusCode());
    }

    @Test
    public void frontPageContainsCorrectText() {
        HttpResponse<String> response = TestServer.get("/");

        assertTrue(response.body().contains("Congratulations"));
    }

    @Test
    public void pagesHaveUtf8Encoding() {
        HttpResponse<String> response = TestServer.get("/");

        String contentType = response.headers().firstValue("Content-Type").get().toLowerCase();

        assertTrue(contentType.contains("utf-8"));
        assertTrue(contentType.contains("text/html"));
    }

    @Test
    public void nonexistingPathsReturnHttp404() {
        HttpResponse<String> response = TestServer.get("/this-is-not-found");

        assertEquals(404, response.statusCode());
    }
}
