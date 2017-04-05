package swim.regulski.BMICalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class About extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.fab)
    protected void onClickFab() {
        Intent mail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "regulskimichal@outlook.com", null))
                .putExtra(Intent.EXTRA_EMAIL, "regulskimichal@outlook.com")
                .putExtra(Intent.EXTRA_SUBJECT, "Question from BMICalculator");
        startActivity(mail);
    }
}
