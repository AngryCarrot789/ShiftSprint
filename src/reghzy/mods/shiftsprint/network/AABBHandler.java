package reghzy.mods.shiftsprint.network;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import org.lwjgl.opengl.GL11;

import java.util.EnumSet;

public class AABBHandler implements ITickHandler {
    public static final AxisAlignedBB activeBox = new AxisAlignedBB(0, 0, 0, 0, 0, 0);
    public static Colour colour = new Colour(1.0f, 1.0f, 1.0f, 1.0f);
    public static boolean isActive = false;
    public static int activeDimension;

    private static class Vector3 {
        public int x;
        public int y;
        public int z;

        public Vector3() {

        }

        public Vector3(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static Vector3 lc;
    public static Vector3 rc;

    private static final EnumSet<TickType> TICKS = EnumSet.of(TickType.RENDER);

    public AABBHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    // @ForgeSubscribe(priority = EventPriority.HIGHEST)
    // public void onBlockInteract(PlayerInteractEvent e) {
    //     if (e.isCancelable() && e.isCanceled()) {
    //         return;
    //     }
//
    //     ItemStack handItem = e.entityPlayer.field_71071_by.func_70448_g();
    //     if (handItem == null) {
    //         return;
    //     }
//
    //     if (handItem.field_77993_c != 271) {
    //         return;
    //     }
//
    //     if (e.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
    //         lc = new Vector3(e.x, e.y, e.z);
    //     }
    //     else if (e.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
    //         rc = new Vector3(e.x, e.y, e.z);
    //     }
    //     else {
    //         return;
    //     }
//
    //     if (lc == null || rc == null) {
    //         return;
    //     }
//
    //     activeBox.func_72324_b(
    //             Math.min(lc.x, rc.x),
    //             Math.min(lc.y, rc.y),
    //             Math.min(lc.z, rc.z),
    //             Math.max(lc.x, rc.x),
    //             Math.max(lc.y, rc.y),
    //             Math.max(lc.z, rc.z));
//
    //     activeDimension = e.entityPlayer.field_70170_p.func_72912_H().field_76105_j;
    //     colour.r = 0.2f;
    //     colour.r = 0.3f;
    //     colour.r = 0.8f;
    //     colour.opacity = 0.4f;
    // }

    @ForgeSubscribe(priority = EventPriority.HIGHEST)
    public void renderWorldLast(RenderWorldLastEvent e) {
        Minecraft minecraft = Minecraft.func_71410_x();
        EntityPlayerSP sp = minecraft.field_71439_g;
        if (!isActive || minecraft.field_71445_n || sp == null) {
            return;
        }

        if (sp.field_70170_p.func_72912_H().field_76105_j != activeDimension) {
            return;
        }

        AxisAlignedBB box = activeBox;
        float x = -((float) (sp.field_70142_S + (sp.field_70165_t - sp.field_70142_S) * (double) e.partialTicks));
        float y = -((float) (sp.field_70137_T + (sp.field_70163_u - sp.field_70137_T) * (double) e.partialTicks));
        float z = -((float) (sp.field_70136_U + (sp.field_70161_v - sp.field_70136_U) * (double) e.partialTicks));

        GL11.glColorMask(true, true, true, true);
        GL11.glEnable(3008);
        GL11.glDisable(2912);
        GL11.glEnable(2884);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glPushMatrix();
        GL11.glColor4f(colour.r, colour.g, colour.b, colour.opacity);
        GL11.glTranslatef(x, y, z);
        drawAABB(box, 0.01D);
        GL11.glPopMatrix();

        // Outline

        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glPushMatrix();
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
        GL11.glLineWidth(2.0F);
        GL11.glDisable(3553);
        GL11.glDepthMask(false);
        GL11.glTranslatef(x, y, z);
        drawOutlinedAABB(box);
        GL11.glDepthMask(true);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public void drawAABB(AxisAlignedBB aabb, double offsetInwards) {
        Tessellator tess = Tessellator.field_78398_a;
        tess.func_78382_b();
        tess.func_78377_a(aabb.field_72340_a + offsetInwards, aabb.field_72337_e - offsetInwards, aabb.field_72339_c + offsetInwards);
        tess.func_78377_a(aabb.field_72336_d - offsetInwards, aabb.field_72337_e - offsetInwards, aabb.field_72339_c + offsetInwards);
        tess.func_78377_a(aabb.field_72336_d - offsetInwards, aabb.field_72338_b + offsetInwards, aabb.field_72339_c + offsetInwards);
        tess.func_78377_a(aabb.field_72340_a + offsetInwards, aabb.field_72338_b + offsetInwards, aabb.field_72339_c + offsetInwards);
        tess.func_78377_a(aabb.field_72340_a + offsetInwards, aabb.field_72338_b + offsetInwards, aabb.field_72334_f - offsetInwards);
        tess.func_78377_a(aabb.field_72336_d - offsetInwards, aabb.field_72338_b + offsetInwards, aabb.field_72334_f - offsetInwards);
        tess.func_78377_a(aabb.field_72336_d - offsetInwards, aabb.field_72337_e - offsetInwards, aabb.field_72334_f - offsetInwards);
        tess.func_78377_a(aabb.field_72340_a + offsetInwards, aabb.field_72337_e - offsetInwards, aabb.field_72334_f - offsetInwards);
        tess.func_78377_a(aabb.field_72340_a + offsetInwards, aabb.field_72338_b + offsetInwards, aabb.field_72339_c + offsetInwards);
        tess.func_78377_a(aabb.field_72336_d - offsetInwards, aabb.field_72338_b + offsetInwards, aabb.field_72339_c + offsetInwards);
        tess.func_78377_a(aabb.field_72336_d - offsetInwards, aabb.field_72338_b + offsetInwards, aabb.field_72334_f - offsetInwards);
        tess.func_78377_a(aabb.field_72340_a + offsetInwards, aabb.field_72338_b + offsetInwards, aabb.field_72334_f - offsetInwards);
        tess.func_78377_a(aabb.field_72340_a + offsetInwards, aabb.field_72337_e - offsetInwards, aabb.field_72334_f - offsetInwards);
        tess.func_78377_a(aabb.field_72336_d - offsetInwards, aabb.field_72337_e - offsetInwards, aabb.field_72334_f - offsetInwards);
        tess.func_78377_a(aabb.field_72336_d - offsetInwards, aabb.field_72337_e - offsetInwards, aabb.field_72339_c + offsetInwards);
        tess.func_78377_a(aabb.field_72340_a + offsetInwards, aabb.field_72337_e - offsetInwards, aabb.field_72339_c + offsetInwards);
        tess.func_78377_a(aabb.field_72340_a + offsetInwards, aabb.field_72338_b + offsetInwards, aabb.field_72334_f - offsetInwards);
        tess.func_78377_a(aabb.field_72340_a + offsetInwards, aabb.field_72337_e - offsetInwards, aabb.field_72334_f - offsetInwards);
        tess.func_78377_a(aabb.field_72340_a + offsetInwards, aabb.field_72337_e - offsetInwards, aabb.field_72339_c + offsetInwards);
        tess.func_78377_a(aabb.field_72340_a + offsetInwards, aabb.field_72338_b + offsetInwards, aabb.field_72339_c + offsetInwards);
        tess.func_78377_a(aabb.field_72336_d - offsetInwards, aabb.field_72338_b + offsetInwards, aabb.field_72339_c + offsetInwards);
        tess.func_78377_a(aabb.field_72336_d - offsetInwards, aabb.field_72337_e - offsetInwards, aabb.field_72339_c + offsetInwards);
        tess.func_78377_a(aabb.field_72336_d - offsetInwards, aabb.field_72337_e - offsetInwards, aabb.field_72334_f - offsetInwards);
        tess.func_78377_a(aabb.field_72336_d - offsetInwards, aabb.field_72338_b + offsetInwards, aabb.field_72334_f - offsetInwards);
        tess.func_78381_a();
    }

    public void drawOutlinedAABB(AxisAlignedBB aabb) {
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78371_b(3);
        tessellator.func_78377_a(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c);
        tessellator.func_78377_a(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72339_c);
        tessellator.func_78377_a(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72334_f);
        tessellator.func_78377_a(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72334_f);
        tessellator.func_78377_a(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c);
        tessellator.func_78381_a();
        tessellator.func_78371_b(3);
        tessellator.func_78377_a(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c);
        tessellator.func_78377_a(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72339_c);
        tessellator.func_78377_a(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f);
        tessellator.func_78377_a(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72334_f);
        tessellator.func_78377_a(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c);
        tessellator.func_78381_a();
        tessellator.func_78371_b(1);
        tessellator.func_78377_a(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c);
        tessellator.func_78377_a(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c);
        tessellator.func_78377_a(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72339_c);
        tessellator.func_78377_a(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72339_c);
        tessellator.func_78377_a(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72334_f);
        tessellator.func_78377_a(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f);
        tessellator.func_78377_a(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72334_f);
        tessellator.func_78377_a(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72334_f);
        tessellator.func_78381_a();
    }

    @Override
    public void tickStart(EnumSet<TickType> enumSet, Object... objects) {

    }

    @Override
    public void tickEnd(EnumSet<TickType> enumSet, Object... objects) {

    }

    @Override
    public EnumSet<TickType> ticks() {
        return TICKS;
    }

    @Override
    public String getLabel() {
        return "ShiftSprint.AABB_DRAW";
    }
}
