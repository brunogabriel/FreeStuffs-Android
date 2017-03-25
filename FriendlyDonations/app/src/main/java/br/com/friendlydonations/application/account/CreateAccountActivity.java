package br.com.friendlydonations.application.account;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import br.com.friendlydonations.R;
import br.com.friendlydonations.application.webview.ActivityWebView;
import br.com.friendlydonations.shared.BaseActivity;
import br.com.friendlydonations.shared.CameraGalleryHelper;
import br.com.friendlydonations.shared.PermissionsHelper;
import br.com.friendlydonations.shared.form.EmailValidator;
import br.com.friendlydonations.shared.form.FormHelper;
import br.com.friendlydonations.shared.form.NameValidator;
import br.com.friendlydonations.shared.form.NotEmptyValidator;
import br.com.friendlydonations.shared.form.PasswordValidator;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Observable;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

import static br.com.friendlydonations.application.webview.ActivityWebView.PATH_EXTRA;
import static br.com.friendlydonations.application.webview.ActivityWebView.TITLE_EXTRA;
import static br.com.friendlydonations.shared.CameraGalleryHelper.CAMERA_CODE;
import static br.com.friendlydonations.shared.CameraGalleryHelper.GALLERY_CODE;
import static br.com.friendlydonations.shared.CameraGalleryHelper.TEMPORARY_IMAGE_FILENAME;
import static com.yalantis.ucrop.UCrop.RESULT_ERROR;

/**
 * Created by brunogabriel on 16/03/17.
 */
