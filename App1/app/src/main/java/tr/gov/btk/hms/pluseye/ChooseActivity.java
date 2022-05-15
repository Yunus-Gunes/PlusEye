package tr.gov.btk.hms.pluseye;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import tr.gov.btk.hms.pluseye.object.LiveObjectAnalyseActivity;

//Başlat Tuşu 
//Basıldığında uygulama açılacak
public class ChooseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main_image);
        this.findViewById(R.id.btn_object).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.startActivity(new Intent(ChooseActivity.this, LiveObjectAnalyseActivity.class));
    }
}

