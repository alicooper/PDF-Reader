package develeoper.arsl.com.pdfreader;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class ShowPdf extends AppCompatActivity {

    PDFView mypdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.i("TAgg", "null");
        } else if (extras.getString("path") != null) {
            mypdf = findViewById(R.id.pdfView);
//            File path = new File(Environment.getExternalStorageDirectory().getPath() + extras.getString("path"));
            mypdf.fromUri(Uri.parse(extras.getString("path")))
//                    .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .load();

        }


    }
}