public class CreateAccountActivity extends BaseActivity implements AccountView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.name_text)
    TextInputEditText nameText;

    @BindView(R.id.name_layout)
    TextInputLayout nameLayout;

    @BindView(R.id.email_text)
    TextInputEditText emailText;

    @BindView(R.id.email_layout)
    TextInputLayout emailLayout;

    @BindView(R.id.who_text)
    TextInputEditText whoText;

    @BindView(R.id.who_layout)
    TextInputLayout whoLayout;

    @BindView(R.id.password_text)
    TextInputEditText passwordText;

    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;

    @BindView(R.id.password_confirm_text)
    TextInputEditText passwordConfirmText;

    @BindView(R.id.password_confirm_layout)
    TextInputLayout passwordConfirmLayout;

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

    @BindView(R.id.profile_image)
    CircleImageView profileImage;

    @BindArray(R.array.array_spinner_who_are_you)
    String[] whoAreYouArray;

    private AccountPresenter presenter;
    private CameraGalleryHelper cameraGalleryHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        unbinder = ButterKnife.bind(this);
        setupToolbar(toolbar, getString(R.string.create_account_title), null, true, true);
        initUI();
        presenter = new AccountPresenter(this);
        cameraGalleryHelper = new CameraGalleryHelper(this,
                new PermissionsHelper(this),
                () -> presenter.openCamera(),
                () -> presenter.openGallery());
    }

    private void initUI() {
        setUpTerms();
        setUpWhoAreYou();
        setUpForm();
    }

    private void setUpForm() {
        Observable<Boolean> nameObservable = FormHelper.fieldObservable(nameLayout, nameText, getString(R.string.form_error_name), new NameValidator());
        Observable<Boolean> emailObservable = FormHelper.fieldObservable(emailLayout, emailText, getString(R.string.form_error_name), new EmailValidator());
        Observable<Boolean> whoAreObservable = FormHelper.fieldObservable(whoLayout, whoText, getString(R.string.form_field_cannot_be_empty), new NotEmptyValidator());
        Observable<Boolean> passwordObservable = FormHelper.fieldObservable(passwordLayout, passwordText, getString(R.string.form_field_password), new PasswordValidator());
        Observable<Boolean> compositeObservable = FormHelper.compositeObservable(passwordConfirmLayout, passwordConfirmText, passwordText, getString(R.string.form_field_password_confirmation), new PasswordValidator());
        Observable<Boolean> checkObservable = FormHelper.checkBoxObservable(agreeCheckbox);

        Observable.combineLatest(nameObservable, emailObservable, whoAreObservable, passwordObservable, compositeObservable, checkObservable,
                (isNameValid, isEmailValid, isWhoValid, isPasswordValid, isCompositeValid, isChecked)
                        -> isNameValid && isEmailValid && isWhoValid && isPasswordValid && isCompositeValid && isChecked)
                .distinctUntilChanged().subscribe(isValid -> createAccountButton.setEnabled(isValid));
    }

    private void setUpWhoAreYou() {
        whoText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dismissKeyboard();
                showWhoAreDialog();
            }
        });

        whoText.setOnClickListener(v -> {
            if (whoText.hasFocus()) {
                dismissKeyboard();
                showWhoAreDialog();
            }
        });
        whoText.setKeyListener(null);
    }

    private void showWhoAreDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setAdapter(
                new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, whoAreYouArray),
                (dialog, position) -> {
                    whoText.setText(whoAreYouArray[position]);
                    dialog.dismiss();
                    passwordText.requestFocus();
                    openKeyboard(passwordText);
                });
        alertDialog.show();
    }

    // View Actions
    @OnClick(R.id.terms_text)
    protected void onClickTerms() {
        presenter.openPrivacyTerms();
    }

    @OnClick(R.id.profile_image)
    protected void onClickProfileImage() {
        presenter.tryToChangeProfileImage();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Custom
    private void setUpTerms() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(getString(R.string.account_agree_not_filled))
                .append(" ")
                .append(getString(R.string.terms), new CalligraphyTypefaceSpan(TypefaceUtils.load(getAssets(), "fonts/Roboto-Bold.ttf")), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                .append(" ")
                .append(getString(R.string.and))
                .append(" ")
                .append(getString(R.string.privacy), new CalligraphyTypefaceSpan(TypefaceUtils.load(getAssets(), "fonts/Roboto-Bold.ttf")), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        termsText.setText(builder);
    }

    @Override
    public void showImageUpdater() {
        cameraGalleryHelper.showChooseCameraOrGallery();
    }

    @Override
    public void cropImage(@NonNull Uri cameraFile,
                          @NonNull String cropImageFileName) {
        callToCrop(cameraFile, Uri.fromFile(cameraGalleryHelper.createOrGetMediaFile(cropImageFileName)));
    }

    @Override
    public void openDeviceCamera() {
        startActivityForResult(cameraGalleryHelper.createCameraIntent(), CAMERA_CODE);
    }

    @Override
    public void openDeviceGallery() {
        startActivityForResult(cameraGalleryHelper.createGalleryIntent(), GALLERY_CODE);
    }

    @Override
    public void updateProfile(@NonNull Uri profileUri) {
        Picasso.with(this)
                .load(profileUri).into(profileImage);
    }

    @Override
    public void openWebView() {
        startActivity(new Intent(this, ActivityWebView.class)
                .putExtra(TITLE_EXTRA, getString(R.string.terms_privacy_title))
                .putExtra(PATH_EXTRA, getString(R.string.terms_privacy_path)));
    }

    @Override
    public void showCropError() {
        showMessageSnackBar(coordinatorLayout, getString(R.string.ucrop_result_error));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_CODE:
                    Uri cameraFile = Uri.fromFile(cameraGalleryHelper.createOrGetMediaFile(TEMPORARY_IMAGE_FILENAME));
                    presenter.tryToCrop(cameraFile);
                    break;
                case GALLERY_CODE:
                    presenter.tryToCrop(data.getData());
                    break;
                case UCrop.REQUEST_CROP:
                    presenter.updateProfileImage(UCrop.getOutput(data));
                    break;

            }
        } else if (resultCode == RESULT_ERROR) {
            presenter.showErrorOnCrop();
        }
    }

    private void callToCrop(Uri sourceUri, Uri destinyUri) {
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        UCrop.of(sourceUri, destinyUri)
                .withAspectRatio(4, 3)
                .withOptions(options)
                .start(this);
    }
}
