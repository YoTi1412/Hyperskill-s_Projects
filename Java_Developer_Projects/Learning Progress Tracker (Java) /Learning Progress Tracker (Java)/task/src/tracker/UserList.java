package tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class UserList {
    //List<User> listOfUsers = new ArrayList<User>();
    LinkedHashMap<Integer, User> listOfUsers = new LinkedHashMap<>();
    Integer totalJava = 600;
    Integer totalDB =480;
    Integer totalDSA = 400;
    Integer totalSpring=550;

    boolean eMailAlreadyTaken( User user){
        if (listOfUsers.get(user.hashCode())!=null)
            return true;
        else return false;
    }

    HashMap<String, Integer> getUserActivity(){
        HashMap<String, Integer> activity = new HashMap<String, Integer>();
        Integer javaActivity =-1;
        Integer dbActivity =-1;
        Integer dsaActivity =-1;
        Integer springActivity =-1;

        for(var u : listOfUsers.entrySet()){
            javaActivity += u.getValue().javaPoints.size();
            dbActivity += u.getValue().dBPoints.size();
            dsaActivity += u.getValue().dSAPoints.size();
            springActivity += u.getValue().springPoints.size();
        }
        activity.put("Java",javaActivity);
        activity.put("Databases",dbActivity);
        activity.put("DSA",dsaActivity);
        activity.put("Spring",springActivity);
        return activity;
    }

    HashMap<String, Integer> getCoursePopularity(){
        HashMap<String, Integer> popularity = new HashMap<String, Integer>();
        Integer javaPopularity =0;
        Integer dbPopularity =0;
        Integer dsaPopularity =0;
        Integer springPopularity =0;

        for(var u : listOfUsers.entrySet()){
            if(u.getValue().javaPoints.size()>0) javaPopularity += 1;
            if(u.getValue().dBPoints.size()>0) dbPopularity += 1;
            if(u.getValue().dSAPoints.size()>0) dsaPopularity += 1;
            if( u.getValue().springPoints.size()>0) springPopularity += 1;
        }
        popularity.put("Java",javaPopularity);
        popularity.put("Databases",dbPopularity);
        popularity.put("DSA",dsaPopularity);
        popularity.put("Spring",springPopularity);
        return popularity;
    }

    HashMap<String, Double> getCourseEasiestHardest(){
        HashMap<String, Double> avg = new HashMap<String, Double>();
        Double javaAvg =0.0;
        Double dbAvg =0.0;
        Double dsaAvg =0.0;
        Double springAvg =0.0;
        int javaCounter = 0;
        int dbCounter =0;
        int dsaCounter = 0;
        int springCounter = 0;

        for(var u : listOfUsers.entrySet()){
            if(u.getValue().javaPoints.size()>0) {
                javaAvg += u.getValue().avg(u.getValue().javaPoints);
                javaCounter++;
            }
            if(u.getValue().dBPoints.size()>0) {
                dbAvg += u.getValue().avg(u.getValue().dBPoints);
                dbCounter++;
            }
            if(u.getValue().dSAPoints.size()>0) {
                dsaAvg += u.getValue().avg(u.getValue().dSAPoints);
                dsaCounter++;
            }
            if( u.getValue().springPoints.size()>0) {
                springAvg += u.getValue().avg(u.getValue().springPoints);
                springCounter++;
            }
        }
        avg.put("Java",javaAvg/javaCounter);
        avg.put("Databases",dbAvg/dbCounter);
        avg.put("DSA",dsaAvg/dsaCounter);
        avg.put("Spring",springAvg/springCounter);
        return avg;
    }
    String getEasiest(){
        HashMap<String, Double> list =getCourseEasiestHardest();
        String highest = "NA";
        Double highestI = 0.0;
        for(var i : list.entrySet()){
            if (i.getValue()>=highestI){

                if (i.getValue() == highestI) {
                    highest += " " + i.getKey();
                }
                else {
                    highest =  i.getKey();
                    highestI = i.getValue();
                }
            }

        }
        if (highestI == 0.0) highest ="n/a";
        return highest;
    }
    String getHardest(){
        HashMap<String, Double> list =getCourseEasiestHardest();
        String lowsest = "NA";
        Double lowestI = 0.0;
        for(var i : list.entrySet()){
            if (i.getValue()<=lowestI || lowestI == 0.0){

                if (i.getValue() == lowestI ) {
                    lowsest += " " + i.getKey();
                }
                else if(!i.getValue().isNaN()) {
                    lowsest =  i.getKey();
                    lowestI = i.getValue();
                }
            }
        }
        if (lowestI == 0.0) lowsest ="n/a";
        return lowsest;
    }

    String getMostPopular(){
        HashMap<String, Integer> list =getCoursePopularity();
        String highest = "NA";
        int highestI = 0;
        for(var i : list.entrySet()){
            if (i.getValue()>=highestI){

                if (i.getValue() == highestI) {
                    highest += " " + i.getKey();
                }
                else {
                    highest =  i.getKey();
                    highestI = i.getValue();
                }
            }
        }
        if (highestI == 0.0) highest ="n/a";
        return highest;

    }

    String getLeastPopular(){
        HashMap<String, Integer> list =getCoursePopularity();
        String lowsest = "NA";
        Integer lowestI = 20000000;
        for(var i : list.entrySet()){
            if (i.getValue()<=lowestI ){

                if (i.getValue() == lowestI || lowestI == 0) {
                    lowsest += " " + i.getKey();
                }
                else if(i.getValue()!= -1){
                    lowsest =  i.getKey();
                    lowestI = i.getValue();
                }
            }
        }
        if (lowestI == 0) lowsest ="n/a";
        if (lowsest.equals(getMostPopular())) lowsest ="n/a";
        return lowsest;
    }

    String getLowesttActivity(){
        HashMap<String, Integer> list =getUserActivity();
        String lowsest = "NA";
        Integer lowestI = 0;
        for(var i : list.entrySet()){
            if (i.getValue()<=lowestI || lowestI == 0){

                if (i.getValue() == lowestI ) {
                    lowsest += " " + i.getKey();
                }
                else if(i.getValue()!= -1) {
                    lowsest =  i.getKey();
                    lowestI = i.getValue();
                }
            }
        }
        if (lowestI == 0) lowsest ="n/a";
        if (lowsest.equals(getHighestActivity())) lowsest ="n/a";
        return lowsest;
    }

    String getHighestActivity(){
        HashMap<String, Integer> list =getUserActivity();
        String highest = "NA";
        int highestI = 0;
        for(var i : list.entrySet()){
            if (i.getValue()>=highestI){

                if (i.getValue() == highestI) {
                    highest += " " + i.getKey();
                }
                else {
                    highest =  i.getKey();
                    highestI = i.getValue();
                }
            }
        }
        if (highestI == 0) highest ="n/a";
        return highest;

    }



    User getTopJava(){
        User winner = new User("NA","Na","NA");
        for(var i : listOfUsers.entrySet()){

            int highest=0;
            if(i.getValue().getTotalJava()>highest) winner = i.getValue();
        }
        return winner;
    }

    User getTopDB(){
        User winner = new User("NA","Na","NA");
        for(var i : listOfUsers.entrySet()){

            int highest=0;
            if(i.getValue().getTotalDB()>highest) winner = i.getValue();
        }
        return winner;
    }

    User getTopDSA(){
        User winner = new User("NA","Na","NA");
        for(var i : listOfUsers.entrySet()){

            int highest=0;
            if(i.getValue().getTotalDSA()>highest) winner = i.getValue();
        }
        return winner;
    }

    User getTopSpring(){
        User winner = new User("NA","Na","NA");
        for(var i : listOfUsers.entrySet()){

            int highest=0;
            if(i.getValue().getTotalSpring()>highest) winner = i.getValue();
        }
        return winner;
    }

    List<UserInTopList> getCourseStatistics(String s){

        List<UserInTopList> statisticList= new ArrayList<>();
        BigDecimal bdRounded = new BigDecimal(0.0);
        double completion = 0.0;
        switch (s) {
            case "java":
                BigDecimal factor = new BigDecimal((double)100 / totalJava);
                for (var i : listOfUsers.entrySet()) {
                    BigDecimal totalJava = new BigDecimal(i.getValue().getTotalJava());
                    bdRounded = factor.multiply(totalJava).setScale(1, RoundingMode.HALF_UP);
                    UserInTopList user = new UserInTopList(i.getKey(), i.getValue().getTotalJava(), bdRounded.toString());
                    if(i.getValue().getTotalJava()!=0)statisticList.add(user);
                }

                break;
            case "dsa":
                BigDecimal factorDSA = new BigDecimal((double)100 / totalDSA);
                for (var i : listOfUsers.entrySet()) {
                    BigDecimal totalDSA = new BigDecimal(i.getValue().getTotalDSA());
                    bdRounded = factorDSA.multiply(totalDSA).setScale(1, RoundingMode.HALF_UP);
                    UserInTopList user = new UserInTopList(i.getKey(), i.getValue().getTotalDSA(), bdRounded.toString());
                    if(i.getValue().getTotalDSA()!=0)statisticList.add(user);
                }

                break;
            case "databases":
                BigDecimal factorDB = new BigDecimal((double)100 / totalDB);
                for (var i : listOfUsers.entrySet()) {
                    BigDecimal totalDB = new BigDecimal(i.getValue().getTotalDB());
                    bdRounded = factorDB.multiply(totalDB).setScale(1, RoundingMode.HALF_UP);
                    UserInTopList user = new UserInTopList(i.getKey(), i.getValue().getTotalDB(), bdRounded.toString());
                    if(i.getValue().getTotalDB()!=0)statisticList.add(user);
                }

                break;
            case "spring":
                BigDecimal factorSpring = new BigDecimal((double)100 / totalSpring);
                for (var i : listOfUsers.entrySet()) {
                    BigDecimal totalSpring = new BigDecimal(i.getValue().getTotalSpring());
                    bdRounded = factorSpring.multiply(totalSpring).setScale(1, RoundingMode.HALF_UP);
                    UserInTopList user = new UserInTopList(i.getKey(), i.getValue().getTotalSpring(), bdRounded.toString());
                    if(i.getValue().getTotalSpring()!=0)statisticList.add(user);
                }

                break;
            default:

                break;
        }
        statisticList.sort(new UserInTopListComparator());
        return statisticList;
    }

    List<completionPair> getCompleted(){
        List<completionPair> completed= new ArrayList<>();
        for (var u : listOfUsers.entrySet()){
            if(u.getValue().getTotalJava()>=totalJava && u.getValue().notifiedJava==false) {
                completed.add(new completionPair(u.getValue(), "Java"));
                u.getValue().notifiedJava=true;
            }
            if(u.getValue().getTotalDB()>=totalDB && u.getValue().notifiedDB == false) {
                completed.add(new completionPair(u.getValue(), "Database"));
                u.getValue().notifiedDB = true;
            }
            if(u.getValue().getTotalDSA()>=totalDSA && u.getValue().notifiesDSA == false) {
                completed.add(new completionPair(u.getValue(), "DSA"));
                u.getValue().notifiesDSA = true;
            }
            if(u.getValue().getTotalDSA()>=totalSpring && u.getValue().notifiedSpring == false) {
                completed.add(new completionPair(u.getValue(), "Spring"));
                u.getValue().notifiedSpring = true;
            }
        }
        return completed;

    }

}

class completionPair{
    String course;
    User user;
    completionPair( User u,String s){
        this.course =s;
        this.user=u;
    }
}