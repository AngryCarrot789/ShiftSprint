package reghzy.mods.shiftsprint;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.settings.EnumOptions;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

import java.util.ArrayList;
import java.util.Arrays;

@Mod(
        modid = "ShiftSprint",
        name = "Shift Sprint",
        version = "1.1.0",
        dependencies = "required-after:Forge@[9.10.0.837,); after:ChickenChunks; after:IronChest; after:OpenPeripheralCore; after:WR-CBE|Core; after:ForgeMultipart; after:ProjRed|Core; after:MFFS; after:AppliedEnergistics; after:ICBM|Explosion; after:InvTweaks; after:ThermalExpansion; after:GalacticraftCore; after:AtomicScience; after:ImmibisMicroblocks; after:IC2; after:BuildCraft|Core; after:BuildCraft|Energy; after:IC2")
// @NetworkMod(
//         clientSideRequired = true,
//         serverSideRequired = true,
//         clientPacketHandlerSpec = @NetworkMod.SidedPacketHandler(
//                 channels = {"RZ"},
//                 packetHandler = RZPacketHandler.class
//         )
// )
public class ShiftSprint {
    public ShiftSprintConfig config;

    public ShiftSprint() {
        // ((LaunchClassLoader) ShiftSprint.class.getClassLoader()).registerTransformer(TransformerA.class.getName());
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        this.config = new ShiftSprintConfig(e.getSuggestedConfigurationFile());
        // loading then saving creates the default config i hope
        this.config.load();
        this.config.save();
        GameSettings settings = Minecraft.func_71410_x().field_71474_y;
        ArrayList<KeyBinding> bindings = new ArrayList<KeyBinding>(Arrays.asList(settings.field_74324_K));
        bindings.add(this.config.getKey());
        settings.field_74324_K = bindings.toArray(new KeyBinding[0]);
        KeyBinding.func_74508_b();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Initialising PlayerTickHandler");
        new PlayerTickHandler(this);
        // TickRegistry.registerTickHandler(new AABBHandler(), Side.CLIENT);
    }

}
