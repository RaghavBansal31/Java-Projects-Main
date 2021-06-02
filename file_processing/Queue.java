
public class Queue
{
    private Node front = new Node(null);
    private Node rear  = new Node(null);
    private int  size;
    private int  maxSize;


    // ----------------------------------------------------------
    /**
     * Create a new Queue object.
     *
     * @param maxSize
     *            the size
     */
    public Queue(int maxSize)
    {
        this.maxSize = maxSize;

        front.setNext(rear);
        rear.setPrev(front);
        size = 0;
    }


    /**
     * remove buffer from pool.
     *
     * @return the removed buffer
     */
    public Buffer dequeue()
    {
        // pool is empty
        if (size == 0)
        {
            return null;
        }
        // otherwise remove buffer, there are at least one buffer
        Buffer remove = front.next().getData();

        front.setNext(front.next().next());
        front.next().setPrev(front);

        size--;
        return remove;
    }


    /**
     * add buffer method by LRU
     *
     * @param buff
     *            the buffer
     * @return the removed buffer if pool is full
     */
    public Buffer enqueue(Buffer buff)
    {
        Buffer removed = null;

        // pool is full
        if (size == maxSize)
        {
            // remove the LRU buffer
            removed = dequeue();
        }
        // enqueue the buffer
        Node newbuff = new Node(buff);

        newbuff.setPrev(rear.prev());
        rear.prev().setNext(newbuff);
        rear.setPrev(newbuff);
        newbuff.setNext(rear);
        // update size
        size++;
        return removed;
    }


    /**
     * search for this block see if the block is in the pool. If it is, remove
     * it from the queue and return this block.
     *
     * @param block
     *            the block
     * @return true if found in pool, false if didn't find.
     */
    public Boolean search(int block)
    {
        if (size == 0)
        {
            return false;
        }

        Node current = front.next();
        int index = 1;
        while (index < size)
        {
            if (current.getData().block() == block)
            {
                // promote
                current.next().setPrev(current.prev());
                current.prev().setNext(current.next());

                current.setPrev(rear.prev());
                rear.prev().setNext(current);
                rear.setPrev(current);
                current.setNext(rear);

                return true;
            }

            current = current.next();
            index++;
        }
        // already at the rear, do not need to promote
        return index == size && current.getData().block() == block;

    }


    /**
     * rear buffer
     *
     * @return the rear buffer
     */
    public Buffer rearBuffer()
    {
        if (size == 0)
        {
            return null;
        }
        return rear.prev().getData();
    }


    // ----------------------------------------------------------
    /**
     * get size of pool.
     *
     * @return size
     */
    public int getSize()
    {
        return maxSize;
    }



}
