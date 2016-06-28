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
public class wenfa {
    private boolean isDeleted=false;
    private String wenfaStr;
   List<String> select=new ArrayList<String>();
    private boolean jump=false;
    
    public wenfa(String wenfaStr){
        this.wenfaStr = wenfaStr;
    }
    
    public String getWenfaStr() {
        return wenfaStr;
    }

   public List<String> getSelect() {
        return select;
    }
    
    public void addSelect(String first) {
        this.select.add(first);
    }
    
    public void addSelectALL(List<String> firstL){
        select.addAll(firstL);
    }
    
    public void printSelect(){
        int size = select.size();
        String firstall="";
        for(int q=0;q<size;q++){
            firstall+=select.get(q);
        }
        System.out.println(wenfaStr+":"+firstall);
    }
    
     public String printSelect(int i){
        int size = select.size();
        String firstall="";
        for(int q=0;q<size;q++){
            firstall+=select.get(q)+"，";
        }
       return firstall;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
    
    public void adjust(){
        boolean add=true;
        String tempS;
        List<String> tempList = new ArrayList<String>();
        for(int i=0;i<select.size();i++){
            tempList.add(select.get(i));
        }
        select.removeAll(select);
        for(int i=0;i<tempList.size();i++){
            add = true;
            tempS = tempList.get(i);
            if(tempS.equals("ε")){
                    continue;
                }
            for(int j=0;j<select.size();j++){
                if(tempS.equals(select.get(j))){
                    add = false;
                    break;
                }
            }
            if(add){
                select.add(tempS);
            }
        }
    }
}
