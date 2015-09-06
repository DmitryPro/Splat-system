/**
 * This class represents objects, that provider sends to server
 * @see ExpandedDataObject
 */
public class DataObject
{
    /**
     * id of some parameter
     */
    Integer id;
    /**
     * value of some parameter
     */
    Integer value;

    /**
     * Constructor, which initiates id,value with default values and initiates providerId with value given
     * 
     * @param id Integer id of some parameter
     * @param value Integer value of some parameter
     */
    public DataObject(Integer id, Integer value)
    {
        this.id = id;
        this.value = value;
    }


    /**
     * Default constructor with default fields filling.
     */
    public DataObject()
    {
        id = 1;
        value = 1;
    }


    @Override
    public String toString()
    {
        return "DataObject{" + "id=" + id + ", value=" + value + '}';
    }
}