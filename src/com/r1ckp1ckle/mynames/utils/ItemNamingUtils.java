package com.r1ckp1ckle.mynames.utils;

import com.r1ckp1ckle.mynames.MyNamesCore;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemNamingUtils {

    public static void CompileGlobalNames(List<Material> compileList) {
        for (String s : MyNamesCore.getInstance().getConfigurationUtils().getData().getConfigurationSection("").getKeys(false)) {
            compileList.add(Material.getMaterial(s));
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

    public static void DeleteNameStack(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(MyNamesCore.getInstance().getConfigurationUtils().getData().getString(itemStack.getType() + ".default-name"));
        itemStack.setItemMeta(meta);
    }

    public static void DeleteGlobalNameStack(ItemStack itemStack, Player player) {
        DeleteNameStack(itemStack);
        MyNamesCore.getInstance().getConfigurationUtils().getData().set(itemStack.getType().toString(), null);
        MyNamesCore.getInstance().getConfigurationUtils().saveFiles();
        MyNamesCore.getInstance().getTweakedMats().remove(itemStack.getType());
    }

    public static void GlobalNameStack(ItemStack itemStack, String name, Player player) {
        MyNamesCore.getInstance().getConfigurationUtils().getData().set(player.getInventory().getItemInMainHand().getType().toString() + ".tag", name);
        if (MyNamesCore.getInstance().getConfigurationUtils().getData().getString(itemStack.getType() + ".default-name") == "") {
            MyNamesCore.getInstance().getConfigurationUtils().getData().set(itemStack.getType().toString() + ".default-name", itemStack.getItemMeta().getDisplayName());
        }
        MyNamesCore.getInstance().getConfigurationUtils().saveFiles();
        MyNamesCore.getInstance().getTweakedMats().add(itemStack.getType());
        NameStack(itemStack, name, player);
    }
}
