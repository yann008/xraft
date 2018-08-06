package in.xnnyygn.xraft.core.log.snapshot;

public class MemorySnapshot implements Snapshot {

    private final int lastIncludedIndex;
    private final int lastIncludedTerm;
    private final byte[] data;

    public MemorySnapshot(int lastIncludedIndex, int lastIncludedTerm) {
        this(lastIncludedIndex, lastIncludedTerm, new byte[0]);
    }

    public MemorySnapshot(int lastIncludedIndex, int lastIncludedTerm, byte[] data) {
        this.lastIncludedIndex = lastIncludedIndex;
        this.lastIncludedTerm = lastIncludedTerm;
        this.data = data;
    }

    @Override
    public int getLastIncludedIndex() {
        return lastIncludedIndex;
    }

    @Override
    public int getLastIncludedTerm() {
        return lastIncludedTerm;
    }

    public int size() {
        return this.data.length;
    }

    @Override
    public SnapshotChunk read(int offset, int length) {
        if (offset < 0 || offset > this.data.length) {
            throw new IndexOutOfBoundsException("offset " + offset + " out of bound");
        }

        int bufferLength = Math.min(this.data.length - offset, length);
        byte[] buffer = new byte[bufferLength];
        System.arraycopy(this.data, offset, buffer, 0, bufferLength);
        return new DefaultSnapshotChunk(buffer, offset + length >= this.data.length);
    }

    @Override
    public byte[] toByteArray() {
        return this.data;
    }

    @Override
    public String toString() {
        return "MemorySnapshot{" +
                "size=" + data.length +
                ", lastIncludedIndex=" + lastIncludedIndex +
                ", lastIncludedTerm=" + lastIncludedTerm +
                '}';
    }

}