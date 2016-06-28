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
public class Token {
    private int number;
    private String content;
    private int type;
    private String typeStr;
    private int row;
    private boolean caozuo=false;
    
    public static String[] table={"关键字","标识符","常数","加号","减号","乘号",
        "除号","取余","赋值","加等于","减等于","乘等于","除等于",
        "取余等于","等于","取非","不等于","AND","OR","左花括号",
        "右花括号","分号","逗号","左括号","右括号","冒号","左尖角","右尖角","左引号","右引号"};
    

    public Token() {
    }

    public Token(int number, String content, int row) {
        this.number = number;
        this.content = content;
        this.row = row;
    }
    
    
    public void setToken(int number, String content, int type,int row) {
        this.number = number;
        this.content = content;
        this.type = type;
        this.row=row;
        chargeTypeStr();
        System.out.println("setToken"+number+content+type);
    }
    
    public void setToken(int number, String content, int row) {
        this.number = number;
        this.content = content;
        this.row=row;
    }
    
   public void  chargeTypeStr(){
       typeStr = table[type];
    }
    
    public String printToken(){        
       return ("    "+number+":"+"  "+typeStr+" "+content+";行号："+row);
    }

    public int getRow() {
        return row;
    }

    public String getContent() {
        return content;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public int getNumber() {
        return number;
    }

    public int getType() {
        return type;
    }

    public boolean isCaozuo() {
        return caozuo;
    }

    public void setCaozuo(boolean caozuo) {
        this.caozuo = caozuo;
    }
   
    
}
