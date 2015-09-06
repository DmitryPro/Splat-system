import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;


/**
 * This class contains all providers. Runs in a single thread. Initiate connections between server and providers.
 *
 * @author Pavel Gordon
 */
class ProvidersHolder implements Runnable
{
    private static Logger logger = Logger.getLogger(ProvidersHolder.class);

    // address = "46.101.58.183";//server address
    private static ArrayList<Provider> providers;

    /**
     * Constructor, which initializes providers list
     */
    ProvidersHolder()
    {
        providers = new ArrayList<>();
    }

    /*
     * Maybe this method will be used in the future...
     * @param provider Provider object, which will be added to ArrayList of Providers
     */
    public void addProvider(Provider provider)
    {
        providers.add(provider);
    }





    /**
     * Adds default provider to providers list and then starts them in different threads
     *
     */
    @Override
    public void run()
    {
        providers = new ArrayList<>();
        providers.add(new Provider());

        for (Provider provider : providers)
        {
            new Thread(provider).start();
        }
    }
}
