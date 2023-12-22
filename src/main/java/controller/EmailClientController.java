package controller;


import api.EmailClient;

public class EmailClientController {

    EmailClient emailCilent;


    public EmailClientController() {
        this.emailCilent = new EmailClient();
    }

    protected void makeAPICallFromEmailClient() {
        emailCilent.makeAPICall();
    }

}
