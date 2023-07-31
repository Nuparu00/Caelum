package nuparu.caelum.client.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nuparu.caelum.Caelum;
import nuparu.caelum.client.data.StarDataManager;

@Mod.EventBusSubscriber(modid = Caelum.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientReloadListeners {
        @SubscribeEvent
        public static void addReloadListener(RegisterClientReloadListenersEvent event) {
            event.registerReloadListener(StarDataManager.INSTANCE);
        }
}
