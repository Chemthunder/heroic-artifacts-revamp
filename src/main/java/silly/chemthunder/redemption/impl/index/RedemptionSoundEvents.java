package silly.chemthunder.redemption.impl.index;

import net.acoyt.acornlib.api.registrants.SoundEventRegistrant;
import net.minecraft.sound.SoundEvent;
import silly.chemthunder.redemption.impl.Redemption;

public interface RedemptionSoundEvents {
    SoundEventRegistrant SOUNDS = new SoundEventRegistrant(Redemption.MOD_ID);

    SoundEvent HUNTER_BLACKOUT = SOUNDS.register("item.hunter.blackout");
    SoundEvent UNSHEATHE = SOUNDS.register("item.unsheathe");
    SoundEvent BECOME_JUDGE = SOUNDS.register("event.become_judge");
    SoundEvent JUDGE_DEATH = SOUNDS.register("event.judge_death");
    SoundEvent PING = SOUNDS.register("event.ping");
    SoundEvent SONAR_PING = SOUNDS.register("event.sonar_ping");

    static void init() {}
}
