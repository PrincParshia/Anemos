package princ.anemos.client.mixin.renderer.fog;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.world.level.material.FogType;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static princ.anemos.client.Constants.configGeneral;

@Mixin(FogRenderer.class)
public class FabricFogRendererMixin {

    @Redirect(
            method = "computeFogColor",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/joml/Vector4f;set(FFFF)Lorg/joml/Vector4f;"
            )
    )
    Vector4f computeFogColor(Vector4f dest, float x, float y, float z, float w, @Local FogType fogType) {
        if (fogType.equals(FogType.LAVA) && !configGeneral.fog.lava ||
                fogType.equals(FogType.WATER) && !configGeneral.fog.water ||
                fogType.equals(FogType.POWDER_SNOW) && !configGeneral.fog.powderSnow ||
                fogType.equals(FogType.ATMOSPHERIC) && !configGeneral.fog.atmospheric
        ) return dest.set(x, y, z, 0.0F);

        return dest.set(x, y, z, w);
    }
}
