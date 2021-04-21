package launch;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

/**
 * Launch adapted from Heroku devcenter's example
 * 
 * https://devcenter.heroku.com/articles/create-a-java-web-application-using-embedded-tomcat
 * https://github.com/heroku/devcenter-embedded-tomcat
 */
public class Main {

    public static void main(String[] args) throws Exception {

        // Jos "PORT" löytyy ympäristömuuttujista, käytetään sitä. Muussa tapauksessa
        // käytetään porttia 8080:
        int httpPort = Integer.valueOf(System.getenv().getOrDefault("PORT", "8080"));

        Tomcat tomcat = createServer(httpPort);

        // Käynnistetään palvelin ja odotetaan yhteyksiä:
        tomcat.start();
        tomcat.getServer().await();
    }

    public static Tomcat createServer(int httpPort) {
        // Tomcat-palvelin, joka huolehtii HTTP-liikenteestä:
        Tomcat tomcat = new Tomcat();

        // Asetetaan kuunneltava Tomcatin HTTP-portti.
        tomcat.setPort(httpPort);

        // Luodaan Connector-olio, joka kuuntelee asettamaamme porttia:
        tomcat.getConnector();

        // Web-sovelluksen julkisten tiedostojen sijainti:
        String webappDirPath = new File("src/main/webapp/").getAbsolutePath();

        // Lisätään oma sovelluksemme Tomcatiin palvelimen juureen:
        Context webApp = tomcat.addWebapp("", webappDirPath);

        // HUOM! Jos haluat, että palvelin käynnistää itsensä uudelleen muutettuasi
        // tiedostoja, poista kommentti seuraavalta riviltä:
        // webApp.setReloadable(true);

        // Määritellään sovelluksemme resurssien sijainnit:
        WebResourceRoot resources = new StandardRoot(webApp);
        resources.addPreResources(
                new DirResourceSet(resources, "/WEB-INF/classes", new File("target/classes").getAbsolutePath(), "/"));
        webApp.setResources(resources);

        // Asetetaan UTF-8 -merkistö HTTP-pyyntöihin ja -vastauksiin:
        webApp.setRequestCharacterEncoding("utf-8");
        webApp.setResponseCharacterEncoding("utf-8");

        return tomcat;
    }
}