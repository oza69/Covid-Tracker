/*
SDC Project
Name: Dhruv Oza
Banner Id: B00856235
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Government {

    //driver and connection initialised
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;

    //statement and connection(to initialise a connenction method below)
    private Statement statement = null;
    private Connection connect = null;


    /*
    Government(constructor) basically initialise database, user, password from a txt file which is first loaded/scanned and also
    using these parameters connection is setup into mysql database
    */
    public Government(String configFile) {
        /* IMPLEMENT */

        //to check if configfile is not empty
        if (!configFile.equals("") && !configFile.equals(null)) {
            try {
                //using scan configFile is scanned and data is stored into list
                Scanner scan;
                scan = new Scanner(new File(configFile));
                List<String> list = new ArrayList<>();
                while (scan.hasNextLine()) {
                    String str = scan.nextLine();
                    list.add(str);
                }
                String database = "";
                String user = "";
                String password = "";
                //required database, user, password are extracted from list
                for (String str_detail : list)
                {
                    int index = str_detail.indexOf("=");

                    if (str_detail.contains("database")) {
                        database = str_detail.substring(index + 1, str_detail.length());
                    } else if (str_detail.contains("user")) {
                        user = str_detail.substring(index + 1, str_detail.length());
                    } else if (str_detail.contains("password")) {
                        password = str_detail.substring(index + 1, str_detail.length());
                    } else {
                        System.out.print("Format of the content not correct\n");
                    }

                    if (str_detail.contains("=")) {
                        continue;
                    } else {
                        System.out.print("Format of the content not correct\n");
                    }
                }

                //using database, user, password getConnection(method) is called to setup connection
                connect = ConnectingDatabase(database, user, password);
                try {
                    //create statement
                    statement = connect.createStatement();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                System.out.println(e);
            }
        } else {
            System.out.print("Invalid Config File name\n");
        }
    }


    /*
    getConnection(method) basically setup the connection using parameters passed
    */
    private static Connection ConnectingDatabase(String database,String username,String password) {
        /* IMPLEMENT */

        try {
            Class.forName(driverName);
            try {
                //connection to database
                connection = DriverManager.getConnection(database, username, password);
            } catch (SQLException e) {
                System.out.println("Database connection failed!!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found!!");
        }
        return connection;
    }


    /*
    mobileContact(method) is basically called by synchronizeData(method) with the needed data which are to be stored into database
    by executing insert query one by one as needed and also notification is send to contacted device if it has been in contact
    with covid positive device within 14 days
    */
    public boolean mobileContact(String initiator, String contactInfo) {
        /* IMPLEMENT */

        if(initiator.equals("") || initiator.equals(null))	{
            System.out.print("Initiator as string is empty\n");
            return false;
        }
        if(contactInfo.equals(null) || contactInfo.equals(""))	{
            System.out.print("Contact Info as string is empty\n");
            return false;
        }

        ResultSet resultSet = null;
        int success=0;
        boolean res = false;

        //with the help of list the data from contactInfo(string) is extracted
        List<String> list = new ArrayList<>();
        String[] str;
        str = contactInfo.split("\\s");
        for(String d : str){
            list.add(d);
        }
        String individual = list.get(0);
        int date = Integer.parseInt(list.get(1));
        int duration = Integer.parseInt(list.get(2));
        String positiveTestHash = list.get(3);

        //In MobileDevice table initiator as mobiledevicehash is inserted
        try {
            //insert ignore is used to avoid inserting same details again into database
            statement.execute("insert ignore into MobileDevice values('"+initiator+"');");
        } catch (SQLException e) {
            System.out.print(e);
        }

        //Inserting MobileDeviceHash, ContactDeviceHash, Contact_Date, Contact_Duration, Boolean_Result into Contact Table
        try {
            //To check whether same data with same MobileDeviceHash, ContactDeviceHash, Contact_Date are present
            resultSet = statement.executeQuery("select Contact_Duration from Contact "
                    + "where MobileDeviceHash = '"+initiator+"' and ContactDeviceHash = '"+individual+"' and Contact_Date = '"+date+"' and Boolean_Result = '"+res+"';");
            while (resultSet.next()){
                System.out.println("Same data present!!");
            }
        } catch (SQLException e) {
            System.out.print(e);
        }

        try {
            //if no data found present then
            if(!resultSet.next()) {

                //Insert new details
                success = statement.executeUpdate("insert ignore into Contact (Contact_Date, Contact_Duration, MobileDeviceHash, ContactDeviceHash, Boolean_Result) values('"+date+"', '"+duration+"', '"+initiator+"', '"+individual+"', '"+res+"');");
                //to check if data sucessfully entered or not
                if(success==1)
                    System.out.println("Contact details inserted sucessfully");
            }
            else {
                //update the time if same details present
                duration = duration + resultSet.getInt("Contact_Duration");
                //update details with updated time duration
                success = statement.executeUpdate("update Contact set Contact_Duration = '"+duration+"' where MobileDeviceHash = '"+initiator+"' "
                        + "and ContactDeviceHash = '"+individual+"' and Contact_Date = '"+date+"' and Boolean_Result = '"+res+"';");
                //to check if data sucessfully entered or not
                if(success==1)
                    System.out.println("Contact details updated sucessfully");
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.print(e);
        }

        try {
            //Inserting in MobileDevice_TestResult with MobileDeviceHash, and TestHash as details
            success = statement.executeUpdate("insert ignore into MobileDevice_TestResult (MobileDeviceHash, TestHash) values('"+initiator+"', '"+positiveTestHash+"');");
            //to check if data sucessfully entered or not
            if(success==1) {
                System.out.println("MobileDevice_TestResult details inserted sucessfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            //Checking the contacts which needs to be notified
            resultSet = statement.executeQuery("select c.ContactID from Contact as c, MobileDevice_TestResult as mdtr, TestResult as tr\n" +
                    "where\n" +
                    "    c.ContactDeviceHash = mdtr.ID and\n" +
                    "    mdtr.TestHash = tr.TestHash and\n" +
                    "    c.MobileDeviceHash = '"+initiator+"' and\n" +
                    "    ABS(DATEDIFF(c.Contact_Date, tr.TestResult_Date)) between 0 and 14 and\n" +
                    "    tr.Result = true and\n" +
                    "    c.Boolean_Result = false;");

            //If details found then update that Boolean_Result to true
            while (resultSet.next()){
                int id = resultSet.getInt("ContactID");
                success = statement.executeUpdate("update Contact as c set c.Boolean_Result = true where c.ContactID in ('"+id+"');");
                //to check if data sucessfully entered or not
                if(success==1)
                    System.out.println("Notify details updated sucessfully");
            }
        } catch (SQLException e) {
            System.out.print(e);
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return true;
    }


    /*
    recordTestResult(method) will basically insert details as testHash, date, result into TestResult table
    */
    public void recordTestResult(String testHash, int date, boolean result){
        /* IMPLEMENT */

        if(testHash == null || testHash.equals("")) {
            System.out.print("Empty string as testHash\n");
            return;
        }
        if(testHash.matches("[a-z]+") || testHash.matches("[0-9]+") || testHash.matches("[A-Z]+")) {
            System.out.print("Not Valid testHash\n");
            return;
        }
        if(date<=0 ) {
            System.out.print("Invalid value for date\n");
            return;
        }

        int success = 0;

        try {
            //insert details as testHash, date, result into TestResult table
            success = statement.executeUpdate("insert ignore into TestResult values('"+testHash+"','"+date+"', "+result+");");
            //to check if data sucessfully entered or not
            if(success==1) {
                System.out.print("Test Result details inserted sucessfully\n");
            }
        } catch (SQLException e) {
            System.out.print(e);
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }


    /*
    findGatherings(method) will basically find the no. of devices gathered together on same date with minSize, minTime,
    and also minDensity. It is calculated using formula given c/m is greater than density than count gatherings where c
    is connect and m is maxpair possible (n(n-1)/2)
    */
    public int findGatherings(int date, int minSize, int minTime, float density){
        /* IMPLEMENT */

        if(date<=0 || minSize<=1 || minTime<=0 || density<=0) {
            return 0;
        }

        int total_gathering = 0;

        try {
            ResultSet resultSet = null;
            //common_List(hashmap) to store key, value as {M=N} and {N=M} for M as Mobile device and N as Contact device
            HashMap<String, List<String>> common_List = new HashMap<String, List<String>>();
            List<List<String>> comm_hashcode = new ArrayList<List<String>>();
            List<List<String>> uniq_hashcode = new ArrayList<List<String>>();

            //Select data from Contact table for date and minTime passed as parameter
            resultSet = statement.executeQuery("select * from Contact where Contact_Date = '"+date+"' and Contact_Duration >= '"+minTime+"'; ");

            while(resultSet.next())
            {
                //Taking MobileDevice and ContactDevice from Contact table
                String MobileDevice = resultSet.getString("MobileDeviceHash");
                String ContactDevice = resultSet.getString("ContactDeviceHash");

                /*Adding MobileDevice and ContactDevice into common_List(hashmap) to form key, value as {M=N} and {N=M} where
                where M is MobileDevice and N is ContactDevice */
                if(common_List.get(MobileDevice)==null)		{
                    common_List.put(MobileDevice, new ArrayList<String>());
                }
                if(!common_List.get(MobileDevice).contains(ContactDevice)) {
                    common_List.get(MobileDevice).add(ContactDevice);
                }
                if(common_List.get(ContactDevice)==null)		{
                    common_List.put(ContactDevice, new ArrayList<String>());
                }
                if(!common_List.get(ContactDevice).contains(MobileDevice)) {
                    common_List.get(ContactDevice).add(MobileDevice);
                }
            }

            //Storing data form common_List.keySet() into hash_List(list)
            List<String> hash_List = new ArrayList<>();
            for(String s : common_List.keySet()){
                if(!hash_List.contains(s)){
                    hash_List.add(s);
                }
            }

            boolean bool =false;

            int n = hash_List.size();

            //Make pairs of particular element with other elements eg: [a,b,c,d] then it should be [[a,b],[a,c],[a,d],[b,c],[b,d],[c,d]]
            List<List<String>> commlist = new ArrayList<>();
            for(int a = 0; a < hash_List.size(); a++){
                for(int b = a+1; b < hash_List.size(); b++){
                    List<String> list2 = new ArrayList<>();
                    list2.add(hash_List.get(a));
                    list2.add(hash_List.get(b));
                    commlist.add(list2);
                }
            }

            for(int i=0;i<((n * (n-1))/2);i++)
            {
                float connect = 0;
                float calculate_density;
                int elements = 0;

                //storing data from commlist(list of list) into store_List
                List<String> store_List = new ArrayList<String>();
                store_List.add(commlist.get(i).get(0));
                store_List.add(commlist.get(i).get(1));

                for (String str : hash_List) {
                    if(!str.equals(commlist.get(i).get(0)) && !str.equals(commlist.get(i).get(1))) {
                        //if value is not present in M,N then continue
                        if (!common_List.get(commlist.get(i).get(0)).contains(str) || !common_List.get(commlist.get(i).get(1)).contains(str))
                        {
                            continue;
                        }else {
                            //otherwise add to store_List(list)
                            store_List.add(str);
                        }
                    }
                }

                //to avoid repeated values in comm_hashcode(list)
                for(List<String> str_list: comm_hashcode) {
                    elements=0;
                    for(String hash_value: store_List) {
                        if (!str_list.contains(hash_value)) {
                            continue;
                        } else {
                            //increase element by one if hash_value(string) is present in str_list(list)
                            elements = elements + 1;
                        }
                    }
                    if (store_List.size() != elements)
                    {
                        continue;
                    }else {
                        //if size of store_List and elements are same
                        break;
                    }
                }

                if(store_List.size()>elements || i==0) {
                    //to meet the requirement of minSize
                    if(minSize<=store_List.size()) {

                        //To find connection by scanning every element in store_List(list)
                        for(String str_pair: store_List) {
                            for(String str_pair2: store_List) {
                                //to skip same pair
                                if(str_pair.equals(str_pair2)) {
                                    bool = true;
                                }
                                if(!str_pair.equals(str_pair2) && bool == true)
                                {
                                    //if connection is not there
                                    if (!common_List.get(str_pair).contains(str_pair2))
                                    {
                                        continue;
                                    }else {
                                        //else increase connect by one
                                        connect = connect+1;
                                    }
                                }
                            }
                            bool = false;
                        }

                        //to find connection by using this equation
                        int z = store_List.size();
                        float maximum_connect = (z*(z-1))/2;
                        calculate_density = connect/maximum_connect;
                        //if calculate_density is greater then density than increase total_gathering
                        if(density<calculate_density)
                        {
                            //gathered elements
                            uniq_hashcode.add(store_List);
                            total_gathering = total_gathering+1;
                        }
                        //adding to comm_hashcode(list)
                        comm_hashcode.add(store_List);

                    }
                }
            }

            //repeating the same steps to go through the remaining elements
            int z=0;
            for(List<String> str_temp1: uniq_hashcode) {
                for(List<String> str_temp2: uniq_hashcode) {
                    if(str_temp2.size()>str_temp1.size()) {
                        for(String hash_value: str_temp1) {
                            if (!str_temp2.contains(hash_value)) {
                                continue;
                            }else {
                                //increase z by one if str_temp2 contains hash_value
                                z = z+1;
                            }
                        }
                        if (z != str_temp1.size()) {
                            continue;
                        }else {
                            //decrease total_gathering by one if z and str_temp1.size() is equal
                            total_gathering = total_gathering - 1;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.print(e);
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return total_gathering;
    }
}
