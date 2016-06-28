/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mybianyi;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class FLSet {//存放非终结符的FIRSTVT和LASTVT
    private String nEndChar;  //非终结符
    List<String> firstvt=new ArrayList<String>();
    List<String> lastvt=new ArrayList<String>();
    List<String> firstList=new ArrayList<String>();
    List<String> lastList=new ArrayList<String>();
    
    public FLSet(String nEndChar){
        this.nEndChar = nEndChar;
    }

    public String getnEndChar() {
        return nEndChar;
    }

    
    public List<String> getFirstvt() {
        return firstvt;
    }
    
    public void addFirstvt(String first) {
        this.firstvt.add(first);
    }

    public void addLastvt(String follow) {
        this.lastvt.add(follow);
    }

    public List<String> getLastvt() {
        return lastvt;
    }
    
    public void addFirstvtALL(List<String> firstL){
        firstvt.addAll(firstL);
    }
    
    public void addLastvtALL(List<String> firstL){
        lastvt.addAll(firstL);
    }
    
    public void addFirstvtList(String str){
        firstList.add(str);
    }
    
    public void addLastList(String str){
        lastList.add(str);
    }

    public List<String> getFirstvtList() {
        return firstList;
    }
    
    public int getFirstListLength(){
        return firstList.size();
    }
    
    public int getLastListLength(){
        return lastList.size();
    }
    
    public String getFirstListI(int i){
        return firstList.get(i);
    }
    
    public String getLastListI(int i){
        return lastList.get(i);
    }
    
    public void remove(int i){
        firstList.remove(i);
    }
    
    public void removeLastList(int i){
        lastList.remove(i);
    }
    
    public void removeLastListAll(){
        lastList.removeAll(lastList);
    }
    
    public void printFirst(){
        int size = firstvt.size();
        String firstall="";
        for(int q=0;q<size;q++){
            firstall+=firstvt.get(q);
        }
        System.out.println("first"+nEndChar+":"+firstall);
    }
    
    public String printFirst(int i){
        int size = firstvt.size();
        String firstall="";
        for(int q=0;q<size;q++){
            firstall+=firstvt.get(q);
        }
        return firstall;
    }
    
    public void printLast(){
        int size = lastvt.size();
        String followall="";
        for(int q=0;q<size;q++){
            followall+=lastvt.get(q)+"，";
        }
        System.out.println("last:"+nEndChar+":"+followall);
    }
    
    public String printLast(int i){
        int size = lastvt.size();
        String followall="";
        for(int q=0;q<size;q++){
            followall+=lastvt.get(q)+"，";
        }
       return followall;
    }
    
    public void printLastList(){
        int size = lastList.size();
        String followall="";
        for(int q=0;q<size;q++){
            followall+=lastList.get(q);
        }
        System.out.println(nEndChar+":"+followall);
    }
    public void printFirstList(){
        int size = firstList.size();
        String followall="";
        for(int q=0;q<size;q++){
            followall+=firstList.get(q);
        }
        System.out.println(nEndChar+":"+followall);
    }
    
    public void adjust(){
        boolean add=true;
        String tempS;
        List<String> tempList = new ArrayList<String>();
        for(int i=0;i<firstvt.size();i++){
            tempList.add(firstvt.get(i));
        }
        firstvt.removeAll(firstvt);
        for(int i=0;i<tempList.size();i++){
            add = true;
            tempS = tempList.get(i);
            for(int j=0;j<firstvt.size();j++){
                if(tempS.equals(firstvt.get(j))){
                    add = false;
                    break;
                }
            }
            if(add){
                firstvt.add(tempS);
            }
        }
      
    }
    
    public void adjustLast(){
        boolean add=true;
        String tempS;
        List<String> tempList = new ArrayList<String>();
        for(int i=0;i<lastvt.size();i++){
            tempList.add(lastvt.get(i));
        }
        lastvt.removeAll(lastvt);
        for(int i=0;i<tempList.size();i++){ 
            add = true;
            tempS = tempList.get(i);
            for(int j=0;j<lastvt.size();j++){   
                if(tempS.equals(lastvt.get(j))){
                    add = false;
                    break;
                }
            }
            if(add){
                lastvt.add(tempS);
            }
        }
    }
    
}
