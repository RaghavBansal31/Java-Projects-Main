import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.File;

// -------------------------------------------------------------------------

public class BufferPool
{
    private RandomAccessFile disk;
    private Queue            pool;
    private int              numBlocks;
    private final static int BLOCK_SIZE  = 4096;
    private final static int RECORD_SIZE = 4;


    // ----------------------------------------------------------
    /**
     * Create a new BufferPool object.
     *
     * @param file
     *            the datda file
     * @param numBuffer
     *            the number of buffers
     * @throws IOException
     * @throws FileNotFoundException
     */
    public BufferPool(File file, int numBuffer)
        throws IOException
    {
        disk = new RandomAccessFile(file, "rw");

        numBlocks = (int)disk.length() / BLOCK_SIZE;

        pool = new Queue(numBuffer);
    }


    // ----------------------------------------------------------
    /**
     * insert buffer into buffer pool.
     *
     * @param buffer to be insert
     * @throws IOException
     */
    public void insert(Buffer buffer)
        throws IOException
    {
        // if removed by LRU

        Buffer remove = pool.enqueue(buffer);
                if (remove != null)
                {
                    remove.writeBack();
                }

    }

    // ----------------------------------------------------------
    /**
     * relate buffer and block.
     *
     * @param block the block index
     * @return the rear buffer
     * @throws IOException
     */
    public Buffer acquireBuffer(int block)
        throws IOException
    {
        Buffer buffer;
        boolean found = pool.search(block);

        if (!found)
        {
            // read from disk and enqueue to pool
            buffer = new Buffer(disk, block);
            buffer.diskRead();
            this.insert(buffer);

        }
        // always return the rear buffer
        return pool.rearBuffer();
    }


    // ----------------------------------------------------------
    /**
     * get key from buffer.
     *
     * @param index
     *            where to get key
     * @return key short integer
     * @throws IOException
     *             exception
     */
    public short getKey(int index)
        throws IOException
    {
        int block = index * RECORD_SIZE / BLOCK_SIZE;
        int position = (index * RECORD_SIZE) % BLOCK_SIZE;

        short key =
            ByteBuffer.wrap(acquireBuffer(block).read()).getShort(position);
        return key;
    }


    // ----------------------------------------------------------
    /**
     * get the number of blocks in disk.
     *
     * @return the number of blocks in disk
     */
    public int numBlocks()
    {
        return numBlocks;
    }


    // ----------------------------------------------------------
    /**
     * flush when sorting finish.
     *
     * @throws IOException
     */
    public void flush()
        throws IOException
    {
        Buffer remove = pool.dequeue();

        while (remove != null)
        {
            remove.writeBack();
            remove = pool.dequeue();
        }
    }
}
