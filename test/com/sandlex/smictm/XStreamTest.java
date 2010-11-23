package com.sandlex.smictm;

import com.sandlex.smictm.model.Activity;
import com.sandlex.smictm.model.State;
import com.sandlex.smictm.model.Task;
import com.thoughtworks.xstream.persistence.FilePersistenceStrategy;
import com.thoughtworks.xstream.persistence.PersistenceStrategy;
import com.thoughtworks.xstream.persistence.XmlArrayList;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author Alexey Peskov
 */
public class XStreamTest {

    private static Task task;

    @BeforeClass
    public static void prepare() {
        task = new Task("Test task");

        task.addState(State.InProgress);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        task.addState(State.Completed);
    }

    @Test
    public void testTask() {
        assertEquals(3, task.getActivitiesNumber());
    }

    @Test
    public void testTaskExport() {

        // prepares the file strategy to directory /tmp
        PersistenceStrategy strategy = new FilePersistenceStrategy(new File("/home/sandlex/java/workspace/smictm/tasks"));
        // creates the list:
        List list = new XmlArrayList(strategy);
        list.add(task);

        Task tsk = new Task("Test task new");
        tsk.addActivity(Activity.Doing);
        list.add(tsk);
    }

    @Test
    public void testTaskImport() {

        // prepares the file strategy to directory /tmp
        PersistenceStrategy strategy = new FilePersistenceStrategy(new File("/home/sandlex/java/workspace/smictm/tasks"));
        // creates the list:
        List list = new XmlArrayList(strategy);

        for (Object task : list) {
            System.out.println(task);
        }
    }

}
