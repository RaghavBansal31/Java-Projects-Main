import java.io.IOException;
import java.io.File;

public class Sorting
{
    private BufferPool       pool;
    private int              diskSize;
    private int              numBuffer;
    private final static int BLOCK_SIZE  = 4096;
    private final static int RECORD_SIZE = 4;


    // ----------------------------------------------------------
    /**
     * Create a new Sorting object.
     *
     * @param name
     *            of the file
     * @param numBuffer
     *            the number of buffers
     * @throws IOException
     *             exception
     */
    public Sorting(String name, int numBuffer)
        throws IOException
    {
        File file = new File(name);
        pool = new BufferPool(file, numBuffer);
        this.numBuffer = numBuffer;
        diskSize = pool.numBlocks() * BLOCK_SIZE / RECORD_SIZE;
    }


    // ----------------------------------------------------------
    /**
     * get the size of the disk.
     *
     * @return diskSize
     */
    public int getSize()
    {
        return diskSize;
    }


    // ----------------------------------------------------------
    /**
     * perform quicksort.
     *
     * @throws IOException
     */
    public void sort()
        throws IOException
    {
        quicksortHelp(0, this.getSize() - 1);
    }


    // ----------------------------------------------------------
    /**
     * perform quicksorthelp.
     * @param i index i
     * @param j index j
     * @throws IOException
     */
    private void quicksortHelp(int i, int j)
        throws IOException
    {
        swap(findPivot(i, j), j);
        int k = partition1(i, j - 1, pool.getKey(j));
        swap(k, j);
        int g = partition2(k + 1, j, pool.getKey(k));

        if ((k - i) > 1)
        {
            quicksortHelp(i, k - 1);
        }
        if ((j - g) > 1)
        {
            quicksortHelp(g + 1, j);
        }
    }


    // ----------------------------------------------------------
    /**
     * find pivot.
     *
     * @param l
     *            left index
     * @param r
     *            right index
     * @return pivot
     */
    public int findPivot(int l, int r)
    {
        return (l + r) / 2;
    }


    /**
     * partition method
     *
     * @param l
     *            left index
     * @param r
     *            right index
     * @param pivot
     *            pivot short
     * @return the first index of rightSub array.
     * @throws IOException
     */
    public int partition1(int l, int r, short pivot)
        throws IOException
    {
        int left = l;
        int right = r;
        while (left <= right)
        {
            while (pool.getKey(left) < pivot)
            {
                left++;
            }
            while (right >= left && pool.getKey(right) >= pivot)
            {
                right--;
            }
            if (right > left)
            {
                swap(left, right);
            }
        }

        return left;
    }


    // ----------------------------------------------------------
    /**
     * partition for right part.
     *
     * @param l index
     * @param r index
     * @param k short
     * @return right index
     * @throws IOException
     */
    public int partition2(int l, int r, short k)
        throws IOException
    {

        int left = l;
        int right = r;
        while (left <= right)
        {
            if (pool.getKey(left) == k)
            {
                left++;
            }
            else if (pool.getKey(right) == k)
            {
                swap(left, right);
                left++;
                right--;
            }
            else
            {
                right--;
            }
        }
        return right;
    }


    // ----------------------------------------------------------
    /**
     * swap.
     *
     * @param l
     *            left index
     * @param r
     *            right index
     * @throws IOException
     *             exception
     */
    public void swap(int l, int r)
        throws IOException
    {
        if (numBuffer == 1)
        {
            specialSwap(l, r);
            return;
        }
        // get block-th and positions
        int lBlock = (l * RECORD_SIZE) / BLOCK_SIZE;
        int lPosition = (l * RECORD_SIZE) % BLOCK_SIZE;
        int rBlock = (r * RECORD_SIZE) / BLOCK_SIZE;
        int rPosition = (r * RECORD_SIZE) % BLOCK_SIZE;

        byte[] lRec = new byte[RECORD_SIZE];
        byte[] rRec = new byte[RECORD_SIZE];
        byte[] buffer;
        Buffer lBuffer = pool.acquireBuffer(lBlock);
        Buffer rBuffer = pool.acquireBuffer(rBlock);

        buffer = lBuffer.read();
        System.arraycopy(buffer, lPosition, lRec, 0, RECORD_SIZE);

        buffer = rBuffer.read();
        System.arraycopy(buffer, rPosition, rRec, 0, RECORD_SIZE);

        System.arraycopy(lRec, 0, buffer, rPosition, RECORD_SIZE);
        rBuffer.write(buffer);

        // Put record 2 in buffer for old record 1
        buffer = lBuffer.read();
        System.arraycopy(rRec, 0, buffer, lPosition, RECORD_SIZE);
        lBuffer.write(buffer);

    }


    /**
     * swap for when numBuffer is one
     *
     * @param l
     * @param r
     * @throws IOException
     *             exception
     */
    private void specialSwap(int l, int r)
        throws IOException
    {
        int lBlock = (l * RECORD_SIZE) / BLOCK_SIZE;
        int lPosition = (l * RECORD_SIZE) % BLOCK_SIZE;
        int rBlock = (r * RECORD_SIZE) / BLOCK_SIZE;
        int rPosition = (r * RECORD_SIZE) % BLOCK_SIZE;

        byte[] lRec = new byte[RECORD_SIZE];
        byte[] rRec = new byte[RECORD_SIZE];
        byte[] buffer;

        Buffer lBuffer = pool.acquireBuffer(lBlock);
        buffer = lBuffer.read();
        System.arraycopy(buffer, lPosition, lRec, 0, RECORD_SIZE);

        Buffer rBuffer = pool.acquireBuffer(rBlock);
        buffer = rBuffer.read();
        System.arraycopy(buffer, rPosition, rRec, 0, RECORD_SIZE);

        System.arraycopy(lRec, 0, buffer, rPosition, RECORD_SIZE);
        rBuffer.write(buffer);
        lBuffer = pool.acquireBuffer(lBlock);

        buffer = lBuffer.read();
        System.arraycopy(rRec, 0, buffer, lPosition, RECORD_SIZE);
        lBuffer.write(buffer);

    }


    // ----------------------------------------------------------
    /**
     * flush when sorting is done.
     *
     * @throws IOException
     */
    public void flush()
        throws IOException
    {
        pool.flush();
    }
}
