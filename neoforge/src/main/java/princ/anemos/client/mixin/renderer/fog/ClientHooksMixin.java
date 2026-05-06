package princ.anemos.client.mixin.renderer.fog;

import net.minecraft.client.Camera;
import net.minecraft.world.level.material.FogType;
import net.neoforged.neoforge.client.ClientHooks;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static princ.anemos.client.Constants.configGeneral;

@Mixin(ClientHooks.class)
public class ClientHooksMixin {
    @Redirect(
            method = "getFogColor",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/joml/Vector4f;set(FFFF)Lorg/joml/Vector4f;"
            )
    )
    private static Vector4f modifyFogAlpha(Vector4f dest, float x, float y, float z, float w, Camera camera) {
        FogType blockFogType = camera.getFluidInCamera();
        FogType fogType = blockFogType == FogType.NONE ? FogType.ATMOSPHERIC : blockFogType;

        if (fogType.equals(FogType.LAVA) && !configGeneral.fog.lava ||
                fogType.equals(FogType.WATER) && !configGeneral.fog.water ||
                fogType.equals(FogType.POWDER_SNOW) && !configGeneral.fog.powderSnow ||
                fogType.equals(FogType.ATMOSPHERIC) && !configGeneral.fog.atmospheric
        ) return dest.set(x, y, z, 0.0F);

        return dest.set(x, y, z, w);
    }
}
