package com.adytechmc.combattimer;

import com.adytechmc.combattimer.config.ModConfig;
import com.adytechmc.combattimer.config.makeJsonReadable;
import com.adytechmc.combattimer.events.ModEventsHandler;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.EventHandler;

public class CombatTimer implements ModInitializer {
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("combat-timer");

	@Override
	public void onInitialize() {
		ModConfig.HANDLER.load();
		makeJsonReadable.addEmptyLineBetweenJSONArguments("config/CombatTimerConfig.json5");
		LOGGER.info("Destroying Combat Loggers since 2024.");
		AttackEntityCallback.EVENT.register(ModEventsHandler::CombatEvent);
		ServerLivingEntityEvents.ALLOW_DAMAGE.register(ModEventsHandler::ProjectileCombatEvent);
		ServerLivingEntityEvents.AFTER_DEATH.register(ModEventsHandler::EntityDeathEvent);
		ServerTickEvents.END_SERVER_TICK.register(ModEventsHandler::TickEvent);
		ServerPlayConnectionEvents.DISCONNECT.register(ModEventsHandler::DisconnectEventHandler);
	}
}