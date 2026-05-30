package princ.anemos.client.mixin.renderer.fog.environment;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.BlindnessFogEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static princ.anemos.client.Constants.*;

@Mixin(BlindnessFogEnvironment.class)
public class BlindnessFogEnvironmentMixin {

    @ModifyVariable(method = "setupFog", at = @At("STORE"), name = "distance")
    float modifyDistance(float distance, FogData fog, Camera camera, ClientLevel level, float renderDistance, DeltaTracker deltaTracker) {
        if (removeBlindness().enabled.get()) {
            return calc(blindnessDistance(), blindnessDistanceState(), removeBlindness().enabled, removeBlindness().transition, deltaTracker.getGameTimeDeltaTicks());
        }
        return distance;
    }
}
