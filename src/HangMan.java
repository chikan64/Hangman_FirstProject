package TicToe;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class HangMan {
    public static String[] symbols;
    public static List<String> wordsAsList;
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Если желаете начать новую игру введите старт, а если не желаете - выход");
        String startOrExit = scanner.nextLine();
        if(startOrExit.equalsIgnoreCase("старт")){
            try {
                initialize();

            } catch (IOException e) {
                System.out.println("Причина ошибки"+e.getCause());
            }
            symbolsLoading();
            start();
        } if(startOrExit.equalsIgnoreCase("выход")){
            System.exit(1);
        }
        System.out.println("В следующий раз введите указанные команды");
        System.exit(1);
    }

    static void start (){

        List<String>chosenWord=wordSplitting();
        List<String> playground = new ArrayList<>();

        for (int i = 0; i < chosenWord.size(); i++)
            playground.add(" ___ ");
        insertAndCheck(chosenWord,playground);
    }

    static void insertAndCheck (List<String> chosenWord,List<String> playground) {
        int errorCounter=0;
        int guessedLetters=0;
        HashSet<String> writtenWord = new HashSet<>();
        int i=0;
        String letterOfChoice=null;
        System.out.println(playground);
        while ((errorCounter<3)&&(guessedLetters!=chosenWord.size())) {
            System.out.println("Введите строчную букву");
            letterOfChoice = scanner.nextLine();
            //начало проверок
            if (letterCorectness(letterOfChoice)) continue;
            if (letterRepeating(letterOfChoice,writtenWord,i)) continue;
            else writtenWord.add(letterOfChoice);
            errorCounter = (letterPresence(chosenWord,letterOfChoice,errorCounter));
            //конец проверок
            while (chosenWord.contains(letterOfChoice)){
                playground.set(chosenWord.indexOf(letterOfChoice),letterOfChoice);
                chosenWord.set(chosenWord.indexOf(letterOfChoice)," ");
                guessedLetters++;
            }

            i++;
            System.out.println(playground);

        }
        if (errorCounter>=3) System.out.println("Вы проиграли. Ну ничего страшного");
        else System.out.println("Вы выйграли. Поздравляю!");
        main(null);
    }
    static void initialize () throws IOException {//можно работать с библиотекой

        try{
            wordsAsList =  Files.readAllLines(Path.of("log.txt"));
            String [] words = wordsAsList.getFirst().split(" ");
            wordsAsList.clear();
            wordsAsList.addAll(Arrays.asList(words));
        }
        catch(IOException e){
            System.out.println("Какая то проблема с библиотекой. Не могу загрузить слова");
        }
        catch (SecurityException e){

            System.out.println("Ошибка доступа к файлу словаря");
        }

    }
    static List<String> wordSplitting() {
        int x = (int) (wordsAsList.size() * Math.random()); //задаем случайное число от 0 до 500;
        String[] word = wordsAsList.get(x).split("");
        List<String> splittedWord = new ArrayList<>(Arrays.asList(word));

        return splittedWord;
    }
    static void symbolsLoading(){
        symbols= new String[4];
        symbols[0]=null;
        symbols[1] = " ___________.._______\n" +
                "| .__________))______|\n" +
                "| | / /      ||\n" +
                "| |/ /       ||\n" +
                "| | /        ||.-''.\n" +
                "| |/         |/  _  \\\n" +
                "| |          ||  `/,|\n" +
                "| |          (\\\\`_.'\n" +
                "| |         .-`--'.";
        symbols[2] = " ___________.._______\n" +
                "| .__________))______|\n" +
                "| | / /      ||\n" +
                "| |/ /       ||\n" +
                "| | /        ||.-''.\n" +
                "| |/         |/  _  \\\n" +
                "| |          ||  `/,|\n" +
                "| |          (\\\\`_.'\n" +
                "| |         .-`--'.| |        /Y . . Y\\\n" +
                "| |       // |   | \\\\\n" +
                "| |      //  | . |  \\\\\n" +
                "| |     ')   |   |   (`";
        symbols[3] = " ___________.._______\n" +
                "| .__________))______|\n" +
                "| | / /      ||\n" +
                "| |/ /       ||\n" +
                "| | /        ||.-''.\n" +
                "| |/         |/  _  \\\n" +
                "| |          ||  `/,|\n" +
                "| |          (\\\\`_.'\n" +
                "| |         .-`--'.| |        \\\n" +
                "| |       // |   | \\\\\n" +
                "| |      //  | . |  \\\\\n" +
                "| |     ')   |   |   (`| |          ||'||\n" +
                "| |          || ||\n" +
                "| |          || ||\n" +
                "| |          || ||\n" +
                "| |         / | | \\\n" +
                "\"\"\"\"\"\"\"\"\"\"|_`-' `-' |\"\"\"|\n" +
                "|\"|\"\"\"\"\"\"\"\\ \\       '\"|\"|\n" +
                "| |        \\ \\        | |\n" +
                ": :         \\ \\       : :  \n" +
                ". .          `'       . .\n       THE END OF THE GAME";
    }
    public static boolean letterCorectness(String letterOfChoice) {
        boolean a =false;
        if ((isNumeric(letterOfChoice))||(Character.isUpperCase(letterOfChoice.charAt(0)))) {
            System.out.println("Введите строчную букву!");
            a = true;
        }
        return a;
    }
    public static boolean letterRepeating(String letterOfChoice,Set<String> writtenWord,int i) {
        boolean a =false;
        if ((writtenWord.contains(letterOfChoice))&&(i>0)) {
            System.out.println("Вы уже вводили эту букву");
            a = true;
        }
        return a;
    }
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static int letterPresence(List<String> chosenWord, String letterOfChoice, int errorCounter) {

        if(!chosenWord.contains(letterOfChoice)){
            errorCounter++;
            System.out.println("Такой буквы нет. Количество ошибок: "+errorCounter);
            System.out.println(symbols[errorCounter]);

        }
        return errorCounter;
    }
}


