package api;

import okhttp3.OkHttpClient;

public class FilemakerClient {

    private FilemakerConnector filemakerConnector;
    private OkHttpClient client;

    public FilemakerClient() {
        this.filemakerConnector = new FilemakerConnector();
        this.client = new OkHttpClient();
    }

    private void makeAPICallFilemaker(){


    }


}
