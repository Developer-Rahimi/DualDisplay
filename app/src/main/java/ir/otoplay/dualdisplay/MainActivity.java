package ir.otoplay.dualdisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ir.otoplay.dualdisplaycontrol.DisplayControl;

public class MainActivity extends RuntimePermissionsActivity implements View.OnClickListener {
    Button btnOne,btnTwo,btnThree;
    Context context=this;
    DisplayControl displayControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 46);
        displayControl=new DisplayControl(context);
        Init();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void onPermissionsDeny(int requestCode) {

    }

    private void Init() {
        btnOne=findViewById(R.id.btn1);
        btnTwo=findViewById(R.id.btn2);
        btnThree=findViewById(R.id.btn3);
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                        R.raw.background);
                displayControl.setQrCode("Hello Worldsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss!");
                displayControl.setOtherBitmap(icon);
                displayControl.setFooterText("Bye!");
                displayControl.setHeaderText("Hi!");
                break;
            case R.id.btn2:
                displayControl.setStatus("پرداخت با موفقیت انجام شد","موجودی   25364   ریال",displayControl.SUCCESS);
                break;
            case R.id.btn3:
                displayControl.setStatus("پرداخت با موفقیت انجام شد","",displayControl.ERROR);
                break;

        }
    }
}