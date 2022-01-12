package reghzy.transformer;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LaunchClassLoader;
import reghzy.mods.shiftsprint.ShiftSprint;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class TransformerA implements IClassTransformer {
    // public static final String PATH = "F:\\MinecraftUtils\\modding\\forge\\1.6.4\\a-mappings\\searge\\server-and-client-bytecode-remap\\src\\TEMPPP\\";
    public static final String PATH = "F:\\TEMPPP\\";

    public static boolean reInit = false;

    public TransformerA () {
        System.out.println("TransformerA loaded");
    }

    public static File getFileForClass(String clazz, boolean create) throws IOException {
        int last = clazz.lastIndexOf('.');
        if (last == -1) {
            return new File(PATH + clazz);
        }

        File folder = new File(PATH + clazz.substring(0, last).replace('.', '\\'));
        if (create) {
            folder.mkdirs();
        }

        File file = new File(folder, clazz.substring(last + 1) + ".class");
        if (create) {
            file.createNewFile();
        }

        return file;
    }

    @Override
    public byte[] transform(String from, String to, byte[] bytes) {
        // if (to.startsWith("net") || to.startsWith("cpw") || to.startsWith("org") || to.startsWith("com") || to.startsWith("guava10")) {
        //     if (!reInit) {
        //         reInit = true;
        //         System.out.println("Re-registering transformer for latest updates");
        //         LaunchClassLoader loader = ((LaunchClassLoader) ShiftSprint.class.getClassLoader()); //.registerTransformer(TransformerA.class.getName());
        //         try {
        //             Field field = LaunchClassLoader.class.getDeclaredField("transformers");
        //             field.setAccessible(true);
        //             ArrayList<IClassTransformer> transformers = (ArrayList<IClassTransformer>) field.get(loader);
        //             Field elementDataField = ArrayList.class.getDeclaredField("elementData");
        //             elementDataField.setAccessible(true);
        //             Object[] data = (Object[]) elementDataField.get(transformers);
        //             int len = transformers.size();
        //             for(int i = 0; i < len; i++) {
        //                 if (data[i] == this) {
        //                     data[i] = data[len - 2];
        //                     data[i + 1] = data[len - 1];
        //                     data[len - 1] = this;
        //                     return ((IClassTransformer)data[i]).transform(from, to, bytes);
        //                 }
        //             }
        //         }
        //         catch (NoSuchFieldException e) {
        //             e.printStackTrace();
        //         }
        //         catch (IllegalAccessException e) {
        //             e.printStackTrace();
        //         }
        //         return bytes;
        //     }
        //     // doWrite(to, bytes);
        //     return bytes;
        // }
        //
        // return bytes;
        return bytes;
    }

    public static void doWrite(String clazzName, byte[] clazzBytes) {
        File file;
        try {
            file = getFileForClass(clazzName, true);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            try {
                System.out.println("Writing class '" + clazzName + "' to file: " + file.getAbsolutePath());
                outputStream.write(clazzBytes);
                outputStream.flush();
                System.out.println("Success!");
            }
            catch (IOException e) {
                System.out.println("Failed to write bytes to " + file.getAbsolutePath());
                e.printStackTrace();
            }
            finally {
                try {
                    outputStream.close();
                }
                catch (IOException e) {
                    System.out.println("Failed to close stream for FileOutputStream to " + file.getAbsolutePath());
                    e.printStackTrace();
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            if (e.getCause() != null) {
                e.getCause().printStackTrace();
                if (e.getCause().getCause() != null) {
                    e.getCause().getCause().printStackTrace();
                }
            }
        }
    }
}
