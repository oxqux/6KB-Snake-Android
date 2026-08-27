package dev.oxqux.snake;
import android.app.Activity;
import android.os.Bundle;
public class MainActivity extends Activity {
    @Override public void onCreate(Bundle b){ super.onCreate(b); setContentView(new GameView(this)); }
}
