package gr.compassbook.snorechat.server.connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerConnection {

    private final String SERVER_ADDRESS = "http://www.compassbook.gr/";
    private HttpURLConnection urlConnection;

    public ServerConnection() {
    }

    /**
     * Establishes a HttpURLConnection with GET as a RequestMethod
     *
     * @param uri the API uri that needs to be requested
     *
     * @return an HttpURLConnection ready to be fired
     */
    public HttpURLConnection openGetConnection(String uri) {

        try {
            URL url = new URL(SERVER_ADDRESS + uri);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Accept", "application/json");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlConnection;
    }
}
