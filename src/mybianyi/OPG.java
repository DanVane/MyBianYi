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
public class OPG {//最终目的是生成opgZu
     private String tfStr1,tfStr2,OPGStr,chargeStr,chargeChar;
    private char firstC,endC;
    private String[] tempWenfa,endStr,nEndStr;
    private wenfa[] wenfaZu;
    private FLSet[] flZu;
     private OPGPoint[] opgZu;
        int currentInt = 2;
     private List<String> alreadyList = new  ArrayList<String>();
     public OPG(char firstC,char endC, String tfStr1, String tfStr2, String OPGStr) {
        this.tfStr1 = tfStr1;
        this.tfStr2 = tfStr2;
        this.endC = endC;
        this.OPGStr = OPGStr;
        this.firstC = firstC;
    }

    public OPGPoint[] getOpgZu() {
        return opgZu;
    }

    public String[] getEndStr() {
        return endStr;
    }

    public String[] getnEndStr() {
        return nEndStr;
    }

    public FLSet[] getFlZu() {
        return flZu;
    }
    
    
    
            boolean error=false;
    public boolean charge(){
        tempWenfa = OPGStr.split("\n");
        chargeStr ="";
        //先划分非终结符、终结符
        endStr = tfStr2.split(";");
        nEndStr = tfStr1.split(";");

        wenfaZu = new wenfa[tempWenfa.length+1];//里面为一条表达式
        flZu = new FLSet[nEndStr.length+1];//里面为一flset结点
        opgZu = new OPGPoint[endStr.length];
        //把所有的空格去掉
        for(int i=0;i<tempWenfa.length;i++){
            chargeStr = "";
            for(int j=0;j<tempWenfa[i].length();j++){
                chargeChar = String.valueOf(tempWenfa[i].charAt(j));
                if(chargeChar.equals(" ")){
                    
                }else{
                    chargeStr+=chargeChar;
                }
            }
            if(i==0){
                chargeStr = chargeStr.substring(1);
            }
            tempWenfa[i]=chargeStr;
        }       
       
        //初始化wenfaZu
        System.out.println("分割后的文法");
        wenfaZu[0]=new wenfa("Q→#"+firstC+"#");
        System.out.println(wenfaZu[0].getWenfaStr());
        for(int i=0;i<wenfaZu.length-1;i++){
            wenfaZu[i+1] = new wenfa(tempWenfa[i]);
            System.out.println(wenfaZu[i+1].getWenfaStr());
        }
        
        //初始化flSet
        flZu[0] = new FLSet("Q");
         System.out.println(flZu[0].getnEndChar());
        for(int i=0;i<flZu.length-1;i++){
            flZu[i+1] = new FLSet(nEndStr[i]);
            System.out.println(flZu[i+1].getnEndChar());
        }
        
        for(int i=0;i<opgZu.length;i++){
                opgZu[i] = new OPGPoint(endStr[i]);
            System.out.println(opgZu[i].getEndChar());
        }
        
        if(setFirstvt()){
            if(setLastvt()){
                setOPGPoint();
            }else{
                return false;
            }
        }else{
            return false;
        }
        
        return error;
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
        for(int a =0;a<nEndStr.length;a++){
            if(chargeChar.equals(nEndStr[a])){
                return true;
            }
            
        }
        return false;
    }
    
    public boolean setFirstvt(){
        int length = wenfaZu.length;
        int length1 =flZu.length;
        int length2;
        String currentChar;
        String chargeChar;
        String currentStr;
        String tempChar;
        boolean breakop=false;
        for(int i=0;i<length1;i++){
            currentChar = flZu[i].getnEndChar();//当前非终结符
            for(int j=0;j<length;j++){
                chargeChar = String.valueOf(wenfaZu[j].getWenfaStr().charAt(0));
                if(currentChar.equals(chargeChar)){//找到匹配的产生式
                    currentStr = wenfaZu[j].getWenfaStr();
                    length2 = currentStr.length();
                    for(int k=2;k<length2;k++){
                        if(chargeEndStr(String.valueOf(currentStr.charAt(k)))){//终结符
                            flZu[i].addFirstvt(String.valueOf(currentStr.charAt(k)));
                            break;
                        }else{//非终结符,缺少算符文法检验
                               tempChar = String.valueOf(currentStr.charAt(k));
                               if(!tempChar.equals(currentChar)){
                                   flZu[i].addFirstvtList(tempChar);
                               }
                               
                               if(k+1<currentStr.length()){
                                   if(chargeEndStr(String.valueOf(currentStr.charAt(k+1)))){
                                       flZu[i].addFirstvt(String.valueOf(currentStr.charAt(k+1)));
                                   }else{
                                       return false;
                                   }
                               }
                               break;
                        }
                    }
                }
            }
        }//构图完毕后，first里存放是终结符，firstList里存放时非终结符
        
        //将firstList里的东西转换至first里，使用递归
        for(int i=0;i<length1;i++){
            if(flZu[i].getFirstListLength()>0){//firstList不为空
                addFirst(i);
            }//其它跳过
        }
        
        for(int i=0;i<length1;i++){
            flZu[i].adjust();
            flZu[i].printFirst();
        }
        
        return true;
    }
    
