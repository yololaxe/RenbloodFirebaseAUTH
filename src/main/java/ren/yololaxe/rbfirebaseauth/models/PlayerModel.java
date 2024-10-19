package ren.yololaxe.rbfirebaseauth.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class PlayerModel {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    public final String email;
    public final String pseudoMinecraft;
    public final String name;
    public final String surname;
    public final String image;
    public final int totalLvl;
    public final String description;
    public final int grade;
    public final int money;
    public final int talentPoint;
    public final int[] stats;
    public final int[] capacity;
    public final int[] global;
    public final int[] experience;

    private static final String DEFAULT_IMAGE_URL = "https://i.ibb.co/Ryj2Tc6/Firefly-A-portrait-of-a-basic-medieval-character-4447.jpg";
    private static final int[] DEFAULT_STATS = {0, 0, 0, 0, 0, 0};
    private static final int[] DEFAULT_CAPACITY = {0, 0, 0, 0, 0, 0};
    private static final int[] DEFAULT_GLOBAL = {0, 0, 0, 0, 0, 0, 0, 0};
    private static final int[] DEFAULT_EXPERIENCE = new int[20]; // Adjusted to the length used in the model

    public PlayerModel(
            @JsonProperty("email") String email,
            @JsonProperty("pseudoMinecraft") String pseudoMinecraft,
            @JsonProperty("name") String name,
            @JsonProperty("surname") String surname,
            @JsonProperty("image") String image,
            @JsonProperty("totalLvl") int totalLvl,
            @JsonProperty("description") String description,
            @JsonProperty("grade") int grade,
            @JsonProperty("money") int money,
            @JsonProperty("talentPoint") int talentPoint,
            @JsonProperty("stats") int[] stats,
            @JsonProperty("capacity") int[] capacity,
            @JsonProperty("global") int[] global,
            @JsonProperty("experience") int[] experience) {

        this.email = email;
        this.pseudoMinecraft = pseudoMinecraft;
        this.name = name != null ? name : "";
        this.surname = surname != null ? surname : "";
        this.image = image != null ? image : DEFAULT_IMAGE_URL;
        this.totalLvl = totalLvl;
        this.description = description != null ? description : "";
        this.grade = grade;
        this.money = money;
        this.talentPoint = talentPoint;
        this.stats = stats != null ? stats : DEFAULT_STATS;
        this.capacity = capacity != null ? capacity : DEFAULT_CAPACITY;
        this.global = global != null ? global : DEFAULT_GLOBAL;
        this.experience = experience != null ? experience : DEFAULT_EXPERIENCE;
    }

    public static PlayerModel fromFile(File jsonFile) {
        try {
            return objectMapper.readValue(jsonFile, PlayerModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getGrade(int index) {
        String[] grades = {
                "§7Esclave",
                "§7Etranger",
                "§fVillageois",
                "§2Citoyen",
                "§2Citoyen Libre",
                "§aPatricien",
                "§eNoble",
                "§6Seigneur",
                "§6Vicompte",
                "§dCompte",
                "§5Marquis",
                "§cModerateur",
                "§§4Admin"
        };
        if (index >= 0 && index < grades.length) {
            return grades[index];
        }
        return "§7Inconnu";
    }

    private static final int[] PALIERS_1 = {20, 50, 90, 140, 200, 270, 350, 450, 600, 750, 1000, 1250, 1600, 2000, 3000};
    private static final int[] PALIERS_2 = {25, 60, 100, 150, 220, 300, 400, 550, 750, 1000};
    private static final int[] PALIERS_3 = {50, 100, 220, 350, 500};

    public int[] getLevels() {
        int[] levels = new int[experience.length];
        int[] currentPalier = PALIERS_1;
        int iteration = 0;

        for (int i = 0; i < experience.length; i++) {
            int xp = experience[i];
            int level = 0;

            if (iteration == 5) {
                currentPalier = PALIERS_2;
            } else if (iteration == 10) {
                currentPalier = PALIERS_3;
            }

            for (int palier : currentPalier) {
                if (xp >= palier) {
                    level++;
                } else {
                    break;
                }
            }
            levels[i] = level;
            iteration++;
        }
        return levels;
    }
}
