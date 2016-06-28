/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mybianyi;

/**
 *
 * @author Administrator
 */
public class DFAPoint {
    int currentInt;
    int[] nextInt=new int[10];
    String[] road=new String[10];
    String collection;
    int i=0;
    public DFAPoint() {
    }
   
    
    public void setPoint(int currentInt,String road,int nextInt){
        this.currentInt = currentInt;
        this.road[i] = road;
        this.nextInt[i++] = nextInt;
    }
    
    public String printPoint(){
        String str="";
        for(int j=0;j<i;j++){
            if(road[j]!=null){
                str+=currentInt+"("+collection+")"+"road:"+road[j]+"→"+nextInt[j]+"\n";
            }
        }
        return str;
    }
    
    
    public String getCollection(){
        return collection;
    }

    public void setCurrentInt(int currentInt) {
        this.currentInt = currentInt;
    }

    public void setNextInt(int nextInt) {
        this.nextInt[i++] = nextInt;
    }

    public void setRoad(String road) {
        System.out.println(collection+"当前的i:"+i);
        this.road[i] = road;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public int getCurrentInt() {
        return currentInt;
    }

    public int[] getNextInt() {
        return nextInt;
    }

    public String[] getRoad() {
        return road;
    }
    
    public void clean(){
        boolean add=true;
        int[] b = new int[nextInt.length];
        String[] c=new String[nextInt.length];
        for (int i = 0; i < nextInt.length; i++){
            b[i] = nextInt[i];
            c[i] = road[i];
        }
        for (int i = 0; i < nextInt.length; i++){
            nextInt[i] = 0;
            road[i]="";
        }
        int index=1,k;
        nextInt[0] = b[0];
        road[0]=c[0];
        for (int i = 1; i < b.length; i++)
        {
            k=b[i];
            for (int j = 0; j < index; j++){
                System.out.println(k+nextInt[index]);
                if (nextInt[j] == k)
                {
                    add=false;
                    break;
                }
            }
            if(add){
                nextInt[index]=k;
                road[index]=c[i];
                index++;
            }
        }
        i=index;
    }
    
    
    public int findRoad(String a){
        int q =100;
        for(int i=0;i<road.length;i++){
            if(a.equals(road[i])){
                q=i;
                break;
            }
        }
        return q;
    }
    
    public int findInt(int a){
        return nextInt[a];
    }
    
    public boolean isEnd(){
        for(int a=0;a<collection.length();a++){
            if(collection.charAt(a)=='Z'){
                return true;
            }
        }
        return false;
    }
}
