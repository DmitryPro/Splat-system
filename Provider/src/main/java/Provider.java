
public class Provider {

    public static void main(String[] args) {
        try {
            new Sender().run();
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }


}
