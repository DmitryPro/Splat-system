import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;

/**
 * This class contains all providers
 * Runs in a single thread
 * Initiate connections between server and providers
 *
 * @author Pavel Gordon
 */
class ProvidersHolder implements Runnable {
    private static Logger logger = Logger.getLogger(ProvidersHolder.class);
    //address = "46.101.58.183";//server address
    private static ArrayList<Provider> providers;


    ProvidersHolder() throws IOException {
        providers = new ArrayList<>();
    }

    public void addProvider(Provider provider) {
        providers.add(provider);
    }


    static void initiateConnections() {

        providers.add(new Provider());

        for (Provider provider : providers) {
            new Thread(provider).start();
        }
    }

    @Override
    public void run() {
        providers = new ArrayList<>();
        providers.add(new Provider());

        for (Provider provider : providers) {
            new Thread(provider).start();
        }
    }
}


