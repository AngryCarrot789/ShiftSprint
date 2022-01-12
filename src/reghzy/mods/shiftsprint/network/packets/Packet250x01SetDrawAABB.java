package reghzy.mods.shiftsprint.network.packets;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.INetworkManager;
import reghzy.mods.shiftsprint.network.AABBHandler;
import reghzy.mods.shiftsprint.network.Colour;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet250x01SetDrawAABB extends RZPacket {
    public int dimId;
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;
    public int colour;

    public Packet250x01SetDrawAABB() {
        this.colour = 0;
    }

    public Packet250x01SetDrawAABB(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX + 1.0f;
        this.maxY = maxY + 1.0f;
        this.maxZ = maxZ + 1.0f;
        this.colour = new Colour(0.2f, 0.3f, 0.8f, 0.4f).toRGBA();
    }

    @Override
    public void readData(DataInput input) throws IOException {
        this.dimId = input.readShort();
        this.minX = input.readDouble();
        this.minY = input.readDouble();
        this.minZ = input.readDouble();
        this.maxX = input.readDouble();
        this.maxY = input.readDouble();
        this.maxZ = input.readDouble();
        this.colour = input.readInt();
    }

    @Override
    public void writeData(DataOutput output) throws IOException {
        output.writeShort(this.dimId);
        output.writeDouble(this.minX);
        output.writeDouble(this.minY);
        output.writeDouble(this.minZ);
        output.writeDouble(this.maxX);
        output.writeDouble(this.maxY);
        output.writeDouble(this.maxZ);
        output.writeInt(this.colour);
    }

    public boolean isActive() {
        return this.colour != 0;
    }

    @Override
    public void handle(INetworkManager network, EntityPlayerSP player) {
        if (isActive()) {
            AABBHandler.isActive = true;
            AABBHandler.activeBox.func_72324_b(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
            AABBHandler.colour.fromRGBA(this.colour);
            AABBHandler.activeDimension = this.dimId;
        }
        else {
            AABBHandler.isActive = false;
        }
    }

    @Override
    public int getLength() {
        return 54;
    }

    @Override
    public int getId() {
        return 1;
    }
}
