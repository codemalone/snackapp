package com.codemalone.snackapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.codemalone.snackapp.dummy.Item;
import com.codemalone.snackapp.dummy.Order;

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
                    .add(R.id.frame_content_menu, mMenuItemFragment)
                    .commit();
        }

        Button submit = findViewById(R.id.frame_content_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Submit:" + mOrder.items.toString());
            }
        });
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
        } else {
            mOrder.items.remove(item);
        }
    }
}
