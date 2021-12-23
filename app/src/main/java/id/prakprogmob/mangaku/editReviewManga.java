package id.prakprogmob.mangaku;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class editReviewManga extends AppCompatActivity {

    private EditText ubahJudul, ubahUlasan, ubahSinopsis;
    private Button Simpan;

    private CheckBox ubahAction, ubahAdventure, ubahSoF, ubahComedy, ubahRomance, ubahFantasy;
    private RadioButton ubahJenisManga, ubahShounen, ubahShoujo, ubahSeinen;
    private RadioGroup ubahPilihanJenisManga;
    private TextView ubahNilaiRating, nilairating;
    private SeekBar ubahRating;
    private CheckBox ubahPilihanGenre;

    private String InputanubahJudul, InputanubahSinopsis, InputanubahUlasan, InputanubahJenisManga, InputanubahRatingString, InputanubahGenreManga;
    private int SelectedubahJenis;
    private float InputanubahRatingFloat;

    DBHelper helper;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_review_manga);
        helper = new DBHelper(this);
        id = getIntent().getLongExtra(DBHelper.row_id, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ubahJudul = (EditText)findViewById(R.id.ubahJudul);
        ubahUlasan= (EditText)findViewById(R.id.ubahUlasan);
        ubahSinopsis = (EditText)findViewById(R.id.ubahSinopsis);

        ubahPilihanJenisManga = (RadioGroup) findViewById(R.id.rbgroupubahJenis);
        ubahSeinen = (RadioButton) findViewById(R.id.rb_ubahseinen);
        ubahShoujo = (RadioButton) findViewById(R.id.rb_ubahshoujo);
        ubahShounen = (RadioButton) findViewById(R.id.rb_ubahshounen);
        ubahRating = (SeekBar) findViewById((R.id.seekBarubahRating));

        ubahAction = (CheckBox) findViewById(R.id.cb_ubahAction);
        ubahSoF = (CheckBox) findViewById(R.id.cb_ubahSliceofLife);
        ubahComedy = (CheckBox) findViewById(R.id.cb_ubahComedy);
        ubahRomance = (CheckBox) findViewById(R.id.cb_ubahRomance);
        ubahAdventure = (CheckBox) findViewById(R.id.cb_ubahAdventure);
        ubahFantasy = (CheckBox) findViewById(R.id.cb_ubahFantasy);
        ubahNilaiRating = (TextView)findViewById(R.id.textViewubahNilaiRating);
        Simpan = (Button) findViewById(R.id.bt_simpan);

        getData();

        ubahRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView NilaiRating =(TextView)findViewById(R.id.textViewubahNilaiRating);

                InputanubahRatingFloat = progress * 0.1f;
                String desimalRating = String.format("%.1f", InputanubahRatingFloat);
                NilaiRating.setText(String.valueOf(desimalRating));
                InputanubahRatingString = Float.toString(InputanubahRatingFloat);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputanubahJudul = ubahJudul.getText().toString();
                InputanubahUlasan = ubahUlasan.getText().toString();
                InputanubahSinopsis = ubahSinopsis.getText().toString();

                if (ubahPilihanJenisManga.getCheckedRadioButtonId() != -1){
                    SelectedubahJenis = ubahPilihanJenisManga.getCheckedRadioButtonId();
                    ubahJenisManga = (RadioButton) findViewById(SelectedubahJenis);
                    InputanubahJenisManga = ubahJenisManga.getText().toString();
                }

                InputanubahGenreManga = "";
                if(ubahAction.isChecked()){
                    InputanubahGenreManga += " " + ubahAction.getText().toString();
                }
                if(ubahRomance.isChecked()){
                    InputanubahGenreManga += " " + ubahRomance.getText().toString();
                }
                if(ubahSoF.isChecked()){
                    InputanubahGenreManga += " " + ubahSoF.getText().toString();
                }
                if(ubahFantasy.isChecked()){
                    InputanubahGenreManga += " " + ubahFantasy.getText().toString();
                }
                if(ubahAdventure.isChecked()){
                    InputanubahGenreManga += " " + ubahAdventure.getText().toString();
                }
                if(ubahComedy.isChecked()){
                    InputanubahGenreManga += " " + ubahComedy.getText().toString();
                }

//                InputanubahGenreManga = "";
//                if(ubahAction.isChecked())
//                {
//                    ubahPilihanGenre = (CheckBox) findViewById(R.id.cb_ubahAction);
//                    InputanubahGenreManga += ubahPilihanGenre.getText().toString()+", ";
//                }
//                if(ubahAdventure.isChecked())
//                {
//                    ubahPilihanGenre = (CheckBox) findViewById(R.id.cb_ubahAdventure);
//                    InputanubahGenreManga += ubahPilihanGenre.getText().toString()+", ";
//                }
//                if(ubahComedy.isChecked())
//                {
//                    ubahPilihanGenre = (CheckBox) findViewById(R.id.cb_ubahComedy);
//                    InputanubahGenreManga += ubahPilihanGenre.getText().toString()+", ";
//                }
//                if(ubahFantasy.isChecked())
//                {
//                    ubahPilihanGenre = (CheckBox) findViewById(R.id.cb_ubahFantasy);
//                    InputanubahGenreManga += ubahPilihanGenre.getText().toString()+", ";
//                }
//                if(ubahSoF.isChecked())
//                {
//                    ubahPilihanGenre = (CheckBox) findViewById(R.id.cb_ubahSliceofLife);
//                    InputanubahGenreManga += ubahPilihanGenre.getText().toString()+", ";
//                }
//                if(ubahRomance.isChecked())
//                {
//                    ubahPilihanGenre = (CheckBox) findViewById(R.id.cb_ubahRomance);
//                    InputanubahGenreManga += ubahPilihanGenre.getText().toString()+", ";
//                }
                validasiData();
            }
        });
    }

    private void getData() {
        Cursor cursor = helper.oneData(id);
        if(cursor.moveToFirst()){
            String ubahjudul = cursor.getString(cursor.getColumnIndex(DBHelper.row_judul));
            String ubahsinopsis = cursor.getString(cursor.getColumnIndex(DBHelper.row_sinopsis));
            String ubahjenis = cursor.getString(cursor.getColumnIndex(DBHelper.row_jenis));
            String ubahgenre = cursor.getString(cursor.getColumnIndex(DBHelper.row_genre));
            String ubahrating = cursor.getString(cursor.getColumnIndex(DBHelper.row_rating));
            String ubahulasan = cursor.getString(cursor.getColumnIndex(DBHelper.row_ulasan));

            ubahJudul.setText(ubahjudul);
            ubahSinopsis.setText(ubahsinopsis);

            if (ubahjenis.equals("Shoujo")){
                ubahSeinen.setChecked(true);
            }else if(ubahjenis.equals("Shounen")){
                ubahShounen.setChecked(true);
            }else if(ubahjenis.equals("Seinen")){
                ubahSeinen.setChecked(true);
            }

            if (ubahgenre.equals("Action")) {
                ubahAction.setChecked(true);
            }if (ubahgenre.equals("Slice Of Life")){
                ubahSoF.setChecked(true);
            }if (ubahgenre.equals("Comedy")) {
                ubahComedy.setChecked(true);
            }if (ubahgenre.equals("Fantasy")) {
                ubahFantasy.setChecked(true);
            }if (ubahgenre.equals("Adventure")) {
                ubahAdventure.setChecked(true);
            }if (ubahgenre.equals("Romance")) {
                ubahRomance.setChecked(true);
            }

            ubahNilaiRating.setText(ubahrating);
            ubahUlasan.setText(ubahulasan);
        }
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(editReviewManga.this);
        builder.setCancelable(false);
        builder.setTitle("Yakin Merubah Data Review Manga ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ubahjudul = ubahJudul.getText().toString().trim();
                String ubahjenis = ubahJenisManga.getText().toString().trim();
                String ubahgenre = InputanubahGenreManga;
                String ubahsinopsis = ubahSinopsis.getText().toString().trim();
                String ubahrating = InputanubahRatingString;
                String ubahulasan = ubahUlasan.getText().toString().trim();

                ContentValues newvalues = new ContentValues();
                newvalues.put(DBHelper.row_judul, ubahjudul);
                newvalues.put(DBHelper.row_jenis, ubahjenis);
                newvalues.put(DBHelper.row_genre, ubahgenre);
                newvalues.put(DBHelper.row_sinopsis, ubahsinopsis);
                newvalues.put(DBHelper.row_rating, ubahrating);
                newvalues.put(DBHelper.row_ulasan, ubahulasan);

                Intent simpan= new Intent(editReviewManga.this,
                        MainActivity.class);
                helper.updateData(newvalues, id);
                startActivity(simpan);
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

    private void validasiData(){
        if(ubahJudul.getText().toString().equals("") && ubahSinopsis.getText().toString().equals("") && ubahUlasan.getText().toString().equals("") &&
                InputanubahGenreManga.isEmpty() && InputanubahRatingFloat == 0){
            showToast("Data Masih Kosong");
        }
        else if(TextUtils.isEmpty(InputanubahJudul)){
            ubahJudul.requestFocus();
            ubahJudul.setError("Judul Tidak Boleh Kosong");
        }
        else if(TextUtils.isEmpty(InputanubahSinopsis)){
            ubahSinopsis.requestFocus();
            ubahSinopsis.setError("Sinopsis Tidak Boleh Kosong");
        }
        else if(TextUtils.isEmpty(InputanubahUlasan)){
            ubahUlasan.requestFocus();
            ubahUlasan.setError("Ulasan Tidak Boleh Kosong");
        }
        else if(ubahPilihanJenisManga.getCheckedRadioButtonId() == -1){
            showToast("Pastikan Memilih Jenis Manga");
        }
        else if(ubahAction.isChecked()==false && ubahAdventure.isChecked()==false && ubahFantasy.isChecked()==false && ubahComedy.isChecked()==false &&
                ubahRomance.isChecked()==false && ubahSoF.isChecked()==false) {
            showToast("Pastikan Untuk Mencentang Genre Manga");
        }
        else if(InputanubahRatingFloat == 0) {
            showToast("Rating Tidak Boleh 0");
        }
        else {
            showDialog();
        }
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
