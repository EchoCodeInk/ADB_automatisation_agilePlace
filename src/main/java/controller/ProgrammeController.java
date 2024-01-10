package controller;

public class ProgrammeController {
    AgilePlaceClientController agilePlaceController;
    boolean startParogramme;

    public ProgrammeController() {
        this.agilePlaceController = new AgilePlaceClientController();
        this.startParogramme = true;
    }

    public void startProgramme() {
        while (startParogramme) {
            //agilePlaceController.moveChildsCardFromBoardEstimation();
            agilePlaceController.calenderAutomate();


            if (false) {
                startParogramme = false;
            }

        }

    }


}
