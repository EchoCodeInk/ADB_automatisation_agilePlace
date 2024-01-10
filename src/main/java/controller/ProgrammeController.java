package controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProgrammeController {
    private AgilePlaceClientController agilePlaceController;
    private ScheduledExecutorService scheduler;
    private volatile boolean startParogramme;

    public ProgrammeController() {
        this.agilePlaceController = new AgilePlaceClientController();
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.startParogramme = true;
    }

    public void startProgramme() {
        System.out.println("Start programme ");
        Runnable task = () -> {
            //agilePlaceController.moveCardOfBoardEstimationOfTheLaneSylvain();
        };


        agilePlaceController.calenderAutomate();

        // Planifie l'exécution de la tâche une fois par heure
        scheduler.scheduleAtFixedRate(task, 0, 60, TimeUnit.SECONDS);

    }

    public void stopScheduler() {
        scheduler.shutdown(); // Arrête l'exécution des tâches planifiées
    }
}


