package net.launcher.utils;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import org.jnbt.ByteArrayTag;
import org.jnbt.ByteTag;
import org.jnbt.CompoundTag;
import org.jnbt.DoubleTag;
import org.jnbt.EndTag;
import org.jnbt.FloatTag;
import org.jnbt.IntTag;
import org.jnbt.ListTag;
import org.jnbt.LongTag;
import org.jnbt.NBTConstants;
import org.jnbt.NBTUtils;
import org.jnbt.ShortTag;
import org.jnbt.StringTag;
import org.jnbt.Tag;

public final class NBTOutputStream implements Closeable {
    private final DataOutputStream os;

    public NBTOutputStream(OutputStream os) throws IOException {
        this.os = new DataOutputStream(os);
    }

    public void writeTag(Tag tag) throws IOException {
        int type = NBTUtils.getTypeCode(tag.getClass());
        String name = tag.getName();
        byte[] nameBytes = name.getBytes(NBTConstants.CHARSET);
        this.os.writeByte(type);
        this.os.writeShort(nameBytes.length);
        this.os.write(nameBytes);
        if(type == 0) {
            throw new IOException("Named TAG_End not permitted.");
        } else {
            this.writeTagPayload(tag);
        }
    }

    private void writeTagPayload(Tag tag) throws IOException {
        int type = NBTUtils.getTypeCode(tag.getClass());
        switch(type) {
            case 0:
                this.writeEndTagPayload((EndTag)tag);
                break;
            case 1:
                this.writeByteTagPayload((ByteTag)tag);
                break;
            case 2:
                this.writeShortTagPayload((ShortTag)tag);
                break;
            case 3:
                this.writeIntTagPayload((IntTag)tag);
                break;
            case 4:
                this.writeLongTagPayload((LongTag)tag);
                break;
            case 5:
                this.writeFloatTagPayload((FloatTag)tag);
                break;
            case 6:
                this.writeDoubleTagPayload((DoubleTag)tag);
                break;
            case 7:
                this.writeByteArrayTagPayload((ByteArrayTag)tag);
                break;
            case 8:
                this.writeStringTagPayload((StringTag)tag);
                break;
            case 9:
                this.writeListTagPayload((ListTag)tag);
                break;
            case 10:
                this.writeCompoundTagPayload((CompoundTag)tag);
                break;
            default:
                throw new IOException("Invalid tag type: " + type + ".");
        }

    }

    private void writeByteTagPayload(ByteTag tag) throws IOException {
        this.os.writeByte(tag.getValue().byteValue());
    }

    private void writeByteArrayTagPayload(ByteArrayTag tag) throws IOException {
        byte[] bytes = tag.getValue();
        this.os.writeInt(bytes.length);
        this.os.write(bytes);
    }

    private void writeCompoundTagPayload(CompoundTag tag) throws IOException {
        Iterator var3 = tag.getValue().values().iterator();

        while(var3.hasNext()) {
            Tag childTag = (Tag)var3.next();
            this.writeTag(childTag);
        }

        this.os.writeByte(0);
    }

    private void writeListTagPayload(ListTag tag) throws IOException {
        Class clazz = tag.getType();
        List tags = tag.getValue();
        int size = tags.size();
        this.os.writeByte(NBTUtils.getTypeCode(clazz));
        this.os.writeInt(size);

        for(int i = 0; i < size; ++i) {
            this.writeTagPayload((Tag)tags.get(i));
        }

    }

    private void writeStringTagPayload(StringTag tag) throws IOException {
        byte[] bytes = tag.getValue().getBytes(NBTConstants.CHARSET);
        this.os.writeShort(bytes.length);
        this.os.write(bytes);
    }

    private void writeDoubleTagPayload(DoubleTag tag) throws IOException {
        this.os.writeDouble(tag.getValue().doubleValue());
    }

    private void writeFloatTagPayload(FloatTag tag) throws IOException {
        this.os.writeFloat(tag.getValue().floatValue());
    }

    private void writeLongTagPayload(LongTag tag) throws IOException {
        this.os.writeLong(tag.getValue().longValue());
    }

    private void writeIntTagPayload(IntTag tag) throws IOException {
        this.os.writeInt(tag.getValue().intValue());
    }

    private void writeShortTagPayload(ShortTag tag) throws IOException {
        this.os.writeShort(tag.getValue().shortValue());
    }

    private void writeEndTagPayload(EndTag tag) {
    }

    public void close() throws IOException {
        this.os.close();
    }
}
