package me.kevincampos.insurancepolicies.vehiclelist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import dagger.android.AndroidInjection
import me.kevincampos.insurancepolicies.R
import me.kevincampos.insurancepolicies.injection.ViewModelFactory
import me.kevincampos.insurancepolicies.vehicledetail.VehicleDetailActivity
import me.kevincampos.presentation.VehicleListUiState
import me.kevincampos.presentation.VehicleListViewModel
import me.kevincampos.presentation.model.VehicleListItem
import javax.inject.Inject


class VehicleListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewFactory: ViewModelFactory<VehicleListViewModel>

    private lateinit var binding: me.kevincampos.insurancepolicies.databinding.ActivityVehicleListBinding
    private lateinit var viewModel: VehicleListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_vehicle_list)
        viewModel = ViewModelProviders.of(this, viewFactory)[VehicleListViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initRecyclerView()

        viewModel.uiState.observe(this, Observer<VehicleListUiState> {
            val uiState = it ?: return@Observer

            uiState.vehiclesWithActivePolicies?.let { vehicleWithPolicies ->
                val adapter = binding.vehicleList.adapter as VehiclesAdapter

                val groupBy = vehicleWithPolicies.groupBy { it.hasActivePolicy }
                val activeVehicles = groupBy[true]

                val listOfItem = mutableListOf<VehicleListItem>()
                if (activeVehicles != null && activeVehicles.isNotEmpty()) {
                    listOfItem.add(HeaderView("Active policies"))
                    listOfItem.addAll(activeVehicles)
                }

                val inactiveVehicles = groupBy[false]
                if (inactiveVehicles != null && inactiveVehicles.isNotEmpty()) {
                    listOfItem.add(HeaderView("Vehicles"))
                    listOfItem.addAll(inactiveVehicles)
                }

                adapter.swapSection(listOfItem)
            }

            uiState.showError?.apply {
                consume()?.let { stringError -> displayError(stringError) }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.refresh_action) {
            viewModel.refresh()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        binding.vehicleList.adapter = VehiclesAdapter { vehicleWithPolicies ->
            val intent = VehicleDetailActivity.getIntent(this, vehicleWithPolicies.vrm)
            startActivity(intent)
        }
    }

    private fun displayError(@StringRes errorString: Int) {
        Snackbar.make(binding.root, errorString, Snackbar.LENGTH_LONG)
            .setAction(R.string.dismiss) { /* do nothing */ }.apply {
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