    //注意先前未判断直接左递归和间接左递归，此处代码不能处理这两种情况，会死循环，需在进行LL1判断前加上递归判断（默认无递归）
    public void addFirst( int i){
        String currentChar;
        int length=flZu[i].getFirstListLength();
        int length1 = flZu.length;
        for(int j=length-1;j>-1;j--){//依次处理firstList里的非终结符
            currentChar=flZu[i].getFirstListI(j);
            for(int k=0;k<length1;k++){
                if(currentChar.equals(flZu[k].getnEndChar())){
                    if(flZu[k].getFirstListLength()==0){//添加至first并删除
                        flZu[i].addFirstvtALL(flZu[k].getFirstvt());
                        flZu[i].remove(j);
                    }else{//递归
                        addFirst(k);
                         flZu[i].addFirstvtALL(flZu[k].getFirstvt());
                    }
                }
            }
        }
    }
    
    int tempInt=0;
    public boolean setLastvt(){
        int length = wenfaZu.length;
        int length1 =flZu.length;
        int length2;
        String currentChar;
        String chargeChar;
        String currentStr;
        String tempChar;
        boolean breakop=false;
        for(int i=0;i<length1;i++){
            currentChar = flZu[i].getnEndChar();//当前非终结符
            for(int j=0;j<length;j++){
                chargeChar = String.valueOf(wenfaZu[j].getWenfaStr().charAt(0));
                if(currentChar.equals(chargeChar)){//找到匹配的产生式
                    currentStr = wenfaZu[j].getWenfaStr();
                    length2 = currentStr.length();
                    for(int k=length2-1;k>1;k--){
                        if(chargeEndStr(String.valueOf(currentStr.charAt(k)))){//终结符
                            System.out.println("终结符"+String.valueOf(currentStr.charAt(k)));
                            flZu[i].addLastvt(String.valueOf(currentStr.charAt(k)));
                            flZu[i].printLast();
                            break;
                        }else{//非终结符,缺少算符文法检验
                               tempChar = String.valueOf(currentStr.charAt(k));
                               if(!tempChar.equals(currentChar)){
                                   flZu[i].addLastList(tempChar);
                               }
                               if(!(k-1==1)){
                                   if(chargeEndStr(String.valueOf(currentStr.charAt(k-1)))){
                                       flZu[i].addLastvt(String.valueOf(currentStr.charAt(k-1)));
                                   }else{
                                       return false;
                                   }
                               }
                               break;
                        }
                    }
                }
            }
        }
        //递归求follow，消followList
        
         for(int i=0;i<length1;i++){
            if(flZu[i].getLastListLength()>0){//firstList不为空
                System.out.println("AddLat:"+flZu[i].getnEndChar());
                flZu[i].printLast();
                flZu[i].printLastList();
                addLast(i);
            }//其它跳过
        }
        
        for(int i=0;i<length1;i++){
            flZu[i].adjustLast();
            flZu[i].printLast();
        }
        
        return true;
    }
    
    public void addLast(int i){
         String currentChar;
        int length=flZu[i].getLastListLength();
        int length1 = flZu.length;
        for(int j=length-1;j>-1;j--){//依次处理firstList里的非终结符
            currentChar=flZu[i].getLastListI(j);
            System.out.println("当期last："+currentChar);
            for(int k=0;k<length1;k++){
                if(currentChar.equals(flZu[k].getnEndChar())){
                    if(flZu[k].getLastListLength()==0){//添加至first并删除
                        flZu[i].addLastvtALL(flZu[k].getLastvt());
                        flZu[i].removeLastList(j);
                    }else{//递归
                        addLast(k);
                         flZu[i].addLastvtALL(flZu[k].getLastvt());
                    }
                }
            }
        }
        return;
    }
    

    public void setOPGPoint(){
        int length = opgZu.length;
        int length1 = flZu.length;
        int length2 = wenfaZu.length;
        int length3;
        String currentStr,currentChar,tempStr;
        String nextChar;
        for(int i=0;i<length;i++){
            currentStr = opgZu[i].getEndChar();
            System.out.println("currentStr"+currentStr);
            for(int j=0;j<length2;j++){
                tempStr = wenfaZu[j].getWenfaStr();
                length3 = tempStr.length();
                for(int k=2;k<length3;k++){
                    currentChar = String.valueOf(tempStr.charAt(k));
                    System.out.println("currentChar"+currentChar);
                    if(currentStr.equals(currentChar)){
                        System.out.println("currentChar"+currentChar+currentStr+":"+tempStr);
                        if(k+1<length3){
                            nextChar = String.valueOf(tempStr.charAt(k+1));
                            for(int q=0;q<length1;q++){
                                if(nextChar.equals(flZu[q].getnEndChar())){
                                    opgZu[i].addFirstvtALL(flZu[q].getFirstvt());
                                    flZu[q].printFirst();
                                    break;
                                }
                            }
                        }
                        if(k+2<length3){
                            nextChar = String.valueOf(tempStr.charAt(k+2));
                            opgZu[i].addDengyu(nextChar);
                        }
                        if(k-1>1){
                            nextChar = String.valueOf(tempStr.charAt(k-1));
                            for(int q=0;q<length1;q++){
                                if(nextChar.equals(flZu[q].getnEndChar())){
                                    opgZu[i].addLastvtALL(flZu[q].getLastvt());
                                    flZu[q].printLast();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        for(int i=0;i<length;i++){
            opgZu[i].printFirst();
            opgZu[i].printDengyu();
            opgZu[i].printLast();
        }
    }
  
}
