package princ.anemos.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;

import static princ.anemos.client.Constants.*;

@Environment(EnvType.CLIENT)
public class KeyMappingImpl {
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(withDefaultNamespace("default"));
    public static final KeyMapping gammaKey = new KeyMapping(withKeyMappingPrefix("gamma"), InputConstants.Type.KEYSYM, InputConstants.KEY_G, CATEGORY);
    public static final KeyMapping fakeNightVisionKey = new KeyMapping(withKeyMappingPrefix("fakeNightVision"), InputConstants.Type.KEYSYM, InputConstants.KEY_N, CATEGORY);
    public static final KeyMapping removeBlindnessKey = new KeyMapping(withKeyMappingPrefix("removeBlindness"), InputConstants.Type.KEYSYM, InputConstants.KEY_B, CATEGORY);
    public static final KeyMapping removeDarknessKey = new KeyMapping(withKeyMappingPrefix("removeDarkness"), InputConstants.Type.KEYSYM, InputConstants.KEY_V, CATEGORY);

    public void registerAll() {
        KeyMappingHelper.registerKeyMapping(gammaKey);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (gammaKey.consumeClick()) {
                toggleGamma(gammaState(), gamma().peak, gammaPrevVal(), gamma().transition, gamma().transitionTime);
            }
        });

        KeyMappingHelper.registerKeyMapping(fakeNightVisionKey);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (fakeNightVisionKey.consumeClick()) {
                toggle(nightVisionScale(), nightVisionScaleState(), fakeNightVision().enabled, fakeNightVision().transition, fakeNightVision().transitionTime);
            }
        });

        KeyMappingHelper.registerKeyMapping(removeBlindnessKey);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (removeBlindnessKey.consumeClick()) {
                toggle(blindnessDistance(), blindnessDistanceState(), removeBlindness().enabled, removeBlindness().transition, removeBlindness().transitionTime);
            }
        });

        KeyMappingHelper.registerKeyMapping(removeDarknessKey);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            if (removeDarknessKey.consumeClick()) {
                toggle(darknessDistance(), darknessDistanceState(), removeDarkness().enabled, removeDarkness().transition, removeDarkness().transitionTime);
            }
        });
    }
}

