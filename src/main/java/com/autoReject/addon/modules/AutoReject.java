package com.autoReject.addon.modules;

import meteordevelopment.meteorclient.events.game.ReceivePacketEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;

public class AutoReject extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> rejectAll = sgGeneral.add(new BoolSetting.Builder()
        .name("Reject All Dialogs")
        .description("Automatically rejects all confirmation dialogs.")
        .defaultValue(true)
        .build());

    private final Setting<Boolean> rejectServerDialogs = sgGeneral.add(new BoolSetting.Builder()
        .name("Reject Server Dialogs")
        .description("Specifically reject dialogs from the server.")
        .defaultValue(true)
        .build());

    private final Setting<Boolean> debug = sgGeneral.add(new BoolSetting.Builder()
        .name("Debug Info")
        .description("Show debug information in chat.")
        .defaultValue(false)
        .build());

    public AutoReject() {
        super(meteordevelopment.meteorclient.categories.Category.Misc, "Auto Reject", "Automatically rejects all dialogs and prevents them from opening.");
    }

    @Override
    public void onActivate() {
        // Hook into screen events to reject dialogs
        ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (isActive() && screen instanceof ConfirmScreen) {
                if (shouldReject(screen)) {
                    // Reject by clicking the right button (usually "Cancel" or "No")
                    client.setScreen(null);
                    if (debug.get()) {
                        System.out.println("[Auto Reject] Rejected dialog: " + screen.getClass().getSimpleName());
                    }
                }
            }
        });
    }

    private boolean shouldReject(Screen screen) {
        if (!isActive()) return false;
        
        if (rejectAll.get()) {
            return true;
        }

        if (rejectServerDialogs.get() && screen instanceof ConfirmScreen) {
            // Check if it's a server-related dialog
            ConfirmScreen confirmScreen = (ConfirmScreen) screen;
            String title = confirmScreen.title.getString();
            return title.toLowerCase().contains("server") || 
                   title.toLowerCase().contains("disconnect") ||
                   title.toLowerCase().contains("connection");
        }

        return false;
    }
}
