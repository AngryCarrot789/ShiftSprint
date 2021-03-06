package reghzy.render;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class Tessellator {
    public static boolean convertQuadsToTriangles;
    public static boolean tryVBO;
    public ByteBuffer byteBuffer;
    public IntBuffer intBuffer;
    public FloatBuffer floatBuffer;
    public ShortBuffer shortBuffer;
    public int[] rawBuffer;
    public int vertexCount;
    public double textureU;
    public double textureV;
    public int brightness;
    public int color;
    public boolean hasColor;
    public boolean hasTexture;
    public boolean hasBrightness;
    public boolean hasNormals;
    public int rawBufferIndex;
    public int addedVertices;
    public boolean isColorDisabled;
    public int drawMode;
    public double xOffset;
    public double yOffset;
    public double zOffset;
    public int normal;
    public static Tessellator instance = new Tessellator(524288);
    public boolean isDrawing;
    public boolean useVBO;
    public IntBuffer vertexBuffers;
    public int vboIndex;
    public int vboCount;
    public int bufferSize;
    public boolean renderingChunk;
    public static boolean littleEndianByteOrder;
    public static boolean renderingWorldRenderer;
    public boolean defaultTexture;
    public int textureID;
    public boolean autoGrow;
    public VertexData[] vertexDatas;
    public boolean[] drawnIcons;
    public TextureAtlasSprite[] vertexQuadIcons;

    public Tessellator() {
        this(65536);
        this.defaultTexture = false;
    }

    public Tessellator(int par1) {
        this.renderingChunk = false;
        this.defaultTexture = true;
        this.textureID = 0;
        this.autoGrow = true;
        this.vertexDatas = null;
        this.drawnIcons = new boolean[256];
        this.vertexQuadIcons = null;
        this.vertexCount = 0;
        this.hasColor = false;
        this.hasTexture = false;
        this.hasBrightness = false;
        this.hasNormals = false;
        this.rawBufferIndex = 0;
        this.addedVertices = 0;
        this.isColorDisabled = false;
        this.isDrawing = false;
        this.useVBO = false;
        this.vboIndex = 0;
        this.vboCount = 10;
        this.bufferSize = par1;
        this.byteBuffer = GLAllocation.func_74524_c(par1 * 4);
        this.intBuffer = this.byteBuffer.asIntBuffer();
        this.floatBuffer = this.byteBuffer.asFloatBuffer();
        this.shortBuffer = this.byteBuffer.asShortBuffer();
        this.rawBuffer = new int[par1];
        this.useVBO = tryVBO && GLContext.getCapabilities().GL_ARB_vertex_buffer_object;
        if (this.useVBO) {
            this.vertexBuffers = GLAllocation.func_74527_f(this.vboCount);
            ARBVertexBufferObject.glGenBuffersARB(this.vertexBuffers);
        }

        this.vertexDatas = new VertexData[4];

        for(int ix = 0; ix < this.vertexDatas.length; ++ix) {
            this.vertexDatas[ix] = new VertexData();
        }

    }

    public void draw(int startQuadVertex, int endQuadVertex) {
        int vxQuadCount = endQuadVertex - startQuadVertex;
        if (vxQuadCount > 0) {
            int startVertex = startQuadVertex * 4;
            int vxCount = vxQuadCount * 4;
            if (this.useVBO) {
                throw new IllegalStateException("VBO not implemented");
            } else {
                this.floatBuffer.position(3);
                GL11.glTexCoordPointer(2, 32, this.floatBuffer);
                OpenGlHelper.func_77472_b(OpenGlHelper.field_77476_b);
                this.shortBuffer.position(14);
                GL11.glTexCoordPointer(2, 32, this.shortBuffer);
                GL11.glEnableClientState(32888);
                OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
                this.byteBuffer.position(20);
                GL11.glColorPointer(4, true, 32, this.byteBuffer);
                this.floatBuffer.position(0);
                GL11.glVertexPointer(3, 32, this.floatBuffer);
                if (this.drawMode == 7 && convertQuadsToTriangles) {
                    GL11.glDrawArrays(4, startVertex, vxCount);
                } else {
                    GL11.glDrawArrays(this.drawMode, startVertex, vxCount);
                }

            }
        }
    }

    public int drawForIcon(TextureAtlasSprite icon, int startQuadPos) {
        icon.bindOwnTexture();
        int firstRegionEnd = -1;
        int lastPos = -1;
        int numQuads = this.addedVertices / 4;

        for(int i = startQuadPos; i < numQuads; ++i) {
            TextureAtlasSprite ts = this.vertexQuadIcons[i];
            if (ts == icon) {
                if (lastPos < 0) {
                    lastPos = i;
                }
            } else if (lastPos >= 0) {
                this.draw(lastPos, i);
                lastPos = -1;
                if (firstRegionEnd < 0) {
                    firstRegionEnd = i;
                }
            }
        }

        if (lastPos >= 0) {
            this.draw(lastPos, numQuads);
        }

        if (firstRegionEnd < 0) {
            firstRegionEnd = numQuads;
        }

        return firstRegionEnd;
    }

    public int draw() {
        if (this.isDrawing) {
            this.isDrawing = false;
            if (this.vertexCount > 0) {
                this.intBuffer.clear();
                this.intBuffer.put(this.rawBuffer, 0, this.rawBufferIndex);
                this.byteBuffer.position(0);
                this.byteBuffer.limit(this.rawBufferIndex * 4);
                if (this.useVBO) {
                    this.vboIndex = (this.vboIndex + 1) % this.vboCount;
                    ARBVertexBufferObject.glBindBufferARB(34962, this.vertexBuffers.get(this.vboIndex));
                    ARBVertexBufferObject.glBufferDataARB(34962, this.byteBuffer, 35040);
                }

                if (this.hasTexture) {
                    if (this.useVBO) {
                        GL11.glTexCoordPointer(2, 5126, 32, 12L);
                    } else {
                        this.floatBuffer.position(3);
                        GL11.glTexCoordPointer(2, 32, this.floatBuffer);
                    }

                    GL11.glEnableClientState(32888);
                }

                if (this.hasBrightness) {
                    OpenGlHelper.func_77472_b(OpenGlHelper.field_77476_b);
                    if (this.useVBO) {
                        GL11.glTexCoordPointer(2, 5122, 32, 28L);
                    } else {
                        this.shortBuffer.position(14);
                        GL11.glTexCoordPointer(2, 32, this.shortBuffer);
                    }

                    GL11.glEnableClientState(32888);
                    OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
                }

                if (this.hasColor) {
                    if (this.useVBO) {
                        GL11.glColorPointer(4, 5121, 32, 20L);
                    } else {
                        this.byteBuffer.position(20);
                        GL11.glColorPointer(4, true, 32, this.byteBuffer);
                    }

                    GL11.glEnableClientState(32886);
                }

                if (this.hasNormals) {
                    if (this.useVBO) {
                        GL11.glNormalPointer(5121, 32, 24L);
                    } else {
                        this.byteBuffer.position(24);
                        GL11.glNormalPointer(32, this.byteBuffer);
                    }

                    GL11.glEnableClientState(32885);
                }

                if (this.useVBO) {
                    GL11.glVertexPointer(3, 5126, 32, 0L);
                } else {
                    this.floatBuffer.position(0);
                    GL11.glVertexPointer(3, 32, this.floatBuffer);
                }

                GL11.glEnableClientState(32884);
                if (this.drawMode == 7 && convertQuadsToTriangles) {
                    GL11.glDrawArrays(4, 0, this.vertexCount);
                } else {
                    GL11.glDrawArrays(this.drawMode, 0, this.vertexCount);
                }

                GL11.glDisableClientState(32884);
                if (this.hasTexture) {
                    GL11.glDisableClientState(32888);
                }

                if (this.hasBrightness) {
                    OpenGlHelper.func_77472_b(OpenGlHelper.field_77476_b);
                    GL11.glDisableClientState(32888);
                    OpenGlHelper.func_77472_b(OpenGlHelper.field_77478_a);
                }

                if (this.hasColor) {
                    GL11.glDisableClientState(32886);
                }

                if (this.hasNormals) {
                    GL11.glDisableClientState(32885);
                }
            }

            int i = this.rawBufferIndex * 4;
            this.reset();
            return i;
        }
        else {
            throw new IllegalStateException("Not tesselating!");
        }
    }

    public void reset() {
        this.vertexCount = 0;
        this.byteBuffer.clear();
        this.rawBufferIndex = 0;
        this.addedVertices = 0;
    }

    public void startDrawingQuads() {
        this.startDrawing(7);
    }

    public void startDrawing(int par1) {
        if (this.isDrawing) {
            throw new IllegalStateException("Already tesselating!");
        } else {
            this.isDrawing = true;
            this.reset();
            this.drawMode = par1;
            this.hasNormals = false;
            this.hasColor = false;
            this.hasTexture = false;
            this.hasBrightness = false;
            this.isColorDisabled = false;
        }
    }

    public void setTextureUV(double par1, double par3) {
        this.hasTexture = true;
        this.textureU = par1;
        this.textureV = par3;
    }

    public void setBrightness(int par1) {
        this.hasBrightness = true;
        this.brightness = par1;
    }

    public void setColorOpaque_F(float par1, float par2, float par3) {
        this.setColorOpaque((int)(par1 * 255.0F), (int)(par2 * 255.0F), (int)(par3 * 255.0F));
    }

    public void setColorRGBA_F(float par1, float par2, float par3, float par4) {
        this.setColorRGBA((int)(par1 * 255.0F), (int)(par2 * 255.0F), (int)(par3 * 255.0F), (int)(par4 * 255.0F));
    }

    public void setColorOpaque(int par1, int par2, int par3) {
        this.setColorRGBA(par1, par2, par3, 255);
    }

    public void setColorRGBA(int par1, int par2, int par3, int par4) {
        if (this.isColorDisabled) {
            return;
        }

        if (par1 > 255) {
            par1 = 255;
        }

        if (par2 > 255) {
            par2 = 255;
        }

        if (par3 > 255) {
            par3 = 255;
        }

        if (par4 > 255) {
            par4 = 255;
        }

        if (par1 < 0) {
            par1 = 0;
        }

        if (par2 < 0) {
            par2 = 0;
        }

        if (par3 < 0) {
            par3 = 0;
        }

        if (par4 < 0) {
            par4 = 0;
        }

        this.hasColor = true;
        if (littleEndianByteOrder) {
            this.color = par4 << 24 | par3 << 16 | par2 << 8 | par1;
        } else {
            this.color = par1 << 24 | par2 << 16 | par3 << 8 | par4;
        }

    }

    public void addVertexWithUV(double x, double y, double z, double u, double v) {
        this.setTextureUV(u, v);
        this.addVertex(x, y, z);
    }

    public void addVertex(double x, double y, double z) {
        if (this.autoGrow && this.rawBufferIndex >= this.bufferSize - 32) {
            this.bufferSize *= 2;
            int[] newRawBuffer = new int[this.bufferSize];
            System.arraycopy(this.rawBuffer, 0, newRawBuffer, 0, this.rawBuffer.length);
            this.rawBuffer = newRawBuffer;
            this.byteBuffer = GLAllocation.func_74524_c(this.bufferSize * 4);
            this.intBuffer = this.byteBuffer.asIntBuffer();
            this.floatBuffer = this.byteBuffer.asFloatBuffer();
            this.shortBuffer = this.byteBuffer.asShortBuffer();
            if (this.vertexQuadIcons != null) {
                TextureAtlasSprite[] newVertexQuadIcons = new TextureAtlasSprite[this.bufferSize / 4];
                System.arraycopy(this.vertexQuadIcons, 0, newVertexQuadIcons, 0, this.vertexQuadIcons.length);
                this.vertexQuadIcons = newVertexQuadIcons;
            }
        }

        ++this.addedVertices;
        if (this.drawMode == 7 && convertQuadsToTriangles && this.addedVertices % 4 == 0) {
            for(int i = 0; i < 2; ++i) {
                int j = 8 * (3 - i);
                if (this.hasTexture) {
                    this.rawBuffer[this.rawBufferIndex + 3] = this.rawBuffer[this.rawBufferIndex - j + 3];
                    this.rawBuffer[this.rawBufferIndex + 4] = this.rawBuffer[this.rawBufferIndex - j + 4];
                }

                if (this.hasBrightness) {
                    this.rawBuffer[this.rawBufferIndex + 7] = this.rawBuffer[this.rawBufferIndex - j + 7];
                }

                if (this.hasColor) {
                    this.rawBuffer[this.rawBufferIndex + 5] = this.rawBuffer[this.rawBufferIndex - j + 5];
                }

                this.rawBuffer[this.rawBufferIndex + 0] = this.rawBuffer[this.rawBufferIndex - j + 0];
                this.rawBuffer[this.rawBufferIndex + 1] = this.rawBuffer[this.rawBufferIndex - j + 1];
                this.rawBuffer[this.rawBufferIndex + 2] = this.rawBuffer[this.rawBufferIndex - j + 2];
                ++this.vertexCount;
                this.rawBufferIndex += 8;
            }
        }

        if (this.hasTexture) {
            this.rawBuffer[this.rawBufferIndex + 3] = Float.floatToRawIntBits((float)this.textureU);
            this.rawBuffer[this.rawBufferIndex + 4] = Float.floatToRawIntBits((float)this.textureV);
        }

        if (this.hasBrightness) {
            this.rawBuffer[this.rawBufferIndex + 7] = this.brightness;
        }

        if (this.hasColor) {
            this.rawBuffer[this.rawBufferIndex + 5] = this.color;
        }

        if (this.hasNormals) {
            this.rawBuffer[this.rawBufferIndex + 6] = this.normal;
        }

        this.rawBuffer[this.rawBufferIndex + 0] = Float.floatToRawIntBits((float)(x + this.xOffset));
        this.rawBuffer[this.rawBufferIndex + 1] = Float.floatToRawIntBits((float)(y + this.yOffset));
        this.rawBuffer[this.rawBufferIndex + 2] = Float.floatToRawIntBits((float)(z + this.zOffset));
        this.rawBufferIndex += 8;
        ++this.vertexCount;
        if (!this.autoGrow && this.addedVertices % 4 == 0 && this.rawBufferIndex >= this.bufferSize - 32) {
            this.draw();
            this.isDrawing = true;
        }

    }

    public void setColorOpaque_I(int par1) {
        int i = par1 >> 16 & 255;
        int j = par1 >> 8 & 255;
        int k = par1 & 255;
        this.setColorOpaque(i, j, k);
    }

    public void setColorRGBA_I(int par1, int par2) {
        int i = par1 >> 16 & 255;
        int j = par1 >> 8 & 255;
        int k = par1 & 255;
        this.setColorRGBA(i, j, k, par2);
    }

    public void disableColor() {
        this.isColorDisabled = true;
    }

    public void setNormal(float par1, float par2, float par3) {
        this.hasNormals = true;
        byte byte0 = (byte)((int)(par1 * 127.0F));
        byte byte1 = (byte)((int)(par2 * 127.0F));
        byte byte2 = (byte)((int)(par3 * 127.0F));
        this.normal = byte0 & 255 | (byte1 & 255) << 8 | (byte2 & 255) << 16;
    }

    public void setTranslation(double par1, double par3, double par5) {
        this.xOffset = par1;
        this.yOffset = par3;
        this.zOffset = par5;
    }

    public void addTranslation(float par1, float par2, float par3) {
        this.xOffset += (double)par1;
        this.yOffset += (double)par2;
        this.zOffset += (double)par3;
    }

    public boolean isRenderingChunk() {
        return this.renderingChunk;
    }

    public void setRenderingChunk(boolean renderingChunk) {
        this.renderingChunk = renderingChunk;
    }

    static {
        littleEndianByteOrder = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
        renderingWorldRenderer = false;
    }
}
