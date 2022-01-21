package reghzy.mods.shiftsprint;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import io.netty.util.concurrent.FastThreadLocalThread;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import org.lwjgl.input.Keyboard;

import java.util.EnumSet;

public class PlayerTickHandler implements ITickHandler {
    private final EnumSet<TickType> ticksToGet;
    private final ShiftSprint shiftSprint;

    private static final float LERP_TIME = 1.0f / 1.2f;
    private static final float DELTA_TIME = LERP_TIME / 20.0f;

    public PlayerTickHandler(ShiftSprint shiftSprint) {
        this.shiftSprint = shiftSprint;
        TickRegistry.registerTickHandler(this, Side.CLIENT);
        this.ticksToGet = EnumSet.of(TickType.PLAYER);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        EntityPlayer player = (EntityPlayer) tickData[0];
        if (Keyboard.isKeyDown(this.shiftSprint.config.getKey().field_74512_d)) {
            player.func_70031_b(true);
        }

        // boolean changeFov = false;
        // if (player.func_70051_ag()) {
        //     this.targetFov = this.launchFov + 0.2f;
        // }
        // else {
        //     this.targetFov = this.launchFov - 0.2f;
        // }
        // setFov(lerp(getFov(), this.targetFov, 0.25f));
    }

    @ForgeSubscribe
    public void onFovChange(FOVUpdateEvent event) {
        final float multi = 1.10f;// this.shiftSprint.config.getFovMultiplier();
        if (event.entity.func_70051_ag()) {
            event.newfov *= multi;
        }
    }

    public static float lerp(float from, float to, float delta) {
        return from + ((to - from) * delta);
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {

    }

    @Override
    public EnumSet<TickType> ticks() {
        return this.ticksToGet;
    }

    @Override
    public String getLabel() {
        return "REghZy/Carrot Player Tick Handler";
    }
}