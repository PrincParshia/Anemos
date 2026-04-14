package princ.anemos.mixin.client.world.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static princ.anemos.AnemosConstants.*;

@Mixin(LivingEntity.class)
@Environment(EnvType.CLIENT)
public class LivingEntityMixin {
    @ModifyReturnValue(method = "hasEffect", at = @At(value = "RETURN"))
    boolean hasEffect(final boolean original, final Holder<MobEffect> effect) {
        if (effect == MobEffects.NIGHT_VISION && configGeneral.fakeNightVision.enabled.get()) {
            return true;
        }
        return original;
    }
}
