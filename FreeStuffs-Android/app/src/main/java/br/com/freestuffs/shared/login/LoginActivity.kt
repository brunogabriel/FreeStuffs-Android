package br.com.freestuffs.shared.login

import android.os.Bundle
import br.com.freestuffs.R
import br.com.freestuffs.shared.application.BaseActivity

/**
 * Created by bruno on 24/08/2017.
 */
class LoginActivity: BaseActivity(), LoginContract.View {
    override var presenter: LoginContract.Presenter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter(this)
    }

    override fun startView() {
        // TODO: Setup view
    }
}