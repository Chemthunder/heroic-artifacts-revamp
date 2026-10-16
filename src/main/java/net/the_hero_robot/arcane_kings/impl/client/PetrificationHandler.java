package net.the_hero_robot.arcane_kings.impl.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.the_hero_robot.arcane_kings.impl.cca.entity.PetrificationComponent;
import net.the_hero_robot.arcane_kings.impl.networking.c2s.SetVertigoTargetPayload;
import net.the_hero_robot.arcane_kings.impl.util.AKKeyBindings;
import org.jetbrains.annotations.NotNull;

/**
 * @author AcoYT
 */
public class PetrificationHandler {
    // Vertigo
    public static int timeTargeting = 0;
    public static TriState prevPressed = TriState.DEFAULT;
    public static int currentTarget = -1;

    // Target
    public static int timeTargeted = 0; // greater than zero if being targeted

    public static void tickKeybindPresses(MinecraftClient client, @NotNull ClientPlayerEntity player) {
        boolean pressed = AKKeyBindings.petrifyTarget.isPressed();

        if (prevPressed == TriState.DEFAULT) {
            prevPressed = TriState.of(pressed);
        }

        if (prevPressed.get() != pressed) {
            prevPressed = TriState.of(pressed);
        }

        //client.inGameHud.setOverlayMessage(Text.literal("Pressed: " + prevPressed.get()), false);
    }

    public static void tick(MinecraftClient client) {
        ClientWorld world = client.world;
        if (world == null) return;

        if (currentTarget != -1 && world.getEntityById(currentTarget) == null) {
            currentTarget = -1;
        }

        if (prevPressed.get()) {
            HitResult target = client.crosshairTarget;

            if (target instanceof EntityHitResult hitResult) {
                Entity entity = hitResult.getEntity();

                if (entity instanceof PlayerEntity player) {
                    client.inGameHud.setOverlayMessage(Text.literal("Progress: " + PetrificationComponent.KEY.get(player).getTimePetrifying() / 100.0F + "%"), false);

                    if (currentTarget == player.getId()) return;

                    currentTarget = player.getId();
                    ClientPlayNetworking.send(new SetVertigoTargetPayload(player.getId()));
                }
            } else {
                currentTarget = -1;
                client.inGameHud.setOverlayMessage(Text.literal("HitResult " + (target != null ? target.getType().name() : "null") + " is not valid!"), false);
            }
        } else {
            if (timeTargeting > 0) {
                timeTargeting = 0;
            }
        }
    }

    public static void renderShaders(WorldRenderContext context) {}

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(PetrificationHandler::tick);
    }
}
