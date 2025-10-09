package princ.anemos.client.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import princ.anemos.util.UnitValueConverter;

import static princ.anemos.AnemosConstants.*;

@Mixin(GameRenderer.class)
@Environment(EnvType.CLIENT)
public class GameRendererMixin {
    @Inject(method = "getNightVisionScale", at = @At("HEAD"), cancellable = true)
    private static void adjustNightVisionScale(LivingEntity livingEntity, float f, CallbackInfoReturnable<Float> cir) {
        if (config.fakeNightVision.enabled.get()) {
            cir.setReturnValue(UnitValueConverter.fromPercent(fnvScale().get()));
        }
    }
}
