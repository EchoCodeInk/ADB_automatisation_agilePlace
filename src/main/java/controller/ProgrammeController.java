package controller;


import api.AgilePlaceClient;

public class ProgrammeController {
    private AgilePlaceClientController agilePlaceController;
    private AgilePlaceClient agilePlaceClient;


    public ProgrammeController() {
        this.agilePlaceController = new AgilePlaceClientController();
        this.agilePlaceClient = new AgilePlaceClient();
    }

    public void startProgramme() {
        System.out.println("START PROGRAMME ");
        //agilePlaceClient.getListOfCardTypes(1823652151);
        agilePlaceClient.updateACardType(2057018447);
        agilePlaceController.setAndUpdateWipLimiteOfEnCourDEstimationLane();
        agilePlaceController.updateAttachmentForMagasinCheckList();
        agilePlaceController.gestionDuplicateCardInLanes();
        agilePlaceController.gestionCardSylvain();
        agilePlaceController.setBoardEstimationLaneCalendrierDesObjectifs();
        agilePlaceController.setBoardEstimationLaneCalendrierEnCoursDEstimation();
    }
}


