package zlw.com.radialprogress;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import zlw.com.radialprogresslib.LoopProgressView;

public class MainActivity extends AppCompatActivity {
    LoopProgressView myview;
    TextView text;
    private float progress = 0f;
    private float offset = 0.01f;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myview.setProgerss(progress);
            text.setText(progress * 100 + "%");

            if (progress > 1 || progress < 0) {
                offset = -offset;
            }
            progress += offset;

            handler.sendEmptyMessageDelayed(0, 60);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        myview = (LoopProgressView) findViewById(R.id.myview);
        handler.sendEmptyMessage(0);//开启进度
    }


}
