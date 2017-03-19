package br.com.friendlydonations.shared.views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.friendlydonations.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by brunogabriel on 18/02/17.
 */

public class ApplicationDialogFragment extends DialogFragment {

    // TODO: Fix problem when change idiom
    private Unbinder unbider;
    private boolean isScrollable;
    private final String title;
    private final String refuse;
    private final String accept;
    private final String content;

    @BindView(R.id.alert_title)
    TextView alertTitle;

    @BindView(R.id.no_text)
    TextView refuseText;

    @BindView(R.id.yes_text)
    TextView acceptText;

    @BindView(R.id.content_text)
    TextView contentText;

    @OnClick(R.id.yes_text)
    protected void onClickYes() {
        dismiss();
    }

    @OnClick(R.id.no_text)
    protected void onClickNo() {
        if (!TextUtils.isEmpty(refuse)) {
            dismiss();
        }
    }

    public ApplicationDialogFragment(String title, String content, String refuse, String accept, boolean isScrollable) {
        super();
        this.title = title;
        this.refuse = refuse;
        this.accept = accept;
        this.content = content;
        this.isScrollable = isScrollable;
    }

    public ApplicationDialogFragment(String title, String content,  String refuse, String accept) {
        super();
        this.title = title;
        this.refuse = refuse;
        this.accept = accept;
        this.content = content;
        this.isScrollable = true;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(isScrollable ? R.layout.alert_choose_scrollable: R.layout.alert_choose, null);
        unbider = ButterKnife.bind(this, view);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        initializeWithParams();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbider.unbind();
    }

    private void initializeWithParams() {
        alertTitle.setText(title);
        refuseText.setText(refuse);
        acceptText.setText(accept);
        contentText.setText(content);
    }
}
