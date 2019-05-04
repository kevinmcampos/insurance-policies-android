package me.kevincampos.insurancepolicies.vehicledetail

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import dagger.android.AndroidInjection
import me.kevincampos.insurancepolicies.injection.ViewModelFactory
import me.kevincampos.insurancepolicies.policytransactions.PolicyTransactionsActivity
import me.kevincampos.presentation.VehicleDetailUiState
import me.kevincampos.presentation.VehicleDetailViewModel
import javax.inject.Inject


class VehicleDetailActivity : AppCompatActivity() {

    companion object {

        private const val VEHICLE_VRM_EXTRA = "VEHICLE_VRM_EXTRA"

        fun getIntent(activity: Activity, vrm: String): Intent {
            val intent = Intent(activity, VehicleDetailActivity::class.java)
            intent.putExtra(VEHICLE_VRM_EXTRA, vrm)
            return intent
        }
    }

    @Inject
    lateinit var viewFactory: ViewModelFactory<VehicleDetailViewModel>

    private lateinit var binding: me.kevincampos.insurancepolicies.databinding.ActivityVehicleDetailBinding
    private lateinit var viewModel: VehicleDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        val vrm = intent.getStringExtra(VEHICLE_VRM_EXTRA)

        binding =
            DataBindingUtil.setContentView(this, me.kevincampos.insurancepolicies.R.layout.activity_vehicle_detail)
        viewModel = ViewModelProviders.of(this, viewFactory)[VehicleDetailViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.loadVehicle(vrm)

        initRecyclerView()

        viewModel.uiState.observe(this, Observer<VehicleDetailUiState> {
            val uiState = it ?: return@Observer

            uiState.vehicleWithPolicies?.let { vehicleWithPolicies ->
                val adapter = binding.policyList.adapter as PoliciesAdapter
                adapter.swap(vehicleWithPolicies.policies)
            }

            uiState.shouldClose?.apply {
                consume()?.let { finish() }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(me.kevincampos.insurancepolicies.R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == me.kevincampos.insurancepolicies.R.id.help_action) {
            Toast.makeText(this, "// TODO: Open help", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        binding.policyList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.policyList.adapter = PoliciesAdapter { policy ->
            val intent = PolicyTransactionsActivity.getIntent(this, policy.policyId)
            startActivity(intent)
        }
    }

}