package com.r1ckp1ckle.mynames.handler;

import com.r1ckp1ckle.mynames.MyNamesCore;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class NameChanger implements Listener {
    @EventHandler
    void OnItemPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!MyNamesCore.getInstance().getTweakedMats().contains(event.getItem().getItemStack().getType().toString() + "$" + event.getItem().getItemStack().getData().toString()))
            return;
        if (CraftItemStack.asNMSCopy(event.getItem().getItemStack()).hasTag()) {
            if (CraftItemStack.asNMSCopy(event.getItem().getItemStack()).getTag().hasKey("r1ckp1ckle.tagged")) return;
        }
        if (event.getItem().getItemStack().getItemMeta().getDisplayName().equals(MyNamesCore.getInstance().getConfigurationUtils().getData().getString(event.getItem().getItemStack().getType().toString() + "$" + event.getItem().getItemStack().getData().toString())))
            return;
        ItemMeta meta = event.getItem().getItemStack().getItemMeta();
        meta.setDisplayName(MyNamesCore.getInstance().getConfigurationUtils().getData().getString(event.getItem().getItemStack().getType().toString() + "$" + event.getItem().getItemStack().getData().toString() + ".tag"));
        event.getItem().getItemStack().setItemMeta(meta);
    }
}
