package controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ProgrammeController {
    private AgilePlaceClientController agilePlaceController;


    public ProgrammeController() {
        this.agilePlaceController = new AgilePlaceClientController();
    }

    public void startProgramme() {
        System.out.println("START PROGRAMME ");
        agilePlaceController.updateAttachmentForMagasinCheckList();
        agilePlaceController.gestionDuplicateCardInLanes();
        agilePlaceController.gestionCardSylvain();
        agilePlaceController.updateBoardEstimationLaneCalendrierDesObjectifsUpdate();
        agilePlaceController.updateBoardEstimationLaneCalendrierEnCoursDEstimation();
    }
}


