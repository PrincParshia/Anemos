package princ.anemos.client.state;

import static princ.anemos.client.AnemosConstants.configInternal;
import static princ.anemos.client.AnemosConstants.minecraft;

public class BlindnessDistanceState extends SharedNumericState<Float> {
    public BlindnessDistanceState() {
        super(configInternal.removeBlindness.minDist, configInternal.removeBlindness.maxDist, 0.0F, 0, 0, false);
    }

    @Override
    public void resolveValues() {
        this.max = (float) (minecraft.options.renderDistance().get() * 16);
        configInternal.removeBlindness.maxDist = this.max;
    }
}
