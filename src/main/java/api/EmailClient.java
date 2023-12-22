package api;


import model.TokenForMail;

import java.util.Timer;
import java.util.TimerTask;

public class EmailClient {
    EmailConnector emailConnector;
    Timer timer;
    TokenForMail token;



    public EmailClient() {
        this.emailConnector = new EmailConnector();
        this.timer = new Timer();
    }

    private void callToken() {
        token = emailConnector.apiCallMailToken();
    }

    private void startTokenScheduler() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
             callToken();
             System.out.println(" callToken();>>>");
            }
        }, 0, 3590000); // 3590 secondes converties en millisecondes
    }

    public void makeAPICall() {
        startTokenScheduler();

    }

}
