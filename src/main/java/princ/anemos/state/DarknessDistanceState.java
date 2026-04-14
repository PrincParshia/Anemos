package princ.anemos.state;

import static princ.anemos.AnemosConstants.*;

public class DarknessDistanceState extends SharedNumericState<Float> {
    public DarknessDistanceState() {
        float min = configInternal.removeDarkness.minDist;
        float max = configInternal.removeDarkness.maxDist;
        super(min, max, 0.0F, 0, 0, false);
    }

    @Override
    public void resolveValues() {
        this.max = (float) (minecraft.options.renderDistance().get() * 16);
        configInternal.removeDarkness.maxDist = this.max;
    }
}
