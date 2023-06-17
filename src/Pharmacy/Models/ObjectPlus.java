package Pharmacy.Models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * The ObjectPlus class is an abstract class that extends Serializable.
 * It provides functionality for managing object extents.
 */
public abstract class ObjectPlus implements Serializable {
    private static Map<Class, List<ObjectPlus>> allExtents = new Hashtable<>();

    /**
     * Constructor that initializes the extent list for the class
     */
    public ObjectPlus() {
        List extent = null;
        Class theClass = this.getClass();

        if(allExtents.containsKey(theClass)) {
            extent = allExtents.get(theClass);
        }
        else {
            extent = new ArrayList();
            allExtents.put(theClass, extent);
        }

        extent.add(this);
    }

    /**
     * Writes the extents to the ObjectOutputStream.
     * @param stream
     * @throws IOException
     */
    public static void writeExtents(ObjectOutputStream stream) throws IOException {
        stream.writeObject(allExtents);
    }

    /**
     * Reads the extents from the ObjectInputStream.
     * @param stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void readExtents(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        allExtents = (Hashtable) stream.readObject();
    }

    /**
     * Gets the extent for the selected class
     * @param type
     * @param <T>
     * @return An Iterable containing the objects in the extent.
     * @throws ClassNotFoundException
     */
    public static <T> Iterable<T> getExtent(Class<T> type) throws ClassNotFoundException {
        if(allExtents.containsKey(type)) {
            return (Iterable<T>) allExtents.get(type);
        }

        throw new ClassNotFoundException(String.format("%s. Stored extents: %s", type.toString(), allExtents.keySet()));
    }

    /**
     * Removes the object from extent
     * @param object
     * @param <T>
     * @throws ClassNotFoundException
     */
    public static <T> void removeExtent(T object) throws ClassNotFoundException {
        List<T> extent = (List<T>) getExtent(object.getClass());
        extent.remove(object);
    }

    /**
     * Shows the extent of the specified class
     * @param theClass
     * @throws Exception If the extent for the specified class does not exist
     */
    public static void showExtent(Class theClass) throws Exception {
        List extent = null;

        if(allExtents.containsKey(theClass)) {
            extent = allExtents.get(theClass);
        }
        else {
            throw new Exception("Unknown class " + theClass);
        }

        System.out.println("Extent of the class: " + theClass.getSimpleName());

        for(Object obj : extent) {
            System.out.println(obj);
        }
    }
}
