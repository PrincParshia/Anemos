package princ.anemos.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;

import static princ.anemos.AnemosConstants.*;

@Environment(EnvType.CLIENT)
public class KeyMappingImpl {
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(withDefaultNamespace("default"));
    public static final KeyMapping GAMMA = new KeyMapping(withKeyMappingPrefix("gamma"), InputConstants.Type.KEYSYM, InputConstants.KEY_G, CATEGORY);
    public static final KeyMapping FNV = new KeyMapping(withKeyMappingPrefix("fakeNightVision"), InputConstants.Type.KEYSYM, InputConstants.KEY_N, CATEGORY);
    public static final KeyMapping RMB = new KeyMapping(withKeyMappingPrefix("removeBlindness"), InputConstants.Type.KEYSYM, InputConstants.KEY_B, CATEGORY);
    public static final KeyMapping RMD = new KeyMapping(withKeyMappingPrefix("removeDarkness"), InputConstants.Type.KEYSYM, InputConstants.KEY_V, CATEGORY);

    public void registerAll() {
        KeyMappingHelper.registerKeyMapping(GAMMA);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (GAMMA.consumeClick()) {
                adjustGamma(GAMMA_STATE, configGeneral.gamma.val, configInternal.gamma.prev, configGeneral.gamma.transition, configGeneral.gamma.transitionTime);
            }
            handleGammaTransition(GAMMA_STATE, configGeneral.gamma.transition);
        });

        KeyMappingHelper.registerKeyMapping(FNV);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (FNV.consumeClick()) {
                adjust(nightVisionScale(), FAKE_NIGHT_VISION_SCALE_STATE, configGeneral.fakeNightVision.enabled, configGeneral.fakeNightVision.transition, configGeneral.fakeNightVision.transitionTime);
            }
            handleTransition(nightVisionScale(), FAKE_NIGHT_VISION_SCALE_STATE, configGeneral.fakeNightVision.enabled, configGeneral.fakeNightVision.transition);
        });

        KeyMappingHelper.registerKeyMapping(RMB);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (RMB.consumeClick()) {
                BLINDNESS_DISTANCE_STATE.resolveValues();
                adjust(blindnessDistance(), BLINDNESS_DISTANCE_STATE, configGeneral.removeBlindness.enabled, configGeneral.removeBlindness.transition, configGeneral.removeBlindness.transitionTime);
            }
            handleTransition(blindnessDistance(), BLINDNESS_DISTANCE_STATE, configGeneral.removeBlindness.enabled, configGeneral.removeBlindness.transition);
        });

        KeyMappingHelper.registerKeyMapping(RMD);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (RMD.consumeClick()) {
                DARKNESS_DISTANCE_STATE.resolveValues();
                adjust(darknessDistance(), DARKNESS_DISTANCE_STATE, configGeneral.removeDarkness.enabled, configGeneral.removeDarkness.transition, configGeneral.removeDarkness.transitionTime);
            }
            handleTransition(darknessDistance(), DARKNESS_DISTANCE_STATE, configGeneral.removeDarkness.enabled, configGeneral.removeDarkness.transition);
        });
    }
}
