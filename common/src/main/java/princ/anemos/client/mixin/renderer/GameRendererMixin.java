package princ.anemos.client.mixin.renderer;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static princ.anemos.client.Constants.configGeneral;
import static princ.anemos.client.Constants.nightVisionScale;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @WrapOperation(
            method = "getNightVisionScale",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getEffect(Lnet/minecraft/core/Holder;)Lnet/minecraft/world/effect/MobEffectInstance;"
            )
    )
    private static MobEffectInstance returnFakeNightVision(LivingEntity camera, Holder<MobEffect> effect, Operation<MobEffectInstance> original) {
        if (configGeneral.fakeNightVision.enabled.get()) {
            return new MobEffectInstance(MobEffects.NIGHT_VISION);
        }
        return original.call(camera, effect);
    }

    @ModifyReturnValue(method = "getNightVisionScale", at = @At("RETURN"))
    private static float modifyNightVisionScale(float original, LivingEntity camera, float a) {
        if (configGeneral.fakeNightVision.enabled.get()) {
            return nightVisionScale().get();
        }
        return original;
    }
}
