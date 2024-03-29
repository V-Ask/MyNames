package com.r1ckp1ckle.mynames.utils;

import com.r1ckp1ckle.mynames.MyNamesCore;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemNamingUtils {

    public static void CompileGlobalNames(List<String> compileList) {
        for (String s : MyNamesCore.getInstance().getConfigurationUtils().getData().getConfigurationSection("").getKeys(false)) {
            compileList.add(s);
        }
    }

    public static void NameStack(ItemStack itemStack, String name, Player player) {
        ItemStack newItemStack = itemStack;
        ItemMeta meta = newItemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        itemStack.setItemMeta(meta);
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        compound.setBoolean("r1ckp1ckle.tagged", true);
        nmsStack.setTag(compound);
        player.getInventory().setItemInMainHand(CraftItemStack.asBukkitCopy(nmsStack));
    }

    public static void DeleteNameStack(ItemStack itemStack, Player player) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(null);
        itemStack.setItemMeta(meta);
        player.getInventory().setItemInMainHand(itemStack);
    }

    public static void DeleteGlobalNameStack(ItemStack stack, Player player) {
        ItemStack itemStack = stack;
        itemStack.setAmount(1);
        DeleteNameStack(itemStack, player);
        MyNamesCore.getInstance().getConfigurationUtils().getData().set(itemStack.getType().toString() + "$" + itemStack.getData().toString(), null);
        MyNamesCore.getInstance().getConfigurationUtils().saveFiles();
        MyNamesCore.getInstance().getTweakedMats().remove(itemStack.getType().toString() + "$" + itemStack.getData().toString());
    }

    public static void GlobalNameStack(ItemStack itemStack, String name, Player player) {
        MyNamesCore.getInstance().getConfigurationUtils().getData().set(itemStack.getType().toString() + "$" + itemStack.getData()
                + ".tag", name);
        MyNamesCore.getInstance().getConfigurationUtils().saveFiles();
        MyNamesCore.getInstance().getTweakedMats().add(itemStack.getType().toString() + "$" + itemStack.getData().toString());
        player.getLocation().getWorld().dropItem(player.getLocation(), itemStack);
        itemStack.setAmount(0);
    }
}
