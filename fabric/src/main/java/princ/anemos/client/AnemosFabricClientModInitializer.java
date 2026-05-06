package princ.anemos.client;

import net.fabricmc.api.ClientModInitializer;

public class AnemosFabricClientModInitializer implements ClientModInitializer {
    public KeyMappingImpl keyMapping;

    public AnemosFabricClientModInitializer() {
        this.keyMapping = new KeyMappingImpl();
    }

    @Override
    public void onInitializeClient() {
        Anemos.init();
        this.keyMapping.registerAll();
    }
}
