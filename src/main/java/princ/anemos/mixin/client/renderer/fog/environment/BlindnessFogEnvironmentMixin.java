package princ.anemos.mixin.client.renderer.fog.environment;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.fog.environment.BlindnessFogEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static princ.anemos.AnemosConstants.*;

@Mixin(BlindnessFogEnvironment.class)
public class BlindnessFogEnvironmentMixin {
    @ModifyExpressionValue(
            method = "setupFog",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/effect/MobEffectInstance;isInfiniteDuration()Z"
            )
    )
    boolean setupFog(final boolean original) {
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
    float setupFog(final float original) {
        if (configGeneral.removeBlindness.enabled.get()) {
            return blindnessDistance().get();
        }
        return original;
    }
}
