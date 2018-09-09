package comw.example.user.testtaska;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import comw.example.user.testtaska.DataBase.DataBaseProvider;
import comw.example.user.testtaska.TabFragments.HistoryFragment;
import comw.example.user.testtaska.TabFragments.HomeFragment;
import comw.example.user.testtaska.DataBase.DataBaseProvider;
import comw.example.user.testtaska.TabFragments.HistoryFragment;
import comw.example.user.testtaska.TabFragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragmentSelected = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentSelected = new HomeFragment();
                    break;
                case R.id.navigation_dashboard:
                    fragmentSelected = new HistoryFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentSelected).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseProvider provider = new DataBaseProvider();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }
}
