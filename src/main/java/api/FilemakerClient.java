package api;

import okhttp3.*;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.security.cert.CertificateException;

import java.io.IOException;
import java.util.Objects;


public class FilemakerClient {

    private FilemakerConnector filemakerConnector;
    private OkHttpClient client;

    public FilemakerClient() {
        this.filemakerConnector = new FilemakerConnector();
        this.client = getUnsafeOkHttpClient();
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void makeAPICallFilemaker() throws IOException {
        String server = filemakerConnector.getApiUrl();  // Get the FileMaker API URL
        String database = filemakerConnector.getDatabase(); // Get the database name
        String username = filemakerConnector.getUsername();
        String password = filemakerConnector.getPassword();
        String version = filemakerConnector.getVersion();  // Get the API version
        System.out.println("database: " + database);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        String url = "https://" + server + "/fmi/data/" + version + "/databases/" + database + "/sessions";
        String credentials = Credentials.basic(username, password);  // Combine username and password


        JSONObject jsonBody = new JSONObject();
        jsonBody.put("apiUrl", url);
        jsonBody.put("databaseName", database);
        jsonBody.put("login", username);
        jsonBody.put("password", password);

        String jsonString = jsonBody.toString();
        // Use basic authentication
        System.out.println("Credentials: " + credentials);


        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(jsonString, mediaType);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Basic RGF0YUFQSUZ1bGxBY2Nlc3M6RGF0YUFQSUZ1bGxBY2Nlc3M=")
                .build();





        DataApi api = new DataApi(options);
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // Process the successful response
                String responseBody = response.body().string();
                // ... parse responseBody as needed
                System.out.println("API request successful: " + responseBody);
            } else {
                // Handle API errors
                System.out.println("API request failed with message : " + response.message());
                System.out.println("API request failed with code: " + response.code());
            }
        } catch (SSLHandshakeException e) {
            System.err.println("SSLHandshakeException occurred: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException occurred: " + e.getMessage());
        }
    }


}
