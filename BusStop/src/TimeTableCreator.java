import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

public class TimeTableCreator{
    private static final int MIN_IN_HR = 60;
    private static final int HR_IN_DAY = 24;

    private ArrayList <Service> serviceList;
    private ArrayList <Service> serviceListPosh;
    private ArrayList <Service> serviceListGrotty;


    public TimeTableCreator (){
        serviceList = new ArrayList <> ();
        serviceListPosh = new ArrayList <> ();
        serviceListGrotty = new ArrayList <> ();
    }


    private void removeGrotty (){ // remove Grotty that starts and ends at the same time as Posh
        Service tmpS;
        for (int i = 1; i < serviceList.size (); i++){
            tmpS = serviceList.get (i - 1);
            if (Time.compare (serviceList.get (i).arrTime, tmpS.arrTime) == 0 && Time.compare (
                    serviceList.get (i).depTime, tmpS.depTime) == 0){
                if (!tmpS.companyName.equals (serviceList.get (i).companyName)){
                    serviceList.remove (i--);
                }
            }
        }
    }

    private void removeDepSameArrLater (){ // remove those that start at the same and reach later only
        Service tmpS;
        for (int i = 1; i < serviceList.size (); i++){
            tmpS = serviceList.get (i - 1);
            if (Time.compare (serviceList.get (i).depTime, tmpS.depTime) == 0){
                if (Time.compare (serviceList.get (i).arrTime, tmpS.arrTime) > 0){
                    serviceList.remove (i--);
                }
            }
        }
    }

    private void removeArrSameDepEarlier (){ // remove those that reach at the same time and start early only
        for (int i = serviceList.size () - 1; i >= 0; i--){
            if (i != serviceList.size () - 1){
                if (Time.compare (serviceList.get (i + 1).arrTime, serviceList.get (i).arrTime) == 0){
                    if (Time.compare (serviceList.get (i).depTime, serviceList.get (i + 1).depTime) > 0){
                        serviceList.remove (++i);
                    }
                }
            }
        }
    }

    private void removeDepEarlierArrLater (){
        for (int i = serviceList.size () - 1; i >= 0; i--){
            if (i != serviceList.size () - 1){
                if (Time.compare (serviceList.get (i + 1).arrTime, serviceList.get (i).depTime) > 0){
                    if (Time.compare (serviceList.get (i).arrTime, serviceList.get (i + 1).arrTime) < 0){
                        serviceList.remove (++i);
                    }
                }
            }
        }
    }


    public void readInput (String fileName){
        try{
            String line;
            File f = new File ("files\\" + fileName);
            BufferedReader br = new BufferedReader (new FileReader (f));

            StringTokenizer st;
            Service service;
            while ((line = br.readLine ()) != null){
                st = new StringTokenizer (line, " :");
                service = new Service (st.nextToken (), parseInt (st.nextToken ()), parseInt (st.nextToken ()),
                                       parseInt (st.nextToken ()), parseInt (st.nextToken ()));

                if (service.arrivalHr == 0 && service.departureHr != 0){
                    service.arrivalHr = HR_IN_DAY;
                }
                if (Math.abs (
                        (service.arrivalHr - service.departureHr) * MIN_IN_HR + service.arrivalMin - service.departureMin) <= MIN_IN_HR){
                    serviceList.add (service);
                }
            }
        } catch (IOException exc){
            System.out.println ("IO exception");
        }
    }

    public void createTimeTable (){
        serviceList.sort ((a, b) -> {
            if (a.arrivalHr != b.arrivalHr){
                return a.arrivalHr - b.arrivalHr;
            }

            if (a.arrivalMin != b.arrivalMin){
                return a.arrivalMin - b.arrivalMin;
            }

            if (a.departureHr != b.departureHr){
                return a.departureHr - b.departureHr;
            }

            if (a.departureMin != b.departureMin){
                return a.departureMin - b.departureMin;
            }

            if (a.companyName.equals ("Grotty") && b.companyName.equals ("Posh")){
                return 1;
            }
            else{
                return -1;
            }
        });

        removeGrotty (); // remove Grotty that starts and ends at the same time as Posh

        removeDepSameArrLater (); // remove those that start at the same and reach later only

        serviceList.sort ((a, b) -> {
            if (a.departureHr == b.departureHr && a.departureMin == b.departureMin){
                if (a.arrivalHr != b.arrivalHr){
                    return a.arrivalHr - b.arrivalHr;
                }
                else{
                    return a.arrivalMin - b.arrivalMin;
                }
            }
            if (a.departureHr != b.departureHr){
                return b.departureHr - a.departureHr;
            }
            else{
                return b.departureMin - a.departureMin;
            }
        });

        removeArrSameDepEarlier (); // remove those that reach at the same time and start early only

        removeDepEarlierArrLater (); // keep those that start later and reach earlier only


        for (int i = serviceList.size () - 1; i >= 0; i--){
            if (serviceList.get (i).companyName.equals ("Posh")){
                serviceListPosh.add (serviceList.get (i));
            }
            else{
                serviceListGrotty.add (serviceList.get (i));
            }
        }
    }

    public void writeTable (){
        try{
            String line;
            BufferedWriter bwP = new BufferedWriter (new FileWriter ("files\\output.txt", false));
            for (int i = 0; i < serviceListPosh.size () && (line = serviceListPosh.get (i).toString ()) != null; i++){
                bwP.write (line);
                bwP.newLine ();
            }
            if (serviceListPosh.size () != 0){
                bwP.newLine ();
                bwP.flush ();
            }


            BufferedWriter bwG = new BufferedWriter (new FileWriter ("files\\output.txt", true));
            for (int i = 0; i < serviceListGrotty.size () && (line = serviceListGrotty.get (
                    i).toString ()) != null; i++){
                bwG.write (line);
                bwG.newLine ();
            }
            bwG.flush ();
        } catch (IOException exc){
            System.out.println ("IO Exception");
        }
    }
}