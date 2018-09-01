public class Controller{
    public static void main (String[] args){
        TimeTableCreator table = new TimeTableCreator ();
        table.readInput ("input.txt");
        table.createTimeTable ();
        table.writeTable ();
    }
}
