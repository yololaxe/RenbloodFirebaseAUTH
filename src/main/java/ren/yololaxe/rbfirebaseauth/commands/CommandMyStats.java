package ren.yololaxe.rbfirebaseauth.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import ren.yololaxe.rbfirebaseauth.models.PlayerModel;
import ren.yololaxe.rbfirebaseauth.services.FirebaseService;

import java.util.ArrayList;
import java.util.List;

public class CommandMyStats implements CommandExecutor, Listener {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        Player player = sender.getServer().getPlayer(sender.getName());

        PlayerModel playerStats = FirebaseService.getPlayerFromPseudo(sender.getName());
//        Inventory mainStatsGUI = Bukkit.createInventory(null, 54, "§8Vos statistiques");


        if (playerStats != null) {
            Inventory inv = Bukkit.createInventory(null, 54, "§8Vos statistiques");
            ;
            createMainStatsGUI(inv, playerStats, player);

            player.openInventory(inv);

        } else {
//            sender.sendMessage("Le joueur n'a pas été trouvé dans la base de données. ("+sender.getName()+")");
        }

        return false;
    }

    public void createMainStatsGUI(Inventory inv, PlayerModel playerStats, Player player) {

        inv.setItem(11, player.getInventory().getHelmet());

        inv.setItem(19, player.getInventory().getItemInMainHand());
        inv.setItem(20, player.getInventory().getChestplate());
        inv.setItem(21, player.getInventory().getItemInOffHand());

        inv.setItem(29, player.getInventory().getLeggings());

        inv.setItem(38, player.getInventory().getBoots());

        inv.setItem(15, getSkull("§aVos infos", new ArrayList() {{
            add("§7"+playerStats.description);
            add("");
            add("§a"+playerStats.pseudoMinecraft);
            add("§2"+playerStats.name+" "+playerStats.surname.toUpperCase());
            add("§e⌘ Niveau §f"+playerStats.totalLvl);
            //add("Description");
            add("§b★ Grade §f"+playerStats.getGrade(playerStats.grade));
            add("§6§l$§l Bourse §f" +playerStats.money);
            add("§5⊙ Points de talent §f"+playerStats.talentPoint);

        }}, player));
        inv.setItem(16, getItem(Material.BOOK, "§eStatistiques", new ArrayList(){{
            add("§7Vous donne l'avantage sur les autres !");
            add("");
            add("§c♥ Vie §f"+playerStats.capacity[0]);
            add("§a❇ Défense §f"+playerStats.capacity[1]);
            add("§c✿ Force §f"+playerStats.capacity[2]);
            add("§f✦ Vitesse §f"+playerStats.capacity[3]);
            add("§b⁂ Mana §f"+playerStats.capacity[4]);
            add("§e⇒ Portée §f" +playerStats.capacity[5]);

        }}));
        inv.setItem(24, getItem(Material.POTION, "§cCapacités", new ArrayList(){{
            add("§7Vous donne l'avantage sur les autres !");
            add("");
            add("§1≈ Eviter §f"+playerStats.capacity[0]);
            add("§7፧ Discrétion §f"+playerStats.capacity[1]);
            add("§d✒ Charisme §f"+playerStats.capacity[2]);
            add("§9∑ Rethorique §f"+playerStats.capacity[3]);
            add("§6☛ Négociation §f"+playerStats.capacity[4]);
            add("§c☼ Régénération §f" +playerStats.capacity[5]);
        }}));
        inv.setItem(25, getItem(Material.CLOCK, "§dGlobales", new ArrayList(){{
            add("§7Vous donne l'avantage sur les autres !");
            add("");
            add("§1≈ Influence §f"+playerStats.capacity[0]);
            add("§7፧ Autodidacte §f"+playerStats.capacity[1]);
            add("§d✒ Réputation §f"+playerStats.capacity[2]);
            add("§9∑ Commerces §f"+playerStats.capacity[3]);
            add("§6☛ Recettes employé §f"+playerStats.capacity[4]);
            add("§c☼ Apprentis §f" +playerStats.capacity[5]);
            add("§c☼ Vitesse de fabrication §f" +playerStats.capacity[5]);
            add("§c☼ Employés §f" +playerStats.capacity[5]);

        }}));
        int[] niveaux = playerStats.getLevels();
        inv.setItem(33, getItem(Material.EXPERIENCE_BOTTLE, "§3Niveau", new ArrayList(){{
            add("§7Vous donne l'avantage sur les autres !");
            add("");
            add("§c⚒ Forgeron §f" + niveaux[0]);
            add("§e✂ Artisan §f" +niveaux[1]);
            add("§a✂ Eleveur §f" +niveaux[2]);
            add("§d⊞ Tailleur de pierre §f" +niveaux[3]);
            add("§a♨ Fermier §f" +niveaux[4]);
            add("§2🪓 Bucheron §f" +niveaux[5]);
            add("§6🍞 Boulanger §f" +niveaux[6]);
            add("§8⛏ Mineur §f" +niveaux[7]);
            add("§d☕ Aubergiste §f" +niveaux[8]);
            add("§6🍽 Restaurateur §f" +niveaux[9]);
            add("§b░ Verrier §f" +niveaux[10]);
            add("§5✒ Tisseur §f" +niveaux[11]);
            add("§3♓ Pêcheur §f" +niveaux[12]);
            add("§b🛡 Garde §f" +niveaux[13]);
            add("§8✉ Transporteur §f" +niveaux[14]);
            add("§9♈ Chenille §f" +niveaux[15]);
            add("§c⚔ Marechal Ferrand §f" +niveaux[16]);
            add("§4☁ Dragonnier §f" +niveaux[17]);
            add("§1⚓ Naval §f" +niveaux[18]);
            add("§7⚡ Gardien §f" +niveaux[19]);




        }}));

        inv.setItem(49, getItem(Material.BARRIER, "§cQuitter", new ArrayList<>()));

    }

    public ItemStack getItem(Material material, String customName, List<String> lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(customName);
        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);
        return item;
    }

    public ItemStack getSkull(String customName, List<String> lore, Player player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        itemMeta.setOwningPlayer(player);
        itemMeta.setDisplayName(customName);
        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);
        return item;
    }


    //CLICK

    @EventHandler
    public void onClick(InventoryClickEvent event) {


        InventoryView invView = event.getView();
        if (invView.getTitle().equals("§8Vos statistiques")) {
            event.setCancelled(true);
            mainStatsGUIClick(event);
        }


    }


    public void mainStatsGUIClick(InventoryClickEvent event) {
//
        switch (event.getRawSlot()) {
            case 15:
                event.getWhoClicked().sendMessage("C TOI");
                break;
        }

    }

}
