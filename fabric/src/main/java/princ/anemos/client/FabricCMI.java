package princ.anemos.client;

import net.fabricmc.api.ClientModInitializer;

public class FabricCMI implements ClientModInitializer {
    public KeyMappingImpl keyMapping;

    public FabricCMI() {
        this.keyMapping = new KeyMappingImpl();
    }

    @Override
    public void onInitializeClient() {
        Anemos.init();
        this.keyMapping.registerAll();
    }
}
