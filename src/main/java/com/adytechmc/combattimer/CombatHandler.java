package com.adytechmc.combattimer;

import com.adytechmc.combattimer.config.ModConfig;
import com.adytechmc.combattimer.interaction.ModMessages;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class CombatHandler {
    private static HashMap<PlayerEntity, Integer> CombatList = new HashMap<>();

    public static void addToCombat(PlayerEntity player) {
        ModMessages.inCombatMessage(player);
        CombatList.put(player, ModConfig.HANDLER.instance().timer_length_in_seconds);
    }

    public static void removeFromCombat(PlayerEntity player){
        if(CombatList.containsKey(player)) CombatList.remove(player);
    }

    public static void tick(){
        for(Map.Entry<PlayerEntity, Integer> entry : CombatList.entrySet()) {
            entry.setValue(entry.getValue() - 1);
            if(entry.getValue() <= 0) {
                ModMessages.outOfCombatMessage(entry.getKey());
                CombatList.remove(entry.getKey());
            }

        }
    }

    public static boolean isInCombat(PlayerEntity player){
        return CombatList.containsKey(player);
    }

    public static HashMap<PlayerEntity, Integer> getCombatList() {
        return CombatList;
    }
}
