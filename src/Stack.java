public class Stack {

    private Object[] elements;
    private int top;

    Stack(int capacity){
        this.elements=new Object[capacity];
        this.top=-1;
    }
    public boolean isEmpty(){
        if (top==-1){
            return true;
        }else{
            return false;
        }
    }
    public boolean isFull(){
        if (top+1==this.elements.length){
            return true;
        }else{
            return false;
        }
    }
    public Object Pop(){
        if (isEmpty()){
            return false;
        }else{
            Object retData=this.elements[top];
            this.elements[top]=null;
            this.top--;
            return retData;
        }
    }
    public Object Peek(){
        if (isEmpty()){
            return false;
        }else{
            return this.elements[top];
        }
    }

    public int Size(){
        return top+1;
    }

    public boolean Push(Object newData){
        if(isFull()){
            return false;
        }else{
            top++;
            this.elements[top]=newData;
            return true;
        }
    }
}
