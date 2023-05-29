package furnygo.sptp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpTpMain implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("sptp");
	public static final furnygo.sptp.SpTpConfig CONFIG = furnygo.sptp.SpTpConfig.createAndLoad();
	@Override
	public void onInitialize() {
		ClientCommandRegistrationCallback.EVENT.register(SpTpCommand::registerCommands);
	}
}
