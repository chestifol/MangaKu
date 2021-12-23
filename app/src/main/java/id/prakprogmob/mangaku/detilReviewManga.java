package id.prakprogmob.mangaku;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class detilReviewManga extends AppCompatActivity {

    DBHelper helper;
    TextView textJudulManga,textSinopsisManga,textUlasanManga,textJenisManga,textGenreManga,textRatingManga;
    long id;
    MangaModel mangaModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil_review_manga);
        helper = new DBHelper(this);
        id = getIntent().getLongExtra(DBHelper.row_id, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        textJudulManga = (TextView) findViewById(R.id.textJudulManga);
        textSinopsisManga = (TextView) findViewById(R.id.textSinopsisManga);
        textUlasanManga = (TextView) findViewById(R.id.textUlasanManga);
        textJenisManga= (TextView) findViewById(R.id.textJenisManga);
        textGenreManga = (TextView) findViewById(R.id.textGenreManga);
        textRatingManga = (TextView) findViewById(R.id.textRatingManga);

        getData();
    }

    private void getData() {
        final Cursor cur = helper.oneData(id);
        if(cur.moveToFirst()) {
            textJudulManga.setText("" + cur.getString(cur.getColumnIndex(DBHelper.row_judul)));
            textSinopsisManga.setText("" + cur.getString(cur.getColumnIndex(DBHelper.row_sinopsis)));
            textUlasanManga.setText("" + cur.getString(cur.getColumnIndex(DBHelper.row_ulasan)));
            textJenisManga.setText("" + cur.getString(cur.getColumnIndex(DBHelper.row_jenis)));
            textGenreManga.setText("" + cur.getString(cur.getColumnIndex(DBHelper.row_genre)));
            textRatingManga.setText("" + cur.getString(cur.getColumnIndex(DBHelper.row_rating)));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }
}


//        mangaModel = getIntent().getParcelableExtra("item");
//
//        textJudulManga.setText("" + mangaModel.getTextjudulManga());
//        textSinopsisManga.setText("" + mangaModel.getTextsinopsisManga());
//        textUlasanManga.setText(""+mangaModel.getTextulasanManga());
//        textJenisManga.setText(""+mangaModel.getTextjenisManga());
//        textGenreManga.setText(""+mangaModel.getTextgenreManga());
//        textRatingManga.setText(" "+mangaModel.getTextratingManga());