package nuparu.caelum.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import nuparu.caelum.client.SkyUtils;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FogRenderer.class)
public class MixinFogRenderer {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;getTimeOfDay(F)F"), method = "setupColor(Lnet/minecraft/client/Camera;FLnet/minecraft/client/multiplayer/ClientLevel;IF)V")
    private static float setupColor$getTimeOfDay(ClientLevel instance, float v) {
        return (float) SkyUtils.apparentTimeOfDay(instance, Minecraft.getInstance().getCameraEntity().position().z(),v);
    }
    @Redirect(at = @At(value = "INVOKE", target = "Lorg/joml/Vector3f;dot(Lorg/joml/Vector3fc;)F", ordinal = 0, remap = false), method = "setupColor(Lnet/minecraft/client/Camera;FLnet/minecraft/client/multiplayer/ClientLevel;IF)V")
    private static float setupColor$sunVector(Vector3f instance, Vector3fc v) {
        return instance.dot(SkyUtils.sunVector(Minecraft.getInstance().level, Minecraft.getInstance().getCameraEntity().position().z(), Minecraft.getInstance().getPartialTick()));
    }
}
