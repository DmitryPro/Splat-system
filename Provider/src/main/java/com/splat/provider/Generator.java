package com.splat.provider;


import java.io.*;
import java.util.Random;


/**
 * @author Dmitry Prokopenko
 */
public class Generator
{
    BufferedReader bufferedReader;

    Integer idxRange;

    Integer valueRange;

    Random random;


    Generator()
    {
        try {
            this.bufferedReader = new BufferedReader(new FileReader("Data.log"));
        }
        catch (FileNotFoundException e) {
            System.err.println("Can't find Data.log");
            System.exit(-1);
        }
        this.random = new Random();
        this.idxRange = Integer.parseInt(PropertiesLoader.getInstance().getValue("idxRange"));
        this.valueRange = Integer.parseInt(PropertiesLoader.getInstance().getValue("valueRange"));
    }


    /**
     * @see DataObject
     * @return new object with Id from 0 to idxRange and Value from 0 to valueRange.
     */
    String next()
    {
        String in_string;
        try {
           in_string  = bufferedReader.readLine();
        }
        catch (IOException e) {
            return null;
        }
        return in_string.substring(in_string.indexOf('{'));
    }

}
