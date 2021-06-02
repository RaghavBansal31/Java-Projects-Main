
import java.io.*;
import java.util.*;

// -------------------------------------------------------------------------
/**
 * Generate a test data file. The size is a multiple of 4096 bytes. Depending on
 * the options, you can generate two types of output. With option "-a", the
 * output will be set so that when interpreted as ASCII characters, it will look
 * like a series of: [space][letter][space][space]. With option "-b", the
 * records are short ints, with each record having a value less than 30,000. *
 *
 
public class Genfile
{

    /**
     * block size
     */
    static final int      BLOCK_SIZE = 4096;
    /**
     * num of records
     */
    static final int      NUM_REC    = 2048;        // Because they are short
// ints

    /** Initialize the random variable */
    static private Random value      = new Random(); // Hold the Random class
// object


    // ----------------------------------------------------------
    /**
     * random generator.
     *
     * @param n
     *            input
     * @return random integer
     */
    static int random(int n)
    {
        return Math.abs(value.nextInt()) % n;
    }


    // ----------------------------------------------------------
    /**
     * main method.
     *
     * @param args
     *            inputs
     * @throws IOException
     *             exceptions
     */
    public static void main(String[] args)
        throws IOException
    {
        short val;

        int filesize = Integer.parseInt(args[2]); // Size of file in blocks
        DataOutputStream file =
            new DataOutputStream(new BufferedOutputStream(new FileOutputStream(
                args[1])));

        if (args[0].charAt(1) == 'b')
        {
            // Write out random numbers
            for (int i = 0; i < filesize; i++)
            {
                for (int j = 0; j < NUM_REC; j++)
                {
                    val = (short)(random(29999) + 1);
                    file.writeShort(val);
                }
            }
        }

        else if (args[0].charAt(1) == 'a')
        {
            // Write out ASCII-readable values
            for (int i = 0; i < filesize; i++)
            {
                for (int j = 0; j < NUM_REC; j++)
                {
                    if ((j % 2) == 1)
                    {
                        val = (short)(8224);
                    }
                    else
                    {
                        val = (short)(random(26) + 0x2041);
                    }
                    file.writeShort(val);
                }
            }
        }
        file.flush();
        file.close();
    }

}
