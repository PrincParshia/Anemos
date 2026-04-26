package princ.anemos.client.config;

import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;

import static princ.anemos.client.AnemosConstants.*;
import static princ.anemos.client.util.UnitValueConverter.fromPercent;

@Translation(prefix = CONFIG_TRANSLATION_ID + ".internal")
public class AnemosConfigInternal extends Config {
    public AnemosConfigInternal() {
        super(withDefaultNamespace("internal"));
    }

    public Gamma gamma = new Gamma();
    public FakeNightVision fakeNightVision = new FakeNightVision();
    public RemoveBlindness removeBlindness = new RemoveBlindness();
    public RemoveDarkness removeDarkness = new RemoveDarkness();

    public static class Gamma extends ConfigSection {
        public double min = 0.0, max = 15.0;
        public ValidatedDouble prev = new ValidatedDouble(fromPercent(configGeneral.gamma.def.get()), this.max, this.min);
    }

    public static class FakeNightVision extends ConfigSection {
        public float minScale = 0.0F, maxScale = 1.0F;
        public ValidatedFloat scale = new ValidatedFloat(this.maxScale, this.maxScale, this.minScale);
    }

    public static class RemoveBlindness extends ConfigSection {
        public float minDist = 5.0F, maxDist = 256.0F;
        public ValidatedFloat dist = new ValidatedFloat(this.maxDist, Float.MAX_VALUE, this.minDist);
    }

    public static class RemoveDarkness extends ConfigSection {
        public float minDist = 15.0F, maxDist = 256.0F;
        public ValidatedFloat dist = new ValidatedFloat(this.maxDist, Float.MAX_VALUE, this.minDist);
    }
}
