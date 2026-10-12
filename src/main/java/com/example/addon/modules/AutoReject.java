package com.example.addon.modules;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gui.screens.ConfirmScreen;

public class AutoReject extends Module {
    public AutoReject() {
        super(com.example.addon.Addon.CATEGORY, "auto-reject", "Automatically rejects confirmation screens.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.currentScreen instanceof ConfirmScreen) {
            mc.setScreen(null);
        }
    }
}
