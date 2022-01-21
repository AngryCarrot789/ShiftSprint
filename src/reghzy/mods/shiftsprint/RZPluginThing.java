package reghzy.mods.shiftsprint;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

public class RZPluginThing implements IFMLLoadingPlugin {
    // ignore this, i was using it to write all of minecraft's source files to my pc with a class transformer to get the real source code
    public RZPluginThing() {
    }

    public String[] getLibraryRequestClass() {
        return null;
    }

    public String[] getASMTransformerClass() {
        return new String[]{}; // TransformerA.class.getName()
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(Map<String, Object> data) {

    }
}