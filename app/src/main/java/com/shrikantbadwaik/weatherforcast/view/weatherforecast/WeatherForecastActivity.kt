package com.shrikantbadwaik.weatherforcast.view.weatherforecast

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.android.gms.location.LocationServices
import com.shrikantbadwaik.weatherforcast.BR
import com.shrikantbadwaik.weatherforcast.R
import com.shrikantbadwaik.weatherforcast.databinding.ActivityWeatherForecastBinding
import com.shrikantbadwaik.weatherforcast.domain.model.Forecast
import com.shrikantbadwaik.weatherforcast.domain.util.AppUtil
import com.shrikantbadwaik.weatherforcast.domain.util.Constants
import com.shrikantbadwaik.weatherforcast.domain.util.DialogUtil
import com.shrikantbadwaik.weatherforcast.domain.util.PermissionUtil
import dagger.android.AndroidInjection
import javax.inject.Inject

class WeatherForecastActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    @Inject
    lateinit var adapter: WeatherForecastRecyclerAdapter

    private lateinit var viewModel: WeatherForecastViewModel
    private lateinit var activityBinding: ActivityWeatherForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setupBindingAndViewModel()
        setupRecyclerView()

        viewModel.setFusedLocationClient(LocationServices.getFusedLocationProviderClient(this))
        if (AppUtil.isMarshmallow()) {
            viewModel.getDeviceLocation(PermissionUtil.hasPermission(this, PermissionUtil.LOCATION_PERMISSION))
        } else viewModel.startLocationUpdates()

        permissionsObserver()
        dialogObserver()
        apiResponseObserver()
        clickObserver()
        weatherForecastObserver()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel.onRequestPermissionResult(requestCode, permissions, grantResults)
    }

    private fun setupBindingAndViewModel() {
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_weather_forecast)
        viewModel = ViewModelProviders.of(this, factory).get(WeatherForecastViewModel::class.java)
        activityBinding.setVariable(BR.viewModel, viewModel)
        activityBinding.executePendingBindings()
    }

    private fun setupRecyclerView() {
        activityBinding.recyclerView.adapter = adapter
        activityBinding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun permissionsObserver() {
        viewModel.getPermissionRequest().observe(this, Observer {
            when (it) {
                Constants.RuntimePermissions.LOCATION_PERMISSION.name -> {
                    PermissionUtil.requestPermission(
                        this, PermissionUtil.LOCATION_PERMISSION,
                        PermissionUtil.PERMISSION_REQUEST_CODE_LOCATION
                    )
                }
            }
        })
    }

    private fun dialogObserver() {
        viewModel.getDialog().observe(this, Observer {
            when (it) {
                Constants.DialogState.GPS_DIALOG.name -> {
                    DialogUtil.showErrorDialog(
                        this, getString(R.string.txt_gps_enabled),
                        DialogInterface.OnClickListener { dialog, which -> dialog?.dismiss() })
                }
                Constants.DialogState.LOCATION_PERMISSION_DIALOG.name -> {
                    DialogUtil.showErrorDialog(
                        this, getString(R.string.txt_location_permission),
                        DialogInterface.OnClickListener { dialog, which -> dialog?.dismiss() })
                }
                Constants.DeviceState.OFFLINE.name -> {
                    DialogUtil.showErrorDialog(
                        this, getString(R.string.txt_no_internet_connection),
                        DialogInterface.OnClickListener { dialog, which -> dialog?.dismiss() })
                }
            }
        })
    }

    private fun apiResponseObserver() {
        viewModel.getRequest().observe(this, Observer {
            when (it) {
                Constants.Request.ON_SUCCESS.name -> {
                    activityBinding.constraintLayout.setBackgroundColor(Color.parseColor("#f5f6f7"))
                    activityBinding.tvTemperature.visibility = View.VISIBLE
                    activityBinding.tvCity.visibility = View.VISIBLE
                    activityBinding.recyclerView.visibility = View.VISIBLE
                    activityBinding.textView01.visibility = View.GONE
                    activityBinding.btnRetry.visibility = View.GONE
                }
                Constants.Request.ON_FAILURE.name -> {
                    activityBinding.constraintLayout.setBackgroundColor(Color.parseColor("#e85959"))
                    activityBinding.tvTemperature.visibility = View.GONE
                    activityBinding.tvCity.visibility = View.GONE
                    activityBinding.recyclerView.visibility = View.GONE
                    activityBinding.textView01.visibility = View.VISIBLE
                    activityBinding.btnRetry.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun clickObserver() {
        viewModel.getClickListener().observe(this, Observer {
            when (it) {
                Constants.RETRY -> {
                    activityBinding.constraintLayout.setBackgroundColor(Color.parseColor("#f5f6f7"))
                    activityBinding.tvTemperature.visibility = View.GONE
                    activityBinding.tvCity.visibility = View.GONE
                    activityBinding.recyclerView.visibility = View.GONE
                    activityBinding.textView01.visibility = View.GONE
                    activityBinding.btnRetry.visibility = View.GONE

                    if (AppUtil.isMarshmallow()) {
                        viewModel.getDeviceLocation(
                            PermissionUtil.hasPermission(this, PermissionUtil.LOCATION_PERMISSION)
                        )
                    } else viewModel.startLocationUpdates()
                }
            }
        })
    }

    private fun weatherForecastObserver() {
        viewModel.getWeatherForecast().observe(this, Observer<ArrayList<Forecast.ForecastDay>> {
            viewModel.setForecast(it)
        })
    }
}