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
	public static final GammaState GAMMA_STATE = new GammaState();
	public static final NightVisionScaleState FAKE_NIGHT_VISION_SCALE_STATE = new NightVisionScaleState();
	public static final BlindnessDistanceState BLINDNESS_DISTANCE_STATE = new BlindnessDistanceState();
	public static final DarknessDistanceState DARKNESS_DISTANCE_STATE = new DarknessDistanceState();

	public static Minecraft minecraft() {
		return Minecraft.getInstance();
	}

	public static Identifier withDefaultNamespace(final String string) {
		return Identifier.fromNamespaceAndPath(NAMESPACE, string);
	}

	public static String withKeyMappingPrefix(final String string) {
		return "key." + NAMESPACE + "." + string;
	}

	public static OptionInstance<Double> gamma() {
		return minecraft().options.gamma();
	}

	public static ValidatedFloat nightVisionScale() {
		return configInternal.fakeNightVision.scale;
	}

	public static ValidatedFloat blindnessDistance() {
		return configInternal.removeBlindness.dist;
	}

	public static ValidatedFloat darknessDistance() {
		return configInternal.removeDarkness.dist;
	}

	public static void adjustGamma(GammaState state, ValidatedInt val, ValidatedDouble prev, boolean transition, int transitionTime) {
		if (transition) {
			if (!state.execTransition) {
				state.targetVal = computeTargetVal(gamma().get(), fromPercent(val.get()), prev.get());
				if (state.targetVal == fromPercent(val.get())) {
					prev.validateAndSet(gamma().get());
				}
				state.transitionTime = transitionTime;
				state.elapsedTransitionTime = 0;
				state.execTransition = true;
			} else {
				state.targetVal = computeTargetVal(state.targetVal, fromPercent(val.get()), prev.get());
				state.transitionTime = state.elapsedTransitionTime;
				state.elapsedTransitionTime = 0;
			}
		} else {
			double targetVal = computeTargetVal(gamma().get(), fromPercent(val.get()), prev.get());
			if (targetVal == fromPercent(val.get())) prev.validateAndSet(gamma().get());
			gamma().set(targetVal);
			configInternal.save();
		}
	}

	public static void handleGammaTransition(GammaState state, boolean transition) {
		if (transition && state.execTransition) {
			if (state.transitionTime <= 0) {
				state.execTransition = false;
				configInternal.save();
			} else {
				gamma().set(lerp(gamma().get(), state.targetVal, state.transitionTime));
				state.transitionTime--;
				state.elapsedTransitionTime++;
			}
		}
	}

	public static void adjust(ValidatedFloat validatedFloat, SharedNumericState<Float> state, ValidatedBoolean enabled, boolean transition, int transitionTime) {
		if (transition) {
			if (!state.execTransition) {
				if (!enabled.get()) {
					state.targetVal = state.max;
					if (validatedFloat.get().equals(state.min)) {
						validatedFloat.validateAndSet(state.min);
					}
					enabled.validateAndSet(true);
				} else {
					state.targetVal = computeTargetVal(validatedFloat.get(), state.max, state.min);
				}
				state.transitionTime = transitionTime;
				state.elapsedTransitionTime = 0;
				state.execTransition = true;
			} else {
				state.targetVal = computeTargetVal(state.targetVal, state.max, state.min);
				state.transitionTime = state.elapsedTransitionTime;
				state.elapsedTransitionTime = 0;
			}
		} else {
			enabled.validateAndSet(!enabled.get());
			validatedFloat.validateAndSet(resolveValFromState(enabled.get(), state.max, state.min));
			configGeneral.save();
		}
	}

	public static void handleTransition(ValidatedFloat validatedFloat, SharedNumericState<Float> state, ValidatedBoolean enabled, boolean transition) {
		if (transition && state.execTransition) {
			if (state.transitionTime <= 0) {
				state.execTransition = false;
				if (state.targetVal.equals(state.min)) {
					enabled.validateAndSet(false);
				}
				configGeneral.save();
				configInternal.save();
			} else {
				validatedFloat.validateAndSet(lerp(validatedFloat.get(), state.targetVal, state.transitionTime));
				state.transitionTime--;
				state.elapsedTransitionTime++;
			}
		}
	}

	static double lerp(double current, double val, int transitionTime) {
		return current + ((val - current) / transitionTime);
	}

	static float lerp(float current, float val, int transitionTime) {
		return current + ((val - current) / transitionTime);
	}

	static double computeTargetVal(double current, double val, double prev) {
		return current != val ? val : prev;
	}

	static float computeTargetVal(float current, float val, float prev) {
		return current != val ? val : prev;
	}

	static float resolveValFromState(boolean enabled, float max, float min) {
		return enabled ? max : min;
	}
}