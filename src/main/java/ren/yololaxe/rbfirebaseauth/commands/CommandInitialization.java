package ren.yololaxe.rbfirebaseauth.commands;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import ren.yololaxe.rbfirebaseauth.models.PlayerModel;
import ren.yololaxe.rbfirebaseauth.services.FirebaseService;

public class CommandInitialization implements CommandExecutor, Listener {
    Plugin plugin;

    public CommandInitialization(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
        if(args.length<1) sender.sendMessage("§c/init {player_name_online}");
        else{
            String playerName = args[0];
            if(Bukkit.getServer().getPlayerExact(playerName) != null){
                initializePlayer(Bukkit.getServer().getPlayerExact(playerName));
            }
            else
                sender.sendMessage("§c/init {player_name_online}");
        }
        return false;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        initializePlayer(player);
        PlayerModel playerStats = FirebaseService.getPlayerFromPseudo(player.getName());
        //REGEN
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                regeneration(event.getPlayer());
            }
        }, 0L,20L * (60/playerStats.capacity[5]));
        //60/REGEN
    }

    public void initializePlayer(Player player) {
        PlayerModel playerStats = FirebaseService.getPlayerFromPseudo(player.getName());

        //SET TOUTES LES STATS DIRECT
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(playerStats.stats[0]);
        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(playerStats.stats[1]);
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(((double) playerStats.stats[2])/1000);
        //MANA A FAIRE
        //REACH
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), ("attribute "+player.getName()+" forge:reach_distance base set "+playerStats.stats[4]));
        player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(playerStats.stats[5]);
    }

    public void regeneration(Player player){
        player.setHealth(player.getHealth()+2);
    }
}
