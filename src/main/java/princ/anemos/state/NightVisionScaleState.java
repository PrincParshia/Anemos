package princ.anemos.state;

import static princ.anemos.AnemosConstants.configInternal;

public class NightVisionScaleState extends SharedNumericState<Float> {
    public NightVisionScaleState() {
        float min = configInternal.fakeNightVision.minScale;
        float max = configInternal.fakeNightVision.maxScale;
        super(min, max, 0.0F, 0, 0, false);
    }

    @Override
    public void resolveValues() {
    }
}
