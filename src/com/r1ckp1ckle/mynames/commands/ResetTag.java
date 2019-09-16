package com.r1ckp1ckle.mynames.commands;

import com.r1ckp1ckle.mynames.MyNamesCore;
import com.r1ckp1ckle.mynames.utils.ItemNamingUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetTag implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            if (MyNamesCore.getInstance().getConfigurationUtils().getData().getConfigurationSection("").getKeys(false).contains(player.getInventory().getItemInMainHand().getType().toString())) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', MyNamesCore.getInstance().getConfig().getString("reset-tag-message")
                        .replace("%tag%", "\"" + ChatColor.stripColor(
                                MyNamesCore.getInstance().getConfigurationUtils().getData().getString(player.getInventory().getItemInMainHand().getType().toString() + ".tag")) + "\"")));
                ItemNamingUtils.DeleteGlobalNameStack(player.getInventory().getItemInMainHand(), player);
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', MyNamesCore.getInstance().getConfig().getString("reset-tag-not-found-message")));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', MyNamesCore.getInstance().getConfig().getString("not-holding-item-message")));
        }
        return true;
    }
}