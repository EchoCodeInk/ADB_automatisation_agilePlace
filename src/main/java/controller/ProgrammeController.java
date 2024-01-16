package controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProgrammeController {
    private AgilePlaceClientController agilePlaceController;
    private ScheduledExecutorService scheduler;

    public ProgrammeController() {
        this.agilePlaceController = new AgilePlaceClientController();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }
    public void startProgramme() {

        System.out.println("START PROGRAMME ");

        Runnable task1 = () -> {
            agilePlaceController.moveCardOfBoardEstimationOfTheLaneSylvain();
        };

        if (LocalDate.now().getDayOfMonth() == 1) {
            agilePlaceController.updateBoardEstimationLaneCalendrierDesObjectifsUpdate();
        }
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY) {
            agilePlaceController.updateBoardEstimationLaneCalendrierEnCoursDEstimation();
        }


        scheduler.scheduleAtFixedRate(task1, 0, 15, TimeUnit.MINUTES);
    }
    public void stopScheduler() {
        scheduler.shutdown(); // Arrête l'exécution des tâches planifiées
    }
}


