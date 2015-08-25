/**
 * Created by ִלטענטי on 25.08.2015.
 */
public class Main {

    public static void main(String[] args)  {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        try {
           Sender sender = new Sender();
            System.out.println(propertiesLoader.getValue("port"));
            sender.setPort(Integer.parseInt(propertiesLoader.getOrDefault("port", "8080")));
            sender.run();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
