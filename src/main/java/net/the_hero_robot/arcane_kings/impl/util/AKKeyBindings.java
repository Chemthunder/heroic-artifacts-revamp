package net.the_hero_robot.arcane_kings.impl.util;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.the_hero_robot.arcane_kings.impl.client.PetrificationHandler;
import org.lwjgl.glfw.GLFW;

/**
 * @author AcoYT
 */
public class AKKeyBindings {
    // Vertigo
    public static KeyBinding petrifyTarget;

    public static void register() {
        registerKeyBindings();
        setupPressDetection();
    }

    private static void registerKeyBindings() {
        petrifyTarget = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.arcane_kings.petrify_target",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "key.categories.arcane_kings"
        ));
    }

    private static void setupPressDetection() {
        ClientTickEvents.START_CLIENT_TICK.register(AKKeyBindings::handleKeyPresses);
    }

    private static void handleKeyPresses(MinecraftClient client) {
        ClientPlayerEntity player = client.player;
        if (player == null) return;

        PetrificationHandler.tickKeybindPresses(client, player);
    }
}
