package ren.yololaxe.rbfirebaseauth;

import org.bukkit.plugin.java.JavaPlugin;
import ren.yololaxe.rbfirebaseauth.commands.CommandInitialization;
import ren.yololaxe.rbfirebaseauth.commands.CommandMyStats;
import ren.yololaxe.rbfirebaseauth.services.FirebaseInitialize;
import ren.yololaxe.rbfirebaseauth.services.FirebaseService;


public final class main extends JavaPlugin {

    @Override
    public void onEnable() {
        //FirebaseApp.initializeApp();
        FirebaseInitialize firebaseInitialize = new FirebaseInitialize();
        firebaseInitialize.initialization();

        //String GOOGLE_APPLICATION_CREDENTIALS = "application_default_credentials.json";
        System.out.println("MonSuperPlugin est activé !");

        FirebaseService firebaseService = new FirebaseService();
        firebaseService.fetchPlayersDetails();


        CommandMyStats commandMyStats = new CommandMyStats();
        CommandInitialization commandInitialization = new CommandInitialization(this);

        //COMMANDES
        getCommand("mystats").setExecutor(commandMyStats);
        getCommand("initialization").setExecutor(commandInitialization);

        //LISTENER
        getServer().getPluginManager().registerEvents(commandMyStats, this);
        getServer().getPluginManager().registerEvents(commandInitialization, this);
        getServer().getPluginManager().registerEvents(new mainListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
