package com.example.cryptoapp.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cryptoapp.databinding.FragmentCoinDetailBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CoinDetailFragment : Fragment() {

    private val symbol by lazy {
        requireArguments().getString(EXTRA_KEY, EMPTY_SYMBOL)
    }

    private val appComponent by lazy {
        (requireActivity().application as CoinApplication)
            .appComponent.activityComponent()
            .create()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: CoinViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("CoinDetailFragment is null!")

    override fun onAttach(context: Context) {
        super.onAttach(context)

        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", "$this")
        viewModel.getDetailInfo(symbol).observe(viewLifecycleOwner) {
            with(binding) {
                tvPrice.text = it.price
                tvMinPrice.text = it.lowDay
                tvMaxPrice.text = it.highDay
                tvLastMarket.text = it.lastMarket
                tvLastUpdate.text = it.lastUpdate
                tvFromSymbol.text = it.fromSymbol
                tvToSymbol.text = it.toSymbol
                Picasso.get().load(it.imageUrl).into(ivLogoCoin)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        private const val EMPTY_SYMBOL = ""
        private const val EXTRA_KEY = "key"
        fun newInstance(fromSymbol: String): Fragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_KEY, fromSymbol)
                }
            }
        }
    }
}
