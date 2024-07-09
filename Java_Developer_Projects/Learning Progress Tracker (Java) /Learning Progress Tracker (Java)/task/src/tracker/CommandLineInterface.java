package tracker;

import java.util.Scanner;

public class CommandLineInterface {
    UserList userList;
    public CommandLineInterface(){
        this.userList = new UserList();
    }

    public void welcomeUser(){
        System.out.println("Learning Progress Tracker");
    }
    public boolean readUserInput(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        boolean foundCommand = false;

        //Itterate over enums to find command
        for (Commands c: Commands.values()) {
            if (c.strCommand.equals(input)){
                c.command.mainFunction(this.userList);
                foundCommand= true;
            }
        }
        if(input.isBlank()&&foundCommand == false) System.out.println("No input.");
        else if(foundCommand == false && input.equals("back")) System.out.println("Enter 'exit' to exit the program.");
        else if(foundCommand == false) System.out.println("Error: unknown command!");
        return foundCommand;
    }

    public void runCLI(){
        welcomeUser();
        while (true){
            readUserInput();
        }
    }

    public void testSetup(){
        User user1 = new User("da", "da", "asd@asd.de");
        User user2 = new User("dsasd", "asdsfd", "asdasd@asd.de");
        User user3 = new User("dsasd", "asdsfd", "asgghjhjj@asd.de");
        User user4 = new User("dsasd", "asdsfd", "asgghjhjasgdsgj@asd.de");
        /*addPoints1(user1,1);
        addPoints1(user2,2);
        addPoints1(user3,3);
        addPoints1(user4,0);
        */
        addPoints2(user1);
        addPoints2(user2);
        addPoints2(user3);
        addPoints3(user4);
        userList.listOfUsers.put(user1.hashCode(),user1);
        System.out.println(user1.hashCode());
        userList.listOfUsers.put(user2.hashCode(),user2);
        System.out.println(user2.hashCode());
        userList.listOfUsers.put(user3.hashCode(),user3);
        System.out.println(user3.hashCode());
        userList.listOfUsers.put(user4.hashCode(),user4);
        System.out.println(user4.hashCode());
    }

    public void addPoints1(User user, int offset){
        for (int i =0; i<offset;i++){
            user.javaPoints.add(i+offset);
        }
        for (int i =0; i<offset+1;i++){
            user.dBPoints.add(i+offset);
        }
        for (int i =0; i<offset+2;i++){
            user.dSAPoints.add(i+offset);
        }
        for (int i =0; i<offset+3;i++){
            user.springPoints.add(i+offset);
        }
    }
    public void addPoints2(User user){
        user.javaPoints.add(5);
        user.dBPoints.add(4);
        user.dSAPoints.add(3);
        user.springPoints.add(2);
    }
    public void addPoints3(User user){
        user.javaPoints.add(600);
        user.dBPoints.add(400);
        user.dSAPoints.add(500);
        user.springPoints.add(500);
    }

}