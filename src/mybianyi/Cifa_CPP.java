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

/*
*TYPE:
*0:关键字
*3：运算符
*4：界符
*2：常数
*1：标识符
*/
public class Cifa_CPP {
    private String cppStr;
    private String[] cppStr1;
  //  private String[] strs;
//    private boolean[] keyWordB;
//    private boolean[] operatorB = new boolean[14];
//    private boolean[] boundedB = new boolean[9];
    private boolean CORJ;
    public static String[] keyWordStr={"begin","end","if","then","else","while","cin","cout",
                                        "do","const","static","char","double","new","switch",
                                        "break","private","this","case","extern","protected",
                                        "catch","float","public","try","for","return","class",
                                        "union","enum","short","continue","virtual","default",
                                        "inline","sizeof","void","delete","int","#include",
                                        "#define","main"};
    public static String[] operatorStr={"+","-","*","/","%","=","+=","-=","*=","/=","%=","==","!","!=","&&","||"};
    public static String[] boundedStr={"{","}",";",",","(",")",":","<",">"};
    public static String[] keyWords={  
       "abstract","boolean","break","byte","case","catch","char","class",  
       "const","continue","default","do","double","else","extends","false",  
       "final","finally","float","for","goto","if","implements","import",
       "instanceof","int","interface","long","native","new","null","package",  
       "private","protected","public","return","short","static","super","switch",  
       "synchronized","this","throw","throws","transient","true","try","void",  
       "volatile","while" ,"String" 
};
 //   List<String> tryList=new ArrayList<String>();
 //   List<String> staticList = new ArrayList<String>();
    List<Token> tokenList = new ArrayList<Token>();
    int[] type = {0,1,2,3,4};
    int number =0;
    int row=0,id=0;
    String currentStr="";
    int errorInt=0;
   List<ErrorInfo> errorList = new ArrayList<ErrorInfo>();
    
   

    Cifa_CPP(String str1,boolean b) {
        cppStr=str1;
        CORJ =b;
        if(b){
           // keyWordB = new boolean[43];
            System.out.println("true");
        }else{
            System.out.println("false");
           // keyWordB = new boolean[50];
        }
    }
/*
    public boolean[] getKeyWordB() {
        return keyWordB;
    }

    public boolean[] getOperatorB() {
        return operatorB;
    }

    public boolean[] getBoundedB() {
        return boundedB;
    }

    public int[] getErrorInt() {
        return errorInt;
    }*/

  /*  public List<String> getTryList() {
        return tryList;
    }

    public List<String> getStaticList() {
        return staticList;
    }*/
    public List<Token> getTokenList() {
        return tokenList;
    }

    public List<ErrorInfo> getErrorList() {
        return errorList;
    }

    
    
    
    
