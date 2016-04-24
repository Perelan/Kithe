package sharecrew.net.kithee;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import sharecrew.net.kithee.Fragment.TravelFragment;

public class MainActivity extends AppCompatActivity {
    RelativeLayout searchbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        searchbar = (RelativeLayout) findViewById(R.id.searchbar);

        final EditText input = (EditText) findViewById(R.id.search_field);

        ImageButton search_btn = (ImageButton) findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = input.getText().toString();
                //if(input.getText().toString().isEmpty()){
                  //  Toast.makeText(v.getContext(), "An input is needed!", Toast.LENGTH_SHORT).show();
                //}else{
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    searchbar.setVisibility(View.GONE);

                    Fragment fragment = TravelFragment.newInstance(price);
                    android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                    ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.slide_out_right);
                    ft.replace(R.id.fragment, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                //}
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        searchbar.setVisibility(View.VISIBLE);
    }
}
