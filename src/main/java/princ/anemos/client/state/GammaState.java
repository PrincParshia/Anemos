package princ.anemos.client.state;

import static princ.anemos.client.AnemosConstants.configInternal;

public class GammaState extends SharedNumericState<Double> {
    public GammaState() {
        super(configInternal.gamma.min, configInternal.gamma.max, 0.0, 0, 0, false);
    }

    @Override
    public void resolveValues() {
    }
}
