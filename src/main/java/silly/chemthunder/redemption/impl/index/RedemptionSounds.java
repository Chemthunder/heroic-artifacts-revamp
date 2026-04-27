package silly.chemthunder.redemption.impl.index;

import net.acoyt.acornlib.api.registrants.SoundEventRegistrant;
import net.minecraft.sound.SoundEvent;
import silly.chemthunder.redemption.impl.Redemption;

public interface RedemptionSounds {
    SoundEventRegistrant SOUNDS = new SoundEventRegistrant(Redemption.MOD_ID);

    SoundEvent HUNTERS_GLASS_BLACKOUT = SOUNDS.register("item.hunters_glass.blackout");
    SoundEvent KATANA_UNSHEATHE = SOUNDS.register("item.katana.unsheathe");
    SoundEvent KATANA_SHEATHE = SOUNDS.register("item.katana.sheathe");

    SoundEvent EVENT_BECOME_JUDGE = SOUNDS.register("event.become_judge");
    SoundEvent EVENT_JUDGE_DEATH = SOUNDS.register("event.judge_death");
    SoundEvent EVENT_PING = SOUNDS.register("event.ping");
    SoundEvent EVENT_SONAR_PING = SOUNDS.register("event.sonar_ping");

    static void init() {}
}
