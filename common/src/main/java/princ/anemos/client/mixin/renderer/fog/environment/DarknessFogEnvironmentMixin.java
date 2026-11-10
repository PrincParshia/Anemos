package princ.anemos.client.mixin.renderer.fog.environment;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.DarknessFogEnvironment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static princ.anemos.client.Constants.*;

@Mixin(DarknessFogEnvironment.class)
public class DarknessFogEnvironmentMixin {

    @ModifyVariable(method = "setupFog", at = @At("STORE"), name = "f")
    float modifyDistance(float distance, FogData fog, Camera camera, ClientLevel level, float renderDistance, DeltaTracker deltaTracker) {
        if (removeDarkness().enabled.get()) {
            return calc(darknessDistance(), darknessDistanceState(), removeDarkness().enabled, removeDarkness().transition, deltaTracker.getGameTimeDeltaTicks());
        }
        return distance;
    }
}
