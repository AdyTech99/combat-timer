package com.adytechmc.combattimer;

import com.adytechmc.combattimer.config.ModConfig;
import com.adytechmc.combattimer.interaction.ModMessages;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CombatHandler {
    private static final ConcurrentHashMap<PlayerEntity, Integer> CombatList = new ConcurrentHashMap<>();

    public static void addToCombat(PlayerEntity player) {
        ModMessages.inCombatMessage(player);
        CombatList.put(player, ModConfig.HANDLER.instance().timer_length_in_seconds);
    }

    public static void renewCombat(PlayerEntity player){
        if(isInCombat(player)){
            CombatList.replace(player, ModConfig.HANDLER.instance().timer_length_in_seconds);
        }
        else{
            addToCombat(player);
        }
    }

    public static void removeFromCombat(PlayerEntity player){
        if(CombatList.containsKey(player)) CombatList.remove(player);
    }

    public static void tick(){
        Iterator<Map.Entry<PlayerEntity, Integer>> iterator = CombatList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<PlayerEntity, Integer> entry = iterator.next();
            entry.setValue(entry.getValue() - 1);
            if (entry.getValue() <= 0) {
                ModMessages.outOfCombatMessage(entry.getKey());
                iterator.remove(); // Safe removal during iteration
            }
        }
    }

    public static boolean isInCombat(PlayerEntity player){
        return CombatList.containsKey(player);
    }

    public static ConcurrentHashMap<PlayerEntity, Integer> getCombatList() {
        return CombatList;
    }
}
