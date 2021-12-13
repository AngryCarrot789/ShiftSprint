package dragonjetz.mods.shiftsprint;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = "ShiftSprint", name = "Shift Sprint", version = "1.0.1")
public class ShiftSprint {
    public ShiftSprint() {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Initialising PlayerTickHandler");
        new PlayerTickHandler();
    }
}
