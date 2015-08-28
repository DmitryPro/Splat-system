
/**
 * This class represents objects,
 * that server sends to clients
 *
 * @author Pavel Gordon
 */
public class ExpandedDataObject extends DataObject {
    Integer providerId;


    /**
     * Constructor, which initialises id,value with default values
     * and initialises providerId with value given
     * @param providerId  generated providerId
     */
    public ExpandedDataObject(Integer providerId) {
        this.id = 2;
        this.value = 2;
        this.providerId = providerId;
    }
    /**
     * Constructor, which creates a copy of DataObject given
     * and initialises providerId with value given
     * @param dataObject object to copy id,value fields
     * @param providerId generated providerId
     */


    public ExpandedDataObject(DataObject dataObject, Integer providerId) {
        this.id = dataObject.id;
        this.value = dataObject.value;
        this.providerId = providerId;
    }


    /**
     * String representation of ExpandedDataObject
     * @return string representation of ExpandedDataObject
     */
    @Override
    public String toString() {
        return "ExpandedDataObject{" +
                "id = " + id +
                ", value=" + value +
                ", providerId=" + providerId +
                '}';
    }
}
