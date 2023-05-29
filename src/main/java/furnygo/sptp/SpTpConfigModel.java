package furnygo.sptp;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = "sptp")
@Config(name = "sptp-config", wrapperName = "SpTpConfig")
public class SpTpConfigModel {
    public boolean useCustomCommand = true;
    public String customCommand = "/warp free_gmspec";
    public String creativeCommand = "/gamemode creative";
    public String survivalCommand = "/gamemode survival";
    public String spectatorCommand = "/gamemode survival";
}
