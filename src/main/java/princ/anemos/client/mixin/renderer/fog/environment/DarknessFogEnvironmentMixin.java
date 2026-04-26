package princ.anemos.client.mixin.renderer.fog.environment;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.fog.environment.DarknessFogEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static princ.anemos.client.AnemosConstants.configGeneral;
import static princ.anemos.client.AnemosConstants.darknessDistance;

@Mixin(DarknessFogEnvironment.class)
public class DarknessFogEnvironmentMixin {
    @ModifyExpressionValue(
            method = "setupFog",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/Mth;lerp(FFF)F"
            )
    )
    float setupFog(final float original) {
        if (configGeneral.removeDarkness.enabled.get()) {
            return darknessDistance().get();
        }
        return original;
    }
}
