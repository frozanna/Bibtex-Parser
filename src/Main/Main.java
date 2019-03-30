package Main;

import Entries.*;
import Show.*;
import Parser.*;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.ArrayList;

/**
 Main class.
**/

public class Main {
    /**
     * Main method. Evokes parser and show menu.
     * @param args                      string with path to file
     * @throws ParseException           if there is a problem with parsing arguments from command line
     */
    public static void main(String[] args) throws ParseException{
        try{
            System.out.println("");
            String path = null;
            String[] authors = null;
            String[] categories = null;

            Options options = new Options();
            Option optionFile = new Option("f", "file",true,"Path to file");
            optionFile.setArgs(1);
            options.addOption(optionFile);
            Option optionAuthors = new Option("a", "authors",true,"List of authors");
            optionAuthors.setArgs(Option.UNLIMITED_VALUES);
            options.addOption(optionAuthors);
            Option optionCat = new Option("c", "categories",true,"List of categories");
            optionCat.setArgs(Option.UNLIMITED_VALUES);
            options.addOption(optionCat);


            CommandLineParser parser = new DefaultParser();

            CommandLine cmd = parser.parse( options, args);

            if(cmd.hasOption("f")) {
                path = cmd.getOptionValues("f")[0];
            }else throw new org.apache.commons.cli.MissingArgumentException("file");
            if(cmd.hasOption("a")) {
                authors = cmd.getOptionValues("a");
            }
            if(cmd.hasOption("c")){
                categories = cmd.getOptionValues("c");
            }


            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            ArrayList<Entry> file= BibTexParser.parse(reader);
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
            ShowAll showAll= new ShowAll();
            showAll.accept(new ShowAllElementVisitor(file));
            //ShowAuthors showAuthors = new ShowAuthors();
            //showAuthors.accept(new ShowAuthorsElementVisitor(file));
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("");

            ShowAuthorEntries showAut = new ShowAuthorEntries();
            ShowCategory showCat= new ShowCategory();

            if(authors != null){
                for(String a : authors){
                    showAut.accept(new ShowAuthorEntryElementVisitor(file,a));
                }
                System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("");
            }

            if(categories != null){
                for(String c : categories){
                    showCat.accept(new ShowCategoryElementVisitor(file,c));
                }
            }

        }catch (FileNotFoundException e){
            System.out.println("Wrong path to file.");
        }catch (org.apache.commons.cli.MissingArgumentException e){
            System.out.println(e);
            System.out.println("");
            showHelp();
        }
    }

    /**
     * Method that show help if there is no arguments given.
     */
    public static void showHelp() {
        System.out.println("Options in command line:");
        System.out.println("-f,  --file         Path to file");
        System.out.println("-a,  --authors      List of authors, whose entries you want to see.");
        System.out.println("-c,  --categories   List of categories, which you want to see.");
        System.out.println("");
        System.out.println("Path to file is required!");
        System.out.println("Separate the next authors and categories by spaces.");
    }
}

