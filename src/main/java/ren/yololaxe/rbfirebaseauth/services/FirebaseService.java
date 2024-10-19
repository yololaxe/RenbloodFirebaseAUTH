package ren.yololaxe.rbfirebaseauth.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import ren.yololaxe.rbfirebaseauth.models.PlayerModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.type.TypeReference;

// ...

public class FirebaseService {

    public static List<PlayerModel> players = new ArrayList<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void fetchPlayersDetails() {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("player").document("7F4DyPxyFoPs6gwzBxaRbyxa1Hp1");

        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = null;
        try {
            document = future.get();
            System.out.println("1");
        } catch (InterruptedException e) {
            System.out.println("2");
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            System.out.println("3");
            throw new RuntimeException(e);
        }

        if (document.exists()) {
            // Le document existe, vous pouvez accéder aux données
            Map<String, Object> data = document.getData();
            // Utilisez les données comme nécessaire
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                // Faites quelque chose avec la clé et la valeur
            }
        }

        try (InputStream inputStream = FirebaseService.class.getResourceAsStream("/PlayersStats.json")) {
            if (inputStream != null) {
                players = fromStream(inputStream);
            } else {
                System.out.println("Le fichier JSON n'a pas été trouvé dans les ressources.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<PlayerModel> fromStream(InputStream inputStream) {
        try {
            return objectMapper.readValue(inputStream, new TypeReference<List<PlayerModel>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception selon les besoins
            return Collections.emptyList(); // Retourne une liste vide en cas d'erreur
        }
    }
    public static List<PlayerModel> getPlayers() {
        return players;
    }
    public static PlayerModel getPlayerFromPseudo(String pseudo) {
        for(PlayerModel player : players){
            if (player.pseudoMinecraft.equals(pseudo)) {
                return player;
            }
        }
        return null;
    }
}


