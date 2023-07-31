package nuparu.caelum.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nuparu.caelum.Caelum;
import nuparu.caelum.client.overlay.DebugOverlay;

@Mod.EventBusSubscriber(modid= Caelum.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        //event.registerAboveAll(Caelum.MODID+"__debug", new DebugOverlay(Minecraft.getInstance()));
    }
}
