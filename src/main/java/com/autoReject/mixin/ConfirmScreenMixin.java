package com.autoReject.mixin;

import com.autoReject.addon.modules.AutoReject;
import net.minecraft.client.gui.screen.ConfirmScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import meteordevelopment.meteorclient.systems.modules.Modules;

@Mixin(ConfirmScreen.class)
public class ConfirmScreenMixin {

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo info) {
        AutoReject autoReject = Modules.get().get(AutoReject.class);
        
        if (autoReject != null && autoReject.isActive()) {
            ConfirmScreen screen = (ConfirmScreen) (Object) this;
            // Close the dialog immediately
            screen.getMinecraftClient().setScreen(null);
        }
    }
}
