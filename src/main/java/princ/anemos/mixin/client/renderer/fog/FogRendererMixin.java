package princ.anemos.mixin.client.renderer.fog;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.world.level.material.FogType;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static princ.anemos.AnemosConstants.*;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {
    @ModifyExpressionValue(
            method = "computeFogColor",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GameRenderer;getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F"
            )
    )
    private static float computeFogColor(final float original) {
        if (configGeneral.fakeNightVision.enabled.get() && !configGeneral.fakeNightVision.fog) {
            return 0.0F;
        }
        return original;
    }

    @Redirect(
            method = "computeFogColor",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/joml/Vector4f;set(FFFF)Lorg/joml/Vector4f;"
            )
    )
    Vector4f setupFog(final Vector4f dest, float x, float y, float z, float w, @Local FogType fogType) {
        if (fogType.equals(FogType.LAVA) && !configGeneral.fog.lava ||
                fogType.equals(FogType.WATER) && !configGeneral.fog.water ||
                fogType.equals(FogType.POWDER_SNOW) && !configGeneral.fog.powderSnow ||
                fogType.equals(FogType.ATMOSPHERIC) && !configGeneral.fog.atmospheric
        ) return dest.set(x, y, z, 0.0F);

        return dest.set(x, y, z, w);
    }
}
