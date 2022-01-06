package develeoper.arsl.com.pdfreader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {

    Data_Access access = new Data_Access(this);

    ImageButton btn_filePicker;
    ImageButton btn_home;
    Intent myFileIntent;

    ListView lv_pdf;
    public static ArrayList<File> fileList = new ArrayList<File>();
    PDFAdapter obj_adapter;
    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    File dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark);
        btn_filePicker = findViewById(R.id.btn_openPdf);
        btn_filePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                myFileIntent.setType("application/pdf");
                startActivityForResult(myFileIntent, 10);
            }
        });
        init();

        btn_home = findViewById(R.id.imageButton);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_home.setImageResource(R.drawable.homered);
                Intent intent = new Intent(BookmarkActivity.this ,MainActivity.class);
//                intent.putExtra("path",path.toString());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    Uri path = data.getData();

                    Intent intent = new Intent(this ,ShowPdf.class);
                    intent.putExtra("path",path.toString());
                    startActivity(intent);
//                    Log.i("test",path);

                }
                break;
        }
    }


    private void init() {

        lv_pdf = (ListView) findViewById(R.id.lv_pdf);
        dir = new File(Environment.getExternalStorageDirectory().toString());
        fn_permission();

        lv_pdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    public ArrayList<File> getfile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    getfile(listFile[i]);

                } else {

                    boolean booleanpdf = false;
                    if (listFile[i].getName().endsWith(".pdf")) {

                        for (int j = 0; j < fileList.size(); j++) {
                            if (fileList.get(j).getName().equals(listFile[i].getName())) {
                                booleanpdf = true;
                            } else {

                            }
                        }

                        if (booleanpdf) {
                            booleanpdf = false;
                        } else {
                            fileList.add(listFile[i]);

                        }
                    }
                }
            }
        }
        return fileList;
    }
    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(BookmarkActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(BookmarkActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;

            getfile(dir);

            access.openDB();
            ArrayList<select_pdf> c = access.getall();
            ArrayList<File> f = new ArrayList<>();
            for (select_pdf p:
                    c) {
                f.add(new File(p.getPath_file()));

            }
            obj_adapter = new PDFAdapter(getApplicationContext(), f );
            lv_pdf.setAdapter(obj_adapter);
            access.closeDB();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                boolean_permission = true;
                getfile(dir);
                access.openDB();
                ArrayList<select_pdf> c = access.getall();
                ArrayList<File> f = new ArrayList<>();
                for (select_pdf p:
                     c) {
                    f.add(new File(p.getPath_file()));

                }
                obj_adapter = new PDFAdapter(getApplicationContext(), f );
                lv_pdf.setAdapter(obj_adapter);
                access.closeDB();

            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }

}
