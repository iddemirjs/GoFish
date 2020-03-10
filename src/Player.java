import javax.sound.midi.SysexMessage;
import java.util.Random;
import java.util.Scanner;

public class Player {

    private String name;

    private Stack cards;

    private int book=0;

    private String userType="Computer";

    public Player(String name, String userType) {
        this.name = name;
        this.userType = userType;
        this.cards=new Stack(24);
    }

    public String getCardsStr(){
        Stack tempStack=new Stack(this.cards.Size());
        String strStack="";
        while(!this.cards.isEmpty()){
            int tempValue=(int)this.cards.Pop();
            tempStack.Push(tempValue);
            strStack+=tempValue+ ", ";
        }
        while (!tempStack.isEmpty()){
            this.cards.Push(tempStack.Pop());
        }
        if (strStack.length()>2) strStack=strStack.substring(0,strStack.length()-2);
        return strStack;
    }

    public int makeCardRequest(){
        if (userType.equalsIgnoreCase("Computer")){
            Random random=new Random();
            int randomCardIndex=random.nextInt(this.cards.Size());
            Stack tempStack=new Stack(randomCardIndex);
            int choicedNumber=0;
            for (int i = 0; i <= randomCardIndex; i++) {
                if (randomCardIndex==i){
                    choicedNumber=(int) this.cards.Peek();
                }else{
                    tempStack.Push(this.cards.Pop());
                }
            }
            while (!tempStack.isEmpty()){
                this.cards.Push(tempStack.Pop());
            }

            System.out.println("( Machine wanted ) "+choicedNumber);
            return choicedNumber;
        }else{
            Scanner scanner=new Scanner(System.in);
            System.out.print(this.name+" asks ?: ");
            String request=scanner.nextLine();
            while (true){
                try {
                    int number=Integer.parseInt(request);
                    if (number<1 || number>6){
                        System.out.println("Your input must be valid integer.");
                        System.out.print("Again... "+this.name+" ask?: ");
                        request=scanner.nextLine();
                        continue;
                    }
                    if (detectCardSize(number)==0){
                        System.out.println("Your don't have this number. Because of that you can't ask it.");
                        System.out.print("Again... "+this.name+" ask?: ");
                        request=scanner.nextLine();
                        continue;
                    }
                }catch (NumberFormatException n){
                    System.out.println("Your input must be valid integer.");
                    System.out.print("Again... "+this.name+" ask?: ");
                    request=scanner.nextLine();
                    continue;
                }
                return Integer.parseInt(request);
            }
        }
    }

    public void defineFourthCards(){
        for (int i = 0; i < cards.Size() ; i++) {
            Stack tempStack=new Stack(cards.Size());
            int controlCard=0;
            for (int j = 0; j <= i ; j++) {
                if (i==j){
                    controlCard= (int) cards.Peek();
                }else{
                    tempStack.Push(cards.Pop());
                }
            }
            while (!tempStack.isEmpty()){
                this.cards.Push(tempStack.Pop());
            }
            int cardFour=detectCardSize(controlCard);
            if (cardFour==4){
                this.book++;
                System.out.println(this.name+" threw four card : "+controlCard+", "+controlCard+", "+controlCard+", "+controlCard);
                this.removeCard(controlCard);
            }
        }
    }

    public int detectCardSize(int cardNumber){
        int cardCounter=0;
        Stack tempStack=new Stack(this.cards.Size());
        while(!this.cards.isEmpty()){
            if ((int)this.cards.Peek()==cardNumber){
                cardCounter++;
            }
            tempStack.Push(this.cards.Pop());
        }
        while (!tempStack.isEmpty()){
            this.cards.Push(tempStack.Pop());
        }
        return cardCounter;
    }

    public void removeCard(int cardNumber){
        Stack tempStack=new Stack(this.cards.Size());
        while(!this.cards.isEmpty()){
            if ((int)this.cards.Peek()==cardNumber){
                this.cards.Pop();
            }else{
                tempStack.Push(this.cards.Pop());
            }
        }
        while (!tempStack.isEmpty()){
            this.cards.Push(tempStack.Pop());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stack getCards() {
        return cards;
    }

    public void setCards(Stack cards) {
        this.cards = cards;
    }

    public int getBook() {
        return book;
    }

    public void increaseBook(){
        this.book++;
    }

    public void setBook(int book) {
        this.book = book;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
