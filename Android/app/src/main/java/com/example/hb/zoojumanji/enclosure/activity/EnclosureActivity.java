package com.example.hb.zoojumanji.enclosure.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hb.zoojumanji.R;
import com.example.hb.zoojumanji.enclosure.adapter.EnclosureAdapter;
import com.example.hb.zoojumanji.enclosure.manager.EnclosureManager;
import com.example.hb.zoojumanji.enclosure.Enclosure;

import java.util.List;

public class EnclosureActivity extends AppCompatActivity {

    protected ListView listView;
    protected TextView emptyTextView;

    protected EnclosureManager manager;
    protected boolean deletion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enclosure);

        emptyTextView = (TextView) findViewById(R.id.empty_text);
        listView = (ListView) findViewById(R.id.enclosures_list);

        manager = new EnclosureManager(this);
        generateList();

        // Get clicked floatingButton to add a new animal
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.add_fab);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Generate new Activity
                Intent intent = new Intent(EnclosureActivity.this, EnclosureCreationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        generateList();
        if (EnclosureManager.isInDeletion()) {
            generateUndeleteSnackBar();
        }
    }

    private void displayEmptyText() {
        listView.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.VISIBLE);
    }

    private void displayList() {
        listView.setVisibility(View.VISIBLE);
        emptyTextView.setVisibility(View.GONE);
    }

    private void generateList() {
        // Get list of enclosures
        List<Enclosure> list = manager.getEnclosures();

        generateList(list);
    }

    private void generateList(List<Enclosure> initialList) {

        List<Enclosure> list = EnclosureManager.cleanEnclosureList(initialList);

        if (list.size() == 0) {
            displayEmptyText();
            return;
        }
        else {
            displayList();
        }

        // Generate specific adapter
        ArrayAdapter<Enclosure> adapter = new EnclosureAdapter(this,
                R.layout.list_enclosure_item, list);

        // Display list
        listView.setAdapter(adapter);

        // Add event listener on elements of list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Send to detail page with id in argument
                Intent intent = new Intent(EnclosureActivity.this, EnclosureDetailActivity.class);
                TextView id_text = (TextView) view.findViewById(R.id.enclosure_id);
                intent.putExtra("id", Integer.valueOf(String.valueOf(id_text.getText())));

                startActivity(intent);
            }
        });

        // Add event listener on elements of list
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // Send to detail page with id in argument
                Intent intent = new Intent(EnclosureActivity.this, EnclosureModifyActivity.class);
                TextView id_text = (TextView) view.findViewById(R.id.enclosure_id);
                intent.putExtra("id", Integer.valueOf(String.valueOf(id_text.getText())));

                startActivity(intent);
                return false;
            }
        });
    }

    private void generateUndeleteSnackBar() {

        // Activation deletion
        deletion = true;

        //The undo action that could be called by the following snackbar
        final View.OnClickListener undoListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abort deletion
                deletion = false;
                Toast.makeText(EnclosureActivity.this, R.string.message_undo_deletion, Toast.LENGTH_LONG)
                        .show();
                manager.restoreEnclosure();
                generateList();
            }
        };

        // Generate snackbar
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                R.string.message_stock_deletion,
                Snackbar.LENGTH_LONG);

        // Generate snackbar events
        snackbar.getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                // Empty
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                if (deletion) {
                    EnclosureActivity.this.manager.cleanEnclosure();
                }
            }
        });

        // Display snackbar
        snackbar.setAction(R.string.message_deletion_undo, undoListener)
                .setActionTextColor(0xFFFF0000)
                .show();
    }

    public void refreshList(List<Enclosure> updatedList) {
        generateList(updatedList);
    }
}
