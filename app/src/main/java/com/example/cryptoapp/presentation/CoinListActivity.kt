package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.ActivityCoinListBinding
import com.example.cryptoapp.domain.entities.Coin
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter

class CoinListActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCoinListBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[CoinViewModel::class.java]
    }

    private val coinAdapter = CoinInfoAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        coinAdapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coin: Coin) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinListActivity,
                    coin.fromSymbol
                )
                startActivity(intent)
            }
        }
        binding.rvCoinPriceList.adapter = coinAdapter

        viewModel.coinList.observe(this@CoinListActivity) {
            coinAdapter.coinInfoList = it
        }
    }
}
