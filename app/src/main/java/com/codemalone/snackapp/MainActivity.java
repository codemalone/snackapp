package com.codemalone.snackapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.codemalone.snackapp.dummy.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MenuItemFragment.OnListFragmentInteractionListener {

    private static final String ARG_MENU = "menu";
    private ArrayList<Item> mMenu;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMenu = createNewMenu();
        initializeRecyclerView();

        // set submit button action listener
        Button submit = findViewById(R.id.frame_content_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // build order list from selected items
                ArrayList<Item> newOrder = new ArrayList<>();

                for (Item e : mMenu) {
                    if (e.isSelected) {
                        newOrder.add(e);
                    }
                }

                // only send a non-empty order
                if (newOrder.isEmpty()) {
                    // this could display a friendly error dialogue.
                } else {
                    // we can make the NETWORK CALL here and pass showOrder as the callback.
                    showOrder(newOrder);
                }
            }
        });

        // set category checkbox listeners
        CheckBox veggie = findViewById(R.id.checkbox_category_veggie);
        veggie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeCategoryVisibility(buttonView, isChecked);
            }
        });

        CheckBox nonVeggie = findViewById(R.id.checkbox_category_non_veggie);
        nonVeggie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeCategoryVisibility(buttonView, isChecked);
            }
        });
    }

    private ArrayList<Item> createNewMenu() {
        final String veggie = getString(R.string.category_content_veggie);
        final String nonVeggie = getString(R.string.category_content_non_veggie);

        ArrayList<Item> newMenu = new ArrayList<>();
        newMenu.add(new Item("Apple", veggie));
        newMenu.add(new Item("Banana", veggie));
        newMenu.add(new Item("Carrots", veggie));
        newMenu.add(new Item("French fries", veggie));
        newMenu.add(new Item("Milkshake", veggie));
        newMenu.add(new Item("Veggieburger", veggie));
        newMenu.add(new Item("Cheeseburger", nonVeggie));
        newMenu.add(new Item("Hamburger", nonVeggie));
        newMenu.add(new Item("Hot dog", nonVeggie));
        return newMenu;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(Item item, boolean isChecked) {
        item.isSelected = isChecked;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_MENU, mMenu);
        System.out.println("SaveState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMenu = savedInstanceState.getParcelableArrayList(ARG_MENU);
        initializeRecyclerView();
        System.out.println("RestoreState");
    }

    private void showOrder(List<Item> theOrder) {
        StringBuilder orderText = new StringBuilder();

        for (Item e : theOrder) {
            orderText.append(e.toString());
            orderText.append("\n");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Order Sent").setMessage(orderText.toString());
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                resetOrder();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void resetOrder() {
        // update menu items
        for (Item e : mMenu) {
            e.isSelected = false;
        }

        // notify recycler view
        mAdapter.notifyDataSetChanged();
    }

    private void initializeRecyclerView() {
        recyclerView = findViewById(R.id.frame_content_menu);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // create a data set with only visible items
        ArrayList<Item> visibleItems = new ArrayList<>();

        for (Item e : mMenu) {
            if (e.isVisible) {
                visibleItems.add(e);
            }
        }

        // specify an adapter (see also next example)
        mAdapter = new MyMenuItemRecyclerViewAdapter(visibleItems, this);
        recyclerView.setAdapter(mAdapter);
    }

    private void changeCategoryVisibility(CompoundButton buttonView, boolean isChecked) {
        // iterate items and set visibility on category match
        for (Item e : mMenu) {
            if (buttonView.getText().equals(e.category)) {
                e.isVisible = isChecked;
                e.isSelected = false;
            }
        }

        // update recycler view
        initializeRecyclerView();
    }
}
