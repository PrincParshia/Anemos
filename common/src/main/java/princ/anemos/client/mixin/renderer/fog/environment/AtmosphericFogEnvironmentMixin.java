package princ.anemos.client.mixin.renderer.fog.environment;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.AtmosphericFogEnvironment;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static princ.anemos.client.Constants.fog;

@Mixin(AtmosphericFogEnvironment.class)
public class AtmosphericFogEnvironmentMixin {

    @WrapOperation(
            method = "setupFog",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/renderer/fog/FogData;environmentalStart:F",
                    opcode = Opcodes.PUTFIELD
            )
    )
    void modifyEnvironmentalStart(FogData instance, float environmentalStart, Operation<Void> original, FogData fog, Camera camera, ClientLevel level, float renderDistance, DeltaTracker deltaTracker) {
        if (level.dimension() == Level.NETHER && fog().netherHorizon) {
            float fogEndDistance = 96.0F;
            float fogStartDistance = 10.0F;
            environmentalStart = renderDistance - (fogEndDistance - fogStartDistance);
        }
        original.call(instance, environmentalStart);
    }

    @WrapOperation(
            method = "setupFog",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/renderer/fog/FogData;environmentalEnd:F",
                    opcode = Opcodes.PUTFIELD
            )
    )
    void modifyEnvironmentalEnd(FogData instance, float environmentalEnd, Operation<Void> original, FogData fog, Camera camera, ClientLevel level, float renderDistance, DeltaTracker deltaTracker) {
        if (level.dimension() == Level.NETHER && fog().netherHorizon) {
            environmentalEnd = renderDistance;
        }
        original.call(instance, environmentalEnd);
    }
}
