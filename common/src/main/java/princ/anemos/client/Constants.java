package princ.anemos.client;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import princ.anemos.client.config.AnemosConfigGeneral;
import princ.anemos.client.config.AnemosConfigInternal;
import princ.anemos.client.state.*;

import static princ.anemos.client.util.UnitValueConverter.fromPercent;

public class Constants {
	public static final String NAMESPACE = "anemos";
	public static final String NAME = "Anemos";
	public static final Logger LOG = LoggerFactory.getLogger(NAME);
	public static final String CONFIG_TRANSLATION_ID = "config." + NAMESPACE;
	public static final AnemosConfigGeneral configGeneral = ConfigApiJava.registerAndLoadConfig(AnemosConfigGeneral::new, RegisterType.CLIENT);
	public static final AnemosConfigInternal configInternal = ConfigApiJava.registerAndLoadNoGuiConfig(AnemosConfigInternal::new, RegisterType.CLIENT);
	private static final GammaState GAMMA_STATE = new GammaState();
	private static final NightVisionScaleState FAKE_NIGHT_VISION_SCALE_STATE = new NightVisionScaleState();
	private static final BlindnessDistanceState BLINDNESS_DISTANCE_STATE = new BlindnessDistanceState();
	private static final DarknessDistanceState DARKNESS_DISTANCE_STATE = new DarknessDistanceState();

	public static Minecraft minecraft() {
		return Minecraft.getInstance();
	}

	public static Identifier withDefaultNamespace(final String string) {
		return Identifier.fromNamespaceAndPath(NAMESPACE, string);
	}

	public static String withKeyMappingPrefix(final String string) {
		return "key." + NAMESPACE + "." + string;
	}

	public static AnemosConfigGeneral.Gamma gamma() {
		return configGeneral.gamma;
	}

	public static OptionInstance<Double> gammaVal() {
		return minecraft().options.gamma();
	}

	public static ValidatedDouble gammaPrevVal() {
		return configInternal.gamma.prev;
	}

	public static GammaState gammaState() {
		return GAMMA_STATE;
	}

	public static AnemosConfigGeneral.FakeNightVision fakeNightVision() {
		return configGeneral.fakeNightVision;
	}

	public static ValidatedFloat nightVisionScale() {
		return configInternal.fakeNightVision.scale;
	}

	public static NightVisionScaleState nightVisionScaleState() {
		return FAKE_NIGHT_VISION_SCALE_STATE;
	}

	public static AnemosConfigGeneral.RemoveBlindness removeBlindness() {
		return configGeneral.removeBlindness;
	}

	public static ValidatedFloat blindnessDistance() {
		return configInternal.removeBlindness.dist;
	}

	public static BlindnessDistanceState blindnessDistanceState() {
		return BLINDNESS_DISTANCE_STATE;
	}

	public static AnemosConfigGeneral.RemoveDarkness removeDarkness() {
		return configGeneral.removeDarkness;
	}

	public static ValidatedFloat darknessDistance() {
		return configInternal.removeDarkness.dist;
	}

	public static DarknessDistanceState darknessDistanceState() {
		return DARKNESS_DISTANCE_STATE;
	}

	public static AnemosConfigGeneral.Fog fog() {
		return configGeneral.fog;
	}

	public static void toggleGamma(GammaState state, ValidatedInt peak, ValidatedDouble prev, boolean transition, int transitionDuration) {
		state.resolveValues();
		if (transition) {
			if (!state.execTransition) {
				state.current = gammaVal().get();
				state.start = state.current;
				state.end = !gammaVal().get().equals(fromPercent(peak.get())) ? fromPercent(peak.get()) : prev.get();
				if (state.end == fromPercent(peak.get())) {
					prev.validateAndSet(gammaVal().get());
				}
				state.progress = 0.0;
				state.transitionDuration = (double) transitionDuration;
				state.execTransition = true;
			} else {
				state.start = state.current;
				state.end = !state.end.equals(fromPercent(peak.get())) ? fromPercent(peak.get()) : prev.get();
				double progress = (state.current - prev.get()) / (fromPercent(peak.get()) - prev.get());
				state.transitionDuration = transitionDuration * (state.end.equals(fromPercent(peak.get())) ? 1.0 - progress : progress);
				state.progress = 0.0;
			}
		} else {
			double endVal = !gammaVal().get().equals(fromPercent(peak.get())) ? fromPercent(peak.get()) : prev.get();
			if (endVal == fromPercent(peak.get())) prev.validateAndSet(gammaVal().get());
			gammaVal().set(endVal);
			configInternal.save();
		}
	}

	public static double calcGamma(GammaState state, boolean transition, float deltaTicks) {
		if (transition && state.execTransition) {
			state.progress += deltaTicks / state.transitionDuration;

			if (state.progress >= 1.0) {
				state.execTransition = false;
				state.current = state.end;
				gammaVal().set(state.end);
				configInternal.save();
			} else {
				return state.current = Mth.lerp(state.progress, state.start, state.end);
			}
		}
		return gammaVal().get();
	}

	public static void toggle(ValidatedFloat val, SharedNumericState<Float> state, ValidatedBoolean enabled, boolean transition, int transitionDuration) {
		state.resolveValues();
		if (transition) {
			if (!state.execTransition) {
				if (!enabled.get()) {
					state.end = state.max;
					if (!val.get().equals(state.min)) {
						val.validateAndSet(state.min);
					}
					enabled.validateAndSet(true);
				} else {
					state.end = !val.get().equals(state.max) ? state.max : state.min;
				}
				state.current = val.get();
				state.start = state.current;
				state.progress = 0.0F;
				state.transitionDuration = (float) transitionDuration;
				state.execTransition = true;
			} else {
				state.start = state.current;
				state.end = !state.end.equals(state.max) ? state.max : state.min;
				float progress = (state.current - state.min) / (state.max - state.min);
				state.transitionDuration = transitionDuration * (state.end.equals(state.max) ? 1.0F - progress : progress);
				state.progress = 0.0F;
			}
		} else {
			enabled.validateAndSet(!enabled.get());
			val.validateAndSet(enabled.get() ? state.max : state.min);
			configGeneral.save();
			configInternal.save();
		}
	}

	public static float calc(ValidatedFloat val, SharedNumericState<Float> state, ValidatedBoolean enabled, boolean transition, float deltaTicks) {
		if (transition && state.execTransition) {
			state.progress += deltaTicks / state.transitionDuration;

			if (state.progress >= 1.0F) {
				state.execTransition = false;
				state.current = state.end;
				val.validateAndSet(state.end);
				if (state.end.equals(state.min)) {
					enabled.validateAndSet(false);
				}
				configGeneral.save();
				configInternal.save();
			} else {
				return state.current = Mth.lerp(state.progress, state.start, state.end);
			}
		}
		return val.get();
	}
}