package princ.anemos.client;

import com.mojang.serialization.Codec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.OptionInstance;

import java.util.Optional;

import static princ.anemos.AnemosConstants.*;

@Environment(EnvType.CLIENT)
public class OptionInstanceImpl {
    @Environment(EnvType.CLIENT)
    public enum UnitDouble implements OptionInstance.SliderableValueSet<Double> {
        INSTANCE;

        final double min, max;

        UnitDouble() {
            this.min = configInternal.gamma.min;
            this.max = configInternal.gamma.max;
        }

        public Optional<Double> validateValue(final Double value) {
            return value >= this.min && value <= this.max ? Optional.of(value) : Optional.empty();
        }

        public double toSliderValue(final Double value) {
            return value;
        }

        public Double fromSliderValue(final double slider) {
            return slider;
        }

        public Codec<Double> codec() {
            return Codec.withAlternative(Codec.doubleRange(this.min, this.max), Codec.BOOL, (b) -> b ? this.max : this.min);
        }
    }
}
