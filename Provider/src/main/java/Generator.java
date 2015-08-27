import java.util.Random;

/**
 * Created by ִלטענטי on 25.08.2015.
 */

/**
 * @author Dmitry Prokopenko
 */
public class Generator {

    Integer idxRange;
    Integer valueRange;
    Random random;

    Generator() {
        this.random = new Random();
        this.idxRange = Integer.parseInt(PropertiesLoader.getInstance().getValue("idxRange"));
        this.valueRange = Integer.parseInt(PropertiesLoader.getInstance().getValue("valueRange"));
    }

    /**
     * @see DataObject
     * @return new object with Id from 0 to idxRange and Value from 0 to valueRange.
     */
    DataObject next() {
        return new DataObject(random.nextInt(idxRange),random.nextInt(valueRange));
    }

}
