package princ.anemos.client.state;

import princ.anemos.client.config.AnemosConfigInternal;

import static princ.anemos.client.Constants.configInternal;
import static princ.anemos.client.Constants.minecraft;

public class DarknessDistanceState extends SharedNumericState<Float> {

    public DarknessDistanceState() {
        super(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, false);
    }

    private AnemosConfigInternal.RemoveDarkness removeDarkness() {
        return configInternal.removeDarkness;
    }

    @Override
    public void resolveValues() {
        this.max = (float) (minecraft().options.renderDistance().get() * 16);
        this.removeDarkness().maxDist = this.max;
        this.min = this.removeDarkness().minDist;
    }
}
