package controller;


import api.AgilePlaceClient;
import api.FilemakerClient;

import java.io.IOException;


public class ProgrammeController {
    private AgilePlaceClientController agilePlaceController;
    private AgilePlaceClient agilePlaceClient;

    private FilemakerClient filemakerClient;


    public ProgrammeController() {
        this.agilePlaceController = new AgilePlaceClientController();
        this.agilePlaceClient = new AgilePlaceClient();
        this.filemakerClient = new FilemakerClient();
    }

    public void startProgramme() throws IOException {
        System.out.println("START PROGRAMME ");
        filemakerClient.makeAPICallFilemaker();
//        //agilePlaceClient.openEtatDeCompte();
//        agilePlaceController.copieChampPersonnalise();
//        agilePlaceController.updateACardType();
//        agilePlaceController.setAndUpdateWipLimiteOfEnCourDEstimationLane();
//        agilePlaceController.updateAttachmentForMagasinCheckList();
//        agilePlaceController.gestionDuplicateCardInLanes();
//        //agilePlaceController.gestionCardSylvain();
//        agilePlaceController.setBoardEstimationLaneCalendrierDesObjectifs();
//        agilePlaceController.setBoardEstimationLaneCalendrierEnCoursDEstimation();
    }
}


