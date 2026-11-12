package myLib;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

import static java.lang.IO.println;

public class UserMethods {
    public void myPrinter(String message){
        println(message);
    }

    public Integer requestInteger(String message){
        boolean integerOK = false;
        Integer integerUser = 0;
        Scanner sc = new Scanner(System.in);
        myPrinter(message);
        while(!integerOK) {
            try {
                integerUser = sc.nextInt();
                integerOK = true;
            } catch (InputMismatchException e) {
                myPrinter("Wrong integer. \nIntroduce an integer:");
                sc = null;
                sc = new Scanner(System.in);
            }
        }
        return integerUser;
    }
    public Path requestPath(String message){
        boolean pathOK = false;
        Path pathUser = Path.of("");
        Scanner sc = new Scanner(System.in);
        myPrinter(message);
        while(!pathOK) {
            if (Files.exists(pathUser)) {
                    pathUser = Path.of(sc.nextLine());
                    if(Files.isReadable(pathUser)) {
                        pathOK = true;
                    }else{
                        myPrinter("Permission denied to access the file.");
                    }
            }else{
                myPrinter("Wrong path. The file does not exist.");
            }
        }
        return pathUser;
    }

    public boolean checkPath(String userPath){
        boolean pathOk;
        try {
            Path.of(userPath);
            pathOk = true;
        }
        catch (InvalidPathException e) {
            pathOk=false;
        }
        return pathOk;
    }

    public Path pathToCreate (){
        Path p;
        Scanner sc = new Scanner(System.in);
        myPrinter("Introduce the path to create: ");
        String userPath = sc.nextLine();
        while(!checkPath(userPath)){
            myPrinter("The path is incorrect.");
            userPath = sc.nextLine();
        }
        p = Path.of(userPath);
        return p;
    }

    public Path fileToRead(String message){
        Path p;
        boolean pOK = false;
        Scanner sc = new Scanner(System.in);
        String userPath="";
        while(!pOK){
            myPrinter(message);
            userPath = sc.nextLine();
            if (!checkPath(userPath)) {
                myPrinter("Illegal characters.");
            }else if(!Files.exists(Path.of(userPath))) {
                myPrinter("The path does not exist.");
            }else if(Files.isDirectory(Path.of(userPath))){
                myPrinter("This is a directory, not a file.");
            }else if(Files.isReadable(Path.of(userPath))){
                pOK= true;
            }else{
                myPrinter("Unable to read the file.");
            }
        }
        p = Path.of(userPath);
        return p;
    }

    public Path fileToWrite(String message){
        Path p;
        boolean pOK = false;
        Scanner sc = new Scanner(System.in);
        String userPath="";
        while(!pOK){
            myPrinter(message);
            userPath = sc.nextLine();
            if (!checkPath(userPath)) {
                myPrinter("Illegal characters.");
            }else if(!Files.exists(Path.of(userPath))) {
                myPrinter("The path does not exist.");
            }else if(Files.isDirectory(Path.of(userPath))){
                myPrinter("This is a directory, not a file.");
            }else if(Files.isWritable(Path.of(userPath))){
                pOK= true;
            }else{
                myPrinter("Unable to read the file.");
            }
        }
        p = Path.of(userPath);
        return p;
    }

    public String requestStringRegex(final String message, String regex) {
        Scanner sc = new Scanner(System.in);
        String dato = "";
        boolean error = true;
        while (error) {
            try {
                dato = "";
                myPrinter(message);
                dato = sc.nextLine();
                if (regex == null) {
                    error = false;
                } else {
                    error = !dato.matches(regex);
                    if (error) {
                        myPrinter("The String does not match the regex. ");
                    }
                }
            } catch (PatternSyntaxException e) {
                myPrinter("Wrong regex.");
            }
        }
        return dato;
    }

    public String requestString(String message) {
        myPrinter(message);
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public Date requestDate( String message){
        myPrinter(message);
        Date date =  null;
        boolean error = true;
        Scanner sc = new Scanner(System.in);
        while (error){
            String dateString = sc.nextLine();
            try {
                date = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
                error = false;
            } catch (ParseException e) {
                myPrinter("Wrong format, please keep the correct format: dd-MM-yyyy: \n");
            }
        }
        return date;
    }
}
