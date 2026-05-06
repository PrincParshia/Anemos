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
    public static final Lazy<KeyMapping> gammaKey = Lazy.of(() -> new KeyMapping(withKeyMappingPrefix("gamma"), InputConstants.Type.KEYSYM, InputConstants.KEY_G, CATEGORY));
    public static final Lazy<KeyMapping> fakeNightVisionKey = Lazy.of(() -> new KeyMapping(withKeyMappingPrefix("fakeNightVision"), InputConstants.Type.KEYSYM, InputConstants.KEY_N, CATEGORY));
    public static final Lazy<KeyMapping> removeBlindnessKey = Lazy.of(() -> new KeyMapping(withKeyMappingPrefix("removeBlindness"), InputConstants.Type.KEYSYM, InputConstants.KEY_B, CATEGORY));
    public static final Lazy<KeyMapping> removeDarknessKey = Lazy.of(() -> new KeyMapping(withKeyMappingPrefix("removeDarkness"), InputConstants.Type.KEYSYM, InputConstants.KEY_V, CATEGORY));

    @SubscribeEvent
    public static void registerAll(RegisterKeyMappingsEvent event) {
        event.register(gammaKey.get());
        event.register(fakeNightVisionKey.get());
        event.register(removeBlindnessKey.get());
        event.register(removeDarknessKey.get());
    }

    public static void listenClicks(ClientTickEvent.Post event) {
        if (gammaKey.get().consumeClick()) {
            adjustGamma(GAMMA_STATE, configGeneral.gamma.val, configInternal.gamma.prev, configGeneral.gamma.transition, configGeneral.gamma.transitionTime);
        }
        handleGammaTransition(GAMMA_STATE, configGeneral.gamma.transition);

        if (fakeNightVisionKey.get().consumeClick()) {
            adjust(nightVisionScale(), FAKE_NIGHT_VISION_SCALE_STATE, configGeneral.fakeNightVision.enabled, configGeneral.fakeNightVision.transition, configGeneral.fakeNightVision.transitionTime);
        }
        handleTransition(nightVisionScale(), FAKE_NIGHT_VISION_SCALE_STATE, configGeneral.fakeNightVision.enabled, configGeneral.fakeNightVision.transition);

        if (removeBlindnessKey.get().consumeClick()) {
            BLINDNESS_DISTANCE_STATE.resolveValues();
            adjust(blindnessDistance(), BLINDNESS_DISTANCE_STATE, configGeneral.removeBlindness.enabled, configGeneral.removeBlindness.transition, configGeneral.removeBlindness.transitionTime);
        }
        handleTransition(blindnessDistance(), BLINDNESS_DISTANCE_STATE, configGeneral.removeBlindness.enabled, configGeneral.removeBlindness.transition);

        if (removeDarknessKey.get().consumeClick()) {
            DARKNESS_DISTANCE_STATE.resolveValues();
            adjust(darknessDistance(), DARKNESS_DISTANCE_STATE, configGeneral.removeDarkness.enabled, configGeneral.removeDarkness.transition, configGeneral.removeDarkness.transitionTime);
        }
        handleTransition(darknessDistance(), DARKNESS_DISTANCE_STATE, configGeneral.removeDarkness.enabled, configGeneral.removeDarkness.transition);
    }
}
