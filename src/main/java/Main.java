import Model.Carte;
import api.APIClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class Main {
    public static void main(String[] args) {

        APIClient apiClient = new APIClient();

       

        //Test methode getInfoCard()
        Carte carte = apiClient.getInfoCard("2057179455");

//        System.out.println("carte.getTitle() >> " + carte.getTitle());
//        carte.setTitle("Retrospective");
//        System.out.println("carte.getTitle() >> " + carte.getTitle());

//        //Test methode moveCard()
//        String reponseAPI2 = apiClient.moveCard(carte.getId(),"2057018462");
//        if (reponseAPI2 != null) {
//            System.out.println("moveCard() >> Réponse de l'API : " + reponseAPI2);
//        } else {
//            System.out.println("moveCard() >> Erreur lors de l'appel à l'API.");
//        }


//        //Test methode getListOfCards()
//        String reponseAPI3 = apiClient.getListOfCards(2057018447);
//        if (reponseAPI3 != null) {
//            System.out.println("getListOfCards() >> Réponse de l'API : " + reponseAPI3);
//        } else {
//            System.out.println("getListOfCards() >> Erreur lors de l'appel à l'API.");
//        }


        //Test methode updateCard()
        String reponseAPI4 = apiClient.updateCard(carte.getId());
        if (reponseAPI4 != null) {
            System.out.println("updateCard() >> Réponse de l'API : " + reponseAPI4);
        } else {
            System.out.println("updateCard() >> Erreur lors de l'appel à l'API."+ reponseAPI4);
        }

        carte = apiClient.getInfoCard("2057179455");

        System.out.println("carte.getTitle() >> " + carte.getTitle());



    }
}
//---------------------------------------------------------------------------------------------------------------
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class Main {
//
//    public static void main(String[] args) {
//        SpringApplication.run(Main.class, args);
//    }
//}
