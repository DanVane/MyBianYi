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
public class FFSet {
    private String nEndChar;  //非终结符
    private String isEpsilon ="YN";   //是否能推出空
    List<String> first=new ArrayList<String>();
    List<String> follow=new ArrayList<String>();
    List<String> firstList=new ArrayList<String>();
    List<String> followList=new ArrayList<String>();
    
    public FFSet(String nEndChar){
        this.nEndChar = nEndChar;
    }

    public String getnEndChar() {
        return nEndChar;
    }

    
    public List<String> getFirst() {
        return first;
    }
    
    public void addFirst(String first) {
        this.first.add(first);
    }

    public void addFollow(String follow) {
        this.follow.add(follow);
    }

    public List<String> getFollow() {
        return follow;
    }

    public String getIsEpsilon() {
        return isEpsilon;
    }

    public void setIsEpsilon(String isEpsilon) {
        this.isEpsilon = isEpsilon;
    }
    
    public void addFirstALL(List<String> firstL){
        first.addAll(firstL);
    }
    
    public void addFollowALL(List<String> firstL){
        follow.addAll(firstL);
    }
    
    public void addFirstList(String str){
        firstList.add(str);
    }
    
    public void addFollowList(String str){
        followList.add(str);
    }

    public List<String> getFirstList() {
        return firstList;
    }
    
    public int getFirstListLength(){
        return firstList.size();
    }
    
    public int getFollowListLength(){
        return followList.size();
    }
    
    public String getFirstListI(int i){
        return firstList.get(i);
    }
    
    public String getFollowListI(int i){
        return followList.get(i);
    }
    
    public void remove(int i){
        firstList.remove(i);
    }
    
    public void removeFollowList(int i){
        followList.remove(i);
    }
    
    public void removeFollowListAll(){
        followList.removeAll(followList);
    }
    
    public void printFirst(){
        int size = first.size();
        String firstall="";
        for(int q=0;q<size;q++){
            firstall+=first.get(q);
        }
        System.out.println(nEndChar+":"+firstall);
    }
    
    public String printFirst(int i){
        int size = first.size();
        String firstall="";
        for(int q=0;q<size;q++){
            firstall+=first.get(q);
        }
        return firstall;
    }
    
    public void printFollow(){
        int size = follow.size();
        String followall="";
        for(int q=0;q<size;q++){
            followall+=follow.get(q)+"，";
        }
        System.out.println(nEndChar+":"+followall);
    }
    
    public String printFollow(int i){
        int size = follow.size();
        String followall="";
        for(int q=0;q<size;q++){
            followall+=follow.get(q)+"，";
        }
       return followall;
    }
    
    public void printFollowList(){
        int size = followList.size();
        String followall="";
        for(int q=0;q<size;q++){
            followall+=followList.get(q);
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
        for(int i=0;i<first.size();i++){
            tempList.add(first.get(i));
        }
        first.removeAll(first);
        for(int i=0;i<tempList.size();i++){
            add = true;
            tempS = tempList.get(i);
            for(int j=0;j<first.size();j++){
                if(tempS.equals(first.get(j))){
                    add = false;
                    break;
                }
            }
            if(add){
                first.add(tempS);
            }
        }
        if(this.isEpsilon.equals("Y")){
            first.add("ε");
        }
    }
    
    public void adjustFollow(){
        boolean add=true;
        String tempS;
        List<String> tempList = new ArrayList<String>();
        for(int i=0;i<follow.size();i++){
            tempList.add(follow.get(i));
        }
        follow.removeAll(follow);
        for(int i=0;i<tempList.size();i++){ 
            add = true;
            tempS = tempList.get(i);
            if(tempS.equals("ε")){
                    continue;
                }
            for(int j=0;j<follow.size();j++){   
                if(tempS.equals(follow.get(j))){
                    add = false;
                    break;
                }
            }
            if(add){
                follow.add(tempS);
            }
        }
    }
}
