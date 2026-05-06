package princ.anemos.client.mixin.renderer.fog.environment;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.fog.environment.BlindnessFogEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static princ.anemos.client.Constants.blindnessDistance;
import static princ.anemos.client.Constants.configGeneral;

@Mixin(BlindnessFogEnvironment.class)
public class BlindnessFogEnvironmentMixin {

    @ModifyExpressionValue(
            method = "setupFog",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/effect/MobEffectInstance;isInfiniteDuration()Z"
            )
    )
    boolean fixCulprits(boolean original) {
        if (configGeneral.removeBlindness.enabled.get()) {
            return false;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "setupFog",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;lerp(FFF)F"
            )
    )
    float modifyDistance(float original) {
        if (configGeneral.removeBlindness.enabled.get()) {
            return blindnessDistance().get();
        }
        return original;
    }
}
