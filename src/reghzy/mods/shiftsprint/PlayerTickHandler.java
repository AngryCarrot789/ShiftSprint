package reghzy.mods.shiftsprint;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

import java.util.EnumSet;

public class PlayerTickHandler implements ITickHandler {
    private final EnumSet<TickType> ticksToGet;
    private final ShiftSprint shiftSprint;

    public PlayerTickHandler(ShiftSprint shiftSprint) {
        this.shiftSprint = shiftSprint;
        TickRegistry.registerTickHandler(this, Side.CLIENT);
        this.ticksToGet = EnumSet.of(TickType.PLAYER);
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        EntityPlayer player = (EntityPlayer) tickData[0];
        if (Keyboard.isKeyDown(this.shiftSprint.config.getKey().field_74512_d)) {
            player.func_70031_b(true);
        }
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