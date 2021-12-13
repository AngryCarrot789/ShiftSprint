package dragonjetz.mods.shiftsprint;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

import java.util.EnumSet;

public class PlayerTickHandler implements ITickHandler {
    private final EnumSet<TickType> ticksToGet;

    /*
     * This Tick Handler will fire for whatever TickType's you construct and register it with.
     */
    public PlayerTickHandler() {
        TickRegistry.registerTickHandler(this, Side.CLIENT);
        this.ticksToGet = EnumSet.of(TickType.PLAYER);
    }

    /*
     * I suggest putting all your tick Logic in EITHER of these, but staying in one
     */
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        playerTick((EntityPlayer) tickData[0]);
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {

    }

    @Override
    public EnumSet<TickType> ticks() {
        return ticksToGet;
    }

    @Override
    public String getLabel() {
        return "REghZy Player Tick Handler";
    }

    public static void playerTick(EntityPlayer player) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            player.func_70031_b(true);
        }
        // testing a NEI exploit... this seems to actually update the spawner at those coords server-side...
        // else if (Keyboard.isKeyDown(Keyboard.KEY_H)) {
        //     player.func_71035_c("Trying GM1...");
        //     NEICPH.sendMobSpawnerID(82, 134, 302, "joe ronimo xd");
        // }
    }
}