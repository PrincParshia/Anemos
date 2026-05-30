package princ.anemos.client.state;

import princ.anemos.client.config.AnemosConfigInternal;

import static princ.anemos.client.Constants.configInternal;

public class GammaState extends SharedNumericState<Double> {

    public GammaState() {
        super(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, false);
    }

    private AnemosConfigInternal.Gamma gamma() {
        return configInternal.gamma;
    }

    @Override
    public void resolveValues() {
        this.min = this.gamma().min;
        this.max = this.gamma().max;
    }
}
