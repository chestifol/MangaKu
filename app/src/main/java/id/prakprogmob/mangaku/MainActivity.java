package id.prakprogmob.mangaku;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnMangaListener {
    DBHelper helper;
    LayoutInflater inflater;
    View dialogView;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter; //Jembatan antara data arraylist dengan recyclerview
    private  RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TambahmangaActivity.class));
            }
        });

        helper = new DBHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void setRecylerView(){
        Cursor cursor = helper.allData();
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, cursor, 1, this);
        recyclerView.setAdapter(recyclerAdapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecylerView();
    }

    @Override
    public void onItemClick(View view, int position) {

        TextView getId = (TextView) view.findViewById(R.id.listReview);
        final long id = Long.parseLong(getId.getText().toString());
        final Cursor cur = helper.oneData(id);
        cur.moveToFirst();

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Pilih Opsi");

        //Add a list
        String[] options = {"Lihat Data", "Edit Data", "Hapus Data"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Intent addata = new Intent(MainActivity.this, detilReviewManga.class);
                        addata.putExtra(DBHelper.row_id, id);
                        startActivity(addata);
                }
                switch (which){
                    case 1:
                        Intent editdata = new Intent(MainActivity.this, editReviewManga.class);
                        editdata.putExtra(DBHelper.row_id, id);
                        startActivity(editdata);
                }
                switch (which){
                    case 2:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setMessage("Review akan dihapus ?");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                helper.deleteData(id);
                                showToast("Data Berhasil Dihapus");
                                setRecylerView();
                            }
                        });
                        builder1.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder1.create();
                        alertDialog.show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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

}