/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mybianyi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class Cifa_DFA {
    private String tfStr1,tfStr2,DFAStr,chargeStr,chargeChar,baoliu,baoliu1,baoliu2;
    private char firstC,endC;
    private int number=0;
    private String[] wenfaStr,endChar,endStr,nEndStr;
    private List<String> NFAList = new ArrayList<String>();
    private List<String> DFAList = new ArrayList<String>();
    private List<String> exList = new ArrayList<String>();
    private List<String> moveList = new ArrayList<String>();
    private List<String> otherList = new ArrayList<String>();
    private List<String> tempList = new ArrayList<String>();
    private List<DFAPoint> pointList = new ArrayList<DFAPoint>();

    public Cifa_DFA(char firstC,char endC, String tfStr1, String tfStr2, String DFAStr) {
        this.tfStr1 = tfStr1;
        this.tfStr2 = tfStr2;
        this.endC = endC;
        this.DFAStr = DFAStr;
        this.firstC = firstC;
    }

    public List<DFAPoint> getPointList() {
        return pointList;
    }
    
    public void change(){
        baoliu = DFAStr;
        wenfaStr = DFAStr.split("\n");
        chargeStr ="";
        //先划分非终结符、终结符
        baoliu1 = tfStr2;
        baoliu2 = tfStr1;
        endStr = tfStr2.split(";");
        nEndStr = tfStr1.split(";");
       
        //把所有的空格去掉
        for(int i=0;i<wenfaStr.length;i++){
            chargeStr = "";
            for(int j=0;j<wenfaStr[i].length();j++){
                chargeChar = String.valueOf(wenfaStr[i].charAt(j));
                if(chargeChar.equals(" ")){
                    
                }else{
                    chargeStr+=chargeChar;
                }
            }
            if(i==0){
                chargeStr = chargeStr.substring(1);
            }
            wenfaStr[i]=chargeStr;
        }
        
        
        //再判断是否是正规文法,若是，则生成NFA
        boolean error1=false,error2=false,error3=false;
        for(int i=0;i<wenfaStr.length;i++){
            for(int j =0;j<wenfaStr[i].length();j++){
                chargeChar = String.valueOf(wenfaStr[i].charAt(j));
                if(j==0){   //第一个字符一定是非终结符
                    if(chargeNEndStr(chargeChar)){     //第一个字符不是非终结符，发生错误
                        chargeStr = chargeChar;
                    }else{
                        error1 = false;
                    }
                }else if(j!=wenfaStr[i].length()-1){        //不是最后一个字符一定不能是非终结符
                    if(chargeChar.equals("→")){
                        
                    }else if(chargeEndStr(chargeChar)){
                        chargeStr += chargeChar;
                    }else if(chargeNEndStr(chargeChar)){
                        System.out.println("出错位置"+i+j);
                        error2 = true;
                    }
                }else if(j==wenfaStr[i].length()-1){  //最后一个字符可以是终结符，也可以是非终结符
                    if(!chargeEndStr(chargeChar)&& !chargeNEndStr(chargeChar)){
                        error3 = true;
                    }else{
                        if(chargeEndStr(chargeChar)){
                            chargeStr+=chargeChar;
                            chargeStr+=endC;
                        }else{
                            chargeStr+=chargeChar;
                        }                        
                    }
                }
            }
            if(error1){
                System.out.println("第一个符号错误");
                break;
            }else if(error2){
                System.out.println("不合适的中间符号");
                break;
            }else  if(error3){
                System.out.println("最后一个符号不存在");
                break;
            }else{
                NFAList.add(chargeStr);
            }            
        }
        
        //先转换为NFA
        if(!error1 && !error2 &&!error3){       //在没有错误的情况下
            System.out.println("初始状态：q="+firstC);
            System.out.println("可接受态：F="+endC);
            System.out.println("字母表:Σ="+tfStr2);
            System.out.println("变换规则如下δ：");
            Iterator it = NFAList.iterator();
            while(it.hasNext()){
                Object ob = it.next();
                System.out.println(ob);
            }
        }
        
        //再转换为DFA
        if(!error1 && !error2 && !error3){
            //firstC的单独求
          //  System.out.println("firstC的单独求");
            for(int i=0;i<NFAList.size();i++){
                if(firstC==NFAList.get(i).charAt(0)){   //从初始符开始求
                    if(NFAList.get(i).charAt(1)=='ε'){
                        DFAList.add("#"+firstC+endC);
                        tempList.add(""+firstC+endC);
                        break;
                    }
                }
            }
            
            String temp,tempS,tempSS="";
            char tempC;
            int tempInt=0;
            boolean first,add=false;
            for(int i=0;i<DFAList.size();i++){
                System.out.println("DFAList"+DFAList.size());
                //先求move集
                temp = DFAList.get(i);
                System.out.println(temp);
                //先找到需开始求move集的位置
                for(int j=temp.length()-1;j>=0;j--){
                    tempC = temp.charAt(j);
                    System.out.println(tempC);
                    if(chargeEndStr(String.valueOf(tempC))||tempC=='#'){
                        tempInt = j+1;
                        System.out.println(j+1);
                        break;
                    }
                }
                
                first = true;
                String tempCC;
                for(;tempInt<temp.length();tempInt++){
                    tempC = temp.charAt(tempInt);
                    System.out.println(tempC);
                    for(int k=0;k<endStr.length;k++){
                        System.out.println("endStr:"+endStr[k]);
                  //      System.out.println(NFAList.get(0));
                        if(endStr[k].equals("ε")){
                            
                        }else{                                                       
                            for(int l=0;l<NFAList.size();l++){
                                tempS = NFAList.get(l);
                                tempCC = String.valueOf(tempS.charAt(0));
                                System.out.println("" +tempC +tempS.charAt(0)+tempS.charAt(1)+endStr[k]);
                                if(String.valueOf(tempC).equals(tempCC)){
                                    if(String.valueOf(tempS.charAt(1)).equals(endStr[k])){
                                        if(first){
                                            tempSS=endStr[k];
                                            add = true;
                                        }                                      
                                        tempSS+=tempS.charAt(2);
                                        first = false;
                                        System.out.println("tempSS"+tempSS);
                                    }
                                }
                            }
                            if(add){
                                moveList.add(temp+tempSS);
                                add=false;
                                first = true;
                                System.out.println(temp+tempSS);
                            }
                        }
                    }
                }    
                
                //再求epsilon闭包      
                    String tempSSS;
                    first = true;
                    for(int ii=0;ii<moveList.size();ii++){
                        tempS = moveList.get(ii);
                        for(int j=tempS.length()-1;j>=0;j--){
                            tempC = tempS.charAt(j);
                            if(chargeEndStr(String.valueOf(tempC))||tempC=='#'){
                                tempInt=j+1;
                                break;
                            }
                        }
                        int g=tempInt;
                        boolean addChar = true;
                        temp ="";
                        for(;tempInt<tempS.length();tempInt++){                            
                            tempC = tempS.charAt(tempInt);
                            for(int j=0;j<NFAList.size();j++){
                                tempSS = NFAList.get(j);
                                tempSSS = String.valueOf(tempSS.charAt(0));
                                if(tempSSS.equals(String.valueOf(tempC))){
                                    tempSSS = String.valueOf(tempSS.charAt(1));
                                    if(tempSSS.equals("ε")){                                       
                                        tempSSS =String.valueOf(tempSS.charAt(2));
                                        for(int k=g;g<tempS.length();g++){
                                            if(tempSSS.equals(String.valueOf(tempS.charAt(k)))){
                                                addChar = false;
                                            }
                                        }
                                        if(addChar){
                                            temp+=tempSSS;
                                        }
                                    }
                                }
                            }
                            System.out.println("temp:"+temp);
                        }
                        
                        System.out.println("tempS:"+tempS+temp);
                        boolean addList=true;
                        for(int k=0;k<tempList.size();k++){
                            if(temp.equals(tempList.get(k))){
                                addList=false;
                                break;
                            }
                        }
                        if(addList){
                            DFAList.add(tempS+temp);
                            tempList.add(temp);
                        }else{
                            otherList.add(tempS+temp);
                        }
                    }
                     
                moveList.removeAll(moveList);
                exList.removeAll(exList);
            }
    }
        DFAList.addAll(otherList);
      /*  for(int a=0;a<DFAList.size();a++){
            System.out.println("DFA"+DFAList.get(a));
        }*/
        trans();
        deleteDFA();
    }
    
    public boolean chargeEndStr(String chargeChar){
        for(int a =0;a<endStr.length;a++){
            if(chargeChar.equals(endStr[a])){
                return true;
            }
        }
        return false;
    }
    
    public boolean chargeNEndStr(String chargeChar){
        if(chargeChar.equals("Z")){
            return true;
        }
        for(int a =0;a<nEndStr.length;a++){
            if(chargeChar.equals(nEndStr[a])){
                return true;
            }
            
        }
        return false;
    }
    
    public void trans(){//修改DFAList,使用exList做中间存储器,使用tfStr1和tfStr2，chargeStr做中间变量
        tfStr1 = tfStr2 ="";
        String tfStr3="";
        int i=0,j=1,k,l;
        int DFAL = DFAList.size();
        for(;i<DFAL;i++){
            tfStr1=tfStr2=tfStr3="";
            DFAStr = DFAList.get(i);
            int strL = DFAStr.length();
            System.out.println(DFAStr);
            for(j=1;j<strL;j++){
                tfStr1 = String.valueOf(DFAStr.charAt(j));
           //     if(j!=strL-1){//未匹配到最后一个字符
                    if(chargeNEndStr(tfStr1)){//如果是非终结符
                        tfStr2+=tfStr1;
                        System.out.println("tfStr2:"+tfStr2);
                        if(j==strL-1){
                            boolean add=true;
                            for(int u=0;u<pointList.size();u++){
                            if(tfStr2.equals(pointList.get(u).getCollection())){
                                add=false;
                            }
                        }
                            if(add){
                                DFAPoint point= new DFAPoint();
                            point.setCurrentInt(number++);
                            point.setCollection(tfStr2);
                            pointList.add(point);
                            tfStr1=tfStr2=tfStr3="";
                            }
                            
                        }
                        continue;
                    }else{//找到road和currentInt
                        chargeStr=tfStr1;
                        System.out.println("road:"+chargeStr);
                        k=j+1;
                        for(;k<strL;k++){           //找nextInt
                            tfStr1 = String.valueOf(DFAStr.charAt(k));                         
                                if(chargeNEndStr(tfStr1)){//如果是非终结符
                                     tfStr3+=tfStr1;
                                }else{
                                    break;
                                }                            
                        }
                        System.out.println("tfStr3:"+tfStr3);
                        //在找到currentInt，road和nextInt后，找出相同状态,tfStr2,chargeStr,tfStr3
                     /*   for(k=0;k<pointList.size();k++){
                            if(tfStr2.equals(pointList.get(k).getCollection())){
                                break;
                            }
                        }
                        for(l=0;l<pointList.size();l++){
                            if(tfStr3.equals(pointList.get(l).getCollection())){
                                break;
                            }
                        }*/
                        if(tfStr2.equals(tfStr3)){
                            System.out.println("tfStr2=tfStr3");
                            for(k=0;k<pointList.size();k++){
                            if(tfStr2.equals(pointList.get(k).getCollection())){
                                System.out.println("find:"+k+";size"+pointList.size());
                                break;
                            }
                        }
                            if(k>=pointList.size()){
                                DFAPoint point= new DFAPoint();
                                point.setCurrentInt(number++);
                                point.setCollection(tfStr2);
                                point.setRoad(chargeStr);
                                point.setNextInt(point.getCurrentInt());
                                pointList.add(point);
                                tfStr2=tfStr3="";
                                continue;
                            }else{
                                System.out.println("正在添加AtoA");
                                System.out.println(chargeStr);
                                DFAPoint point= pointList.get(k);   
                                System.out.println(point.getCurrentInt()+point.getCollection());
                                point.setRoad(chargeStr);
                                point.setNextInt(point.getCurrentInt());
                                System.out.println(point.printPoint());
                                pointList.remove(k);
                                pointList.add(point);                 
                                tfStr2=tfStr3="";
                                continue;
                            }
                        }else{  
                            for(k=0;k<pointList.size();k++){
                            if(tfStr2.equals(pointList.get(k).getCollection())){
                                break;
                            }
                        }
                            for(l=0;l<pointList.size();l++){
                                if(tfStr3.equals(pointList.get(l).getCollection())){
                                    break;
                                }
                            }
                        //根据k判断是新建节点，还是在源节点上修改
                        if(k>=pointList.size()){//需新建节点
                            DFAPoint point= new DFAPoint();
                            point.setCurrentInt(number++);
                            point.setCollection(tfStr2);
                            point.setRoad(chargeStr);                          
                            if(l>=pointList.size()){//目的节点也需新建
                                DFAPoint point1= new DFAPoint();
                                point1.setCurrentInt(number++);
                                point1.setCollection(tfStr3);
                                point.setNextInt(point1.getCurrentInt());
                                pointList.add(point1);
                            }else{
                                point.setNextInt(pointList.get(l).getCurrentInt());
                            }
                            pointList.add(point);
                            tfStr1=tfStr2=tfStr3="";
                        }else{
                            DFAPoint point=pointList.get(k);   
                            point.setRoad(chargeStr);
                            if(l>=pointList.size()){//目的节点也需新建
                                DFAPoint point1= new DFAPoint();
                                point1.setCurrentInt(number++);
                                point1.setCollection(tfStr3);
                                point.setNextInt(point1.getCurrentInt());
                                pointList.add(point1);
                            }else{
                                point.setNextInt(pointList.get(l).getCurrentInt());
                            }
                            pointList.remove(k);
                            pointList.add(point);                          
                            tfStr1=tfStr2=tfStr3="";
                        }
                        }
                    }
                }
            }
        //}
    }
    
    public void deleteDFA(){
        int i=0;
        int j=pointList.size();
        for(;i<j;i++){
            DFAPoint point=pointList.get(i);
            point.clean();
            pointList.add(point);
        }
        for(i=0;i<j;i++){
            pointList.remove(i);
        }
        for(i=0;i<j;i++){
            System.out.println(pointList.get(i).printPoint());
        }
    }
    
    
    public String getW(){
        String str="";
         str+="\n 初始状态：q="+firstC;
         str+="\n可接受态：F="+endC;
         str+="\n字母表:Σ="+baoliu1+baoliu2;
         str+="\n变换规则如下δ：";
          str+="\n"+baoliu;
            return str;
    }
}
