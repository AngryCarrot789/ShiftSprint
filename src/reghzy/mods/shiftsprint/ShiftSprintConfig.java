package reghzy.mods.shiftsprint;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import org.lwjgl.input.Keyboard;

import java.io.File;

public class ShiftSprintConfig extends Configuration {
    private KeyBinding sprintKey;

    private float fovMultiplier;

    public ShiftSprintConfig(File file) {
        super(file);
        this.getKey().field_74512_d = getKeyProperty().getInt();
        KeyBinding.func_74508_b();
    }

    @Override
    public void load() {
        super.load();
        this.getKey().field_74512_d = getKeyProperty().getInt();
        this.fovMultiplier = (float) getFovMultiplierProperty().getDouble(1.10d);
        KeyBinding.func_74508_b();
    }

    @Override
    public void save() {
        getKeyProperty().set(this.getKey().field_74512_d);
        getFovMultiplierProperty().set(this.fovMultiplier);
        super.save();
    }

    private Property getKeyProperty() {
        return this.get("general", "sprintKey", Keyboard.KEY_LSHIFT, "The sprint key. This is a LWJGL key, so 42 would be left shift. You can google 'lwjgl key codes' for a list of keys");
    }

    private Property getFovMultiplierProperty() {
        return this.get("general", "fovMultiplier", 1.10f, "The multiplier for the FOV when sprinting. I have no clue how this really works apart from 'it is just a multiplier'. It might be the difference between max fov and current fov");
    }

    public KeyBinding getKey() {
        if (this.sprintKey == null) {
            return this.sprintKey = new KeyBinding("key.sprint", Keyboard.KEY_LSHIFT);
        }

        return this.sprintKey;
    }

    public float getFovMultiplier() {
        return this.fovMultiplier;
    }
}
