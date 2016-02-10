package noteboy.noteboy.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.joanzapata.pdfview.PDFView;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by Chirag Shenoy on 25-Jan-16.
 */
public class NotesViewer extends ListActivity {

    String[] pdflist;
    File[] imagelist;
    PDFView pdfView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        String folder_main = "NoteBoy";

        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }


//        File images =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        imagelist = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return ((name.endsWith(".pdf")));
            }
        });

        if (imagelist == null) {
            try {
                if (imagelist.length == 0) {
                    Toast.makeText(getApplicationContext(), "No notes downloaded.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "No notes downloaded.", Toast.LENGTH_SHORT).show();
            }
            pdflist = new String[imagelist.length];
            for (int i = 0; i < imagelist.length; i++) {
                pdflist[i] = imagelist[i].getName();
            }
            this.setListAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, pdflist));
        } else {
            Toast.makeText(getApplicationContext(), "No notes downloaded.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String path = imagelist[(int) id].getAbsolutePath();
        openPdfIntent(path);
    }

    private void openPdfIntent(String path) {
        try {
            final Intent intent = new Intent(NotesViewer.this, PDFClass.class);
            intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, path);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}