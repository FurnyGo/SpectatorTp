package furnygo.sptp;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.network.packet.c2s.play.SpectatorTeleportC2SPacket;
import net.minecraft.text.Text;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;

public class SpTpCommand {
    public static final SuggestionProvider<FabricClientCommandSource> SUGGESTION_PLAYER = (context, builder) -> {
        ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;
        String playerInput = builder.getRemainingLowerCase();
        if (clientPlayer != null) {
            List<String> playerNamesList = clientPlayer.networkHandler.getPlayerList().stream()
                    .map(PlayerListEntry::getProfile)
                    .map(GameProfile::getName)
                    .toList();

            for (String playerName : playerNamesList) {
                if (playerName.toLowerCase().contains(playerInput))
                    builder.suggest(playerName);
            }
        }

        return CompletableFuture.completedFuture(builder.build());

    };
    public static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        LiteralArgumentBuilder<FabricClientCommandSource> sptpCommand = ClientCommandManager.literal("sptp");

        sptpCommand.then(ClientCommandManager.argument("nick", greedyString()).suggests(SUGGESTION_PLAYER).executes(ctx -> {
                    String nickname = ctx.getArgument("nick", String.class);
                    var MC = MinecraftClient.getInstance();
                    ClientPlayNetworkHandler handler = MinecraftClient.getInstance().getNetworkHandler();
                    if (handler == null) {
                        MC.inGameHud.getChatHud().addMessage(Text.of("§c§lYou are not on server\n"));
                    }

                    var nick = handler.getPlayerListEntry(nickname);
                    if (nick == null) {
                        MC.inGameHud.getChatHud().addMessage(Text.of("§c§lNick not found\n"));
                    }

                    var playeruuid = nick.getProfile().getId();

                    if (MC.player.isCreative()){
                        MC.player.networkHandler.sendChatCommand("gamemode spectator");
                        MC.player.networkHandler.sendPacket(new SpectatorTeleportC2SPacket(playeruuid));
                        MC.player.networkHandler.sendChatCommand("gamemode creative");
                    }
                    else MC.player.networkHandler.sendPacket(new SpectatorTeleportC2SPacket(playeruuid));
                    return 1;
                })
        );
        dispatcher.register(sptpCommand);
    }

}
