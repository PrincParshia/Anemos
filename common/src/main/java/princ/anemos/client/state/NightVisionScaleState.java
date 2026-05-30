package princ.anemos.client.state;

import princ.anemos.client.config.AnemosConfigInternal;

import static princ.anemos.client.Constants.configInternal;

public class NightVisionScaleState extends SharedNumericState<Float> {

    public NightVisionScaleState() {
        super(0.0F, 0.0F, 0.0F, 0.0F, 0.0F,0.0F, 0.0F, false);
    }

    private AnemosConfigInternal.FakeNightVision fakeNightVision() {
        return configInternal.fakeNightVision;
    }

    @Override
    public void resolveValues() {
        this.min = this.fakeNightVision().minScale;
        this.max = this.fakeNightVision().maxScale;
    }
}
