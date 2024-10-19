package ren.yololaxe.rbfirebaseauth;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class mainListener implements Listener {


    @EventHandler
    public void onPlayerBedEnter(PlayerBedLeaveEvent event){
        Player player = event.getPlayer();
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100 , 8));
    }

}
