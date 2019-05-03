package com.codemalone.snackapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.codemalone.snackapp.dummy.Item;
import com.codemalone.snackapp.dummy.Order;

import java.util.Collection;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements MenuItemFragment.OnListFragmentInteractionListener {

    private static final String ARG_ORDER = "order";
    private MenuItemFragment mMenuItemFragment;
    private Order mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            mMenuItemFragment = new MenuItemFragment();
            mOrder = new Order();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_content_menu, mMenuItemFragment)
                    .commit();
        } else {
            Order savedOrder = (Order) savedInstanceState.getSerializable(ARG_ORDER);

            if (savedOrder != null) {
                mOrder = savedOrder;
            } else {
                mOrder = new Order();
            }

            // something missing with mMenuItemFragment
            // review lifecycle
        }

        Button submit = findViewById(R.id.frame_content_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // we can make the network call here and pass showOrder as the callback.
                showOrder();

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putSerializable(ARG_ORDER, mOrder);
        super.onSaveInstanceState(outState, outPersistentState);
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
        if (isChecked) {
            mOrder.items.add(item);
            item.isChecked = true;
        } else {
            mOrder.items.remove(item);
            item.isChecked = false;
        }
    }

    private void showOrder() {
        StringBuilder orderText = new StringBuilder();

        for (Item e : mOrder.items) {
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
        // create new order
        mOrder = new Order();

        // update menu items
        for (Item e : mMenuItemFragment.mMenu.items) {
            e.isChecked = false;
        }

        // notify recycler view
        RecyclerView v = (RecyclerView) mMenuItemFragment.getView();
        v.getAdapter().notifyDataSetChanged();
    }

    private void resetMenuView() {

    }
}
