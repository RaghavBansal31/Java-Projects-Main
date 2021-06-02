
public class Node
{
    private Node   next;
    private Node   prev;
    private Buffer data;


    // ----------------------------------------------------------
    /**
     * Create a new Link object.
     *
     * @param data
     *            the data
     */
    public Node(Buffer data)
    {
        this.data = data;
        next = null;
        prev = null;
    }


    // ----------------------------------------------------------
    /**
     * next
     *
     * @return next link
     */
    public Node next()
    {
        return next;
    }


    // ----------------------------------------------------------
    /**
     * set next
     *
     * @param next
     *            next link
     */
    public void setNext(Node next)
    {
        this.next = next;
    }


    // ----------------------------------------------------------
    /**
     * get prev node.
     *
     * @return prev node
     */
    public Node prev()
    {
        return prev;
    }


    // ----------------------------------------------------------
    /**
     * set prev node.
     *
     * @param prev to be set
     */
    public void setPrev(Node prev)
    {
        this.prev = prev;
    }


    // ----------------------------------------------------------
    /**
     * get data
     *
     * @return the data
     */
    public Buffer getData()
    {
        return data;
    }


    // ----------------------------------------------------------
    /**
     * set data
     *
     * @param data
     *            the data
     */
    public void setData(Buffer data)
    {
        this.data = data;
    }

}
