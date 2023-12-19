//package Server;
//
//
//
//public class Server {
//
//    public void startServer() {
//        System.out.println("Serveur démarré!");
//
//        // Port sur lequel le serveur écoute
//        port(8080 );
//
//        // Définir une route pour recevoir les appels d'Agile Place
//        post( "/agileplace", (request, response) -> {
//            String body = request.body(); // Obtenir le corps de la requête
//
//            // Traiter les données reçues d'Agile Place ici
//            System.out.println("Données reçues d'Agile Place : " + body);
//
//            // Répondre à Agile Place si nécessaire
//            return "Données reçues avec succès!";
//        });
//    }
//}
