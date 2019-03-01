package com.shrikantbadwaik.weatherforcast.view.weatherforecast

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.location.Geocoder
import android.os.Looper
import com.google.android.gms.location.*
import com.shrikantbadwaik.weatherforcast.R
import com.shrikantbadwaik.weatherforcast.data.remote.CallbackObserverWrapper
import com.shrikantbadwaik.weatherforcast.data.repository.Repository
import com.shrikantbadwaik.weatherforcast.domain.Constants
import com.shrikantbadwaik.weatherforcast.domain.model.CurrentJSON
import com.shrikantbadwaik.weatherforcast.domain.model.ForeCastJSON
import com.shrikantbadwaik.weatherforcast.domain.model.Forecast
import com.shrikantbadwaik.weatherforcast.domain.util.AppUtil
import com.shrikantbadwaik.weatherforcast.domain.util.PermissionUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class WeatherForecastViewModel @Inject constructor(
    private val ctx: Application, private val appUtil: AppUtil, private val repository: Repository
) : AndroidViewModel(ctx) {
    private var locationRequest: LocationRequest = LocationRequest()
    private var locationCallback: LocationCallback? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private val permissionRequest: MutableLiveData<String> = MutableLiveData()
    private val dialog: MutableLiveData<String> = MutableLiveData()
    private var disposable: Disposable? = null
    private val loading: ObservableBoolean = ObservableBoolean(false)
    private val request: MutableLiveData<String> = MutableLiveData()
    private val tempInC: ObservableField<String> = ObservableField()
    private val city: ObservableField<String> = ObservableField()
    private val clickListener: MutableLiveData<Int> = MutableLiveData()
    private val forecast: ObservableArrayList<Forecast.ForecastDay> = ObservableArrayList()

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

    fun getPermissionRequest(): MutableLiveData<String> = permissionRequest

    fun getDialog(): MutableLiveData<String> = dialog

    fun setLoading(state: Boolean) {
        loading.set(state)
    }

    fun isLoading(): ObservableBoolean = loading

    fun getRequest(): MutableLiveData<String> = request

    fun getTempInC(): ObservableField<String> = tempInC

    fun getCity(): ObservableField<String> = city

    fun getClickListener(): MutableLiveData<Int> = clickListener

    fun getForecast(): ObservableArrayList<Forecast.ForecastDay> = forecast

    fun onRetryButtonClicked() {
        clickListener.value = Constants.RETRY
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)
    }

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                locationResult?.let {
                    val currentLocation = it.lastLocation
                    getWeatherUpdates(currentLocation.latitude, currentLocation.longitude)
                    stopLocationUpdates()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    fun stopLocationUpdates() {
        fusedLocationClient?.removeLocationUpdates(locationCallback)
    }

    fun setFusedLocationClient(client: FusedLocationProviderClient?) {
        this.fusedLocationClient = client
    }

    fun getWeatherUpdates(latitude: Double, longitude: Double) {
        if (AppUtil.isDeviceOnline(ctx)) {
            setLoading(true)
            disposable = Observable.fromCallable {
                val geocoder = Geocoder(ctx, Locale.getDefault())
                val locations = geocoder.getFromLocation(latitude, longitude, 1)
                return@fromCallable locations[0].locality
            }.flatMap(object : Function<String, Observable<Pair<CurrentJSON, ForeCastJSON>>> {
                override fun apply(city: String): Observable<Pair<CurrentJSON, ForeCastJSON>> {
                    return Observable.zip(
                        repository.getCurrentWeatherForecast(city), repository.getNextFourDayWeatherForecast(city),
                        BiFunction<CurrentJSON, ForeCastJSON, Pair<CurrentJSON, ForeCastJSON>> { t1, t2 ->
                            Pair(t1, t2)
                        })
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackObserverWrapper<Pair<CurrentJSON, ForeCastJSON>>() {
                    override fun onSuccess(result: Pair<CurrentJSON, ForeCastJSON>) {
                        setLoading(false)
                        request.value = Constants.Request.ON_SUCCESS.name
                        val currentJSON = result.first
                        currentJSON.current?.let {
                            tempInC.set(appUtil.getString(R.string.format_01, it.tempInC))
                        }
                        currentJSON.location?.let {
                            city.set(it.city)
                        }
                        //TODO: show 4 days list
                    }

                    override fun onFailure(error: String) {
                        setLoading(false)
                        request.value = Constants.Request.ON_FAILURE.name
                    }

                    override fun onCompleted() {
                        setLoading(false)
                        request.value = Constants.Request.ON_COMPLETED.name
                    }
                })
        } else dialog.value = Constants.DeviceState.OFFLINE.name
    }

    fun getDeviceLocation(isPermissionGranted: Boolean) {
        if (!appUtil.isGPSEnabled()) {
            dialog.value = Constants.DialogState.GPS_DIALOG.name
        } else {
            if (isPermissionGranted) {
                createLocationCallback()
                createLocationRequest()
                startLocationUpdates()
            } else permissionRequest.value = Constants.RuntimePermissions.LOCATION_PERMISSION.name
        }
    }

    fun onRequestPermissionResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PermissionUtil.PERMISSION_REQUEST_CODE_LOCATION) {
            if (grantResults[0] == PermissionUtil.PERMISSION_GRANTED) {
                getDeviceLocation(true)
            } else dialog.value = Constants.DialogState.LOCATION_PERMISSION_DIALOG.name
        }
    }
}