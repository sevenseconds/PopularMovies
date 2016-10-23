package th.in.droid.popularmovies.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import th.in.droid.popularmovies.app.R;
import th.in.droid.popularmovies.app.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
    }
}
