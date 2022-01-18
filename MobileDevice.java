/*
SDC Project
Name: Dhruv Oza
Banner Id: B00856235
*/
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MobileDevice {

    //contactDeviceHashMap(hashmap) to store mobiledeviceHash as key and contactDeviceDetails_List(list) to store individual, date, duration
    private static HashMap<String, List<DetailsofContactDevice>> contactDeviceHashMap = new HashMap<>();
    //positiveTestHashMap(hashmap) to store mobiledeviceHash as key and positiveTestDetails_List(list) to store testhash
    private static HashMap<String, List<String>> positiveTestHashMap = new HashMap<>();

    //contactDeviceDetails_List(list) as multi-datatype to store individual, date, duration
    private List<DetailsofContactDevice> contactDeviceDetails_List =new ArrayList<DetailsofContactDevice>();

    //positiveTestDetails_List(list) as list to store testhash
    private List<String> positiveTestDetails_List = new ArrayList<String>();

    //contactTracer as object of Government class to pass data to mobileContact method of Government
    private Government contactTracer;
    //string to join two string as address and deviceName
    private String hashCodeBuilderforDevice;
    //to create hashcode for hashCodeBuilderforDevice(string)
    private String encoded;
    //detailsofContactDevice(object) of DetailsofContactDevice(class)
    private DetailsofContactDevice detailsofContactDevice;


    /*
    MobileDevice(constructor) basically scans data from configFile as txt and store it hashCodeBuilderforDevice(string)
    and create hashcode as encoded(string) and also initialise the object as contactTracer for Government class
    */
    public MobileDevice(String configFile, Government contactTracer) throws Exception {
        /* IMPLEMENT */

        //to check whether configFile is null or empty
        if (!configFile.equals("") || !configFile.equals(null)) {
            try {
                Scanner scan;
                scan = new Scanner(new File(configFile));
                List<String> list = new ArrayList<>();
                while (scan.hasNextLine()) {
                    String str = scan.nextLine();
                    list.add(str);
                }
                if(list.size() != 2) {
                    System.out.print("Invalid Content in Configuration File\n");
                    throw new Exception();
                }

                //declaring address and deviceName as string to extract data from list to concat address and deviceName
                String address = "";
                String deviceName = "";
                for (String str_detail : list)
                {
                    int index = str_detail.indexOf("=");

                    if (str_detail.contains("address")) {
                        address = str_detail.substring(index + 1, str_detail.length());
                    } else if (str_detail.contains("deviceName")) {
                        deviceName = str_detail.substring(index + 1, str_detail.length());
                    } else {
                        System.out.print("Format of the content not correct\n");
                        throw new Exception();
                    }

                    if (str_detail.contains("=")) {
                        continue;
                    }else {
                        System.out.print("Format of the content not correct\n");
                        throw new Exception();
                    }
                }

                //if address and deviceName are not null concat them and create encoded(string) as hashcode
                if (!address.equals(null) && !deviceName.equals(null) && !address.equals(" ") && !deviceName.equals(" ")) {
                    hashCodeBuilderforDevice = address.concat(deviceName);
                    System.out.println("Successfully concated both string as address and deviceName!!");
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    byte[] byteOfTextToHash = hashCodeBuilderforDevice.getBytes(StandardCharsets.UTF_8);
                    byte[] hashedByetArray = digest.digest(byteOfTextToHash);
                    encoded = DatatypeConverter.printHexBinary(hashedByetArray).toLowerCase();
                    System.out.println("Successfully created hashcode of mobile device!!");
                    this.contactTracer = contactTracer;
                } else {
                    System.out.println("Null value found in file for address and device");
                    throw new Exception();
                }

            } catch (FileNotFoundException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }
        } else {
            System.out.print("Invalid Config File name\n");
        }
    }


    /*
    recordContact(method) basically stores data as individual(string), date(int), duration(int) which are passed from
    through DetailsofContactDevice(class) to store multi datatype data into contactDeviceHashMap(hashmap) for further use
    */
    public void recordContact(String individual, int date, int duration){
        /* IMPLEMENT */

        if(individual==null || individual.equals(""))	{
            System.out.print("Empty string as individual\n");
            return;
        }
        if(date<=0 ) {
            System.out.print("Invalid value for date\n");
            return;
        }
        if(duration > 1140 || duration <= 0) {
            System.out.print("Invalid value for duration\n");
            return;
        }

        try {
            //calling object as detailsofContactDevice(object) to initialise data to be stored in contactDeviceHashMap(hashmap)
            detailsofContactDevice = new DetailsofContactDevice(individual, date, duration);
            if (contactDeviceHashMap.get(encoded) == null) {
                contactDeviceDetails_List.clear();
            }
            else {
                contactDeviceDetails_List = contactDeviceHashMap.get(encoded);
            }
            //adding detailsofContactDevice(details) into contactDeviceDetails_List(list)
            contactDeviceDetails_List.add(detailsofContactDevice);

            //storing encoded(string) as key and  contactDeviceDetails_List(list) as value in contactDeviceHashMap(hashmap)
            contactDeviceHashMap.put(encoded, contactDeviceDetails_List);
            System.out.print("Successfully stored in contactDeviceHashMap(hashmap) where encoded hashcode as key and contact detail's list as value!!\n");
        }catch (Exception e){
            System.out.println(e);
        }
    }


    /*
    positiveTest(method) basically stores testhash(string) in positiveTestDetails_List(list) and that is stored as value
    with key as encoded(string) to form a positiveTestHashMap(hashmap)
    */
    public void positiveTest(String testHash){
        /* IMPLEMENT */

        if(testHash == null || testHash.equals("")) {
            System.out.print("Empty string as testHash\n");
            return;
        }
        if(testHash.matches("[a-z]+") || testHash.matches("[0-9]+") || testHash.matches("[A-Z]+")) {
            System.out.print("Not Valid testHash\n");
            return;
        }

        try {
            if (positiveTestHashMap.get(encoded) == null) {
                positiveTestDetails_List.clear();
            } else {
                positiveTestDetails_List = positiveTestHashMap.get(encoded);
            }
            if (positiveTestDetails_List.contains(testHash)) {
                System.out.print("Duplicate testHash for same device\n");
            }
            //positiveTestDetails_List(list) to store testHash(string)
            positiveTestDetails_List.add(testHash);
            //positiveTestHashMap(hashmap) to store encoded(string) as key and positiveTestDetails_List(list) as value
            positiveTestHashMap.put(encoded, positiveTestDetails_List);
            System.out.print("Successfully stored in positiveTestHashMap(hashmap) where encoded hashcode as key and positiveTestDetails_List as value!!\n");
        }catch (Exception e){
            System.out.println(e);
        }
    }


    /*
    synchronizeData(method) basically collects data which was stored in contactDeviceHashMap(hashmap) and positiveTestHashMap(hashmap)
    then it pass all data into a single content(string) which is further used to send data to mobileContact(method of government class)
    using contactTracer as object.
    */
    public boolean synchronizeData(){
        /* IMPLEMENT */

        boolean result;
        try {
            String content = "";
            String contactInformation = "";

            //using contactDeviceHashMap(hashmap) and positiveTestHashMap(hashmap) data is stored in content(string)
            if (contactDeviceHashMap.get(encoded) != null) {
                for (DetailsofContactDevice detail : contactDeviceHashMap.get(encoded))
                {
                    content += detail.individual_hashcode + "\n";
                    content += detail.date + "\n";
                    content += detail.duration + "\n";
                }
            }
            if (positiveTestHashMap.get(encoded) != null) {
                for (String test : positiveTestHashMap.get(encoded)) {
                    content += test;
                }
            }

            //content(string) data is passed to contactInformation(string)
            contactInformation = content;

            if (content != null) {

                //collected data is passed to mobileContact(method) for storing that data into database
                result = contactTracer.mobileContact(encoded, contactInformation);

                //once the data is send to government class then content is deleted from below hashmaps and lists
                contactDeviceDetails_List.clear();
                contactDeviceHashMap.remove(encoded);
                positiveTestDetails_List.clear();
                positiveTestHashMap.remove(encoded);

                //return true if the device is nearer to covid positive within 14 days
                if (result == true) {
                    System.out.println(result);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }
}
