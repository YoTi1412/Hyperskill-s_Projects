package tracker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    String firstName;
    String lastName;
    String eMail;
    List<Integer> javaPoints = new ArrayList<>();
    List<Integer> dSAPoints= new ArrayList<>();
    List<Integer> dBPoints = new ArrayList<>();
    List<Integer> springPoints = new ArrayList<>();
    boolean notifiedJava =false;
    boolean notifiesDSA = false;
    boolean notifiedDB = false;
    boolean notifiedSpring = false;

    User(String firstName, String lastName, String eMail){

        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;


    }
    User(){

        this.firstName = "Empty";
        this.lastName = "Empty";
        this.eMail = "Empty";



    }
    boolean checkEMail(String eMail) {
        //Email pattern : Starts with word then @ then word than . then word
        String patternStr = "^[^@\\s]+@[^@\\s\\.]+\\.[^@\\.\\s]+$";


        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(eMail);
        return matcher.find();

    }
    //obsolete: Implemented new functions for each type
    boolean checkName(String name) {
        //Email pattern : Starts with word then @ then word than . then word
        String specialChar = "[~!@#$%^&*()_+{}\\[\\]:;,.<>/?]|\\d";
        String latinChar = "(?=\\pL)(?![a-zA-Z])";
        String firstAndLastChar = "^-|^'|-$|'$";

        Pattern specialCharPattern = Pattern.compile(specialChar);
        Pattern latinCharPattern = Pattern.compile(latinChar);
        Pattern firstAndLastCharPattern = Pattern.compile(firstAndLastChar);

        Matcher firstAndLastCharMatcher = firstAndLastCharPattern.matcher(name);
        Matcher specialCharMatcher = specialCharPattern.matcher(name);
        Matcher latinCharMatcher = latinCharPattern.matcher(name);

        boolean specialCharPatternB = specialCharMatcher.find();
        boolean firstAndLastCharB = firstAndLastCharMatcher.find();
        boolean latinCharB = latinCharMatcher.find();

        boolean isCorrect = !(specialCharPatternB || firstAndLastCharB || latinCharB);
        if(name.length() <=1) isCorrect = false;

        return isCorrect;

    }

    boolean checkRegRules(String[] in, String toCheck){
        boolean returnBool = false;
        for(int i =0; i<in.length; i++){
            Pattern inPattern = Pattern.compile(in[i]);
            Matcher inMatcher = inPattern.matcher(toCheck);
            if(inMatcher.find()) returnBool = true;
        }
        //returns true if no match to rule
        return !returnBool;
    }

    boolean checkFirstName (String name){
        String specialChar = "[~!@#$%^&*()_+{}\\[\\]:;,.<>/?]|[0-9]|(-'|'-|--|'')";
        String latinChar = "(?=\\pL)(?![a-zA-Z])";
        String firstAndLastChar = "^-|^'|-$|'$";
        String[] toCheckArray = {specialChar,latinChar,firstAndLastChar};
        boolean returnBool = (checkRegRules(toCheckArray, name) && name.length()!=1);
        return returnBool;
    }
    boolean checkLastName (String name){
        String specialChar = "[~!@#$%^&*()_+{}\\[\\]:;,.<>/?]|\\d|(-'|'-|--|'')";
        String latinChar = "(?=\\pL)(?![a-zA-Z])";
        String firstAndLastChar = "^-|^'|-$|'$";
        String[] toCheckArray = {specialChar,latinChar,firstAndLastChar};
        boolean returnBool = (checkRegRules(toCheckArray, name) && name.length()!=1);
        return returnBool;
    }
    String getLastName(String[] in){
        String lastName="";
        for (int i =1; i< in.length-1;i++){
            lastName+=" "+in[i];
        }
        lastName = lastName.substring(1);
        return lastName;
    }

    @Override
    public int hashCode() {
        int hash =0;
        for (int i =0; i< this.eMail.length();i++)
            hash += (int)eMail.charAt(i)%27;
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        /* Check this and other refer to the same object */
        if (this == other) {
            return true;
        }

        /* Check other is Person and not null */
        if (!(other instanceof User)) {
            return false;
        }

        User person = (User) other;

        /* Compare all required fields */
        return eMail == person.eMail;
    }

    public int getTotalJava(){
        int sum = 0;
        for (Integer i: javaPoints) {
            sum+=i;
        }
        return sum;
    }
    public int getTotalDB(){
        int sum = 0;
        for (Integer i: dBPoints) {
            sum+=i;
        }
        return sum;
    }

    public int getTotalDSA(){
        int sum = 0;
        for (Integer i: dSAPoints) {
            sum+=i;
        }
        return sum;
    }

    public int getTotalSpring(){
        int sum = 0;
        for (Integer i: springPoints) {
            sum+=i;
        }
        return sum;
    }

    public double avg(List<Integer> a){
        int sum = 0;
        for (Integer i: a) {
            sum+=i;
        }
        if(a.size()!=0)return (double) sum/a.size();
        else return 0;
    }

}
class UserInTopList{
    Integer id;
    Integer points;
    String completion;

    UserInTopList(int id, int points, String completion){
        this.id = id;
        this.points = points;
        this.completion = completion;
    }
}
class UserInTopListComparator implements Comparator<UserInTopList> {

    @Override
    public int compare(UserInTopList userInTopList, UserInTopList t1) {
        return (userInTopList.points.compareTo(t1.points) == 0) ? userInTopList.id.compareTo(t1.id) : userInTopList.points.compareTo(t1.points)*-1;
    }
}