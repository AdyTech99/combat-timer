package com.adytechmc.combattimer.events;

import com.adytechmc.combattimer.CombatHandler;
import com.adytechmc.combattimer.config.ModConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ToolItem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ModEventsHandler {

    public static ActionResult CombatEvent(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        if(!player.isSpectator() && entity instanceof PlayerEntity && !(friendlyFireExemption(player, hand))){
            CombatEventHandler(player, (PlayerEntity) entity);
        }
        return ActionResult.PASS;
    }

    public static boolean ProjectileCombatEvent(LivingEntity livingEntity, DamageSource damageSource, float v) {
        if(livingEntity instanceof PlayerEntity) {
            if (damageSource.getSource() instanceof ProjectileEntity pe) {
                if (pe.getOwner() instanceof PlayerEntity) {
                    if(pe.getOwner() != livingEntity) CombatEventHandler((PlayerEntity) pe.getOwner(), (PlayerEntity) livingEntity);
                }
            }
        }
        return true;
    }

    public static void CombatEventHandler(PlayerEntity attacker, PlayerEntity victim){
        if(!CombatHandler.isInCombat(attacker)) CombatHandler.addToCombat(attacker);
        else CombatHandler.renewCombat(attacker);
        if(!CombatHandler.isInCombat(victim)) CombatHandler.addToCombat(victim);
        else CombatHandler.renewCombat(victim);
    }

    public static void TickEvent(MinecraftServer server) {
        if(server.getTicks() % 20 == 0) CombatHandler.tick();
    }

    public static void DisconnectEventHandler(ServerPlayNetworkHandler serverPlayNetworkHandler, MinecraftServer server) {
        if(CombatHandler.isInCombat(serverPlayNetworkHandler.getPlayer())) serverPlayNetworkHandler.player.kill();
        CombatHandler.removeFromCombat(serverPlayNetworkHandler.getPlayer());
    }

    private static boolean friendlyFireExemption(PlayerEntity attacker, Hand hand){
        return ModConfig.HANDLER.instance().combat_tag_if_punched && !(attacker.getStackInHand(hand).getItem() instanceof ToolItem);
    }

    public static void EntityDeathEvent(LivingEntity livingEntity, DamageSource damageSource) {
        if(livingEntity instanceof PlayerEntity pe){
            CombatHandler.removeFromCombat(pe);
        }
    }
}
