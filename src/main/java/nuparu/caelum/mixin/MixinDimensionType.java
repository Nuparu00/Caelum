package nuparu.caelum.mixin;

import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import nuparu.caelum.client.MoonController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionType.class)
public class MixinDimensionType {
    @Inject(at = @At("HEAD"), method = "moonPhase(J)I", cancellable = true)
    private void inject$moonPhase(long time, CallbackInfoReturnable<Integer> cir) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            cir.cancel();
            cir.setReturnValue(MoonController.MOON.getMoonPhase(time));
        }
    }
}
