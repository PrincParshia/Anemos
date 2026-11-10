package princ.anemos.client.mixin.renderer.fog;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.Camera;
import net.minecraft.world.level.material.FogType;
import net.neoforged.neoforge.client.ClientHooks;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static princ.anemos.client.Constants.fog;

@Mixin(ClientHooks.class)
public class ClientHooksMixin {

    @ModifyReturnValue(
            method = "getFogColor",
            at = @At("RETURN")
    )
    private static Vector4f modifyFogAlpha(Vector4f original, Camera camera) {
        FogType blockFogType = camera.getFluidInCamera();
        FogType fogType = blockFogType == FogType.NONE ? FogType.ATMOSPHERIC : blockFogType;

        if (fogType.equals(FogType.LAVA) && !fog().lava ||
                fogType.equals(FogType.WATER) && !fog().water ||
                fogType.equals(FogType.POWDER_SNOW) && !fog().powderSnow ||
                fogType.equals(FogType.ATMOSPHERIC) && !fog().atmospheric
        ) return new Vector4f(original.x, original.y, original.z, 0.0F);

        return original;
    }
}
