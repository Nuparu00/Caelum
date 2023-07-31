package nuparu.caelum.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import nuparu.caelum.client.SkyUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientLevel.class)
public class MixinClientLevel {
    @Shadow @Final private Minecraft minecraft;

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getTimeOfDay(F)F"), method = "getSkyColor(Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;")
    private float getSkyColor$getTimeOfDay(ClientLevel instance, float v) {
        return (float) SkyUtils.apparentTimeOfDay(instance, minecraft.getCameraEntity().position().z(),v);
    }
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getTimeOfDay(F)F"), method = "Lnet/minecraft/client/multiplayer/ClientLevel;getStarBrightness(F)F")
    private float getStarBrightness$getTimeOfDay(ClientLevel instance, float v) {
        return (float) SkyUtils.apparentTimeOfDay(instance, minecraft.getCameraEntity().position().z(),v);
    }
}
