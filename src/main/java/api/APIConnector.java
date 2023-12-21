package api;

public class APIConnector {
    private static final String API_BASE_URL = "https://adb2022.leankit.com/io";
    private static final String API_JETON_KEY = "286a7ce09afec445c2553d063cf6a1d7601426564f7fb0da31a9dbf7ec27ed312a69dde85f40c8dd1eafc26dd1faab65c0f9a9091d822d5abca03fc3965ca4fa";

    public String getAPIBaseUrl() {
        return API_BASE_URL;
    }
    public String getAPIJetonKey() {
        return API_JETON_KEY;
    }

}
