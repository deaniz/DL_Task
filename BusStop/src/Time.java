public class Time{
    public static final int MIN_IN_HR = 60;

    public int hr;
    public int min;

    public Time (int hr, int min){
        this.hr = hr;
        this.min = min;
    }

    public static int compare (Time t1, Time t2){
        if (t1.hr == t2.hr && t1.min == t2.min){
            return 0;
        }
        if ((t1.hr - t2.hr) * MIN_IN_HR + t1.min - t2.min > 0){
            return 1;
        }
        else{
            return -1;
        }
    }
}
