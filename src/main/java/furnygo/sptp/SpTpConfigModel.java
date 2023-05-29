package furnygo.sptp;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = "sptp")
@Config(name = "sptp-config", wrapperName = "SpTpConfig")
public class SpTpConfigModel {
    public boolean useTeleportToRegion = false;
    public String teleportToRegionCommand = "warp free_spectator";
    public int teleportToRegionCooldown = 100;
    public String creativeCommand = "gamemode creative";
    public String survivalCommand = "gamemode survival";
    public String spectatorCommand = "gamemode spectator";
}
