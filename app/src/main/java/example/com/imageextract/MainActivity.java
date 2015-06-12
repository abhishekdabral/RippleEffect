package example.com.imageextract;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity{


    private ViewGroup mGroup;
    ImageView image ;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.imageView);
        mGroup = (ViewGroup) findViewById(R.id.group);
        Log.d(TAG, "Show RIppleInit" + mGroup.getChildCount());

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Show RIpple" + mGroup.getChildCount());
                RippleView rippleView = new RippleView(MainActivity.this);

                rippleView.setRippleStrokeColor(getResources().getColor(R.color.smoke_white));
//                rippleView.setRippleStrokeWidth(5);
//                rippleView.setRippleDuration(500);
//                rippleView.setRippleVelocity(10);

                //finally call it to make ripples
                rippleView.initRipple(v, mGroup);
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

}
