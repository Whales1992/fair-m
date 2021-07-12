package com.android.fairmoney.ui

import android.R.layout.simple_spinner_dropdown_item
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.text.bold
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.fairmoney.R
import com.android.fairmoney.adapter.GridViewAdapter
import com.android.fairmoney.adapter.IAdapterLogicActions
import com.android.fairmoney.business.BusinessLogic
import com.android.fairmoney.databinding.ActivityMainBinding
import com.android.fairmoney.rectrofit.ApiCalls
import com.android.fairmoney.room.AppDatabase
import com.android.fairmoney.room.entities.RateEntity
import com.android.fairmoney.viewModel.*
import dagger.android.support.DaggerAppCompatActivity
import retrofit2.Retrofit
import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Named

class MainActivity : DaggerAppCompatActivity(), IAdapterLogicActions {

    private lateinit var currencyRateViewModel: CurrencyRateViewModel
    private lateinit var mHandler: Handler
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var gridViewAdapter: GridViewAdapter

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var provideNetworkClient: Retrofit

    @Inject
    lateinit var provideDataBase: AppDatabase

    @Inject
    @Named("token")
    lateinit var token: Map<String, String>

    private lateinit var sharedPreference: SharedPreferences

    private var unit = 0.0
    private var position = 0
    private val rateList = ArrayList<RateEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreference = getSharedPreferences("com.android.fairmoney", Context.MODE_PRIVATE)
        mHandler = Handler(Looper.getMainLooper())

        currencyRateViewModel = ViewModelProvider(this, ViewModelFactory(provideDataBase)).get(CurrencyRateViewModel::class.java)
        currencyRateViewModel.getCurrencyRatesAndTypes(ApiCalls(provideNetworkClient, token), sharedPreference).observe(this, ratesObserver)
        currencyRateViewModel.retrieveKeptUnitAndSelected().observe(this, conversionPossibleEventObserver)

        binding.amountHintEdittext.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC)
        binding.amountHintEdittext.doOnTextChanged { text, _, _, _ ->
            val amount = text.toString()

            if (amount.isEmpty())
                currencyRateViewModel.keepUnitAndSelected(-1.0, -1)
            else
                currencyRateViewModel.keepUnitAndSelected(amount.toDouble(), -1)
        }
    }

    private val conversionPossibleEventObserver = Observer<Map<String, Any>> { t ->
        runOnUiThread {
            unit = t["unit"] as Double
            position = t["selectedPosition"] as Int

            try {
                gridViewAdapter.notifyDataSetChanged()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private val ratesObserver = Observer<ResponseObjectMapper> { t ->
        if (t != null && t.isSuccess() && t.responseObject() != null) {
            val lists = ArrayList<String>()

            rateList.addAll(t.responseObject() as ArrayList<RateEntity>)

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                rateList.stream().forEach { lists.add("--${it.currencyCode}-- ${it.currencyName}") }
            }

            runOnUiThread {
                adapter = ArrayAdapter<String>(this, simple_spinner_dropdown_item, lists)
                binding.conversionRateListSpinner.adapter = adapter
                binding.conversionRateListSpinner.onItemSelectedListener = spinnerAdapter
                adapter.notifyDataSetChanged()

                binding.emptyTextview.text = if (rateList.size < 0) getText(R.string.empty_text) else ""

                gridViewAdapter = GridViewAdapter(this, rateList, this)
                binding.listGridView.adapter = gridViewAdapter
            }
        }
    }

    private val spinnerAdapter = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            adapter.setDropDownViewResource(R.layout.activity_spinner)
            currencyRateViewModel.keepUnitAndSelected(-1.0, position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            currencyRateViewModel.keepUnitAndSelected(-1.0, -1)
        }
    }

    override fun convert(countryTextView: TextView, currencyTextView: TextView, rateItem: RateEntity) {
        val selectRate = rateItem.currencyRate
        countryTextView.text = rateItem.currencyName

        val convertedAmount = BusinessLogic().convertUnSecure(unit, selectRate!!, rateList[position].currencyRate!!)
        val money = DecimalFormat("#,###.##").format(convertedAmount)

        val finalResult = SpannableStringBuilder()
                .append("${rateItem.currencyCode} \n")
                .bold{ append(money) }

        currencyTextView.text = finalResult
    }
}