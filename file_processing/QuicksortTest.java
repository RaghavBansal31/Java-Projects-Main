import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;


public class QuicksortTest
{

    // ----------------------------------------------------------
    /**
     * test quicksort.
     *
     * @throws IOException
     */
    @Test
    public void test()
        throws IOException
    {
        String[] str1 = { "-a", "text", "1" };
        Genfile.main(str1);
        String[] str2 = { "text", "1", "stat" };
        Quicksort.main(str2);

        String[] str9 = { "-b", "text", "10" };
        Genfile.main(str9);
        String[] str10 = { "text", "10", "stat" };
        Quicksort.main(str10);

        assertEquals("10", str10[1]);

    }

}
