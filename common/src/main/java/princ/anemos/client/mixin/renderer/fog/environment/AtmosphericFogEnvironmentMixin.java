package princ.anemos.client.mixin.renderer.fog.environment;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.AtmosphericFogEnvironment;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static princ.anemos.client.Constants.fog;

@Mixin(AtmosphericFogEnvironment.class)
public class AtmosphericFogEnvironmentMixin {

    @ModifyExpressionValue(
            method = "setupFog",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/attribute/EnvironmentAttributeProbe;getValue(Lnet/minecraft/world/attribute/EnvironmentAttribute;F)Ljava/lang/Object;",
                    ordinal = 0
            )
    )
    Object modifyEnvironmentalStart(Object original, FogData fog, Camera camera, ClientLevel level, float renderDistance, DeltaTracker deltaTracker) {
        if (level.dimension() == Level.NETHER && fog().netherHorizon) {
            float fogEndDistance = 96.0F;
            float fogStartDistance = 10.0F;
            float renderDistanceFogSpan = fogEndDistance - fogStartDistance;
            return renderDistance - renderDistanceFogSpan;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "setupFog",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/attribute/EnvironmentAttributeProbe;getValue(Lnet/minecraft/world/attribute/EnvironmentAttribute;F)Ljava/lang/Object;",
                    ordinal = 1
            )
    )
    Object modifyEnvironmentalEnd(Object original, FogData fog, Camera camera, ClientLevel level, float renderDistance, DeltaTracker deltaTracker) {
        if (level.dimension() == Level.NETHER && fog().netherHorizon) {
            return renderDistance;
        }
        return original;
    }
}
