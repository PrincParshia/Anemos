package princ.anemos.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Constants.NAMESPACE, dist = Dist.CLIENT)
public class Anemos {

    public Anemos(IEventBus eventBus) {
        NeoForge.EVENT_BUS.addListener(Anemos::onClientTick);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        KeyMappingImpl.listenClicks(event);
    }
}