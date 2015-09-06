package com.splat.provider;

/**
 * Created by ִלטענטי on 25.08.2015.
 */

/**
 * Main class for objects.
 * @author Dmitry Prokopenko
 */
public class DataObject {

    Integer id;
    Integer value;

    /**
     * @param id Id of object
     * @param value Value of object
     */

    DataObject() {
        this.id = 0;
        this.value = 0;
    }

    DataObject(Integer id,Integer value) {
        this.id = id;
        this.value = value;
    }
}
