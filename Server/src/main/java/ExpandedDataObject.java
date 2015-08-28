/**
 * Created by Freemahn on 26.08.2015.
 */
public class ExpandedDataObject extends DataObject {
    Integer providerId;

    public ExpandedDataObject(Integer providerId) {
        this.id = 2;
        this.value = 2;
        this.providerId = providerId;
    }

    public ExpandedDataObject(DataObject object, Integer providerId) {
        this.id = object.id;
        this.value = object.value;
        this.providerId = providerId;
    }

    public ExpandedDataObject(Integer id, Integer value, Integer providerId) {
        super(id, value);
        this.providerId = providerId;
    }

    @Override
    public String toString() {
        return "ExpandedDataObject{" +
                "id = " + id +
                ", value=" + value +
                ", providerId=" + providerId +
                '}';
    }
}
