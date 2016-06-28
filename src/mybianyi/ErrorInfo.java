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
public class ErrorInfo {
    private int id;
    private int row;
    public static String[] errorMes = {"编译头文件有语法错 ","不明确的符号","调用函数时没有函数的说明","分程序漏掉'{' ","缺少'；'",
    "用零作除数 ","函数调用缺少右括号 ","函数调用缺少左括号 ","不相容的类型转换","重复定义","下标缺少右方括号","语句缺少左括号 ",
    "语句缺少右括号 ","语句缺少右尖角","语句缺少引号","非法函数名","函数调用缺少右花括号 ","函数调用缺少左花括号 ","定义错误"};
    private String errorStr;            

    public void changeToString(int errorType){
        errorStr = errorMes[errorType];
    }
    
    public ErrorInfo(int id, int row,int errorType) {
        this.id = id;
        this.row = row;
        changeToString(errorType);
    }
    
     public ErrorInfo(int id, String str,int row) {
        this.id = id;
        this.row = row;
        errorStr = str;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
    
    public String printError(){        
       return ("    "+id+":"+"  "+errorStr+" "+";行号："+row);
    }
    
}
