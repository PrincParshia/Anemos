package princ.anemos.state;

import static princ.anemos.AnemosConstants.*;

public class BlindnessDistanceState extends SharedNumericState<Float> {
    public BlindnessDistanceState() {
        float min = configInternal.removeBlindness.minDist;
        float max = configInternal.removeBlindness.maxDist;
        super(min, max, 0.0F, 0, 0, false);
    }

    @Override
    public void resolveValues() {
        this.max = (float) (minecraft.options.renderDistance().get() * 16);
        configInternal.removeBlindness.maxDist = this.max;
    }
}
