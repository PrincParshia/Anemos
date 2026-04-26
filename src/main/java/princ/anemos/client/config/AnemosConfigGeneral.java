package princ.anemos.client.config;

import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;

import static princ.anemos.client.AnemosConstants.CONFIG_TRANSLATION_ID;
import static princ.anemos.client.AnemosConstants.withDefaultNamespace;

@Translation(prefix = CONFIG_TRANSLATION_ID)
public class AnemosConfigGeneral extends Config {
    public AnemosConfigGeneral() {
        super(withDefaultNamespace("general"));
    }

    public Gamma gamma = new Gamma();
    public FakeNightVision fakeNightVision = new FakeNightVision();
    public RemoveBlindness removeBlindness = new RemoveBlindness();
    public RemoveDarkness removeDarkness = new RemoveDarkness();
    public Fog fog = new Fog();

    @Translation(prefix = CONFIG_TRANSLATION_ID + ".gamma")
    public static class Gamma extends ConfigSection {
        public ValidatedInt def = new ValidatedInt(100, 100, 50);
        public ValidatedInt val = new ValidatedInt(1500, 1500, 200);
        public boolean transition = false;
        public int transitionTime = 30;
    }

    @Translation(prefix = CONFIG_TRANSLATION_ID + ".fakeNightVision")
    public static class FakeNightVision extends ConfigSection {
        public ValidatedBoolean enabled = new ValidatedBoolean(false);
        public boolean transition = false;
        public int transitionTime = 10;
        public boolean fog = false;
    }

    @Translation(prefix = CONFIG_TRANSLATION_ID + ".removeBlindness")
    public static class RemoveBlindness extends ConfigSection {
        public ValidatedBoolean enabled = new ValidatedBoolean(false);
        public boolean transition = false;
        public int transitionTime = 20;
    }

    @Translation(prefix = CONFIG_TRANSLATION_ID + ".removeDarkness")
    public static class RemoveDarkness extends ConfigSection {
        public ValidatedBoolean enabled = new ValidatedBoolean(false);
        public boolean transition = false;
        public int transitionTime = 20;
    }

    @Translation(prefix = CONFIG_TRANSLATION_ID + ".fog")
    public static class Fog extends ConfigSection {
        public boolean lava = true;
        public boolean water = true;
        public boolean powderSnow = true;
        public boolean atmospheric = true;
    }
}