 /*   
    
    public void charge(){
        String chargeStr="";
        String chargeChar;
        List<String> tryStr= new ArrayList<String>();
    //    List<String> tryList=new ArrayList<String>();
        List<String> secList=new ArrayList<String>();
     //   List<String> staticList = new ArrayList<String>();
        String[] temp;
        int m=1;
        int w=0;
        
         //先把引号里的东西剔除
        int beginInt=0;
        int endInt=0;
        boolean giveCharge =true;
        String cppStr2 = cppStr;
        System.out.println("cppStr的长度是"+cppStr.length());
        int z=0;
        for(int currentInt=0;currentInt<cppStr.length();currentInt++){
          //  System.out.println(cppStr.charAt(currentInt));
            z=cppStr.length();
         //   System.out.println("z="+z);
            if(String.valueOf(cppStr.charAt(currentInt)).equals("\"")&&giveCharge){
                beginInt = currentInt;
                giveCharge = false;
            }else  if(String.valueOf(cppStr.charAt(currentInt)).equals("\"")&&!giveCharge){
                endInt = currentInt;
                giveCharge = true;
            }
            if(giveCharge&&(endInt>beginInt)){
                String tempS = cppStr.substring(beginInt, endInt+1);
                staticList.add(tempS);
                tempS=" ";
                String beginStr = cppStr.substring(0, beginInt);
                String endStr = cppStr.substring(endInt+1, z-1);
                cppStr = beginStr+tempS+endStr;
                System.out.println("剔除引号后的字符串是"+cppStr);
                currentInt-=(endInt-beginInt);
                beginInt=endInt=0;
            }
        }
        
        cppStr =cppStr.replace("\t", " ");
        cppStr1=cppStr.split("\n");
        for(int i=0;i<cppStr1.length;i++){
             temp = cppStr1[i].split(" ");
             if(i==0&&CORJ){
                 temp[0]=temp[0].substring(1);
             }
             for(int y=0;y<temp.length;y++){
                 System.out.println(temp[y]+temp[y].length());
                 if(temp[y].length()>0){
                    tryStr.add(temp[y]);
                 }
             }
        }          
          
             for(int k=0;k<tryStr.size();k++){
                 System.out.println("当前正在处理的单行是"+tryStr.get(k));
                 for(int l=0;l<tryStr.get(k).length();l++){
                     chargeChar =String.valueOf(tryStr.get(k).charAt(l));
                     System.out.println("当前正在处理的符号是"+chargeChar);
                     if(boundedOperator(chargeChar)){
                         if(m==1){
                            for(int g=0;g<tryStr.size();g++){
                                if(chargeChar.equals("(")||chargeChar.equals(")")||chargeChar.equals("{")||chargeChar.equals("}")){
                                    System.out.println(tryStr.get(g).length());
                                     temp = tryStr.get(g).split("\\"+chargeChar);
                                 }else{
                                    temp = tryStr.get(g).split(chargeChar);
                                 }
                                for(int h=0;h<temp.length;h++){
                                    if(temp[h].length()!=0){
                                         tryList.add(temp[h]);
                                    }
                                }
                             }
                            m=2;
                            for(int g=0;g<tryList.size();g++){
                                System.out.println("第一次界符分割后的List是"+tryList.get(g));
                            }
                         }else{
                             System.out.println("不再用tryStr进行分割,tryList的长度是"+tryList.size());
                             int tempInt = tryList.size();
                             secList.removeAll(secList);
                             for(int q=0;q<tempInt;q++){
                            //     System.out.println(q);
                            //     System.out.println("正在处理的tryList串是");
                           //      System.out.println(tryList.get(q));
                                 if(chargeChar.equals("(")||chargeChar.equals(")")||chargeChar.equals("{")||chargeChar.equals("}")){
                                     temp = tryList.get(q).split("\\"+chargeChar);
                                     if(chargeChar.equals("{")){
                                         w=1;
                                     }
                                     if(chargeChar.equals("}")){
                                         w=2;
                                     }
                                     if(chargeChar.equals("(")){
                                         w=4;
                                     }
                                     if(chargeChar.equals(")")){
                                         w=5;
                                     }
                                 }else{
                                    temp = tryList.get(q).split(chargeChar);
                                 }
                                 if(temp.length==0){
                                     System.out.println("未找到当前分割符");
                                     secList.add(tryList.get(q));
                                 }
                                 else{
                                    for(int h=0;h<temp.length;h++){
                                        if(temp[h].length()!=0){
                             //               System.out.println("欲添加的temp是"+temp[h]);
                                            secList.add(temp[h]);
                                        }                                         
                                    }    
                                 }
                             }
                             tryList.removeAll(tryList);
                          for(int f=0;f<secList.size();f++){
                              if(w==0){
                                  tryList.add(secList.get(f));
                              }else if(w==1){
                                  if(secList.get(f).equals("{")){
                                      
                                  }else{
                                      tryList.add(secList.get(f));
                                  }
                              }else if(w==2){
                                  if(secList.get(f).equals("}")){
                                      
                                  }else{
                                      tryList.add(secList.get(f));
                                  }
                              }
                              else if(w==4){
                                  if(secList.get(f).equals("(")){
                                      
                                  }else{
                                      tryList.add(secList.get(f));
                                  }
                              }
                              else if(w==5){
                                  if(secList.get(f).equals(")")){
                                      
                                  }else{
                                      tryList.add(secList.get(f));
                                  }
                              }
                          }
                          w=0;
                             for(int g=0;g<tryList.size();g++){
                                System.out.println("界符分割后的List是"+tryList.get(g));
                            }
                         }
                     }
                 }
             }//界符划分完毕
             
             //用运算符分割
             tryStr.removeAll(tryStr);
             for(int a =0;a<tryList.size();a++){
                 tryStr.add(tryList.get(a));
             }
             for(int k=0;k<tryStr.size();k++){
                 System.out.println("当前正在处理的单行是"+tryStr.get(k));
                 for(int l=0;l<tryStr.get(k).length()-1;l++){
                     chargeChar =String.valueOf(tryStr.get(k).charAt(l));
                     chargeStr=String.valueOf(tryStr.get(k).charAt(l+1));
                     chargeChar +=chargeStr;
                     System.out.println("当前正在处理的符号是"+chargeChar);
                     if(operator(chargeChar)){
                         if(m==1){
                            for(int g=0;g<tryStr.size();g++){
                                temp = tryStr.get(g).split(chargeChar);
                                for(int h=0;h<temp.length;h++){
                                    if(temp[h].length()!=0){
                                         tryList.add(temp[h]);
                                    }
                                }
                             }
                            m=2;
                            l++;
                            for(int g=0;g<tryList.size();g++){
                                System.out.println("第一次运算符分割后的List是"+tryList.get(g));
                            }
                         }else{
                             System.out.println("不再用tryStr进行分割,tryList的长度是"+tryList.size());
                             int tempInt = tryList.size();
                             secList.removeAll(secList);
                             for(int q=0;q<tempInt;q++){
                       //          System.out.println(q);
                       //          System.out.println("正在处理的tryList串是");
                       //          System.out.println(tryList.get(q));
                                 if(chargeChar.equals("*")){
                                     temp = tryList.get(q).split("\\"+chargeChar);
                                 }else{
                                    temp = tryList.get(q).split(chargeChar);
                                 }
                                 if(temp.length==0){
                                     System.out.println("未找到当前分割符");
                                     secList.add(tryList.get(q));
                                 }
                                 else{
                                    for(int h=0;h<temp.length;h++){
                                        if(temp[h].length()!=0){
                         //                   System.out.println("欲添加的temp是"+temp[h]);
                                            secList.add(temp[h]);
                                        }                                         
                                    }    
                                 }
                             }
                             tryList.removeAll(tryList);
                          for(int f=0;f<secList.size();f++){
                              tryList.add(secList.get(f));
                          }
                          l++;
                             for(int g=0;g<tryList.size();g++){
                                System.out.println("运算符分割后的List是"+tryList.get(g));
                            }
                         }
                     }else  if(operator(String.valueOf(chargeChar.charAt(0)))){
                        chargeChar = String.valueOf(chargeChar.charAt(0));
                         if(m==1){
                            for(int g=0;g<tryStr.size();g++){
                                temp = tryStr.get(g).split(chargeChar);
                                for(int h=0;h<temp.length;h++){
                                    if(temp[h].length()!=0){
                                         tryList.add(temp[h]);
                                    }
                                }
                             }
                            m=2;
                            l++;
                            for(int g=0;g<tryList.size();g++){
                                System.out.println("第一次运算符分割后的List是"+tryList.get(g));
                            }
                         }else{
                             System.out.println("不再用tryStr进行分割,tryList的长度是"+tryList.size());
                             int tempInt = tryList.size();
                             secList.removeAll(secList);
                             for(int q=0;q<tempInt;q++){
                    //             System.out.println(q);
                    //             System.out.println("正在处理的tryList串是");
                    //             System.out.println(tryList.get(q));
                                 if(chargeChar.equals("*")||chargeChar.equals("+")){
                                     temp = tryList.get(q).split("\\"+chargeChar);
                                    
                                 }else{
                                    temp = tryList.get(q).split(chargeChar);
                                 }
                                 if(temp.length==0){
                                     System.out.println("未找到当前分割符");
                                     secList.add(tryList.get(q));
                                 }
                                 else{
                                    for(int h=0;h<temp.length;h++){
                                        if(temp[h].length()!=0){
                    //                        System.out.println("欲添加的temp是"+temp[h]);
                                            secList.add(temp[h]);
                                        }                                         
                                    }    
                                 }
                             }
                             tryList.removeAll(tryList);
                          for(int f=0;f<secList.size();f++){
                              tryList.add(secList.get(f));
                          }
                          l++;
                             for(int g=0;g<tryList.size();g++){
                                System.out.println("运算符分割后的List是"+tryList.get(g));
                            }
                         }
                     }
                 }
             }//运算符划分完毕
             
             
              //判断关键字
             tryStr.removeAll(tryStr);
             for(int a =0;a<tryList.size();a++){
                 tryStr.add(tryList.get(a));
             }
             for(int d=0;d<tryStr.size();d++){
                 if(keyWord(tryStr.get(d))){
                     secList.removeAll(secList);
                     secList.add(tryStr.get(d));
                     tryList.removeAll(secList);
                 }
             }
             for(int g=0;g<tryList.size();g++){
                  System.out.println("关键字判断后的List是"+tryList.get(g));
             }//关键字判断完毕
             
             //分别标识符和数字常量
             secList.removeAll(secList);
             giveCharge=true;
             String tempSS;
             //消除相同的字符串
             for(int e=0;e<tryList.size();e++){
                 tempSS=tryList.get(e);
                 for(int f=0;f<secList.size();f++){
                     if(tempSS.equals(secList.get(f))){
                         giveCharge=false;
                     }
                 }
                 if(giveCharge){
                     System.out.println(tempSS+"添加到secList中");
                     secList.add(tempSS);                     
                 }
                 giveCharge=true;
             }
             tryList.removeAll(tryList);
             for(int e=0;e<secList.size();e++){
                 tempSS=secList.get(e);
                 if(ownIdentifier(tempSS)){
                     tryList.add(tempSS);
                 }else{
                     staticList.add(tempSS);
                 }
             }
             for(int e=0;e<tryList.size();e++){
                 System.out.println("标识符有"+tryList.get(e));
             }
             for(int e=0;e<staticList.size();e++){
                 System.out.println("常量有"+staticList.get(e));
             }
             
        secList=null;
    }
   
    */
    public boolean keyWord(String chargeStr){
        if(CORJ){
            for(int a =0;a<keyWordStr.length;a++){
                    if(chargeStr.equals(keyWordStr[a])){
                        System.out.println("当前匹配的关键字是"+keyWordStr[a]);
                     //   if(keyWordB[a]==false){
                     //   keyWordB[a] = true;
                     //   return true;
                   // }
                    return true;
                }
            }
        }else if(!CORJ){
            for(int a =0;a<keyWords.length;a++){
                    if(chargeStr.equals(keyWords[a])){
                        System.out.println("当前匹配的关键字是"+keyWords[a]);
                     /*   if(keyWordB[a]==false){
                        keyWordB[a] = true;
                        return true;
                    }*/
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean operator(String chargeStr){
        for(int a =0;a<operatorStr.length;a++){
            if(chargeStr.equals(operatorStr[a])){
                System.out.println("当前匹配的标识符是"+operatorStr[a]);
                id=a;
              /*  if(operatorB[a] == false){
                    operatorB[a] = true;
                    return true;
                }*/
                return true;
            }
        }
        return false;
    }
    
    public boolean boundedOperator(String chargeChar){
        for(int i=0;i<boundedStr.length;i++){
            if(chargeChar.equals(boundedStr[i])){
                System.out.println("当前匹配的界符是"+boundedStr[i]);
                id=i;
               /* if(boundedB[i]==false){
                     boundedB[i] = true;
                     return true;
                }*/
                return true;
            }
        }
        return false;
    }
    
    public String ownIdentifier(String tempSS){
        try{
            System.out.println("正在判断的标识符是"+tempSS);
            Double.parseDouble(tempSS);
        }catch(Exception e){
           char ch=tempSS.charAt(0);
            if((('A'<ch)&&('Z'>ch))||(('a'<ch)&&('z'>ch))||('A'==ch)||('Z'==ch)||('z'==ch)||('a'==ch)||('_'>ch)){//第一个字符为下划线或字母
                return "yes";
            }         
            return "error";
        }
        return "number";//是数字
    }
    
    
    
    
    public void chargeCurrentStr(){
        Token token= new Token();
        
        if(keyWord(currentStr)){
            token.setToken(number++,currentStr,type[0],row);
            tokenList.add(token);
        }else{
            if(ownIdentifier(currentStr).equals("yes")){//标识符
                token.setToken(number++,currentStr,type[1],row);
                 tokenList.add(token);
            }else if(ownIdentifier(currentStr).equals("number")){
                token.setToken(number++,currentStr,type[2],row);
                 tokenList.add(token);
            }else{
               ErrorInfo errorInfo =new ErrorInfo(errorInt++,"不合适的标识符",row);
               errorList.add(errorInfo);
            }
        }
        currentStr="";
    }
    
    
    
    
    
    
    
    public void charge(){
        String nextChar;
        String currentChar;
        int cppStrL = cppStr.length();
        int i=0;
        if(CORJ){
            i=1;
        }else{
            i=0;
        }
        for(;i<cppStrL-1;i++){
            Token token = new Token();
            currentChar = String.valueOf(cppStr.charAt(i));
            nextChar=String.valueOf(cppStr.charAt(i+1));
            if(currentChar.equals(" ")||currentChar.equals("\n")||currentChar.equals("  ")) {//忽略空格和换行
                if(currentChar.equals("\n")){
                    row++;
                }
                if(currentStr.equals("#")){
                    continue;
                }else  if(currentStr.length()>0){  
                    chargeCurrentStr();//判别currentStr
                }
                continue;
            }else
            if(currentChar.equals("#")){
                if(currentStr.length()>0){  
                    chargeCurrentStr();//判别currentStr
                }
                currentStr+=currentChar;
                continue;
            }else
            if(currentChar.equals("\"")){
                if(currentStr.length()>0){  // 当识别第一个引号时，currentStr不为空，需先将currentStr判别并置为空
                    chargeCurrentStr();//判别currentStr
                }
                token.setToken(number++,currentChar,28,row);
                tokenList.add(token);
                int j;
                for(j=i+1;j<cppStrL-1;j++){
                    currentChar = String.valueOf(cppStr.charAt(j));
                    if(currentChar.equals("\n")){
                        continue;
                    }else if(currentChar.equals("\"")){
                       break;
                    }else{
                        currentStr +=currentChar;
                    }
                }
                chargeCurrentStr();//判别currentStr
                token.setToken(number++,currentChar,29,row);
                tokenList.add(token);
                i=j;
            }else
            if(boundedOperator(currentChar)){
               if(currentStr.length()>0){  
                    chargeCurrentStr();//判别currentStr
                }
              token.setToken(number++,currentChar,19+id,row);
              tokenList.add(token);
            }else
            if(operator(currentChar)){//运算符分割
                if(currentStr.length()>0){  
                    chargeCurrentStr();//判别currentStr后currentStr为空
                }
                if(operator(currentChar+nextChar)){
                    currentStr+=(currentChar+nextChar);
                    token.setToken(number++,currentStr,3+id,row);
                    tokenList.add(token);
                    i++;
                }else{
                    token.setToken(number++,currentChar,3+id,row);
                    tokenList.add(token);
                }
            }else{
                currentStr +=currentChar;
            }
        }
    }
}

