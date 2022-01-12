package reghzy.mods.shiftsprint;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import org.lwjgl.input.Keyboard;

import java.io.File;

public class ShiftSprintConfig extends Configuration {
    private KeyBinding sprintKey;

    public ShiftSprintConfig(File file) {
        super(file);
        this.getKey().field_74512_d = getKeyProperty().getInt();
        KeyBinding.func_74508_b();
    }

    @Override
    public void load() {
        super.load();
        Property property = getKeyProperty();
        this.getKey().field_74512_d = property.getInt();
        KeyBinding.func_74508_b();
    }

    @Override
    public void save() {
        getKeyProperty().set(this.getKey().field_74512_d);
        super.save();
    }

    private Property getKeyProperty() {
        return this.get("general", "sprintKey", Keyboard.KEY_LSHIFT, "The sprint key. This is a LWJGL key, so 42 would be left shift. You can google 'lwjgl key codes' for a list of keys");
    }

    public KeyBinding getKey() {
        if (this.sprintKey == null) {
            return this.sprintKey = new KeyBinding("key.sprint", Keyboard.KEY_LSHIFT);
        }

        return this.sprintKey;
    }
}
