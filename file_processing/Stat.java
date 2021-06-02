

public class Stat
{

    /**
     * file name
     */
    public static String fileName;
    /**
     * cache hits
     */
    public static int    cacheHits     = 0;
    /**
     * times of disk reads
     */
    public static int    diskReads     = 0;
    /**
     * times of disk writes
     */
    public static int    diskWrites    = 0;
    /**
     * execution time
     */
    public static long   executionTime = 0;


    // ----------------------------------------------------------
    /**
     * output info.
     *
     * @return the output string
     */
    public static String output()
    {
        String output = "";
        output += "\nSort on " + fileName;
        output += "\nCache Hits: " + cacheHits;
        output += "\nDisk Reads: " + diskReads;
        output += "\nDisk Writes: " + diskWrites;
        output += "\nTime is " + executionTime;

        return output;
    }
}
