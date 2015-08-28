/**
 * Created by Freemahn on 25.08.2015.
 */
public class DataObject {

    /**
     * Object Id
     */
    Integer id;
    /**
     * Object value
     */
    Integer value;

    public DataObject(Integer id, Integer value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return "DataObject{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}