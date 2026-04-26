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

public class AnemosConstants {
    public static final String NAMESPACE = "anemos";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);
    public static final String CONFIG_TRANSLATION_ID = "config." + NAMESPACE;
    public static final Minecraft minecraft = Minecraft.getInstance();
    public static final AnemosConfigGeneral configGeneral = ConfigApiJava.registerAndLoadConfig(AnemosConfigGeneral::new, RegisterType.CLIENT);
    public static final AnemosConfigInternal configInternal = ConfigApiJava.registerAndLoadNoGuiConfig(AnemosConfigInternal::new, RegisterType.CLIENT);
    public static final GammaState GAMMA_STATE = new GammaState();
    public static final NightVisionScaleState FAKE_NIGHT_VISION_SCALE_STATE = new NightVisionScaleState();
    public static final BlindnessDistanceState BLINDNESS_DISTANCE_STATE = new BlindnessDistanceState();
    public static final DarknessDistanceState DARKNESS_DISTANCE_STATE = new DarknessDistanceState();

    public static Identifier withDefaultNamespace(final String string) {
        return Identifier.fromNamespaceAndPath(NAMESPACE, string);
    }

    public static String withKeyMappingPrefix(final String string) {
        return "key." + NAMESPACE + "." + string;
    }

    public static OptionInstance<Double> gamma() {
        return minecraft.options.gamma();
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

    public static void adjustGamma(final GammaState state, final ValidatedInt val, final ValidatedDouble prev, final boolean transition, final int transitionTime) {
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

    public static void handleGammaTransition(final GammaState state, final boolean transition) {
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

    public static void adjust(final ValidatedFloat validatedFloat, final SharedNumericState<Float> state, final ValidatedBoolean enabled, final boolean transition, final int transitionTime) {
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

    public static void handleTransition(final ValidatedFloat validatedFloat, final SharedNumericState<Float> state, final ValidatedBoolean enabled, final boolean transition) {
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

    static double lerp(final double current, final double val, final int transitionTime) {
        return current + ((val - current) / transitionTime);
    }

    static float lerp(final float current, final float val, final int transitionTime) {
        return current + ((val - current) / transitionTime);
    }

    static double computeTargetVal(final double current, final double val, final double prev) {
        return current != val ? val : prev;
    }

    static float computeTargetVal(final float current, final float val, final float prev) {
        return current != val ? val : prev;
    }

    static float resolveValFromState(final boolean enabled, final float max, final float min) {
        return enabled ? max : min;
    }
}
