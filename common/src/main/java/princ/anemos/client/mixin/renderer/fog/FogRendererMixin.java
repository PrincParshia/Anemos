package princ.anemos.client.mixin.renderer.fog;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.fog.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static princ.anemos.client.Constants.configGeneral;


@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @ModifyExpressionValue(
            method = "computeFogColor",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GameRenderer;getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F"
            )
    )
    private static float modifyNightVisionFog(float original) {
        if (configGeneral.fakeNightVision.enabled.get() && !configGeneral.fakeNightVision.fog) {
            return 0.0F;
        }
        return original;
    }
}
