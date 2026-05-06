package princ.anemos.client.state;

import static princ.anemos.client.Constants.configInternal;

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
