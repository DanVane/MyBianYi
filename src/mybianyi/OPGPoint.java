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
public class OPGPoint {
    private String endChar;  //非终结符
    List<String> firstvt=new ArrayList<String>();//<
    List<String> lastvt=new ArrayList<String>();//>
    List<String> dengyuvt=new ArrayList<String>();//=
    
    public OPGPoint(String endChar){
        this.endChar = endChar;
    }

    public String getEndChar() {
        return endChar;
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
    
    public void printFirst(){
        int size = firstvt.size();
        String firstall="";
        for(int q=0;q<size;q++){
            firstall+=firstvt.get(q);
        }
        System.out.println(endChar+"<"+firstall);
    }
    
    public String printFirst(int i){
        int size = firstvt.size();
        String firstall="";
        for(int q=0;q<size;q++){
            firstall+=firstvt.get(q);
        }
        return firstall;
    }
    
    public void addDengyu(String str){
        dengyuvt.add(str);
    }

    public List<String> getDengyuvt() {
        return dengyuvt;
    }
    
    public void printLast(){
        int size = lastvt.size();
        String followall="";
        for(int q=0;q<size;q++){
            followall+=lastvt.get(q)+"，";
        }
        System.out.println(endChar+">"+followall);
    }
    
    public void printDengyu(){
        int size = dengyuvt.size();
        String followall="";
        for(int q=0;q<size;q++){
            followall+=dengyuvt.get(q)+"，";
        }
        System.out.println(endChar+"="+followall);
    }
    
    public String printLast(int i){
        int size = lastvt.size();
        String followall="";
        for(int q=0;q<size;q++){
            followall+=lastvt.get(q)+"，";
        }
       return followall;
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
            if(tempS.equals("ε")){
                    continue;
                }
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
