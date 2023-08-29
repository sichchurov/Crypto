package com.example.cryptoapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.cryptoapp.CoinApplication
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinListBinding
import com.example.cryptoapp.domain.entities.Coin
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter
import javax.inject.Inject

class CoinListActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCoinListBinding.inflate(layoutInflater)
    }

    private val appComponent by lazy {
        (application as CoinApplication).appComponent.activityComponent().create()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: CoinViewModel by viewModels { viewModelFactory }

    private val coinAdapter = CoinInfoAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {

        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRecyclerView()
        coinClickListener()

        Log.d("TAG", "$this")
    }

    private fun initRecyclerView() {
        binding.rvCoinPriceList.apply {
            adapter = coinAdapter
            itemAnimator = null
        }

        viewModel.coinList.observe(this) {
            coinAdapter.submitList(it)
        }
    }

    private fun isOnePaneMode() = binding.fragmentContainerView == null

    private fun coinClickListener() {
        coinAdapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coin: Coin) {
                if (isOnePaneMode()) {
                    startActivityDetail(coin.fromSymbol)
                } else {
                    startFragmentDetail(coin.fromSymbol)
                }
            }
        }
    }

    private fun startActivityDetail(fromSymbol: String) {
        startActivity(
            CoinDetailActivity.newIntent(
                this@CoinListActivity,
                fromSymbol
            )
        )
    }

    private fun startFragmentDetail(fromSymbol: String) {
        supportFragmentManager.apply {
            popBackStack()
            commit {
                addToBackStack(null)
                replace(R.id.fragment_container_view, CoinDetailFragment.newInstance(fromSymbol))
            }
        }
    }
}
