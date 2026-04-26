package princ.anemos.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

import static princ.anemos.client.AnemosConstants.*;

@Environment(EnvType.CLIENT)
public class KeyMappingImpl {
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(withDefaultNamespace("default"));
    public static final KeyMapping gammaKey = new KeyMapping(withKeyMappingPrefix("gamma"), InputConstants.Type.KEYSYM, InputConstants.KEY_G, CATEGORY);
    public static final KeyMapping fakeNightVisionKey = new KeyMapping(withKeyMappingPrefix("fakeNightVision"), InputConstants.Type.KEYSYM, InputConstants.KEY_N, CATEGORY);
    public static final KeyMapping removeBlindnessKey = new KeyMapping(withKeyMappingPrefix("removeBlindness"), InputConstants.Type.KEYSYM, InputConstants.KEY_B, CATEGORY);
    public static final KeyMapping removeDarknessKey = new KeyMapping(withKeyMappingPrefix("removeDarkness"), InputConstants.Type.KEYSYM, InputConstants.KEY_V, CATEGORY);

    public void registerAll() {
        KeyBindingHelper.registerKeyBinding(gammaKey);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (gammaKey.consumeClick()) {
                adjustGamma(GAMMA_STATE, configGeneral.gamma.val, configInternal.gamma.prev, configGeneral.gamma.transition, configGeneral.gamma.transitionTime);
            }
            handleGammaTransition(GAMMA_STATE, configGeneral.gamma.transition);
        });

        KeyBindingHelper.registerKeyBinding(fakeNightVisionKey);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (fakeNightVisionKey.consumeClick()) {
                adjust(nightVisionScale(), FAKE_NIGHT_VISION_SCALE_STATE, configGeneral.fakeNightVision.enabled, configGeneral.fakeNightVision.transition, configGeneral.fakeNightVision.transitionTime);
            }
            handleTransition(nightVisionScale(), FAKE_NIGHT_VISION_SCALE_STATE, configGeneral.fakeNightVision.enabled, configGeneral.fakeNightVision.transition);
        });

        KeyBindingHelper.registerKeyBinding(removeBlindnessKey);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (removeBlindnessKey.consumeClick()) {
                BLINDNESS_DISTANCE_STATE.resolveValues();
                adjust(blindnessDistance(), BLINDNESS_DISTANCE_STATE, configGeneral.removeBlindness.enabled, configGeneral.removeBlindness.transition, configGeneral.removeBlindness.transitionTime);
            }
            handleTransition(blindnessDistance(), BLINDNESS_DISTANCE_STATE, configGeneral.removeBlindness.enabled, configGeneral.removeBlindness.transition);
        });

        KeyBindingHelper.registerKeyBinding(removeDarknessKey);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (removeDarknessKey.consumeClick()) {
                DARKNESS_DISTANCE_STATE.resolveValues();
                adjust(darknessDistance(), DARKNESS_DISTANCE_STATE, configGeneral.removeDarkness.enabled, configGeneral.removeDarkness.transition, configGeneral.removeDarkness.transitionTime);
            }
            handleTransition(darknessDistance(), DARKNESS_DISTANCE_STATE, configGeneral.removeDarkness.enabled, configGeneral.removeDarkness.transition);
        });
    }
}
