package model;

import com.google.gson.annotations.SerializedName;

public class TokenForMail {
    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private int expiresIn;

    @SerializedName("ext_expires_in")
    private int extExpiresIn;

    @SerializedName("access_token")
    private String accessToken;

    // Getters and setters for each field


    public String getTokenType() {
        return tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public int getExtExpiresIn() {
        return extExpiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    // You can also override toString() to print the response
    @Override
    public String toString() {
        return "TokenResponse{" +
                "tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", extExpiresIn=" + extExpiresIn +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
