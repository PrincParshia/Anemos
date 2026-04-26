package princ.anemos.client.state;

import static princ.anemos.client.AnemosConstants.configInternal;

public class NightVisionScaleState extends SharedNumericState<Float> {
    public NightVisionScaleState() {
        super(configInternal.fakeNightVision.minScale, configInternal.fakeNightVision.maxScale, 0.0F, 0, 0, false);
    }

    @Override
    public void resolveValues() {
    }
}
