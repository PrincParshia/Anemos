package princ.anemos.state;

import static princ.anemos.AnemosConstants.configInternal;

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
