package com.example.project5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.project5.databinding.FragmentTranslateBinding


class TranslateFragment : Fragment() {
    private var _binding: FragmentTranslateBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SharedViewModel

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state. Not currently implemented.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        _binding = FragmentTranslateBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        binding.editText.addTextChangedListener { text -> viewModel.setText(text.toString())}
        return view
    }

    /**
     * Called when the view previously created by onCreateView has been detached from the fragment.
     * Clean up resources associated with the binding.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}