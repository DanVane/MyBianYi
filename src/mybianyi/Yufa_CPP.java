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
public class Yufa_CPP {

    private List<Token> tokenList = new ArrayList<Token>();
    int i;
    int row;
    private List<Token> identifierList = new ArrayList<Token>();
    private List<Token> operatorList = new ArrayList<Token>();
    private List<ErrorInfo> errorList = new ArrayList<ErrorInfo>();
    private List<String> yuyiList = new ArrayList<String>();
    private String currentStr,nextStr;
    private int number = 0,errorInt = 0;

    public Yufa_CPP(List<Token> tokenList) {
        this.tokenList = tokenList;
        i = tokenList.size();
        System.out.println("输出格式：\n（操作符  运算对象1，运算对象2,运算结果)\n");
        yuyiList.add("输出格式：\n（操作符  运算对象1(运算结果)，运算对象2）\n");
    }
  
    private int tempInt =0;//控制临时变量
    private String temp="temp";
    private String yuyiStr;
    private String returnTemp="";
    public String chargeYuyi(String op,String object1,String object2,String object3){//将翻译的结果存入yuyiList
        switch(op){
            case "+":
                yuyiStr="MOV \t"+"AL ,\t"+object1
                        +"\n ADD \t"+"AL ,\t"+object2
                        +"\n MOV \t"+object3+"\t ,AL";
                break;
            case "-":
                yuyiStr="MOV \t"+"AL ,\t"+object1
                        +"\n SUB \t"+"AL ,\t"+object2
                        +"\n MOV \t"+object3+"\t ,AL";
                break;
            case "*":
                yuyiStr="MOV \t"+"AL ,\t"+object1
                        +"\n MUL \t"+"AL ,\t"+object2
                        +"\n MOV \t"+object3+"\t ,AX";
                break;
            case "/":
                yuyiStr="MOV \t"+"AX ,\t"+object1
                        +"\n DIV \t"+"AX ,\t"+object2
                        +"\n MOV \t"+object3+"\t ,AL";
                break;
            case "%":
               yuyiStr="MOV \t"+"AX ,\t"+object1
                        +"\n DIV \t"+"AX ,\t"+object2
                        +"\n MOV \t"+object3+"\t ,AH";
                break;
            case "%=":
                yuyiStr="MOV \t"+"AX ,\t"+object1
                        +"\n DIV \t"+"AX ,\t"+object2
                        +"\n MOV \t"+object2+"\t ,AL";
                break;
            case "*=":
                yuyiStr="MOV \t"+"AL ,\t"+object1
                        +"\n MUL \t"+"AL ,\t"+object2
                        +"\n MOV \t"+object2+"\t ,AX";
                break;
            case "+=":
                yuyiStr="MOV \t"+"AL ,\t"+object1
                        +"\n ADD \t"+"AL ,\t"+object2
                        +"\n MOV \t"+object2+"\t ,AL";
                break;
            case "-=":
                yuyiStr="MOV \t"+"AL ,\t"+object1
                        +"\n SUB \t"+"AL ,\t"+object2
                        +"\n MOV \t"+object2+"\t ,AL";
                break;
            case "/=":
                yuyiStr="MOV \t"+"AX ,\t"+object1
                        +"\n DIV \t"+"AX ,\t"+object2
                        +"\n MOV \t"+object2+"\t ,AL";
                break;
            case "=":
                yuyiStr="MOV \t"+"AX ,\t"+object2
                        +"\n MOV \t"+object1+"\t ,AX";
                break;
             case "==":
                yuyiStr="MOV \t"+"AX ,\t"+object1
                        +"\n DIV \t"+"AX ,\t"+object2
                        +"\n MOV \t"+object2+"\t ,AL";
                break;
            case "&&"://注意递归调用,避免递归调用的方法是先入栈，再出栈
                yuyiStr="MOV \t"+"AL ,\t"+object1
                        +"\n ADD \t"+"AL ,\t"+object2
                        +"\n MOV \t"+object3+"\t ,AL";
                break;
            case "||"://注意递归调用
                yuyiStr="MOV \t"+"AL ,\t"+object1
                        +"\n ADD \t"+"AL ,\t"+object2
                        +"\n MOV \t"+object3+"\t ,AL";
                break;
            case "while"://注意递归调用
                yuyiStr="";
                break;
            case "if"://注意递归调用
                yuyiStr="";
                break;
            case "JZ"://以}为界限
                yuyiStr="";
                break;
            case "JNZ":
                yuyiStr="";
                break;
            case "#include":
                yuyiStr="INCLUDE "+"\t"+object1;
                break;
            case "define":
                yuyiStr="Proc "+"\t"+object1;
                break;
        }
        System.out.println(yuyiStr);
        yuyiList.add(yuyiStr);
        return returnTemp;
    }
    
