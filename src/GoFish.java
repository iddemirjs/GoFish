import java.util.Random;
import java.util.Scanner;

public class GoFish {
    private Stack table;
    private Scanner scanner;
    private Player playerOne;
    private Player playerTwo;


    GoFish(){
        scanner=new Scanner(System.in);
        System.out.print("Please enter your name:");
        String playerName= scanner.nextLine();
        Player p1=new Player(playerName,"human");
        Player p2=new Player("Computer","Computer");
        this.playerOne=p1;
        this.playerTwo=p2;
        this.table=new Stack(24);
        this.startCardDistributing();
        this.writeCurrentData();
        this.playerOne.defineFourthCards();
        this.playerTwo.defineFourthCards();
        this.startCardTrade();

    }

    private void startCardDistributing() {
        Integer[] tempNumberArr = new Integer[24];
        int index = 0;
        for (int i = 1; i <= 6; i++) {
            for (int j = 0; j < 4; j++) {
                tempNumberArr[index] = i;
                index++;
            }
        }
        Random random = new Random();
        int randomIndex;
        int addedCardSizeP1=0,addedCardSizeP2=0,addedCardSizeTable=0;
        boolean isCompletedDistributing=false;
        while (!isCompletedDistributing){
            randomIndex = random.nextInt(24);
            if (tempNumberArr[randomIndex] != null && addedCardSizeP1 !=7 ) {
                this.playerOne.getCards().Push(tempNumberArr[randomIndex]);
                tempNumberArr[randomIndex]=null;
                addedCardSizeP1++;
            }
            randomIndex = random.nextInt(24);
            if (tempNumberArr[randomIndex] != null && addedCardSizeP2 !=7) {
                this.playerTwo.getCards().Push(tempNumberArr[randomIndex]);
                tempNumberArr[randomIndex]=null;
                addedCardSizeP2++;
            }
            randomIndex = random.nextInt(24);
            if (tempNumberArr[randomIndex] != null && addedCardSizeTable !=10) {
                this.table.Push(tempNumberArr[randomIndex]);
                tempNumberArr[randomIndex]=null;
                addedCardSizeTable++;
            }
            if (addedCardSizeP1==7 && addedCardSizeP2==7 && addedCardSizeTable==10){
                isCompletedDistributing=true;
            }
        }
        System.out.println("ok");
    }

    private void writeCurrentData(){
        System.out.println(this.playerOne.getName()+
                " : "+
                this.playerOne.getCardsStr()+
                "   Book: "+this.playerOne.getBook()+
                "   Table: "+this.getTableStr());
        System.out.println(this.playerTwo.getName()+
                " : "+
                this.playerTwo.getCardsStr()+
                "   Book: "+this.playerTwo.getBook());
    }

    private String getTableStr(){
        Stack tempStack=new Stack(this.table.Size());
        String strStack="";
        while(!this.table.isEmpty()){
            int tempValue=(int)this.table.Pop();
            tempStack.Push(tempValue);
            strStack+=tempValue+ ", ";
        }
        while (!tempStack.isEmpty()){
            this.table.Push(tempStack.Pop());
        }
        return strStack.substring(0,strStack.length()-2);
    }

    private void startCardTrade() {
        int currentUser=0;
        while (!(this.playerOne.getCards().isEmpty()) && !(this.playerTwo.getCards().isEmpty())){
            if (currentUser==0){
                int requestingInt=playerOne.makeCardRequest();
                int requestingIntSize=this.playerTwo.detectCardSize(requestingInt);
                if (requestingIntSize>0){
                    for (int i = 0; i <requestingIntSize ; i++) {
                        this.playerOne.getCards().Push(requestingInt);
                    }
                    this.playerTwo.removeCard(requestingInt);
                    this.playerOne.detectCardSize(requestingInt);
                }else{
                    currentUser=1;
                    System.out.println("Go Fish.");
                    requestingInt=(int)this.table.Pop();
                    this.playerOne.getCards().Push(requestingInt);
                }
                if (this.playerOne.detectCardSize(requestingInt)==4){
                    System.out.println(this.playerOne.getName()+" threw four card : "+requestingInt+", "+requestingInt+", "+requestingInt+", "+requestingInt);
                    this.playerOne.removeCard(requestingInt);
                    this.playerOne.increaseBook();
                }

            }else{
                int requestingInt=playerTwo.makeCardRequest();
                int requestingIntSize=this.playerOne.detectCardSize(requestingInt);
                if (requestingIntSize>0){
                    for (int i = 0; i <requestingIntSize ; i++) {
                        this.playerTwo.getCards().Push(requestingInt);
                    }
                    this.playerOne.removeCard(requestingInt);
                }else{
                    currentUser=0;
                    System.out.println("Go Fish.");
                    requestingInt=(int) this.table.Pop();
                    this.playerTwo.getCards().Push(requestingInt);

                }
                if (this.playerTwo.detectCardSize(requestingInt)==4){
                    System.out.println(this.playerTwo.getName()+" threw four card : "+requestingInt+", "+requestingInt+", "+requestingInt+", "+requestingInt);
                    this.playerTwo.removeCard(requestingInt);
                    this.playerTwo.increaseBook();
                }
            }
            writeCurrentData();
        }
        if (this.playerTwo.getBook()>this.playerOne.getBook()){
            System.out.println("\u001B[107m"+playerTwo.getName()+" is winner.\u001B[0m");
        }else if(this.playerTwo.getBook()<this.playerOne.getBook()){
            System.out.println("\u001B[107m"+this.playerOne.getName()+" is winner.\u001B[0m");
        }else{
            System.out.println("\u001B[107m"+"This game don't has a winner.\u001B[0m");
        }
    }
}
