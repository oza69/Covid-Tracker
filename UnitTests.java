/*
SDC Project
Name: Dhruv Oza
Banner Id: B00856235
*/
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTests {
    String configFile_Government = "ConfigFile_Government.txt";		//config file name for government class
    Government contactTracer = new Government(configFile_Government);
    private MobileDevice c;

    @Test
    @DisplayName("Testing null or empty file entered!!")
    public void filename_null_empty(){
        String filename = "";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            System.out.println("Empty or Null file entered!!");
        }
    }

    @Test
    @DisplayName("Testing if file exist or not!!")
    public void filename_validOrNot(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            System.out.println("File not found!!");
        }
    }

    @Test
    @DisplayName("Checking if null string passed as individual(string)!!")
    public void recordContact_Test1(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        c.recordContact(null, 20210405, 54);

        assertEquals("Empty string as individual", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("Checking if empty string passed as individual(string)!!")
    public void recordContact_Test2(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        c.recordContact("", 20210405, 54);

        assertEquals("Empty string as individual", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("Checking if negative int passed as date(int)!!")
    public void recordContact_Test3(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        c.recordContact("d29d9b2760a311cd38f280105ffe9959aaed53cde10f9299a5e2e528b347e86c", -20210405, 54);

        assertEquals("Invalid value for date", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("Checking if 0 as int passed as date(int)!!")
    public void recordContact_Test4(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        c.recordContact("d29d9b2760a311cd38f280105ffe9959aaed53cde10f9299a5e2e528b347e86c", 0, 54);

        assertEquals("Invalid value for date", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("Checking if greater value from 1140 as int passed as duration(int)!!")
    public void recordContact_Test5(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        c.recordContact("d29d9b2760a311cd38f280105ffe9959aaed53cde10f9299a5e2e528b347e86c", 20210405, 1141);

        assertEquals("Invalid value for duration", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("Checking if 0 as int passed as duration(int)!!")
    public void recordContact_Test6(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        c.recordContact("d29d9b2760a311cd38f280105ffe9959aaed53cde10f9299a5e2e528b347e86c", 20210405, 0);

        assertEquals("Invalid value for duration", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("Checking if parameters inserted are actual!!")
    public void recordContact_Test7(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        c.recordContact("d29d9b2760a311cd38f280105ffe9959aaed53cde10f9299a5e2e528b347e86c", 20210405, 24);

        assertEquals("Successfully stored in contactDeviceHashMap(hashmap) where encoded hashcode as key and contact detail's list as value!!", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("Checking if empty string passed as testHash(string)!!")
    public void positiveTest_Test1(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        c.positiveTest("");

        assertEquals("Empty string as testHash", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("Checking if null string passed as testHash(string)!!")
    public void positiveTest_Test2(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        c.positiveTest(null);

        assertEquals("Empty string as testHash", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("Checking if string passed as testHash(string) contains only small alphabets!!")
    public void positiveTest_Test3(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        c.positiveTest("aaaccc");

        assertEquals("Not Valid testHash", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("Checking if string passed as testHash(string) contains only capital letters!!")
    public void positiveTest_Test4(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        c.positiveTest("AASSF");

        assertEquals("Not Valid testHash", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("Checking if string passed as testHash(string) contains only numbers!!")
    public void positiveTest_Test5(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        c.positiveTest("124");

        assertEquals("Not Valid testHash", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("Checking if string passed as testHash(string) contains alphanumeric!!")
    public void positiveTest_Test6(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        c.positiveTest("abc123");

        assertEquals("Successfully stored in positiveTestHashMap(hashmap) where encoded hashcode as key and positiveTestDetails_List as value!!", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("Checking that without calling other methods just calling synchronizeData()!!")
    public void synchronizeData_Test1(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(false,c.synchronizeData());
    }

    @Test
    @DisplayName("Calling required methods before calling synchronizeData but with empty string as testHash(string) in positiveTest!!")
    public void synchronizeData_Test2(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        contactTracer.recordTestResult("bse123", 20210405, true);
        c.recordContact("d29d9b2760a311cd38f280105ffe9959aaed53cde10f9299a5e2e528b347e86c", 20210405, 24);

        c.positiveTest("");

        assertEquals(false, c.synchronizeData());
    }

    @Test
    @DisplayName("With all correct parameters calling required methods first and then synchronizeData method!!")
    public void synchronizeData_Test3(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        contactTracer.recordTestResult("bse123", 20210405, true);
        c.recordContact("d29d9b2760a311cd38f280105ffe9959aaed53cde10f9299a5e2e528b347e86c", 20210405, 24);

        c.positiveTest("bse123");

        assertEquals(true, c.synchronizeData());
    }

    @Test
    @DisplayName("recordTestResult(method) tested with empty string parameter as testHash!!")
    public void recordTestResult_Test1(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        contactTracer.recordTestResult("", 20210405, true);

        assertEquals("Empty string as testHash", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("recordTestResult(method) tested with null as string parameter as testHash!!")
    public void recordTestResult_Test2(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        contactTracer.recordTestResult(null, 20210405, true);

        assertEquals("Empty string as testHash", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("recordTestResult(method) tested with 0 as int parameter as date!!")
    public void recordTestResult_Test3(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        contactTracer.recordTestResult("acbd123", 0, true);

        assertEquals("Invalid value for date", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("recordTestResult(method) tested with negative int as parameter as date!!")
    public void recordTestResult_Test4(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        contactTracer.recordTestResult("acbd123", -11, true);

        assertEquals("Invalid value for date", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("recordTestResult(method) tested with small letters as string parameter as testHash!!")
    public void recordTestResult_Test5(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        contactTracer.recordTestResult("acb", 20210405, true);

        assertEquals("Not Valid testHash", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("recordTestResult(method) tested with capital letters as string parameter as testHash!!")
    public void recordTestResult_Test6(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        contactTracer.recordTestResult("ABC", 20210405, true);

        assertEquals("Not Valid testHash", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("recordTestResult(method) tested with only numbers as string parameter as testHash!!")
    public void recordTestResult_Test7(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        contactTracer.recordTestResult("123", 20210405, true);

        assertEquals("Not Valid testHash", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("recordTestResult(method) tested with valid parameter!!")
    public void recordTestResult_Test8(){
        String filename = "ConfigFile_MobileDevice.txt";
        try {
            c = new MobileDevice(filename, contactTracer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        contactTracer.recordTestResult("aqc123", 20210402, true);

        assertEquals("Test Result details inserted sucessfully", out.toString().replaceAll("\n", ""));
    }

    @Test
    @DisplayName("findGatherings(method) tested with negative or 0 as int parameter as date!!")
    public void findGatherings_Test1(){

        assertEquals(0, contactTracer.findGatherings(0, 2, 3, 1));

        assertEquals(0, contactTracer.findGatherings(-1, 2, 3, 1));
    }

    @Test
    @DisplayName("findGatherings(method) tested with negative or 1 as int parameter as minSize!!")
    public void findGatherings_Test2(){

        assertEquals(0, contactTracer.findGatherings(20210405, 1, 3, 1));

        assertEquals(0, contactTracer.findGatherings(20210405, -1, 3, 1));
    }

    @Test
    @DisplayName("findGatherings(method) tested with negative or 0 as int parameter as minTime!!")
    public void findGatherings_Test3(){

        assertEquals(0, contactTracer.findGatherings(20210405, 2, 0, 1));

        assertEquals(0, contactTracer.findGatherings(20210405, 2, -1, 1));
    }

    @Test
    @DisplayName("findGatherings(method) tested with negative or 0 as int parameter as density!!")
    public void findGatherings_Test4(){

        assertEquals(0, contactTracer.findGatherings(20210405, 2, 2, -1));

        assertEquals(0, contactTracer.findGatherings(20210405, 2, 1, 0));
    }

    @Test
    @DisplayName("findGatherings(method) tested with all valid parameter!!")
    public void findGatherings_Test5(){

        assertEquals(1, contactTracer.findGatherings(20210205, 2, 43, 0.5F));

    }

}
