package de.olivermakesco.bta_discord_integration.mixin.server;

import de.olivermakesco.bta_discord_integration.server.DiscordChatRelay;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityLiving.class, remap = false)
public abstract class Mixin_EntityLiving {
    @Shadow protected abstract String getDeathMessage(Entity entity);

    @Inject(
            method = "onDeath",
            at = @At("RETURN")
    )
    void processDeathMessage(Entity entity, CallbackInfo ci) {
        String message = getDeathMessage(entity).replaceAll("§.", "");
        DiscordChatRelay.sendDeathMessage(message);
    }
}
