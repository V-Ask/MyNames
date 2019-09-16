package com.r1ckp1ckle.mynames.commands;

import com.r1ckp1ckle.mynames.MyNamesCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AllTags implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        String output = "&n&6All Items and Tags:";
        for (String key : MyNamesCore.getInstance().getConfigurationUtils().getData().getConfigurationSection("").getKeys(false)) {
            output += "\n" + ChatColor.stripColor(MyNamesCore.getInstance().getConfigurationUtils().getData().getString(key + ".tag") + " : " + MyNamesCore.getInstance().getConfigurationUtils().getData().getString(key + ".default-name"));
        }
        commandSender.sendMessage(output);
        return true;
    }
}
