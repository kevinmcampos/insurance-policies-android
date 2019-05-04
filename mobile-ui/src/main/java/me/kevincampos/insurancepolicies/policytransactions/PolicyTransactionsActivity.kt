package me.kevincampos.insurancepolicies.policytransactions

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import dagger.android.AndroidInjection
import me.kevincampos.insurancepolicies.R
import me.kevincampos.insurancepolicies.databinding.ActivityPolicyTransactionsBinding
import me.kevincampos.insurancepolicies.injection.ViewModelFactory
import me.kevincampos.presentation.PolicyTransactionsUiState
import me.kevincampos.presentation.PolicyTransactionsViewModel
import javax.inject.Inject


class PolicyTransactionsActivity: AppCompatActivity() {

    companion object {

        private const val POLICY_ID_EXTRA = "POLICY_ID_EXTRA"

        fun getIntent(activity: Activity, policyId: String): Intent {
            val intent = Intent(activity, PolicyTransactionsActivity::class.java)
            intent.putExtra(POLICY_ID_EXTRA, policyId)
            return intent
        }
    }

    @Inject
    lateinit var viewFactory: ViewModelFactory<PolicyTransactionsViewModel>

    private lateinit var binding: ActivityPolicyTransactionsBinding
    private lateinit var viewModel: PolicyTransactionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        val policyId = intent.getStringExtra(POLICY_ID_EXTRA)

        binding = setContentView(this, R.layout.activity_policy_transactions)
        viewModel = ViewModelProviders.of(this, viewFactory)[PolicyTransactionsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.loadTransactions(policyId)

        initRecyclerView()

        viewModel.uiState.observe(this, Observer<PolicyTransactionsUiState> {
            val uiState = it ?: return@Observer

            uiState.financialTransactions?.let { financialTransactions ->
                val adapter = binding.transactionList.adapter as FinancialTransactionsAdapter
                adapter.swap(financialTransactions)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.help_action) {
            Toast.makeText(this, "// TODO: Open help", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        binding.transactionList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.transactionList.adapter = FinancialTransactionsAdapter { policy ->
            // TODO: Open policy transactions
        }
    }

    private fun displayError(@StringRes errorString: Int) {
        Snackbar.make(binding.root, errorString, Snackbar.LENGTH_LONG)
            .setAction(me.kevincampos.insurancepolicies.R.string.dismiss) { /* do nothing */ }.apply {
                val params = view.layoutParams as FrameLayout.LayoutParams
                val marginInDps =
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics).toInt()

                params.setMargins(
                    params.leftMargin + marginInDps,
                    params.topMargin,
                    params.rightMargin + marginInDps,
                    params.bottomMargin + marginInDps
                )

                view.layoutParams = params
                show()
            }
    }

}