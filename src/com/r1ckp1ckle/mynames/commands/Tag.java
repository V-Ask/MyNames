package com.r1ckp1ckle.mynames.commands;

import com.r1ckp1ckle.mynames.MyNamesCore;
import com.r1ckp1ckle.mynames.utils.ItemNamingUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Tag implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;
        player.getInventory().getItemInMainHand();
        if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            String joined = String.join(" ", strings);
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            ItemNamingUtils.NameStack(itemStack, ChatColor.translateAlternateColorCodes('&', joined), player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', MyNamesCore.getInstance().getConfig().getString("tag-message").replace("%tag%", "\"" + ChatColor.stripColor(joined) + "\"")));
        }
        return true;
    }
}
