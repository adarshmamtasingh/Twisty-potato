package test.twistypotato;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Twisty Potato</font>"));
        getSupportFragmentManager().beginTransaction().replace(R.id.login_content, new LoginFragment()).commit();
    }
}
