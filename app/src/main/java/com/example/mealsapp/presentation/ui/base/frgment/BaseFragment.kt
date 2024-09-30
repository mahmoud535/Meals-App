package com.example.mealsapp.presentation.ui.base.frgment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.mealsapp.presentation.ui.base.extentions.bindView


abstract class BaseFragment<Binding : ViewBinding> : Fragment() {

    private var _binding: Binding? = null
    protected val binding: Binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        onFragmentReady(savedInstanceState)
        setupObservers()
    }

    abstract fun onFragmentReady(savedInstanceState: Bundle?)

    abstract fun setupObservers()

    abstract fun setupListener()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}