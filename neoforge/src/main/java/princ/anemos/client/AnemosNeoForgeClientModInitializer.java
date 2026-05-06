package princ.anemos.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Constants.NAMESPACE, dist = Dist.CLIENT)
public class AnemosNeoForgeClientModInitializer {

    public AnemosNeoForgeClientModInitializer(IEventBus eventBus) {
        Anemos.init();
        NeoForge.EVENT_BUS.addListener(AnemosNeoForgeClientModInitializer::onClientTick);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        KeyMappingImpl.listenClicks(event);
    }
}