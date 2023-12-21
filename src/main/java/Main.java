import controller.APIClientController;


public class Main {
    public static void main(String[] args) {
        APIClientController APIcontroller = new APIClientController();

        boolean startParogramme = true;

        while (startParogramme){

            //APIcontroller.moveChildsCardFromApiClient("2061990583");

            // APIcontroller.getInfoCardFromApiClient("2057179455");
           APIcontroller.moveChildsCardFromBoardEstimation();

            if (false) {
                startParogramme = false;
            }

        }

    }
}


