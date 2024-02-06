package controller;

import api.AgilePlaceClient;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AgilePlaceClientController {
    private AgilePlaceClient apiClient;
    private ScheduledExecutorService scheduler;

    AgilePlaceClientController() {
        this.apiClient = new AgilePlaceClient();
        this.scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(
                this::gestionCardSylvain,
                1,
                2,
                TimeUnit.MINUTES
        );

        scheduler.scheduleAtFixedRate(
                this::gestionDuplicateCardInLanes,
                0,
                2,
                TimeUnit.MINUTES
        );

        scheduler.scheduleAtFixedRate(
                this::setBoardEstimationLaneCalendrierDesObjectifs,
                0,
                calculateInitialDelayForDailyExecution(),
                TimeUnit.MINUTES
        );

        scheduler.scheduleAtFixedRate(
                this::setBoardEstimationLaneCalendrierEnCoursDEstimation,
                0,
                calculateInitialDelayForDailyExecution(),
                TimeUnit.MINUTES
        );
        scheduler.scheduleAtFixedRate(
                this::setAndUpdateWipLimiteOfEnCourDEstimationLane,
                0,
                calculateInitialDelayForDailyExecution(),
                TimeUnit.MINUTES
        );
        scheduler.scheduleAtFixedRate(
                this::updateACardType,
                0,
                calculateInitialDelayForDailyExecution(),
                TimeUnit.MINUTES
        );

    }

    private long calculateInitialDelayForDailyExecution() {
        LocalDate now = LocalDate.now();
        LocalDate tomorrow = now.plusDays(1);
        LocalDateTime midnight = LocalDateTime.of(tomorrow, LocalTime.MIDNIGHT);
        return Duration.between(LocalDateTime.now(), midnight).toMinutes();
    }


    protected void gestionCardSylvain() {
        System.out.println("gestionCardSylvain()");
        try {
            apiClient.moveCardOfBoardEstimationOfTheLaneSylvain();
            apiClient.reflectionOfBoardEstimationLainSuiviEstimationSylvain();
            System.out.println("gestionCardSylvain() - End");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in gestionCardSylvain(): " + e.getMessage());
        }
    }

    protected void setBoardEstimationLaneCalendrierDesObjectifs() {
        System.out.println("setBoardEstimationLaneCalendrierDesObjectifs()");
        try {

            if (LocalDate.now().getDayOfMonth() == 1) {
                apiClient.setBoardEstimationLaneCalendrierDesObjectifs();
            }
            System.out.println("setBoardEstimationLaneCalendrierDesObjectifs() - End");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in setBoardEstimationLaneCalendrierDesObjectifs(): " + e.getMessage());
        }
    }

    protected void setBoardEstimationLaneCalendrierEnCoursDEstimation() {
        System.out.println("setBoardEstimationLaneCalendrierEnCoursDEstimation()");
        try {
            if (LocalDate.now().getDayOfWeek() == DayOfWeek.SUNDAY) {
                apiClient.setBoardEstimationLaneCalendrierEnCoursDEstimation();
            }
            System.out.println("setBoardEstimationLaneCalendrierEnCoursDEstimation() - End");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in setBoardEstimationLaneCalendrierEnCoursDEstimation(): " + e.getMessage());
        }
    }

    protected void updateAttachmentForMagasinCheckList() {
        System.out.println("updateAttachmentForMagasinCheckList()");
        try {
            apiClient.updateAttachmentForMagasinCheckList();
            System.out.println("updateAttachmentForMagasinCheckList() - End");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in updateAttachmentForMagasinCheckList(): " + e.getMessage());
        }
    }

    protected void gestionDuplicateCardInLanes() {
        System.out.println("gestionDuplicateCardInLanes()");
        try {
            apiClient.findDuplicateCardInLanes();
            System.out.println("gestionDuplicateCardInLanes() - End");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in gestionDuplicateCardInLanes(): " + e.getMessage());
        }
    }

    protected void setAndUpdateWipLimiteOfEnCourDEstimationLane() {
        System.out.println("setAndUpdateWipLimiteOfEnCourDEstimationLane()");
        try {
            apiClient.setAndUpdateWipLimiteOfEnCourDEstimationLane();
            System.out.println("setAndUpdateWipLimiteOfEnCourDEstimationLane() - End");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in setAndUpdateWipLimiteOfEnCourDEstimationLane(): " + e.getMessage());
        }
    }

    protected void updateACardType() {
        System.out.println("updateACardType()");
        int BOARD_ADMINISTRATION = 2076380043;
        int BOARD_USINE_ADB = 1823652151;
        int BOARD_ESTIMATION = 1954823342;
        try {
            List<Integer> boardList = List.of(BOARD_ADMINISTRATION, BOARD_USINE_ADB, BOARD_ESTIMATION);
            for (int boardId : boardList) {
                apiClient.updateACardType(boardId);
            }
            System.out.println("updateACardType() - End");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in updateACardType(): " + e.getMessage());
        }
    }
}