    public void setError(int row,int type){
        ErrorInfo errorinfo = new ErrorInfo(errorInt++,row,type);
        System.out.println(errorinfo.printError());
        errorList.add(errorinfo);
    }
    /*table={"关键字","标识符","常数","加号","减号","乘号",
        "除号","取余","赋值","加等于","减等于","乘等于","除等于",
        "取余等于","等于","取非","不等于","AND","OR","左花括号",
        "右花括号","分号","逗号","左括号","右括号","冒号","左尖角","右尖角","左引号","右引号"};*/   
    
    int k, a = 0;
    int currentInt,nextInt;
    boolean op = false;
    boolean error = false;
    Token token,nextToken;
    int currentRow,nextRow;
    String yun1,yun2,ret,wait;
    public boolean charge(){
        int j=0;
        for(;j<i-1;j++){
            token = tokenList.get(j);
            nextToken = tokenList.get(j+1);
            currentInt = token.getType();
            nextInt = nextToken.getType();
            currentStr = token.getContent();
            nextStr = nextToken.getContent();
            currentRow = token.getRow();
            nextRow = nextToken.getRow();
          System.out.println(currentInt+currentStr+nextStr);
           
            if(currentInt==0){//关键字处理
                if(currentStr.equals("#include")){//单独处理头文件包含
                    if(nextStr.equals("<")){//下一符号必须是<或者"
                        if(tokenList.get(j+3).getContent().equals(">")){//下下符号是>
                            chargeYuyi("#include",tokenList.get(j+2).getContent(),"","");
                        }else{
                            setError(token.getRow(),13);
                            error=true;
                        }                        
                    }else if(nextStr.equals("\"")){
                        if(tokenList.get(j+3).getContent().equals("\"")){//下下符号是>
                            chargeYuyi("#include",tokenList.get(j+2).getContent(),"","");
                        }else{
                            setError(token.getRow(),14);
                            error=true;
                        }
                    }else{//头文件出错
                       setError(currentRow,0);
                       error=true;
                    }
                    //跳至下一行
                    for(++j;j<i-1;j++){
                        if(tokenList.get(j).getRow()>currentRow){
                            break;
                        }
                    }
                    j--;
                    continue;
                }//#include
                if(currentStr.equals("void")){//处理函数头,如果是函数说明，则跳过语义，否则翻译
                    System.out.println("void"+nextStr+nextInt);
                    if(nextInt==1||nextToken.getContent().equals("main")){//下一字符应是标识符，否则出错     
                        if(tokenList.get(j+2).getContent().equals("(")){
                           k=j+3;
                           a =k;
                        }else{
                            setError(nextRow,7);
                            error=true;
                            k=j+2;
                            a=k;
                        }
                        for(k=j+2;k<i-1;k++){
                            if(tokenList.get(k).getRow()>currentRow){
                                 System.out.println("next:"+k);
                                 a=k;
                                break;
                            }
                        }
                        if(tokenList.get(k-1).getContent().equals(";")){//函数说明
                            if(!tokenList.get(k-2).getContent().equals(")")){
                                setError(nextRow,6);
                                error=true;
                            }
                            j =k;
                            continue;
                        }else{//函数
                            //此处匹配{}  
                            if(!(tokenList.get(k-1).getContent().equals(")")||tokenList.get(k-2).getContent().equals(")"))){
                                setError(nextRow,6);
                                error=true;
                            }
                            int rownum=0;
                           if(tokenList.get(k-1).getContent().equals("{")){
                          k=k-1;
                          rownum++;
                                }else if(tokenList.get(k).getContent().equals("{")){
                                    rownum++;
                                }else{
                                    setError(nextRow,17);
                                    error=true;
                                } 
                           boolean dog=false;
                            for(k++;k<i;k++){
                               if(tokenList.get(k).getContent().equals("{")){
                                    rownum++;
                                    dog = true;
                                }else if(tokenList.get(k).getContent().equals("}")){
                                    rownum--;
                                    dog=true;
                                }
                               if(dog&& rownum==0){
                                   break;
                               }
                            }
                            if(rownum==0){
                                
                            }else if(rownum>0){
                                setError(nextRow,16);
                                error=true;
                            }else{
                                setError(nextRow,17);
                                error=true;
                            }
                            continue;
                        }
                    }else{
                         setError(nextRow,15);
                         error=true;
                    }
                   
                }else{//其他关键字
                    if(!(currentStr.equals("main")||currentStr.equals("cout"))){
                        if(nextInt!=1){
                        System.out.println(currentStr+"   "+nextStr);
                        setError(currentRow,18);
                         error=true;
                    }
                    }
                    
                }  
                
            }else
            if(currentInt ==1){//标识符处理
                System.out.println("biaozhifu"+currentStr+nextStr);
                boolean add = true;
                for(int n=0;n<identifierList.size();n++){
                    if(currentStr.equals(identifierList.get(n).getContent())){
                        add=false;
                        break;
                    }
                }             
                if(add){
                    Token temptoken = token;
                    identifierList.add(temptoken);
                }
                if(currentRow!=nextRow){
                   
                             setError(currentRow,4);
                       
                 
                }
            }else
            if(currentInt==2){//常数处理
                
            }else
            if(2<currentInt && currentInt<19){ 
                //算术逻辑运算符
        /*        yun1= tokenList.get(j-1).getContent();
                if(currentInt==8){//赋值
                   wait = yun1;
                   op=true;
                }
                    if(currentInt==3){
                        chargeYuyi("+",tokenList.get(j+2).getContent(),"","");
                    }else if(currentInt==4){
                        chargeYuyi("-",tokenList.get(j+2).getContent(),"","");
                    }else if(currentInt==5){
                        chargeYuyi("*",tokenList.get(j+2).getContent(),"","");
                    }else if(currentInt==6){
                        chargeYuyi("/",tokenList.get(j+2).getContent(),"","");
                    }else if(currentInt==7){
                        chargeYuyi("%",tokenList.get(j+2).getContent(),"","");
                    }else if(currentInt==9){
                        chargeYuyi("+=",tokenList.get(j+2).getContent(),"","");
                    }else if(currentInt==10){
                        chargeYuyi("-=",tokenList.get(j+2).getContent(),"","");
                    }else if(currentInt==11){
                        chargeYuyi("*=",tokenList.get(j+2).getContent(),"","");
                    }else if(currentInt==12){
                        chargeYuyi("/=",tokenList.get(j+2).getContent(),"","");
                    }else if(currentInt==13){
                        chargeYuyi("%=",tokenList.get(j+2).getContent(),"","");
                    }else if(currentInt==14){
                        chargeYuyi("==",tokenList.get(j+2).getContent(),"","");
                    }else if(currentInt==15){
                        chargeYuyi("!",tokenList.get(j+2).getContent(),"","");
                    }else if(currentInt==16){
                        chargeYuyi("!=",tokenList.get(j+2).getContent(),"","");
                    }else if(currentInt==17){
                        chargeYuyi("AND",tokenList.get(j+2).getContent(),"","");
                    }else if(currentInt==18){
                        chargeYuyi("OR",tokenList.get(j+2).getContent(),"","");
                    }
                */
            }else{
                if(currentRow!=nextRow){
                    if(!(currentStr.equals(">")||currentStr.equals(")")||currentStr.equals("{")||currentStr.equals("}"))){
                        if(!currentStr.equals(";")){
                             setError(currentRow,4);
                        }
                       
                    }
                }
            }
        }
        return error;
    }

    public List<ErrorInfo> getErrorList() {
        return errorList;
    }
    
    

}
