package princ.anemos.client.mixin.renderer;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static princ.anemos.client.Constants.*;

@Mixin(LightTexture.class)
public class LightmapRenderStateExtractorMixin {

    @ModifyExpressionValue(
            method = "updateLightTexture",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/renderer/LightTexture;updateLightTexture:Z",
                    opcode = Opcodes.GETFIELD
            )
    )
    boolean modifyNeedsUpdate(boolean original) {
        if (nightVisionScaleState().execTransition || gammaState().execTransition) {
            return true;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "updateLightTexture",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;",
                    ordinal = 2
            )
    )
    Object modifyGamma(Object original) {
        DeltaTracker deltaTracker = Minecraft.getInstance().getDeltaTracker();
        return calcGamma(gammaState(), gamma().transition, deltaTracker.getGameTimeDeltaTicks());
    }
}
