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
public class LL1 {
    private String tfStr1,tfStr2,LLStr,chargeStr,chargeChar;
    private char firstC,endC;
    private String[] tempWenfa,endStr,nEndStr;
    private wenfa[] wenfaZu;
    private FFSet[] ffZu;
        int currentInt = 2;
     private List<String> alreadyList = new  ArrayList<String>();
    
    public LL1(char firstC,char endC, String tfStr1, String tfStr2, String LLStr) {
        this.tfStr1 = tfStr1;
        this.tfStr2 = tfStr2;
        this.endC = endC;
        this.LLStr = LLStr;
        this.firstC = firstC;
    }

    public wenfa[] getWenfaZu() {
        return wenfaZu;
    }

    public FFSet[] getFfZu() {
        return ffZu;
    }

    public String[] getEndStr() {
        return endStr;
    }

    public String[] getnEndStr() {
        return nEndStr;
    }
    
    
    
    public boolean charge(){
        tempWenfa = LLStr.split("\n");
        chargeStr ="";
        //先划分非终结符、终结符
        endStr = tfStr2.split(";");
        nEndStr = tfStr1.split(";");
        
        wenfaZu = new wenfa[tempWenfa.length];//里面为一条表达式
        ffZu = new FFSet[nEndStr.length];//里面为一ffset结点
        
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
        for(int i=0;i<wenfaZu.length;i++){
            wenfaZu[i] = new wenfa(tempWenfa[i]);
            System.out.println(wenfaZu[i].getWenfaStr());
        }
        
        //初始化ffSet
        for(int i=0;i<ffZu.length;i++){
            ffZu[i] = new FFSet(nEndStr[i]);
            System.out.println(ffZu[i].getnEndChar());
        }
        
        setEpsilon();
        setFirst();
        setFollow();
        setSelect();
        if(chargeLL1()){
            return true;
        }else{
            return false;
        }
        
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
    
    public boolean chargeEpsilon(String chargeChar){
        for(int i=0;i<ffZu.length;i++){
            if(chargeChar.equals(ffZu[i].getnEndChar())){
                if(ffZu[i].getIsEpsilon().equals("Y")){
                    return false;
                }
            }
        }
        return true;
    }
    
    public void setEpsilon(){
        //第一遍扫描，将右边第一字符为终结符的全部标记isDeleted为true；在之后扫描不予理会
        int length = wenfaZu.length;
        String currentChar;
        String currentnEndStr;
        for(int i =0;i<length;i++){
            if(!wenfaZu[i].isIsDeleted()){//只判断未删除的产生式                
            currentChar = String.valueOf(wenfaZu[i].getWenfaStr().charAt(2));
            if(chargeEndStr(currentChar)){//有可能为空
                if(currentChar.equals("ε")){//右部第一字符为空，将ffZu相应isEpsilon设置为true，并将相应wenfaZu设置isdeleted为true
                    currentnEndStr = String.valueOf(wenfaZu[i].getWenfaStr().charAt(0));
                    for(int j=0;j<ffZu.length;j++){
                        if(ffZu[j].getnEndChar().equals(currentnEndStr)){//找到相应ffZU
                            ffZu[j].setIsEpsilon("Y");
                            break;
                        }
                    }
                    for(int j=0;j<length;j++){
                         if(String.valueOf(wenfaZu[j].getWenfaStr().charAt(0)).equals(currentnEndStr)){//找到相应ffZU
                            wenfaZu[j].setIsDeleted(true);
                        }
                    }
                }else{
                    wenfaZu[i].setIsDeleted(true);
                }
            }
        }
        }
         int length1 = ffZu.length;
        //判断删除后的产生式是否为否
        boolean no = true;
        for(int i=0;i<length1;i++){
            if(ffZu[i].getIsEpsilon().equals("YN")){//在尚未判定的情况下判别
            currentnEndStr = ffZu[i].getnEndChar();
            for(int j=0;j<length;j++){
                currentChar = String.valueOf(wenfaZu[j].getWenfaStr().charAt(0));
                if(currentnEndStr.equals(currentChar)){//找到相应文法
                    if(!wenfaZu[j].isIsDeleted()){//存在没被删除的产生式，表示未必是否
                        no = false;
                        break;
                    }
                }
            }
            if(no){//是否
                ffZu[i].setIsEpsilon("N");
            }
            no = true;
        }
        }
        
        //递归判断至无YN
        for(int i=0;i<length1;i++){
            if(ffZu[i].getIsEpsilon().equals("YN")){//这是一个未定项
                System.out.println(ffZu[i].getnEndChar());
                currentInt=2;
              if(  setYN(ffZu[i].getnEndChar(),currentInt)){
                  ffZu[i].setIsEpsilon("Y");
              }else{
                  ffZu[i].setIsEpsilon("N");
              }
            }            
        }
        
        for(int i=0;i<length1;i++){
            System.out.println(ffZu[i].getnEndChar()+":"+ffZu[i].getIsEpsilon()+"\n");         
        }
    }

    public boolean setYN(String currentYN,int tempInt){
        int length = wenfaZu.length;
        String currentChar;
        String chargeChar;
        for(int i=0;i<length;i++){//先找文法产生式
            currentChar = String.valueOf(wenfaZu[i].getWenfaStr().charAt(0));
            if(currentChar.equals(currentYN)){//找到相应文法产生式
                chargeChar = String.valueOf(wenfaZu[i].getWenfaStr().charAt(tempInt++)); 
                return nextChar(chargeChar,wenfaZu[i].getWenfaStr());
            }
        }
        return false;
    }
    
    public boolean nextChar(String currentChar,String str){
        int length1 = ffZu.length;
        if(chargeEndStr(currentChar)){
            return false;
        }
        try{
         for(int j=0;j<length1;j++){//
             if(currentChar.equals(ffZu[j].getnEndChar())){
                            if(ffZu[j].getIsEpsilon().equals("Y")){//递归下一字符                                
                              return   nextChar(String.valueOf(str.charAt(currentInt++)),str);
                            }else if(ffZu[j].getIsEpsilon().equals("N")){
                                return false;
                            }else{//递归当前字符
                                System.out.println("递归的字符是"+ffZu[j].getnEndChar());
                               return setYN(ffZu[j].getnEndChar(),currentInt++);                             
                            }
                        }
                    }
        }catch(Exception e){
            return true;
        }
        return false;        
    }
    
    public void setFirst(){
        int length = wenfaZu.length;
        int length1 =ffZu.length;
        int length2;
        String currentChar;
        String chargeChar;
        String currentStr;
        String tempChar;
        boolean breakop=false;
        for(int i=0;i<length1;i++){
            currentChar = ffZu[i].getnEndChar();
            for(int j=0;j<length;j++){
                chargeChar = String.valueOf(wenfaZu[j].getWenfaStr().charAt(0));
                if(currentChar.equals(chargeChar)){//找到匹配的产生式
                    currentStr = wenfaZu[j].getWenfaStr();
                    length2 = currentStr.length();
                    for(int k=2;k<length2;k++){
                        if("ε".equals(String.valueOf(currentStr.charAt(k)))){//空产生式,跳过
                            break;
                        }else if(chargeEndStr(String.valueOf(currentStr.charAt(k)))){//非终结符
                            ffZu[i].addFirst(String.valueOf(currentStr.charAt(k)));
                            break;
                        }else{//非终结符
                               tempChar = String.valueOf(currentStr.charAt(k));
                               for (int q=0;q<length1;q++){
                                   if(tempChar.equals(ffZu[q].getnEndChar())){
                                       if(ffZu[q].getIsEpsilon().equals("Y")){
                                           ffZu[i].addFirstList(tempChar);
                                       }else{
                                           ffZu[i].addFirstList(tempChar);
                                           breakop = true;
                                           break;
                                       }
                                   }
                               }
                               if(breakop){
                                   break;
                               }
                        }
                    }
                }
            }
        }//构图完毕后，first里存放是终结符，firstList里存放时非终结符
        
        //将firstList里的东西转换至first里，使用递归
        for(int i=0;i<length1;i++){
            if(ffZu[i].getFirstListLength()>0){//firstList不为空
                addFirst(i);
            }//其它跳过
        }
        
        for(int i=0;i<length1;i++){
            ffZu[i].adjust();
            ffZu[i].printFirst();
        }
    }
    
    //注意先前未判断直接左递归和间接左递归，此处代码不能处理这两种情况，会死循环，需在进行LL1判断前加上递归判断（默认无递归）
    public void addFirst( int i){
        String currentChar;
        int length=ffZu[i].getFirstListLength();
        int length1 = ffZu.length;
        for(int j=length-1;j>-1;j--){//依次处理firstList里的非终结符
            currentChar=ffZu[i].getFirstListI(j);
            for(int k=0;k<length1;k++){
                if(currentChar.equals(ffZu[k].getnEndChar())){
                    if(ffZu[k].getFirstListLength()==0){//添加至first并删除
                        ffZu[i].addFirstALL(ffZu[k].getFirst());
                        ffZu[i].remove(j);
                    }else{//递归
                        addFirst(k);
                         ffZu[i].addFirstALL(ffZu[k].getFirst());
                    }
                }
            }
        }
    }
    
    int tempInt=0;
    public void setFollow(){
        //先将#加入firstC里
        int length = ffZu.length;
        int length1 = wenfaZu.length;
        int length2;
        
        for(int i=0;i<length;i++){
            if(ffZu[i].getnEndChar().equals(String.valueOf(firstC))){
                ffZu[i].addFollow("#");
                tempInt =i;
                break;
            }
        }
        //构图
        String currentStr;
        String tempStr;
        String currentChar;
        String nextChar;
        boolean breakop=false;
        for(int i=0;i<length;i++){
            currentStr = ffZu[i].getnEndChar();
            for(int j=0;j<length1;j++){
                tempStr=wenfaZu[j].getWenfaStr();
                length2 = tempStr.length();
                for(int k=2;k<length2;k++){
                    if("ε".equals(String.valueOf(tempStr.charAt(k)))){
                        break;
                    }else{
                        currentChar = String.valueOf(tempStr.charAt(k));
                        if(currentStr.equals(currentChar)){//
                            if(k==length2-1){//如果在最后
                                nextChar = String.valueOf(tempStr.charAt(0));
                                if(!currentStr.equals(nextChar)){
                                    ffZu[i].addFollowList(nextChar);
                                }
                            }else{
                                nextChar = String.valueOf(tempStr.charAt(k+1));
                                if(chargeEndStr(nextChar)){//后面是终结符，加入follow
                                    ffZu[i].addFollow(nextChar);
                                }else{
                                    for(int q=0;q<length;q++){
                                        if(nextChar.equals(ffZu[q].getnEndChar())){
                                            ffZu[i].addFollowALL(ffZu[q].getFirst());
                                            if(ffZu[q].getIsEpsilon().equals("N")){
                                                breakop = true;
                                                break;
                                            }else{
                                                boolean breakopp=false;
                                                int otherInt =k+1;
                                                String otherStr ;
                                                for(;otherInt<length2;otherInt++){
                                                    otherStr = String.valueOf(tempStr.charAt(otherInt));
                                                    for(int p=0;p<length;p++){
                                                        if(otherStr.equals(ffZu[p].getnEndChar())){
                                                            ffZu[i].addFollowALL(ffZu[p].getFirst());
                                                            if(ffZu[p].getIsEpsilon().equals("N")){
                                                                breakop = true;
                                                                breakopp=true;
                                                                 break;
                                                            }
                                                        }
                                                    } 
                                                    if(breakopp){
                                                        break;
                                                    }
                                                    if(otherInt==length2-1){
                                                        nextChar = String.valueOf(tempStr.charAt(0));
                                                        if(!currentStr.equals(nextChar)){
                                                            ffZu[i].addFollowList(nextChar);
                                                         }
                                                    }
                                                   
                                                }
                                            }
                                        }
                                    }
                                    if(breakop){
                                        breakop = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }//构图完毕
        //递归求follow，消followList
        alreadyList.add(String.valueOf(firstC));
        addFollow(tempInt);//此处有漏，待议
       ffZu[tempInt].removeFollowListAll();
        for(int i=0;i<length;i++){
            if(!ffZu[i].getnEndChar().equals(String.valueOf(firstC))){
                if(ffZu[i].getFollowListLength()>0){
                moveFollow(i);
            }
            }
            
        }
        for(int i=0;i<length;i++){
            ffZu[i].adjustFollow();
            ffZu[i].printFollow();
        }
        
    }
    
    
    public void addFollow(int i){
        //第一遍遍历，从S出发，至alreadyList终止，确定followS，消除S的followList
        int length = ffZu[i].getFollowListLength();
        int length1 = alreadyList.size();
        boolean over = false;
        String currentStr;
        for(int j=0;j<length;j++){
            currentStr = ffZu[i].getFollowListI(j);
            for(int k=0;k<length1;k++){
                if(currentStr.equals(alreadyList.get(k))){
                    over = true;
                    break;
                }
            }
            if(over){
                over = false;
            }else{
               int q=0;
                for(;q<ffZu.length;q++){
                    if(currentStr.equals(ffZu[q].getnEndChar())){
                        break;
                    }
                }
                ffZu[tempInt].addFollowALL(ffZu[q].getFollow());
                alreadyList.add(ffZu[q].getnEndChar());
                addFollow(q);
            }
        }
        return;
    }
    
    public void moveFollow(int i){
        //第二遍遍历，消followList,是递归的过程
        int j = ffZu[i].getFollowListLength();
        String currentStr;
        String currentChar;
        for(int k=j-1;k>-1;k--){
            currentStr =ffZu[i].getFollowListI(k);
            for(int q=0;q<ffZu.length;q++){
                if(currentStr.equals(ffZu[q].getnEndChar())){
                    ffZu[q].printFollow();
                     ffZu[i].addFollowALL(ffZu[q].getFollow());
                    if(ffZu[q].getFollowListLength()>0){
                        moveFollow(q);
                    }else{
                        ffZu[i].removeFollowList(k);
                    }
                }
            }
            
        }
    }
    
    public void setSelect(){
        int length = wenfaZu.length;
        int length1 = ffZu.length;
        int length2;
        String wenfaStr;
        String currentStr;
        String currentChar;
        for(int i=0;i<length;i++){
            wenfaStr = wenfaZu[i].getWenfaStr();
            length2  = wenfaStr.length();
            for(int k=2;k<length2;k++){
                currentStr = String.valueOf(wenfaStr.charAt(k));
                if(currentStr.equals("ε")){
                    currentStr = String.valueOf(wenfaStr.charAt(0));
                    for(int q=0;q<length1;q++){
                        if(currentStr.equals(ffZu[q].getnEndChar())){
                            wenfaZu[i].addSelectALL(ffZu[q].getFollow());
                        }
                    }
                    break;
                }else if(chargeEndStr(currentStr)){//终结符直接加入
                    wenfaZu[i].addSelect(currentStr);
                    break;
                }else{//非终结符
                    boolean goon=true;
                    for(int q=0;q<length1;q++){
                        if(currentStr.equals(ffZu[q].getnEndChar())){
                            wenfaZu[i].addSelectALL(ffZu[q].getFirst());
                            if(ffZu[q].getIsEpsilon().equals("N")){
                                goon=false;
                                break;
                            }
                        }
                    } 
                    boolean breakop = false;
                    if(goon){
                        for(int u=k+1;u<length2;u++){
                            currentStr = String.valueOf(wenfaStr.charAt(u));
                            if(chargeEndStr(currentStr)){
                                 wenfaZu[i].addSelect(currentStr);
                                 break;
                            }else{
                                for(int y=0;y<length1;y++){
                                    if( currentStr.equals(ffZu[y].getnEndChar())){
                                        wenfaZu[i].addSelectALL(ffZu[y].getFirst());
                                         if(ffZu[y].getIsEpsilon().equals("N")){
                                        breakop = true;
                                        break;
                                    }else{
                                             if(u==length2-1){
                                                 currentStr = String.valueOf(wenfaStr.charAt(0));
                                                 System.out.println("这里"+currentStr);
                                                for(int q=0;q<length1;q++){
                                                     if(currentStr.equals(ffZu[q].getnEndChar())){
                                                         wenfaZu[i].addSelectALL(ffZu[q].getFollow());
                                                         breakop = true;
                                                         break;
                                                         }
                                                    }
                                             }
                                         }
                                }
                                    if(breakop){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
         for(int i=0;i<length;i++){
             wenfaZu[i].adjust();
             wenfaZu[i].printSelect();
        }
    }
    
    public boolean chargeLL1(){//用alreadyList做中间变量
        int length = wenfaZu.length;
        String currentStr;
        String tempS;
        List<String> tempList1 = new ArrayList<String>();
        for(int i=0;i<length;i++){
            System.out.println("呵呵");
            if(!wenfaZu[i].isJump()){
                alreadyList.removeAll(alreadyList);
                tempList1.removeAll(tempList1);
                currentStr = String.valueOf(wenfaZu[i].getWenfaStr().charAt(0));
                alreadyList.addAll(wenfaZu[i].getSelect());
                for(int j=i+1;j<length;j++){
                    if(currentStr.equals(String.valueOf(wenfaZu[j].getWenfaStr().charAt(0)))){
                        alreadyList.addAll(wenfaZu[j].getSelect());
                        wenfaZu[j].setJump(true);
                    }
                } 
                for(int q=0;q<alreadyList.size();q++){
                 System.out.println("already:"+alreadyList.get(q));
                tempList1.add(alreadyList.get(q));
            }
            alreadyList.removeAll(alreadyList);
             for(int q=0;q<tempList1.size();q++){
                tempS = tempList1.get(q);
                System.out.println("temp:"+tempS);
                for(int j=0;j<alreadyList.size();j++){
                    if(tempS.equals(alreadyList.get(j))){
                    return false;
                }
            }
                alreadyList.add(tempS);
        }
            }
             
        }
        return true;
    }
}
