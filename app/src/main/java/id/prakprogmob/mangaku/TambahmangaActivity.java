package id.prakprogmob.mangaku;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class TambahmangaActivity extends AppCompatActivity {

    private Button Tambah, Ok;
    private EditText Judul, Sinopsis, Ulasan;

    private CheckBox Action, Adventure, SoF, Comedy, Romance, Fantasy;
    private RadioButton JenisManga, Shounen, Shoujo, Seinen;
    private RadioGroup PilihanJenisManga;

    private SeekBar Rating;
    private CheckBox PilihanGenre;
    MangaModel mangaModel;

    private String InputanJudul, InputanSinopsis, InputanUlasan, InputanJenisManga, InputanRatingString, InputanGenreManga;
    private int SelectedJenis;
    private float InputanRatingFloat;

    DBHelper helper;
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahmanga);
        helper = new DBHelper(this);
        id = getIntent().getLongExtra(DBHelper.row_id, 0);

        Tambah = (Button) findViewById(R.id.bt_simpan);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Judul  = (EditText) findViewById(R.id.editTextJudul);
        Sinopsis = (EditText) findViewById(R.id.editTextSinopsis);
        Ulasan  = (EditText) findViewById(R.id.editTextUlasan);

        PilihanJenisManga = (RadioGroup) findViewById(R.id.rbgroupJenis);
        Seinen = (RadioButton) findViewById(R.id.rb_seinen);
        Shoujo = (RadioButton) findViewById(R.id.rb_shoujo);
        Shounen = (RadioButton) findViewById(R.id.rb_shounen);
        Rating = (SeekBar) findViewById((R.id.seekBarRating));

        Action = (CheckBox) findViewById(R.id.cb_Action);
        Adventure = (CheckBox) findViewById(R.id.cb_Adventure);
        SoF = (CheckBox) findViewById(R.id.cb_SliceofLife);
        Comedy = (CheckBox) findViewById(R.id.cb_Comedy);
        Romance = (CheckBox) findViewById(R.id.cb_Romance);
        Fantasy = (CheckBox) findViewById(R.id.cb_Fantasy);

        Judul.setFocusableInTouchMode(true);
        Judul.requestFocus();

        Rating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView NilaiRating =(TextView)findViewById(R.id.textViewNilaiRating);

                InputanRatingFloat = progress * 0.1f;
                String desimalRating = String.format("%.1f", InputanRatingFloat);
                NilaiRating.setText(String.valueOf("Score : " + desimalRating));
                InputanRatingString = Float.toString(InputanRatingFloat);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputanJudul = Judul.getText().toString();
                InputanUlasan = Ulasan.getText().toString();
                InputanSinopsis = Sinopsis.getText().toString();

                if (PilihanJenisManga.getCheckedRadioButtonId() != -1){
                    SelectedJenis = PilihanJenisManga.getCheckedRadioButtonId();
                    JenisManga = (RadioButton) findViewById(SelectedJenis);
                    InputanJenisManga = JenisManga.getText().toString();
                }

                InputanGenreManga = "";
                if(Action.isChecked())
                {
                    PilihanGenre = (CheckBox) findViewById(R.id.cb_Action);
                    InputanGenreManga += PilihanGenre.getText().toString()+", ";
                }
                if(Adventure.isChecked())
                {
                    PilihanGenre = (CheckBox) findViewById(R.id.cb_Adventure);
                    InputanGenreManga += PilihanGenre.getText().toString()+", ";
                }
                if(Comedy.isChecked())
                {
                    PilihanGenre = (CheckBox) findViewById(R.id.cb_Comedy);
                    InputanGenreManga += PilihanGenre.getText().toString()+", ";
                }
                if(Fantasy.isChecked())
                {
                    PilihanGenre = (CheckBox) findViewById(R.id.cb_Fantasy);
                    InputanGenreManga += PilihanGenre.getText().toString()+", ";
                }
                if(SoF.isChecked())
                {
                    PilihanGenre = (CheckBox) findViewById(R.id.cb_SliceofLife);
                    InputanGenreManga += PilihanGenre.getText().toString()+", ";
                }
                if(Romance.isChecked())
                {
                    PilihanGenre = (CheckBox) findViewById(R.id.cb_Romance);
                    InputanGenreManga += PilihanGenre.getText().toString()+", ";
                }
                validasiData();
            }
        });
    }

    private void validasiData(){
        if(Judul.getText().toString().equals("") && Sinopsis.getText().toString().equals("") && Ulasan.getText().toString().equals("") &&
            InputanGenreManga.isEmpty() && InputanRatingFloat == 0){
            showToast("Data Masih Kosong");
        }
        else if(TextUtils.isEmpty(InputanJudul)){
            Judul.requestFocus();
            Judul.setError("Judul Tidak Boleh Kosong");
        }
        else if(TextUtils.isEmpty(InputanSinopsis)){
            Sinopsis.requestFocus();
            Sinopsis.setError("Sinopsis Tidak Boleh Kosong");
        }
        else if(TextUtils.isEmpty(InputanUlasan)){
            Ulasan.requestFocus();
            Ulasan.setError("Ulasan Tidak Boleh Kosong");
        }
        else if(PilihanJenisManga.getCheckedRadioButtonId() == -1){
            showToast("Pastikan Memilih Jenis Manga");
        }
        else if(Action.isChecked()==false && Adventure.isChecked()==false &&  Fantasy.isChecked()==false && Comedy.isChecked()==false &&
                Romance.isChecked()==false &&  SoF.isChecked()==false) {
            showToast("Pastikan Untuk Mencentang Genre Manga");
        }
        else if(InputanRatingFloat == 0) {
            showToast("Rating Tidak Boleh 0");
        }
        else {
            showDialog();
        }
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(TambahmangaActivity.this);
        builder.setCancelable(false);
        builder.setTitle("Yakin Menambah Review Manga ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MangaModel manga = new MangaModel(Judul.getText().toString(), JenisManga.getText().toString(), InputanGenreManga, Sinopsis.getText().toString(), InputanRatingString, Ulasan.getText().toString());

                String judul = Judul.getText().toString().trim();
                String jenis = JenisManga.getText().toString().trim();
                String genre = InputanGenreManga;
                String sinopsis = Sinopsis.getText().toString().trim();
                String rating = InputanRatingString;
                String ulasan = Ulasan.getText().toString().trim();

                ContentValues values = new ContentValues();
                values.put(DBHelper.row_judul, judul);
                values.put(DBHelper.row_jenis, jenis);
                values.put(DBHelper.row_genre, genre);
                values.put(DBHelper.row_sinopsis, sinopsis);
                values.put(DBHelper.row_rating, rating);
                values.put(DBHelper.row_ulasan, ulasan);

                Intent tambah = new Intent(TambahmangaActivity.this,
                        MainActivity.class);
                tambah.putExtra("item", manga);
                helper.insertData(values);
                startActivity(tambah);
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showToast(String Msg){
        Context context = getApplicationContext();
        CharSequence text = Msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        View view = toast.getView();
        TextView toastMassage = view.findViewById(android.R.id.message);
        toastMassage.setTextSize(18);
        toastMassage.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        toastMassage.setTextColor(Color.WHITE);
        view.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        toast.show();
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