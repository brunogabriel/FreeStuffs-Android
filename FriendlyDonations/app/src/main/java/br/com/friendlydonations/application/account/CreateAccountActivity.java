package br.com.friendlydonations.application.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.friendlydonations.R;
import br.com.friendlydonations.application.webview.ActivityWebView;
import br.com.friendlydonations.shared.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * Created by brunogabriel on 16/03/17.
 */
public class CreateAccountActivity extends BaseActivity implements AccountView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.name_text)
    AppCompatEditText nameText;

    @BindView(R.id.name_layout)
    TextInputLayout nameLayout;

    @BindView(R.id.email_text)
    AppCompatEditText emailText;

    @BindView(R.id.email_layout)
    TextInputLayout emailLayout;

    @BindView(R.id.who_text)
    AppCompatEditText whoText;

    @BindView(R.id.who_layout)
    TextInputLayout whoLayout;

    @BindView(R.id.agree_checkbox)
    AppCompatCheckBox agreeCheckbox;

    @BindView(R.id.terms_text)
    TextView termsText;

    @BindView(R.id.form_linear_layout)
    LinearLayout formLinearLayout;

    @BindView(R.id.create_account_button)
    AppCompatButton createAccountButton;


    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        unbinder = ButterKnife.bind(this);
        setupToolbar(toolbar, getString(R.string.create_account_title), null, true, true);
        initUI();
    }

    private void initUI() {
        setupTerms();
    }

    // View Actions
    @OnClick(R.id.terms_text)
    protected void onClickCheckBoxLayout () {
        // TODO: Show View
        startActivity(new Intent(this, ActivityWebView.class));
    }

    private void setupTerms() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(getString(R.string.account_agree_not_filled))
                .append(" " + getString(R.string.terms), new CalligraphyTypefaceSpan(TypefaceUtils.load(getAssets(), "fonts/Roboto-Bold.ttf")), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                .append(" " + getString(R.string.and))
                .append(" " + getString(R.string.privacy), new CalligraphyTypefaceSpan(TypefaceUtils.load(getAssets(), "fonts/Roboto-Bold.ttf")), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        termsText.setText(builder);
    }
}
