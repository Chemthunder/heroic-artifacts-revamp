package net.the_hero_robot.redemption.impl.util;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.networking.c2s.IkirGameModeSwitchPayload;
import net.the_hero_robot.redemption.impl.networking.c2s.IkirImmolationPayload;
import org.lwjgl.glfw.GLFW;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public class RedemptionKeyBindings {
    public static KeyBinding switchGameMode;
    public static KeyBinding useImmolation;

    public static void register() {
        registerKeyBindings();
        setupPressDetection();
    }

    private static void registerKeyBindings() {
        switchGameMode = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.redemption.switch_gamemode",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UP,
                "key.categories.redemption"
        ));

        useImmolation = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.redemption.use_immolation",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_DOWN,
                "key.categories.redemption"
        ));
    }

    private static void setupPressDetection() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                if (switchGameMode.isPressed()) {
                    handleSwitch(client);
                }

                if (useImmolation.isPressed()) {
                    handleImmolation(client);
                }
            }
        });
    }

    // Global variable to track the last time the key was pressed
    private static long lastToggleTime = 0;  // Time of last toggle in milliseconds
    private static final long COOLDOWN_TIME = 500;  // Cooldown time in milliseconds (500 ms = 0.5 seconds)

    private static void handleSwitch(MinecraftClient client) {
        if (!IkirPowerManager.isHero(client.player)) return;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastToggleTime < COOLDOWN_TIME) return;

        try {
            ClientPlayNetworking.send(IkirGameModeSwitchPayload.INSTANCE);

            lastToggleTime = System.currentTimeMillis();
        } catch (Exception e) {
            Redemption.LOGGER.error("Failed to send Ikir Switch Payload!", e);
        }
    }

    private static void handleImmolation(MinecraftClient client) {
        if (!IkirPowerManager.isHero(client.player)) return;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastToggleTime < COOLDOWN_TIME) return;

        try {
            ClientPlayNetworking.send(IkirImmolationPayload.INSTANCE);

            lastToggleTime = System.currentTimeMillis();
        } catch (Exception e) {
            Redemption.LOGGER.error("Failed to send Ikir Ability Payload!", e);
        }
    }
}
