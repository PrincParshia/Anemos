package princ.anemos.client.mixin.world.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static princ.anemos.client.Constants.fakeNightVision;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyReturnValue(method = "hasEffect", at = @At(value = "RETURN"))
    boolean returnFakeNightVision(boolean original, Holder<MobEffect> effect) {
        if (effect == MobEffects.NIGHT_VISION && fakeNightVision().enabled.get()) {
            return true;
        }
        return original;
    }

    @ModifyReturnValue(method = "getEffect", at = @At("RETURN"))
    MobEffectInstance returnFakeNightVision(@Nullable MobEffectInstance original, Holder<MobEffect> effect) {
        if (effect == MobEffects.NIGHT_VISION && fakeNightVision().enabled.get()) {
            return new MobEffectInstance(MobEffects.NIGHT_VISION);
        }
        return original;
    }
}
