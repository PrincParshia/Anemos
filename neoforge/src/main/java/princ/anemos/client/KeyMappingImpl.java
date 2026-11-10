package princ.anemos.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;

import static princ.anemos.client.Constants.*;

@EventBusSubscriber(modid = NAMESPACE, value = Dist.CLIENT)
public class KeyMappingImpl {
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(withDefaultNamespace("default"));
    public static final Lazy<KeyMapping> gammaKey = Lazy.of(() -> new KeyMapping(withKeyMappingPrefix("gamma"), InputConstants.Type.KEYBOARD, InputConstants.KEY_G, CATEGORY));
    public static final Lazy<KeyMapping> fakeNightVisionKey = Lazy.of(() -> new KeyMapping(withKeyMappingPrefix("fakeNightVision"), InputConstants.Type.KEYBOARD, InputConstants.KEY_N, CATEGORY));
    public static final Lazy<KeyMapping> removeBlindnessKey = Lazy.of(() -> new KeyMapping(withKeyMappingPrefix("removeBlindness"), InputConstants.Type.KEYBOARD, InputConstants.KEY_B, CATEGORY));
    public static final Lazy<KeyMapping> removeDarknessKey = Lazy.of(() -> new KeyMapping(withKeyMappingPrefix("removeDarkness"), InputConstants.Type.KEYBOARD, InputConstants.KEY_V, CATEGORY));

    @SubscribeEvent
    public static void registerAll(RegisterKeyMappingsEvent event) {
        event.register(gammaKey.get());
        event.register(fakeNightVisionKey.get());
        event.register(removeBlindnessKey.get());
        event.register(removeDarknessKey.get());
    }

    public static void listenClicks(ClientTickEvent.Post event) {
        if (gammaKey.get().consumeClick()) {
            toggleGamma(gammaState(), gamma().peak, gammaPrevVal(), gamma().transition, gamma().transitionTime);
        }

        if (fakeNightVisionKey.get().consumeClick()) {
            toggle(nightVisionScale(), nightVisionScaleState(), fakeNightVision().enabled, fakeNightVision().transition, fakeNightVision().transitionTime);
        }

        if (removeBlindnessKey.get().consumeClick()) {
            toggle(blindnessDistance(), blindnessDistanceState(), removeBlindness().enabled, removeBlindness().transition, removeBlindness().transitionTime);
        }

        if (removeDarknessKey.get().consumeClick()) {
            toggle(darknessDistance(), darknessDistanceState(), removeDarkness().enabled, removeDarkness().transition, removeDarkness().transitionTime);
        }
    }
}
