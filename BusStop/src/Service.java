public class Service{
    public Time depTime;
    public Time arrTime;

    public String companyName;
    public int departureHr;
    public int departureMin;
    public int arrivalHr;
    public int arrivalMin;


    public Service (String companyName, int departureHr, int departureMin, int arrivalHr, int arrivalMin){
        depTime = new Time (departureHr, departureMin);
        arrTime = new Time (arrivalHr, arrivalMin);
        this.companyName = companyName;
        this.departureHr = departureHr;
        this.departureMin = departureMin;
        this.arrivalHr = arrivalHr;
        this.arrivalMin = arrivalMin;
    }

    @Override
    public String toString (){
        String depHr = departureHr < 10 ? "0" + departureHr : departureHr + "";
        String depMin = departureMin < 10 ? "0" + departureMin : departureMin + "";
        String arrHr = arrivalHr < 10 ? "0" + arrivalHr : arrivalHr != 24 ? arrivalHr + "" : "00";
        String arrMin = arrivalMin < 10 ? "0" + arrivalMin : arrivalMin + "";

        return companyName + " " + depHr + ":" + depMin + " " + arrHr + ":" + arrMin;
    }
}
