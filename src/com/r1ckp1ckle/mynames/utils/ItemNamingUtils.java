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
        meta.setDisplayName(MyNamesCore.getInstance().getConfigurationUtils().getData().getString(itemStack.getType().toString() + "$" + itemStack.getData().toString() + ".default-name"));
        itemStack.setItemMeta(meta);
        player.getInventory().setItemInMainHand(itemStack);
    }

    public static void DeleteGlobalNameStack(ItemStack itemStack, Player player) {
        DeleteNameStack(itemStack, player);
        MyNamesCore.getInstance().getConfigurationUtils().getData().set(itemStack.getType().toString() + "$" + itemStack.getData().toString(), null);
        MyNamesCore.getInstance().getConfigurationUtils().saveFiles();
        MyNamesCore.getInstance().getTweakedMats().remove(itemStack.getType().toString() + "$" + itemStack.getData().toString());
    }

    public static void GlobalNameStack(ItemStack itemStack, String name, Player player) {
        MyNamesCore.getInstance().getConfigurationUtils().getData().set(itemStack.getType().toString() + "$" + itemStack.getData()
                + ".tag", name);
        System.out.println("STRING IS [" + MyNamesCore.getInstance().getConfigurationUtils().getData().getString(itemStack.getType().toString() + "$" + itemStack.getData().toString() + ".default-name") + "]");
        if (!HasTag(itemStack, "r1ckp1ckle.tagged")) {
            if (MyNamesCore.getInstance().getConfigurationUtils().getData().getString(itemStack.getType().toString() + "$" + itemStack.getData().toString() + ".default-name") == null) {
                MyNamesCore.getInstance().getConfigurationUtils().getData().set(itemStack.getType().toString() + "$" + itemStack.getData().toString() + ".default-name", itemStack.getItemMeta().getDisplayName());
            } else if (MyNamesCore.getInstance().getConfigurationUtils().getData().getString(itemStack.getType().toString() + "$" + itemStack.getData().toString() + ".default-name").equals("")) {
                MyNamesCore.getInstance().getConfigurationUtils().getData().set(itemStack.getType().toString() + "$" + itemStack.getData().toString() + ".default-name", itemStack.getItemMeta().getDisplayName());
            }
        }
        MyNamesCore.getInstance().getConfigurationUtils().saveFiles();
        MyNamesCore.getInstance().getTweakedMats().add(itemStack.getType().toString() + "$" + itemStack.getData().toString());
        NameStack(itemStack, name, player);
    }

    public static boolean HasTag(ItemStack itemStack, String tag) {
        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        if (nmsStack.hasTag()) {
            return nmsStack.getTag().hasKey(tag);
        }
        return false;
    }
}
