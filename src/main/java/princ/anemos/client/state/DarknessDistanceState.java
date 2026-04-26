package princ.anemos.client.state;

import static princ.anemos.client.AnemosConstants.configInternal;
import static princ.anemos.client.AnemosConstants.minecraft;

public class DarknessDistanceState extends SharedNumericState<Float> {
    public DarknessDistanceState() {
        super(configInternal.removeDarkness.minDist, configInternal.removeDarkness.maxDist, 0.0F, 0, 0, false);
    }

    @Override
    public void resolveValues() {
        this.max = (float) (minecraft.options.renderDistance().get() * 16);
        configInternal.removeDarkness.maxDist = this.max;
    }
}
