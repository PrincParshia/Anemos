package princ.anemos.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static princ.anemos.AnemosConstants.*;
import static princ.anemos.util.MobEffectUtil.*;

@Mixin(FogRenderer.class)
@Environment(EnvType.CLIENT)
public class FogRendererMixin {
    @Inject(method = "computeFogColor", at = @At("HEAD"), cancellable = true)
    private void computeFogColor(Camera camera, float f, ClientLevel clientLevel, int i, float g, boolean bl, CallbackInfoReturnable<Vector4f> cir) {
        if ((hasEffect(MobEffects.BLINDNESS) && config.removeBlindness.enabled.get()) || (hasEffect(MobEffects.DARKNESS) && config.removeDarkness.enabled.get())) {
            cir.setReturnValue(new Vector4f(0.0F, 0.0F, 0.0F, 1.0F));
        }
    }

    @Redirect(method = "computeFogColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F"))
    private static float redirectNightVisionFogColor(LivingEntity livingEntity, float f) {
        if (config.fakeNightVision.enabled.get() && !config.fakeNightVision.fog) {
            return 0.0F;
        }
        return GameRenderer.getNightVisionScale(livingEntity, f);
    }
}
