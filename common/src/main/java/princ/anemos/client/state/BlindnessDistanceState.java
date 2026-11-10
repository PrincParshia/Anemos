package princ.anemos.client.state;

import princ.anemos.client.config.AnemosConfigInternal;

import static princ.anemos.client.Constants.configInternal;
import static princ.anemos.client.Constants.minecraft;

public class BlindnessDistanceState extends SharedNumericState<Float> {

    public BlindnessDistanceState() {
        super(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, false);
    }

    private AnemosConfigInternal.RemoveBlindness removeBlindness() {
        return configInternal.removeBlindness;
    }

    @Override
    public void resolveValues() {
        this.max = (float) (minecraft().options.renderDistance().get() * 16);
        this.removeBlindness().maxDist = this.max;
        this.min = this.removeBlindness().minDist;
    }
}
