package laerte.esy.es.qrcodelaerte;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private TextView formatTxt, contentTxt;
    private Message okMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formatTxt = (TextView) findViewById(R.id.scan_format);
        contentTxt = (TextView) findViewById(R.id.scan_content);
    }//fecha onCreate

    //product barcode mode
    public void scanBar(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog

            //showDialog(MainActivity.this,"No Scanner Found", "Download a Scanner code acritity?", "Yes", "NO");
            // showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();

        }
    }
    //alert dialog for downloadDialog
    private static AlertDialog shoDialog(final AppCompatActivity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo){
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);


        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);

            }catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
        }
    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                formatTxt.setText("FORMAT: " + format);
                contentTxt.setText("CONTENT: " + contents);
                Toast toast = Toast.makeText(this, "Leitura:" + contents +  "Formato:" + format, Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }


}//fecha classe


