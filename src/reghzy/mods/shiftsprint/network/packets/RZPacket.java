package reghzy.mods.shiftsprint.network.packets;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class RZPacket {
    public static final String CHANNEL_NAME = "RZ";

    public RZPacket() { }

    public Packet250CustomPayload constructPacket() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(getLength() + 1);
        DataOutput output = new DataOutputStream(outputStream);

        try {
            output.writeByte(getId());
            this.writeData(output);
        }
        catch (IOException e) {
            throw new IOException("Failed to write packet data");
        }

        return new Packet250CustomPayload(CHANNEL_NAME, outputStream.toByteArray());
    }

    public abstract void readData(DataInput input) throws IOException;

    public abstract void writeData(DataOutput output) throws IOException;

    public abstract void handle(INetworkManager network, EntityPlayerSP player);

    /**
     * An average length of the packet. Must be bigger than or equal to 0
     */
    public abstract int getLength();

    /**
     * The packet ID
     */
    public abstract int getId();
}
