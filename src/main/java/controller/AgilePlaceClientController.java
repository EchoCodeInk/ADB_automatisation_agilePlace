package controller;

import api.AgilePlaceClient;

import java.time.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AgilePlaceClientController {
    private AgilePlaceClient apiClient;
    private ScheduledExecutorService scheduler;
    private ScheduledExecutorService scheduler2;

    AgilePlaceClientController() {
        this.apiClient = new AgilePlaceClient();
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.scheduler2 = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(
                this::gestionCardSylvain,
                1,
                2,
                TimeUnit.MINUTES
        );

        scheduler.scheduleAtFixedRate(
                this::gestionDuplicateCardInLanes,
                0,
                1,
                TimeUnit.MINUTES
        );

        scheduler.scheduleAtFixedRate(
                this::setBoardEstimationLaneCalendrierDesObjectifs,
                calculateInitialDelayForDailyExecution(),
                1,
                TimeUnit.DAYS
        );

        scheduler.scheduleAtFixedRate(
                this::setBoardEstimationLaneCalendrierEnCoursDEstimation,
                calculateInitialDelayForDailyExecution(),
                1,
                TimeUnit.DAYS
        );
    }
    private long calculateInitialDelayForDailyExecution() {
        LocalDate now = LocalDate.now();
        LocalDate tomorrow = now.plusDays(1);
        LocalDateTime midnight = LocalDateTime.of(tomorrow, LocalTime.MIDNIGHT);
        long initialDelay = Duration.between(LocalDateTime.now(), midnight).toMillis();
        return initialDelay;
    }


    protected void gestionCardSylvain() {
        System.out.println("gestionCardSylvain()");
        apiClient.moveCardOfBoardEstimationOfTheLaneSylvain();
        apiClient.reflectionOfBoardEstimationLainSuiviEstimationSylvain();

    }

    protected void setBoardEstimationLaneCalendrierDesObjectifs() {
        System.out.println("setBoardEstimationLaneCalendrierDesObjectifs()");
        if (LocalDate.now().getDayOfMonth() == 1) {
            apiClient.setBoardEstimationLaneCalendrierDesObjectifs();
        }
    }

    protected void setBoardEstimationLaneCalendrierEnCoursDEstimation() {
        System.out.println("setBoardEstimationLaneCalendrierEnCoursDEstimation()");
        if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY) {
            apiClient.setBoardEstimationLaneCalendrierEnCoursDEstimation();
        }
    }

    protected void updateAttachmentForMagasinCheckList() {
        System.out.println("updateAttachmentForMagasinCheckList()");
        apiClient.updateAttachmentForMagasinCheckList();
    }
    protected void gestionDuplicateCardInLanes() {
        System.out.println("gestionDuplicateCardInLanes()");
        apiClient.findDuplicateCardInLanes();
    }
}
