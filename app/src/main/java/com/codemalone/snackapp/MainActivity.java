package com.codemalone.snackapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.codemalone.snackapp.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Main activity for the Snack App.
 *
 * Author: Jared Malone
 * Date: 5/3/2019
 */
public class MainActivity extends AppCompatActivity implements MenuItemFragment.OnListFragmentInteractionListener {

    /* The instance state of all items are stored under this key. */
    private static final String ARG_MENU = "menu";

    /* Reference to all items. */
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

        // create a new menu. changes to menu do not persist if the app is reloaded.
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
                    // TODO: NETWORK CALL here and pass showOrder as the callback.
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

    /**
     * This builds a new menu list. We could update this data from a network resource.
     * @return newMenu containing an ArrayList of all items.
     */
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
    public void onListFragmentInteraction(Item item, boolean isChecked) {
        item.isSelected = isChecked;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_MENU, mMenu);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMenu = savedInstanceState.getParcelableArrayList(ARG_MENU);
        initializeRecyclerView();
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

            // TODO: call method to display dialog for adding new menu item.
            showAddItemDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Displays a dialog with the contents of the sent order.
     * @param theOrder a list of items ordered.
     */
    private void showOrder(final List<Item> theOrder) {
        final StringBuilder orderText = new StringBuilder();

        for (Item e : theOrder) {
            orderText.append(e.toString());
            orderText.append("\n");
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Order Sent").setMessage(orderText.toString());
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                resetOrder();
            }
        });
        builder.create().show();
    }

    /**
     * Resets all menu items to a non-selected state.
     */
    private void resetOrder() {
        // update menu items
        for (Item e : mMenu) {
            e.isSelected = false;
        }

        // notify recycler view
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Called to create a new recycler view during app initialization or
     * when the set of visible categories has changed.
     */
    private void initializeRecyclerView() {
        recyclerView = findViewById(R.id.frame_content_menu);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // create a new data set consisting of only visible items
        ArrayList<Item> visibleItems = new ArrayList<>();

        for (Item e : mMenu) {
            if (e.isVisible) {
                visibleItems.add(e);
            }
        }

        mAdapter = new MyMenuItemRecyclerViewAdapter(visibleItems, this);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * Event listener to update the state of menu items when its category is selected
     * or deselected. If the isVisible state of an item changes then it is also deselected
     * from the menu list.
     * @param buttonView the category checkBox that has changed.
     * @param isChecked true if the category has been selected by the user.
     */
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

    /**
     * Displays a dialog to allow the addition of a new menu item.
     */
    private void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View v = inflater.inflate(R.layout.fragement_add_item, null);
        builder.setView(v);

        RadioButton button1 = v.findViewById(R.id.dialog_add_item_button1);
        button1.toggle();

        builder.setPositiveButton(R.string.button_add_item_save, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                RadioGroup group = v.findViewById(R.id.dialog_add_item_group);
                final int selected = group.getCheckedRadioButtonId();

                Button button = v.findViewById(selected);
                final String category = button.getText().toString();

                EditText nameField = v.findViewById(R.id.dialog_add_item_name);
                final String name = nameField.getText().toString();

                addItem(name, category);
            }
        });

        builder.setNegativeButton(R.string.button_add_item_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.setTitle(R.string.action_add_item);

        // inflate xml layout
        builder.create().show();
    }

    /**
     * Inserts a new item to the menu.
     * @param theName new item name.
     * @param theCategory a valid category.
     */
    private void addItem(final String theName, final String theCategory) {
        Item newItem = new Item(theName, theCategory);

        // check current category state
        CheckBox veggieCheckBox = findViewById(R.id.checkbox_category_veggie);
        CheckBox nonVeggieCheckBox = findViewById(R.id.checkbox_category_non_veggie);

        if (theCategory.equals(veggieCheckBox.getText().toString())) {
            newItem.isVisible = veggieCheckBox.isChecked();
        } else {
            newItem.isVisible = nonVeggieCheckBox.isChecked();
        }

        mMenu.add(newItem);
        initializeRecyclerView();
    }
}
