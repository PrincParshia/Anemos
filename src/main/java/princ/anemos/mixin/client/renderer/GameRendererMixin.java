package princ.anemos.mixin.client.renderer;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static princ.anemos.AnemosConstants.*;

@Mixin(GameRenderer.class)
@Environment(EnvType.CLIENT)
public class GameRendererMixin {
    @WrapOperation(
            method = "getNightVisionScale",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;getEffect(Lnet/minecraft/core/Holder;)Lnet/minecraft/world/effect/MobEffectInstance;"
            )
    )
    private static MobEffectInstance getNightVisionScale(final LivingEntity camera, final Holder<MobEffect> effect, final Operation<MobEffectInstance> original) {
        if (configGeneral.fakeNightVision.enabled.get()) {
            return new MobEffectInstance(MobEffects.NIGHT_VISION);
        }
        return original.call(camera, effect);
    }

    @ModifyReturnValue(method = "getNightVisionScale", at = @At("RETURN"))
    private static float getNightVisionScale(final float original, final LivingEntity camera, final float a) {
        if (configGeneral.fakeNightVision.enabled.get()) {
            return nightVisionScale().get();
        }
        return original;
    }
}
