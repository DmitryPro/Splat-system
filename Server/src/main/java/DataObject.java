/**
 * This class represents objects,
 * that provider sends to server
 */
public class DataObject {


    Integer id;
    Integer value;

    public DataObject(Integer id, Integer value) {
        this.id = id;
        this.value = value;
    }

    public DataObject() {
    }

    @Override
    public String toString() {
        return "DataObject{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}