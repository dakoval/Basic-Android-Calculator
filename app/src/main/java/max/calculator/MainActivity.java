package max.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    Button b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,bDec,bRP,bLP,bM,bD,bA,bS,bClear,bSolve;
    EditText textInput;
    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b0 = (Button) findViewById(R.id.zero);
        b1 = (Button) findViewById(R.id.one);
        b2 = (Button) findViewById(R.id.two);
        b3 = (Button) findViewById(R.id.three);
        b4 = (Button) findViewById(R.id.four);
        b5 = (Button) findViewById(R.id.five);
        b6 = (Button) findViewById(R.id.six);
        b7 = (Button) findViewById(R.id.seven);
        b8 = (Button) findViewById(R.id.eight);
        b9 = (Button) findViewById(R.id.nine);
        bDec = (Button) findViewById(R.id.decimal);
        bRP = (Button) findViewById(R.id.rightP);
        bLP = (Button) findViewById(R.id.leftP);
        bM = (Button) findViewById(R.id.multiply);
        bD = (Button) findViewById(R.id.divide);
        bA = (Button) findViewById(R.id.add);
        bS = (Button) findViewById(R.id.subtract);
        bClear = (Button) findViewById(R.id.clear);
        bSolve = (Button) findViewById(R.id.solve);
        textInput = (EditText) findViewById(R.id.input);
        textResult = (TextView) findViewById(R.id.result);

        textInput.setInputType(InputType.TYPE_NULL); //hide keyboard that pops up

        b0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+"0");
            }
        });

        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+"1");
            }
        });

        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+"2");
            }
        });

        b3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+"3");
            }
        });

        b4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+"4");
            }
        });

        b5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+"5");
            }
        });

        b6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+"6");
            }
        });

        b7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+"7");
            }
        });

        b8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+"8");
            }
        });

        b9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+"9");
            }
        });

        bDec.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+".");
            }
        });
        /*
        bRP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+"(");
            }
        });

        bLP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+")");
            }
        });
        */
        bM.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+"*");
            }
        });

        bD.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+"/");
            }
        });

        bA.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+"+");
            }
        });

        bS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText(textInput.getText()+"-");
            }
        });

        bClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textInput.setText("");
                textResult.setText("");
            }
        });

        bSolve.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    double x = solve(textInput.getText()+"");
                    textResult.setText( (Double.isInfinite(x) || Double.isNaN(x)) ? "You can't divide by zero" : x+"");
                }catch (Exception e){
                    textResult.setText("Equation Invalid");
                }
            }
        });

    }


    private ArrayList<String> tokens;
    private Stack<String> stack;
    private Stack<Double> stackD;
    private Queue<String> queue;
    private double solve(String s){
        tokens = new ArrayList<>();
        stack = new Stack();
        stackD = new Stack();
        queue = new LinkedList<>();

        int count =0;
        String st;
        //splits everything into tokens
        while(count <s.length()){
            st = "";
            if(Character.isDigit(s.charAt(count))|| s.charAt(count)=='.'){
                while(count<s.length() && (Character.isDigit(s.charAt(count))||s.charAt(count)=='.')){
                    st+=s.charAt(count);
                    count++;
                }
                tokens.add(st);
            }else{
                tokens.add(""+s.charAt(count++));
            }
        }

        count =0;
        //puts the problem into postfix
        while(count<tokens.size()){
            st = tokens.get(count);
            if(st.equals("/") || st.equals("*")){
                if(!tokens.get(count+1).equals("(")){
                    while(!stack.isEmpty() && (stack.peek().equals("*")||stack.peek().equals("/"))){
                        queue.add(stack.pop());
                    }
                    stack.push(st);
                    queue.add(tokens.get(++count));
                    queue.add((stack.pop()));
                }else{
                    stack.push(st);
                }
            }else if(st.equals("-")){
                while(!stack.isEmpty() && !stack.peek().equals("*") && !stack.peek().equals("/")){
                    queue.add(stack.pop());
                }
                stack.push(st);
            }else if(st.equals("+")){
                while(!stack.isEmpty() && !stack.peek().equals("+") && !stack.peek().equals("(")){
                    queue.add((stack.pop()));
                }
                stack.push(st);
            }else if(st.equals("(")){
                stack.push((st));
            }else if(st.equals(")")){
                while(!stack.isEmpty() || !stack.peek().equals("(")){
                    queue.add(stack.pop());
                }
                stack.pop();
            }else{
                queue.add(st);
            }
            count++;
        }

        while(!stack.isEmpty()){
            queue.add(stack.pop());
        }

        //solves the actual problem
        while(!queue.isEmpty()){
            double temp1;
            double temp2;
            st = queue.poll();
            if(Character.isDigit(st.charAt(0))){
                stackD.push(Double.parseDouble(st));
            }else{
                temp1 = stackD.pop();
                temp2 = stackD.pop();
                switch(st){
                    case "+": stackD.push(temp2+temp1);
                        break;
                    case "-": stackD.push(temp2-temp1);
                        break;
                    case "*": stackD.push(temp2*temp1);
                        break;
                    case "/": stackD.push(temp2/temp1);
                        break;
                }
            }
        }
        // last thing in stack is the answer;
        return stackD.pop();

    }

    public static void main(String[] args) {

    }
}
