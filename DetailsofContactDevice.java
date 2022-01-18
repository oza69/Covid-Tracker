/*
SDC Project
Name: Dhruv Oza
Banner Id: B00856235
*/
public class DetailsofContactDevice {
    /* IMPLEMENT */

    //initialised these variables to store multidatatype data into list which is used in MobileDevice(class)
    protected int date ,  duration;
    protected String individual_hashcode;

    //DetailsofContactDevice(constructor) gto initialise data which are as parameters
    protected DetailsofContactDevice(String individual_hashcode, int date, int duration)
    {
        this.individual_hashcode = individual_hashcode;
        this.date = date;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return individual_hashcode+","+date+","+duration;
    }
}
