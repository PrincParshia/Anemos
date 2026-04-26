package princ.anemos.client.mixin.renderer.fog;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.world.level.material.FogType;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static princ.anemos.client.AnemosConstants.configGeneral;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {
    @ModifyExpressionValue(
            method = "computeFogColor",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/GameRenderer;getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F"
            )
    )
    private static float computeFogColor(float original) {
        if (configGeneral.fakeNightVision.enabled.get() && !configGeneral.fakeNightVision.fog) {
            return 0.0F;
        }
        return original;
    }

    @WrapOperation(
            method = "computeFogColor",
            at = @At(
                    value = "NEW",
                    target = "(FFFF)Lorg/joml/Vector4f;"
            )
    )
    Vector4f computeFogColor(float x, float y, float z, float w, Operation<Vector4f> original, Camera camera, @Local FogType fogType) {
        if (fogType.equals(FogType.LAVA) && !configGeneral.fog.lava ||
                fogType.equals(FogType.WATER) && !configGeneral.fog.water ||
                fogType.equals(FogType.POWDER_SNOW) && !configGeneral.fog.powderSnow ||
                fogType.equals(FogType.ATMOSPHERIC) && !configGeneral.fog.atmospheric
        ) return original.call(x, y, z, 0.0F);
        return original.call(x, y, z, w);
    }
}
