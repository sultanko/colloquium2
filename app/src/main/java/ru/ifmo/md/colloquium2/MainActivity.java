package ru.ifmo.md.colloquium2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends Activity {

    private ListView listView;
    private EditText editText;
    private MyListAdapter myListAdapter;
    private Button buttonStart;
    private Button buttonAdd;
    private boolean voting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);

        myListAdapter = new MyListAdapter(this);
        listView.setAdapter(myListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (!voting) {
                    return;
                }
                MyListAdapter myListAdapter1 = (MyListAdapter) adapterView.getAdapter();
                myListAdapter1.addVoteToMan(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                MyListAdapter myListAdapter1 = (MyListAdapter) adapterView.getAdapter();
                myListAdapter1.deleteMan(i);

                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("Voting", voting);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        voting = savedInstanceState.getBoolean("Voting");
        if (voting) {
            buttonAdd.setEnabled(false);
            buttonStart.setText(R.string.end_vote);
        } else {
            buttonStart.setText(R.string.start_vote);
            buttonAdd.setEnabled(true);
        }
    }

    public void addMan(View view) {
        myListAdapter.addMan(editText.getText().toString());
    }

    public void startVotes(View view) {
        if (buttonStart.getText().toString() == getApplicationContext().getString(R.string.start_vote)) {
            buttonAdd.setEnabled(false);
            buttonStart.setText(R.string.end_vote);
            voting = true;
        } else {
            buttonStart.setText(R.string.start_vote);
            buttonAdd.setEnabled(true);
            myListAdapter.clearDatabase();
            voting = false;
        }
    }

    public void clearResult(View view) {
        myListAdapter.clearResult();
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
}
