package ren.yololaxe.rbfirebaseauth.services;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;

public class FirebaseInitialize {
    public void initialization() {
//
        // Charger les options Firebase à partir du fichier JSON
        try (FileInputStream serviceAccount = new FileInputStream("./ServicesAccountKey.json")) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://le-royaume-renb-default-rtdb.europe-west1.firebasedatabase.app")
                    .build();

            FirebaseApp.initializeApp(options);

            System.out.println("FirebaseApp initialisé avec succès.");

//            List<QueryDocumentSnapshot> documents = db.collection("player").get().get().getDocuments();
//
//
//            for (QueryDocumentSnapshot document : documents) {
//                System.out.println("User: " + document.getId());
//                System.out.println("First: " + document.getString("name"));
//            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
