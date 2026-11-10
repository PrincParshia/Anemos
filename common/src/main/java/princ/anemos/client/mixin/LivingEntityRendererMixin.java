package princ.anemos.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @ModifyReturnValue(method = "getRenderType", at = @At("RETURN"))
    RenderType getRenderType(@Nullable RenderType original, @Local Identifier texture) {
        if (original == RenderTypes.entityCutout(texture)) {
            return RenderTypes.entityTranslucent(texture);
        }
        return original;
    }
}
