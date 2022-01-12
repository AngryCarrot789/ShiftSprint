package reghzy.mods.shiftsprint.network;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import reghzy.mods.shiftsprint.network.packets.Packet250x01SetDrawAABB;
import reghzy.mods.shiftsprint.network.packets.RZPacket;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;

public class RZPacketHandler implements IPacketHandler {
    private static final HashMap<Integer, Class<? extends RZPacket>> ID_TO_PACKET;

    @Override
    public void onPacketData(INetworkManager iNetworkManager, Packet250CustomPayload packet, Player cpwPlayer) {
        if (packet.field_73629_c == null) {
            System.out.println("Null packet data!");
            return;
        }

        if (cpwPlayer instanceof EntityPlayerSP) {
            EntityPlayerSP player = (EntityPlayerSP) cpwPlayer;
            if (player.field_70170_p.field_72995_K) {
                DataInput input = new DataInputStream(new ByteArrayInputStream(packet.field_73629_c));
                try {
                    handlePacket250(iNetworkManager, packet, input, player);
                }
                catch (RuntimeException e) {
                    System.out.println("Failed to handle packet. Length = " + packet.field_73628_b);
                    e.printStackTrace();
                }
                catch (IOException e) {
                    System.out.println("Failed to read packet data. Length = " + packet.field_73628_b);
                    e.printStackTrace();
                }
                catch (Throwable e) {
                    System.out.println("Unexpected exception handling Packet250Custom. Length = " + packet.field_73628_b);
                    e.printStackTrace();
                }
            }
        }
    }

    public static void handlePacket250(INetworkManager network, Packet250CustomPayload packet, DataInput input, EntityPlayerSP player) throws IOException {
        int id = input.readUnsignedByte();
        Class<? extends RZPacket> clazz = ID_TO_PACKET.get(id);
        if (clazz == null) {
            throw new RuntimeException("Unknown packet with ID: " + id);
        }

        RZPacket rzPacket = createPacket(clazz);

        try {
            rzPacket.readData(input);
        }
        catch (RuntimeException e) {
            throw new IOException("RuntimeException while reading packet data for packet " + clazz.getSimpleName() + ". ID = " + id, e);
        }
        catch (IOException e) {
            throw new IOException("IOException while reading packet data for " + clazz.getSimpleName() + ". ID = " + id, e);
        }

        try {
            rzPacket.handle(network, player);
        }
        catch (Throwable e) {
            throw new RuntimeException("Failed to invoke " + clazz.getSimpleName() + " handler after reading successfully", e);
        }
    }

    private static <T extends RZPacket> T createPacket(Class<T> clazz) {
        try {
            return clazz.newInstance();
        }
        catch (InstantiationException e) {
            throw new RuntimeException("InstantiationException while creating instance of " + clazz.getSimpleName(), e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("IllegalAccessException while creating instance of " + clazz.getSimpleName(), e);
        }
    }

    static {
        ID_TO_PACKET = new HashMap<Integer, Class<? extends RZPacket>>();
        ID_TO_PACKET.put(1, Packet250x01SetDrawAABB.class);
    }
}
