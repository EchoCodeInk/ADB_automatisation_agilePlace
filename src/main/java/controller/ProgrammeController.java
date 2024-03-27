package controller;


import api.AgilePlaceClient;


public class ProgrammeController {
    private AgilePlaceClientController agilePlaceController;
    private AgilePlaceClient agilePlaceClient;


    public ProgrammeController() {
        this.agilePlaceController = new AgilePlaceClientController();
        this.agilePlaceClient = new AgilePlaceClient();
    }

    public void startProgramme()  {
        System.out.println("START PROGRAMME ");
        //agilePlaceClient.openEtatDeCompte();
        agilePlaceController.copieChampPersonnalise();
        agilePlaceController.updateACardType();
        agilePlaceController.setAndUpdateWipLimiteOfEnCourDEstimationLane();
        agilePlaceController.updateAttachmentForMagasinCheckList();
        agilePlaceController.gestionDuplicateCardInLanes();
        //agilePlaceController.gestionCardSylvain();
        agilePlaceController.setBoardEstimationLaneCalendrierDesObjectifs();
        agilePlaceController.setBoardEstimationLaneCalendrierEnCoursDEstimation();
    }
}


