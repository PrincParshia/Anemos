package princ.anemos.client.mixin.renderer.fog;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.world.level.material.FogType;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static princ.anemos.client.Constants.fog;

@Mixin(FogRenderer.class)
public class FabricFogRendererMixin {

    @ModifyReturnValue(method = "computeFogColor", at = @At("RETURN"))
    Vector4f computeFogColor(Vector4f original, @Local FogType fogType) {
        if (fogType.equals(FogType.LAVA) && !fog().lava ||
                fogType.equals(FogType.WATER) && !fog().water ||
                fogType.equals(FogType.POWDER_SNOW) && !fog().powderSnow ||
                fogType.equals(FogType.ATMOSPHERIC) && !fog().atmospheric
        ) return new Vector4f(original.x, original.y, original.z, 0.0F);

        return original;
    }
}
