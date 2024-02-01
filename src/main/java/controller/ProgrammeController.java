package controller;


public class ProgrammeController {
    private AgilePlaceClientController agilePlaceController;


    public ProgrammeController() {
        this.agilePlaceController = new AgilePlaceClientController();
    }

    public void startProgramme() {
        System.out.println("START PROGRAMME ");
        agilePlaceController.setAndUpdateWipLimiteOfEnCourDEstimationLane();
        agilePlaceController.updateAttachmentForMagasinCheckList();
        agilePlaceController.gestionDuplicateCardInLanes();
        agilePlaceController.gestionCardSylvain();
        agilePlaceController.setBoardEstimationLaneCalendrierDesObjectifs();
        agilePlaceController.setBoardEstimationLaneCalendrierEnCoursDEstimation();
    }
}


