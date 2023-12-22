package controller;

public class ProgrammeController {
    APIClientController APIcontroller  ;
    boolean startParogramme ;

    public ProgrammeController() {
        this.APIcontroller = new APIClientController();
        this.startParogramme = true;
    }

    public void startProgramme(){
        while (startParogramme){
            APIcontroller.moveChildsCardFromBoardEstimation();
            //APIcontroller.createCard_boardUsineADB();

            if (false) {
                startParogramme = false;
            }

        }

    }



}
