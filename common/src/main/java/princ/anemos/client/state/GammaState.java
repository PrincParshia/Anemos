package princ.anemos.client.state;

import static princ.anemos.client.Constants.configInternal;

public class GammaState extends SharedNumericState<Double> {

    public GammaState() {
        double min = configInternal.gamma.min;
        double max = configInternal.gamma.max;
        super(min, max, 0.0, 0, 0, false);
    }

    @Override
    public void resolveValues() {
    }
}
