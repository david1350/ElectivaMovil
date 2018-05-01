package co.edu.uptc.proyectocanvaspaint;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{


    Button buttonPencil;
    Button buttonLine;
    Button buttonSquare;
    Button buttonOval;
    Button buttonEraser;
    Button buttonBote;

    Button buttonBlue;
    Button buttonRed;
    Button buttonYellow;
    Button buttonBlack;
    Button buttonWhite;

    private ArrayList<Button> listButton;


    CanvasClass canvasClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.canvasClass = (CanvasClass) findViewById(R.id.canvasClass);
        this.buttonBlue = (Button) findViewById(R.id.colorBlue);
        this.buttonRed = (Button) findViewById(R.id.colorRed);
        this.buttonYellow = (Button) findViewById(R.id.colorYellow);
        this.buttonBlack = (Button) findViewById(R.id.colorBlack);
        this.buttonWhite = (Button) findViewById(R.id.colorWhite);

        this.buttonPencil = (Button) findViewById(R.id.buttonPencil);
        this.buttonLine = (Button) findViewById(R.id.buttonLine);
        this.buttonSquare =  (Button) findViewById(R.id.buttonSquare);
        this.buttonOval = (Button) findViewById(R.id.buttonOval);
        this.buttonEraser = (Button) findViewById(R.id.buttonEraser);
        this.buttonBote = (Button) findViewById(R.id.buttonBote);



        this.buttonPencil.setOnClickListener(this);
        this.buttonLine.setOnClickListener(this);
        this.buttonSquare.setOnClickListener(this);
        this.buttonOval.setOnClickListener(this);
        this.buttonEraser.setOnClickListener(this);
        this.buttonBote.setOnClickListener(this);

        this.buttonBlue.setOnClickListener(this);
        this.buttonRed.setOnClickListener(this);
        this.buttonYellow.setOnClickListener(this);
        this.buttonBlack.setOnClickListener(this);
        this.buttonWhite.setOnClickListener(this);

        addButtonsList();

    }


    public void addButtonsList (){
        this.listButton = new ArrayList<>();
        this.listButton.add(buttonPencil);
        this.listButton.add(buttonLine);
        this.listButton.add(buttonSquare);
        this.listButton.add(buttonOval);
        this.listButton.add(buttonEraser);
        this.listButton.add(buttonBote);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonPencil:
                verifyStateButtons(v);
                this.canvasClass.setShape("pencil");
                break;
            case R.id.buttonLine:
                verifyStateButtons(v);
                this.canvasClass.setShape("line");
                break;
            case R.id.buttonSquare:
                verifyStateButtons(v);
                this.canvasClass.setShape("square");
                break;
            case R.id.buttonOval:
                verifyStateButtons(v);
                this.canvasClass.setShape("oval");
                break;
            case R.id.buttonEraser:
                verifyStateButtons(v);
                this.canvasClass.setShape("eraser");
                break;
            case R.id.buttonBote:
                verifyStateButtons(v);
                this.canvasClass.setShape("bote");
                break;

            case R.id.colorBlue:
                this.canvasClass.setColor(v.getTag().toString());
                break;
            case R.id.colorRed:
                this.canvasClass.setColor(v.getTag().toString());
                break;
            case R.id.colorYellow:
                this.canvasClass.setColor(v.getTag().toString());
                break;
            case R.id.colorBlack:
                this.canvasClass.setColor(v.getTag().toString());
                break;
            case R.id.colorWhite:
                this.canvasClass.setColor(v.getTag().toString());
                break;
        }

    }

    public void verifyStateButtons (View view){
        view.setBackgroundColor(Color.LTGRAY);

        for (int i  = 0;  i<listButton.size(); i++){
            if (view.getId()!=listButton.get(i).getId()){
                listButton.get(i).setBackgroundColor(Color.WHITE);
            }
        }

    }



}
