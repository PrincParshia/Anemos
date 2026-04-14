package princ.anemos.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Anemos implements ClientModInitializer {
	public KeyMappingImpl keyMapping;

	public Anemos() {
		this.keyMapping = new KeyMappingImpl();
	}

	@Override
	public void onInitializeClient() {
		this.keyMapping.registerAll();
	}
}