package org.uribeacon.validator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Switch;

import org.uribeacon.validator.TestTypesAdapter.StartTestType;


public class MainActivity extends Activity {

  public static final String TEST_TYPE = "MainActivity.TestType";
  public static final String LOCK_IMPLEMENTED = "MainActivity.LockImplemented";
  private boolean lockImplemented = false;

  private final StartTestType mStartTestType = new StartTestType() {
    @Override
    public void startTestType(String type) {
      Intent intent = new Intent(MainActivity.this, TestActivity.class);
      intent.putExtra(MainActivity.TEST_TYPE, type);
      intent.putExtra(MainActivity.LOCK_IMPLEMENTED, lockImplemented);
      startActivity(intent);
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_types);

    recyclerView.setHasFixedSize(true);

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);

    RecyclerView.Adapter mAdapter = new TestTypesAdapter(getTestsInfo(), mStartTestType);
    recyclerView.setAdapter(mAdapter);

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu items for use in the action bar
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_activity_actions, menu);
    // Set listener for switch
    MenuItem toggleLockImplemented = menu.findItem(R.id.action_lock);
    View switchLockImplemented = toggleLockImplemented.getActionView();
    Switch s = (Switch) switchLockImplemented.findViewById(R.id.switch_lock_implemented);
    s.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        lockImplemented = ((Switch) v).isChecked();
      }
    });
    return super.onCreateOptionsMenu(menu);
  }


  private TestInfo[] getTestsInfo() {
    return new TestInfo[]{
        new TestInfo(CoreUriBeaconTests.TEST_NAME, CoreUriBeaconTests.class.getName()),
        new TestInfo(SpecUriBeaconTests.TEST_NAME, SpecUriBeaconTests.class.getName())
    };
  }

  public class TestInfo {

    public final String testName;
    public final String className;

    public TestInfo(String testName, String className) {
      this.testName = testName;
      this.className = className;
    }
  }

}
