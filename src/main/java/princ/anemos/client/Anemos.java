package princ.anemos.client;

import net.fabricmc.api.ClientModInitializer;

public class Anemos implements ClientModInitializer {
	public final KeyMappingImpl keyMapping;

	public Anemos() {
		this.keyMapping = new KeyMappingImpl();
	}

	@Override
	public void onInitializeClient() {
		this.keyMapping.registerAll();
	}
}