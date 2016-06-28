/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mybianyi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class MyBianYi extends JFrame implements ActionListener{

    int COL = 12, ROW = 12;
    private JFrame frame,subFrame;
    private JPanel panel1,panel2,panel3,panel4,panel5;
    private JMenuBar menubar1;
    private JMenu menu1, menu5,menu6;
    private JMenuItem menuitem11, menuitem12, menuitem13, menuitem14, menuitem19; 
    private JMenuItem menuitem51;
    private JMenuItem menuitem61;
    private JTextArea text2,text3,text4;  
    private JTextField tf1,tf2,tf3,tf4,tf5,tf6;
    private JButton button1,button2,button3,button4,button5,button6,button7;
    private JLabel label1,label2,label3,label4,label5,label6,label7,label8,label9,label10;
    private ButtonGroup bg1,bg2,bg3;
    private JRadioButton rbt1,rbt2,rbt3,rbt4,rbt5,rbt7,rbt8,rbt10;
    private String chargeStr;
     private JTable table,tabel1;
     private DefaultTableModel dtm,dtm1;
      private String[] endStr,nEndStr;
    private wenfa[] wenfaZu;
    private FileDialog file_dialog_load;
    OPGPoint[] opgs;
    
    private java.util.List<Token> tokenList = new ArrayList<Token>();
    private java.util.List<DFAPoint> pointList = new ArrayList<DFAPoint>();
   private java.util.List<ErrorInfo> errorList = new ArrayList<ErrorInfo>();
    String tfStr1 ;
        String tfStr2 ;
        char firstC ;
        char endC;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Font font = new Font("华文楷体",Font.BOLD,16);
        UIManager.put("Button.font", font); 
        UIManager.put("Menu.font", font);
        UIManager.put("MenuItem.font", font);
        UIManager.put("Label.font",font);
        MyBianYi mybianyi = new MyBianYi();
        mybianyi.form();
    }
    
    public void form(){
        frame = new JFrame();     
        frame.setLayout(new BorderLayout());
        frame.setTitle("风向标编译器");
        subFrame = new JFrame();
        
        table=new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(1060,100));
         tabel1=new JTable();
        tabel1.setPreferredScrollableViewportSize(new Dimension(1060,100));
        
        ImageIcon background = new ImageIcon(getClass().getResource("333732.jpg"));
	JLabel label = new JLabel(background);
	label.setBounds(0, 0, background.getIconWidth(),background.getIconHeight());
	JPanel imagePanel = (JPanel) frame.getContentPane();
	imagePanel.setOpaque(false);
		
	frame.getLayeredPane().setLayout(null);
	frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        panel1 = new JPanel( );
        panel1.setOpaque(false);
        panel2 = new JPanel();
        panel2.setOpaque(false);
        
   
        menubar1 = new JMenuBar();
        menubar1.setOpaque(false);
	menu1 = new JMenu("文件(F)");
	menu5 = new JMenu("Compile(C)");
	menu6 = new JMenu("帮助(H)");
	menuitem11 = new JMenuItem("新建");
	menuitem12 = new JMenuItem("打开");
	menuitem13 = new JMenuItem("关闭");
	menuitem14 = new JMenuItem("保存");
	menuitem19 = new JMenuItem("退出");
	menu1.add(menuitem11);
	menu1.add(menuitem12);
	menu1.add(menuitem13);
	menu1.add(menuitem14);	
        menu1.addSeparator();
	menu1.add(menuitem19);
        
        
        menuitem51 = new JMenuItem("一键");
	menu5.add(menuitem51);
        
        menuitem61 = new JMenuItem("关于……");
	menu6.add(menuitem61);
        
	menubar1.add(menu1);
	menubar1.add(menu5);
	menubar1.add(menu6);
	frame.setJMenuBar(menubar1);
	menuitem11.addActionListener(this);
	menuitem12.addActionListener(this);
	menuitem13.addActionListener(this);
	menuitem14.addActionListener(this);
	menuitem19.addActionListener(this);
	menuitem51.addActionListener(this);
	menuitem61.addActionListener(this);
        
        label1 = new JLabel("词法分析");
        label2 = new JLabel("语法分析");
        
        bg1 = new ButtonGroup();
        bg2 = new ButtonGroup();
        
        rbt1 = new JRadioButton("C++");
        rbt2 = new JRadioButton("Java");
        rbt7 = new JRadioButton("正规文法");
        rbt10 = new JRadioButton("跳过");
        rbt7.addActionListener(this);
        bg1.add(rbt1);
        bg1.add(rbt2);
        bg1.add(rbt7);
        bg1.add(rbt10);
        rbt3 = new JRadioButton("LR(0)");
        rbt4 = new JRadioButton("LL(1)");
        rbt5 = new JRadioButton("算符优先");
        rbt8 = new JRadioButton("跳过");
        rbt4.addActionListener(this);
        rbt5.addActionListener(this);
        bg2.add(rbt3);
        bg2.add(rbt4);
        bg2.add(rbt5);
        bg2.add(rbt8);
        
        panel1.setLayout(new GridLayout(10,1));
        panel1.add(label1);
        panel1.add(rbt1);
        panel1.add(rbt2);
        panel1.add(rbt7);
        panel1.add(rbt10);
        panel1.add(label2);
        panel1.add(rbt3);
        panel1.add(rbt4);
        panel1.add(rbt5);
        panel1.add(rbt8);        
        
       
         text2=new JTextArea(20,20);
         text3=new JTextArea(20,20);
         text4=new JTextArea(20,20);
         text2.setFont(new   Font( "标楷体 ",Font.BOLD,16));
         text3.setFont(new   Font( "标楷体 ",Font.BOLD,16));
         text4.setFont(new   Font( "标楷体 ",Font.BOLD,16));
         text2.setLineWrap(true);
         text2.setWrapStyleWord(true);
         text3.setLineWrap(true);
         text3.setWrapStyleWord(true);
         text4.setLineWrap(true);
         text4.setWrapStyleWord(true);
            
         JScrollPane jp1=new JScrollPane(text2);
         JScrollPane jp2=new JScrollPane(text3);
         JScrollPane jp3=new JScrollPane(text4);
         
         panel2.add(jp1);
         panel2.add(jp2);
         panel2.add(jp3);
         
         panel3 = new JPanel(new GridLayout(2,1));
         panel3.setOpaque(false);
        panel3.add(new JScrollPane(table));
         panel3.add(new JScrollPane(tabel1));
         panel4 = new JPanel();
         panel4.setOpaque(false);
         panel4.setLayout(new GridLayout(11,1));
	label4 = new JLabel("输入文法产生式：");
        label5 = new JLabel("输入字符串");
        label6 = new JLabel("→");
        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        button1 = new JButton("添加");
        button2 = new JButton("重置");
        button3 = new JButton("导入");
        button4 = new JButton("判断");
        button5 = new JButton("分析");
        button1.addActionListener(this);
	button2.addActionListener(this);
        button3.addActionListener(this);
	button4.addActionListener(this);
        button5.addActionListener(this);
        panel4.add(label4);
        panel4.add(tf1);
        panel4.add(label6);
        panel4.add(tf2);
        panel4.add(button1);
        panel4.add(button2);
        panel4.add(button3);
        panel4.add(button4);
        panel4.add(label5);
        panel4.add(tf3);
        panel4.add(button5);
    
        file_dialog_load = new FileDialog(this, "Open file...", FileDialog.LOAD);
		
        frame.add(panel1,"West");
       frame.add(panel2,"Center");
       frame.add(panel3,"South");
       frame.add(panel4,"East");
	frame.setSize(1200, 700);
	frame.setResizable(false);
	frame.setLocation(80, 15);
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        button6 = new JButton("确定");
        button7 = new JButton("放弃");
        button6.addActionListener(this);
        button7.addActionListener(this);
        label7 = new JLabel("请输入该文法的非终结符");
        label8 = new JLabel("请输入该文法的终结符");
        label9 = new JLabel("请输入初始状态：");
        tf4 = new JTextField(20);
        tf5 = new JTextField(20);
        tf6 = new JTextField(20);
        panel5 = new JPanel();
        panel5.setLayout(new GridLayout(4,2));
        panel5.add(label9);
        panel5.add(tf6);
        panel5.add(label7);
        panel5.add(tf4);
        panel5.add(label8);
        panel5.add(tf5);
        panel5.add(button6);
        panel5.add(button7);
        subFrame.add(panel5);
        subFrame.setSize(400,300);
        subFrame.setLocation(400, 300);
        subFrame.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == menuitem19){
            System.exit(0);
        }else if(e.getSource()==menuitem11){
            text2.setText(null);
            text3.setText(null);
            text4.setText(null);
        }else if(e.getSource() == rbt7){
            subFrame.setVisible(true);
        }else if(e.getSource() == rbt4){
            subFrame.setVisible(true);
        }
        else if(e.getSource() == rbt5){
            subFrame.setVisible(true);
        }
        else if(e.getSource() == menuitem12){
               file_dialog_load.setVisible(true);
      
               File myfile = new File(file_dialog_load.getDirectory(), file_dialog_load.getFile());
               try{   
                    InputStreamReader read = new InputStreamReader(new FileInputStream(myfile), "UTF-8");    
                    BufferedReader bufReader = new BufferedReader(new FileReader(myfile));
                    String content = "";
                    String str;
                           
                    while((str = bufReader.readLine()) != null){
                          content += str + "/n";
                          text2.setText(content);
              }
        }catch(IOException ie){
              System.out.println("IOexception occurs...");
        }
    }else if(e.getSource()==button1){
        String a=tf1.getText();
        String b=tf2.getText();
        System.out.println(a+'→'+b);
        text2.append(a+'→'+b+'\n');
    }else if(e.getSource()==button2){
        tf1.setText(null);
        tf2.setText(null);
    }else if(e.getSource()==button3){
        file_dialog_load.setVisible(true);
      
        File myfile = new File(file_dialog_load.getDirectory(), file_dialog_load.getFile());
        try{   
            InputStreamReader read = new InputStreamReader(new FileInputStream(myfile), "UTF-8");    
            BufferedReader bufReader = new BufferedReader(new FileReader(myfile));
            String content = "";
            String str;
                           
              while((str = bufReader.readLine()) != null){
                     content += str + "\n";
                     text2.setText(content);
                }
        }catch(IOException ie){
               System.out.println("IOexception occurs...");
        }
    }else if(e.getSource()==button4){
        clean();
        chargeStr ="c";
        if(bg1.getSelection()==rbt1.getModel()){
           chargeStr +="1";
        }else if(bg1.getSelection()==rbt2.getModel()){
            chargeStr += "2";
        }else if(bg1.getSelection()==rbt7.getModel()){
            chargeStr += "3";
        }else if(bg1.getSelection()==rbt10.getModel()){
            chargeStr += "4";
        }
        if(bg2.getSelection() == rbt3.getModel()){
            chargeStr += "1";
        }else if(bg2.getSelection() == rbt4.getModel()){
            chargeStr +="2";
        }else if(bg2.getSelection() == rbt5.getModel()){
            chargeStr +="3";
        }else if(bg2.getSelection() == rbt8.getModel()){
            chargeStr +="4";
        }
        System.out.println(chargeStr);
        String str1 = text2.getText();
        if(str1.length()<=1){
            System.out.println("请先输入待判断的内容！");
        }else{
            
            if(chargeStr.length()<=1){
                System.out.println("请选择词法分析!");
            }else{
                if(chargeStr.charAt(1)=='1'){
                   clean();
                    tokenList.removeAll(tokenList);
                     Cifa_CPP cifaCpp = new Cifa_CPP(str1,true);
                     cifaCpp.charge();
                     tokenList = cifaCpp.getTokenList();
                     errorList = cifaCpp.getErrorList();
                     text3.setText(null);
                     text3.append("词法分析结果如下：\n");
                     for(int i=0;i<tokenList.size();i++){
                         text3.append(tokenList.get(i).printToken()+"\n");
                     }
                      text3.append("*****************\n");
                     text3.append("词法错误出现如下：\n");
                     for(int i=0;i<errorList.size();i++){
                         text3.append(errorList.get(i).printError()+"\n");
                     }
                      text3.append("*****************\n");
                     if(errorList.size()==0){
                         text3.append("无词法错误\n");
                     }
                     Yufa_CPP yufacpp = new Yufa_CPP(tokenList);
                     boolean right = yufacpp.charge();
                     errorList = yufacpp.getErrorList();
                     if(right){
                          text4.append("存在语法错误\n");
                     }else{
                         text4.append("无语法错误\n");
                     }
                     charge2();
                }else if(chargeStr.charAt(1)=='2'){
                    clean();
                    tokenList.removeAll(tokenList);
                      Cifa_CPP cifaCpp = new Cifa_CPP(str1,false);
                     cifaCpp.charge();
                     tokenList = cifaCpp.getTokenList();
                     errorList = cifaCpp.getErrorList();
                     text3.setText(null);
                     text3.append("词法分析结果如下：\n");
                     for(int i=0;i<tokenList.size();i++){
                         text3.append(tokenList.get(i).printToken()+"\n");
                     }
                     text3.append("*****************\n");
                     text3.append("词法错误出现如下：\n");
                     for(int i=0;i<errorList.size();i++){
                         text3.append(errorList.get(i).printError()+"\n");
                     }
                     text3.append("*****************\n");
                     if(errorList.size()==0){
                         text3.append("无词法错误\n");
                     }
                }else if(chargeStr.charAt(1)=='3'){
                    String DFAStr = text2.getText();
                    Cifa_DFA cifaDFA = new Cifa_DFA(firstC,'Z',tfStr1,tfStr2,DFAStr);
                    cifaDFA.change();
                    pointList = cifaDFA.getPointList();
                    int j=pointList.size();
                    text3.append(cifaDFA.getW());
                    text4.append("生成的DFA为：\n");
                    for(int i=0;i<j;i++){
                        text4.append(pointList.get(i).printPoint());
                    }
                }else if(chargeStr.charAt(1)=='4'){
                    
                }
            }
            if(chargeStr.length()<=2){
                System.out.println("请选择语法分析!");
            }else{
                if(chargeStr.charAt(2)=='1'){
                
                }else if(chargeStr.charAt(2)=='2'){
                    clean();
                    String tempS = text2.getText();
                    LL1 ll1 = new LL1(firstC,'Z',tfStr1,tfStr2,tempS);
                    boolean yN=ll1.charge();
                     wenfaZu = ll1.getWenfaZu();
                    FFSet[] ffZu = ll1.getFfZu();
                    int j=ffZu.length;
                    text3.append("first集如下：\n");
                    for(int i=0;i<j;i++){
                        text3.append("First("+ffZu[i].getnEndChar()+")={"+ffZu[i].printFirst(0)+"}\n");
                    }
                    text3.append("\n");
                    text3.append("follow集如下：\n");
                    for(int i=0;i<j;i++){
                        text3.append("Follow("+ffZu[i].getnEndChar()+")={"+ffZu[i].printFollow(0)+"}\n");
                    }
                     j=wenfaZu.length;
                    text4.append("select集如下：\n");
                    for(int i=0;i<j;i++){
                        text4.append("Select("+wenfaZu[i].getWenfaStr()+")={"+wenfaZu[i].printSelect(1)+"}\n");
                    }
                    text4.append("\n");
                    if(yN){
                        text4.append("该文法是LL1文法\n");
                        endStr=ll1.getEndStr();
                        nEndStr = ll1.getnEndStr();
                        showTable();
                    }else{
                        text4.append("该文法不是LL1文法\n");
                    }
                    
                }else if(chargeStr.charAt(2)=='3'){
                     clean();
                    String tempS = text2.getText();
                    OPG opg = new OPG(firstC,'Z',tfStr1,tfStr2,tempS);
                    boolean yN=opg.charge();
                    FLSet[] flZu = opg.getFlZu();
                    int j=flZu.length;
                    text3.append("firstvt如下：\n");
                    for(int i=0;i<j;i++){
                        text3.append("FirstVT("+flZu[i].getnEndChar()+")={"+flZu[i].printFirst(0)+"}\n");
                    }
                    text3.append("\n");
                    text3.append("lastvt如下：\n");
                    for(int i=0;i<j;i++){
                        text3.append("LastVT("+flZu[i].getnEndChar()+")={"+flZu[i].printLast(0)+"}\n");
                    }
                    text4.append("\n");
                    if(!yN){
                        endStr=opg.getEndStr();
                        nEndStr = opg.getnEndStr();
                         opgs=opg.getOpgZu();
                       if(showTable2()){
                           text4.append("该文法是OPG文法\n");
                       }else{
                           text4.append("该文法不是OPG文法\n");
                       }
                       
                    }else{
                        text4.append("该文法不是OPG文法\n");
                    }
                
                }else if(chargeStr.charAt(3)=='4'){
                
                }
            }
            if(chargeStr.length()<=3){
                System.out.println("请选择语义分析!");
            }else{
                if(chargeStr.charAt(3)=='1'){
                
                }else if(chargeStr.charAt(3)=='2'){
                
                }
            }
        }
    
    }else if(e.getSource()==button5){
        if(chargeStr.charAt(1)=='3'){
            tfStr1 = tf3.getText();
            charge1();
        }
        if(chargeStr.charAt(2)=='2'){
            tfStr1 =  tf3.getText();
            charge();
        }
        if(chargeStr.charAt(2)=='3'){
            tfStr1 =  tf3.getText();
            charge3();
        }
    }else if(e.getSource()==button6){
         tfStr1 = tf4.getText();
         tfStr2 = tf5.getText();
         firstC = tf6.getText().charAt(0);
         subFrame.setVisible(false);
    }else if(e.getSource()==button7){
        tf4.setText(null);
        tf5.setText(null);
        tf6.setText(null);
        subFrame.setVisible(false);
    }
}
    
    String[][] data;
        String[] col;
    public void showTable(){
        dtm=new DefaultTableModel();//显示预测分析表
             table.setModel(dtm);
         data=new String[nEndStr.length][endStr.length+1];
        col = new String[endStr.length+1];
        String tempStr,tempChar,currentChar;
        int tempRow=0,tempCol=0;
        java.util.List<String> tempList = new ArrayList<String>();
        col[0]="";
        for(int i=1;i<endStr.length+1;i++){
            if(endStr[i-1].equals("ε")){
                col[i]="#";
            }else{
                col[i]=endStr[i-1];
            }
        }
        
        for(int i=0;i<nEndStr.length;i++){
            data[i][0]=nEndStr[i];
        }
        
        for(int i=0;i<wenfaZu.length;i++){
            tempChar = String.valueOf(wenfaZu[i].getWenfaStr().charAt(0));
            tempStr=wenfaZu[i].getWenfaStr().substring(1);
            tempList=wenfaZu[i].getSelect();
            for(int j=0;j<data.length;j++){
                if(tempChar.equals(data[j][0])){
                    tempRow =j;
                    break;
                }
            }
            for(int j=0;j<tempList.size();j++){
                currentChar = tempList.get(j);
                for(int q=0;q<col.length;q++){
                    if(currentChar.equals(col[q])){
                        tempCol=q;
                        break;
                    }
                }
                data[tempRow][tempCol]=tempStr;
            }
        }
        dtm=new DefaultTableModel(data,col);//显示预测分析表
             table.setModel(dtm);
    }
    
    public boolean showTable2(){
        System.out.println("table2");
        dtm=new DefaultTableModel();//显示预测分析表
        table.setModel(dtm);
         data=new String[endStr.length+1][endStr.length+1];
        col = new String[endStr.length+1];
        String tempStr,tempChar,currentChar;
        int tempRow=0,tempCol=0;
        java.util.List<String> tempList = new ArrayList<String>();
        col[0]="";
        for(int i=1;i<endStr.length+1;i++){
                col[i]=endStr[i-1];
                data[i-1][0]=endStr[i-1];
        }
        
        for(int i=0;i<opgs.length;i++){
            tempStr = String.valueOf(opgs[i].getEndChar());
            tempList = opgs[i].getFirstvt();
            for(int j=0;j<tempList.size();j++){
                tempChar = tempList.get(j);
                for(int q=0;q<data.length;q++){
                    if(tempStr.equals(data[q][0])){
                        tempRow =q;
                        break;
                    }
               }
                for(int q=0;q<col.length;q++){
                    if(tempChar.equals(col[q])){
                        tempCol=q;
                        break;
                    }
                }
                data[tempRow][tempCol]="<";
            }
             tempList = opgs[i].getDengyuvt();
            for(int j=0;j<tempList.size();j++){
                tempChar = tempList.get(j);
                for(int q=0;q<data.length;q++){
                    if(tempStr.equals(data[q][0])){
                        tempRow =q;
                        break;
                    }
               }
                for(int q=0;q<col.length;q++){
                    if(tempChar.equals(col[q])){
                        tempCol=q;
                        break;
                    }
                }
            //    if(data[tempRow][tempCol]){
                    data[tempRow][tempCol]="=";
           //     }else{
            //        return false;
           //    }
            }
            tempList = opgs[i].getLastvt();
            for(int j=0;j<tempList.size();j++){
                tempChar = tempList.get(j);
                for(int q=0;q<data.length;q++){
                    if(tempChar.equals(data[q][0])){
                        tempRow =q;
                        break;
                    }
               }
                for(int q=0;q<col.length;q++){
                    if(tempStr.equals(col[q])){
                        tempCol=q;
                        break;
                    }
                }
//                if(data[tempRow][tempCol].isEmpty()){
                    data[tempRow][tempCol]=">";
          //      }else{
          //          return false;
          //      }
            }
            
        }
        dtm=new DefaultTableModel(data,col);//显示预测分析表
             table.setModel(dtm);
             return true;
    }
    
    public void charge(){//tfstr1
        dtm1=new DefaultTableModel();
        tabel1.setModel(dtm1);
        tfStr1+="#";
        String[][] data1=new String[25][5];
        String[] col1 = {"步骤","分析栈","当前字符","剩余串","动作"};
        int step =1,tempRow=0,tempCol=0,a;
        String currentStr="#S";
        String currentChar="",firstChar="",actionStr="";
        boolean error=false;
        for(int i=0;i<tfStr1.length();){
            if(!actionStr.isEmpty()){//actionStr不为空
                System.out.println("last char:"+actionStr.charAt(actionStr.length()-1));
                if("ε".equals(String.valueOf(actionStr.charAt(actionStr.length()-1)))){
                     currentStr=currentStr.substring(0,currentStr.length()-1);
                }else{
                    a = actionStr.length();
                    currentStr = currentStr.substring(0, currentStr.length()-1);
                    for(int b=a-1;b>1;b--){
                        currentStr+=actionStr.charAt(b);
                    }
                }
            }
            currentChar= String.valueOf(tfStr1.charAt(i));
            firstChar=String.valueOf(currentStr.charAt(currentStr.length()-1));
                data1[step][0]=String.valueOf(step);
                data1[step][1]=currentStr;
                data1[step][2]=currentChar;
                data1[step][3]=tfStr1.substring(i+1);
                if(firstChar.equals("#")&&currentChar.equals("#")){
                    data1[step][4]="匹配成功";
                    break;
                }
                if(firstChar.equals(currentChar)){
                    data1[step][4]=firstChar+"匹配";
                    currentStr=currentStr.substring(0,currentStr.length()-1);
                    i++;
                    actionStr="";
                    continue;
                }else{
                for(int j=0;j<col.length;j++){
                    if(currentChar.equals(col[j])){
                        tempCol = j;
                        System.out.println(j);
                        break;
                    }
                }
                for(int j=0;j<data.length;j++){
                    if(firstChar.equals(data[j][0])){
                        tempRow =j;
                        System.out.println(j);
                        break;
                    }
                }
                if(tempRow == tempCol && tempRow==0){
                    error = true;
                    break;
                }
                if(data[tempRow][tempCol].length()>1){
                    actionStr = firstChar+data[tempRow][tempCol];
                    data1[step][4] = actionStr;
                    tempRow = tempCol =0;
                }else{
                    error = true;
                    break;
                }
                }
                step++;
        }
        if(error){
            data1[++step][0]="匹配错误";
        }
        dtm1=new DefaultTableModel(data1,col1);//显示预测分析表
             tabel1.setModel(dtm1);
    }
    
    public void charge1(){
          dtm1=new DefaultTableModel();
        tabel1.setModel(dtm1);
        String[][] data1=new String[25][5];
        String[] col1 = {"步骤","过程节点","当前字符","剩余串","路径"};
        int step =0;
        String currentStr="0";
        String currentChar="";
        int firstChar,q;
        boolean error=false;
        for(int i=0;i<tfStr1.length();i++){
            step++;
            data1[step][0] = String.valueOf(step);
            data1[step][1] = currentStr;
            data1[step][2] = currentChar;
            data1[step][3] = tfStr1.substring(i+1);
            
            firstChar = Integer.parseInt(String.valueOf(currentStr.charAt(currentStr.length()-1)));
            currentChar = String.valueOf(tfStr1.charAt(i));
            for(int a=0;a<pointList.size();a++){
                if(firstChar==pointList.get(a).getCurrentInt()){
                    q=pointList.get(a).findRoad(currentChar);
                    if(q!=100){
                        q=pointList.get(a).findInt(q);
                        currentStr+=q;
                        data1[step][4]=firstChar+currentChar+q;
                    }else{
                        error = true;
                        data1[++step][0]="匹配失败";
                        break;
                    }
                }
            }
            
            if(i==tfStr1.length()-1){
                firstChar = Integer.parseInt(String.valueOf(currentStr.charAt(currentStr.length()-1)));
               for(int a=0;a<pointList.size();a++){
                   if(firstChar==pointList.get(a).getCurrentInt()){
                       if(pointList.get(a).isEnd()){
                           break;
                       }else{
                           error = true;
                            data1[++step][0]="匹配失败";
                       }
                   }
               }
                
            }
            if(error){
                break;
            }
            System.out.println(data1[step][0]+data1[step][1]+data1[step][2]+data1[step][3]+data1[step][4]);
        }
              if(!error){
                  data1[++step][0]="匹配成功";
              }  
        dtm1=new DefaultTableModel(data1,col1);//显示预测分析表
             tabel1.setModel(dtm1);
    }
    
    public void clean(){
        text3.setText(null);
        text4.setText(null);
        dtm=new DefaultTableModel();//显示预测分析表
             table.setModel(dtm);
        dtm1=new DefaultTableModel();//显示预测分析表
             tabel1.setModel(dtm1);
    }
    
    public void charge2(){
         dtm1=new DefaultTableModel();
        tabel1.setModel(dtm1);
        String[][] data1=new String[25][2];
        String[] col1 = {"序号","详情"};
        int step =0;
        String currentStr;
        for(int i=0;i<errorList.size();i++){
            step++;
            data1[step][0] = String.valueOf(step);
            data1[step][1] = errorList.get(i).printError();
        }
        dtm1=new DefaultTableModel(data1,col1);//显示预测分析表
         tabel1.setModel(dtm1);
    }
    
    public void charge3(){
          dtm1=new DefaultTableModel();
        tabel1.setModel(dtm1);
        String[][] data1=new String[25][6];
        String[] col1 = {"步骤","栈","优先关系","当前字符","剩余串","动作"};
        int step =0,tempRow =0,tempCol =0,beginInt=0,endInt=0;
        String[] buffer = new String[20];
        int bufferInt=0;
        buffer[bufferInt++] ="#";
        String currentStr="#";
        String currentChar="",op;
        String firstChar;
        boolean error=false;
        for(int i=0;i<tfStr1.length();){
            tempRow = tempCol =0;
            firstChar = String.valueOf(currentStr.charAt(currentStr.length()-1));
            if(firstChar.equals("N")){
                firstChar = String.valueOf(currentStr.charAt(currentStr.length()-2));
            }
            currentChar = String.valueOf(tfStr1.charAt(i));
            step++;
            data1[step][0] = String.valueOf(step);
            data1[step][1] = currentStr;
            data1[step][3] = currentChar;
            data1[step][4] = tfStr1.substring(i+1);
            for(int q=0;q<data.length;q++){
                System.out.println(data[q][0]);
                    if(firstChar.equals(data[q][0])){
                        tempRow =q;
                        break;
                    }
               }
                for(int q=0;q<col.length;q++){
                    if(currentChar.equals(col[q])){
                        tempCol=q;
                        break;
                    }
                }
                if(data[tempRow][tempCol].length()<0||tempCol==0){
                    System.out.println(""+firstChar+tempRow+tempCol);
                    data1[step][5]="匹配失败";
                    error = true;
                    break;
                }else{
                     data1[step][2] = data[tempRow][tempCol];
                }
               
                if(currentStr.equals("#N")&&currentChar.equals("#")){
                    data1[step][5]="匹配成功";
                    break;
                }
                if(data[tempRow][tempCol].equals("<")){
                    beginInt++;
                    buffer[bufferInt++]=currentChar;
                    currentStr+=currentChar;
                    data1[step][5]="移进";
                    i ++;
                }
                if(data[tempRow][tempCol].equals("=")){
                    currentStr+=currentChar;
                    data1[step][5]="移进";
                    i ++;
                }
                if(data[tempRow][tempCol].equals(">")){
                    data1[step][5]="归约";
                    int l=0;
                    System.out.println(buffer[bufferInt-1]);
                 for(l=currentStr.length()-1;l>0;l--){
                  //  System.out.println(buffer[bufferInt-1]+1+String.valueOf(currentStr.charAt(l)));
                     if(buffer[bufferInt-1].equals(String.valueOf(currentStr.charAt(l)))){
                         break;
                     }
                 }
                 System.out.println(l);
                 if(l-1>-1){
                     if("N".equals(String.valueOf(currentStr.charAt(l-1)))){
                       l--;
                    }
                 }
                 System.out.println(l);
                 String ten=currentStr;
                 currentStr ="";
               //  System.out.println(ten+l);
                 for(int a=0;a<l;a++){
                     currentStr+=ten.charAt(a);
                 }
                 currentStr+="N";
                 bufferInt--;
                }
            System.out.println(currentStr+data1[step][0]+data1[step][1]+data1[step][2]+data1[step][3]+data1[step][4]+data1[step][5]);
        }
        dtm1=new DefaultTableModel(data1,col1);//显示预测分析表
             tabel1.setModel(dtm1);
    }
}
