package controller;

public class ProgrammeController {
    AgilePlaceClientController agilePlaceController;
    EmailClientController emailController;
    boolean startParogramme;

    public ProgrammeController() {
        this.agilePlaceController = new AgilePlaceClientController();
        this.startParogramme = true;
        this.emailController = new EmailClientController();
    }

    public void startProgramme() {
        while (startParogramme) {
            //agilePlaceController.moveChildsCardFromBoardEstimation();
            //agilePlaceController.createCard_boardUsineADB();
            emailController.makeAPICallFromEmailClient();


            if (false) {
                startParogramme = false;
            }

        }

    }


}
