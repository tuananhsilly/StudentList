package com.example.studentlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.studentlist.databinding.FragmentUpdateStudentBinding
import androidx.lifecycle.ViewModelProvider

class UpdateStudentFragment : Fragment() {
    
    private var _binding: FragmentUpdateStudentBinding? = null
    private val binding get() = _binding!!
    
    private val args: UpdateStudentFragmentArgs by navArgs()
    private val viewModel: StudentViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[StudentViewModel::class.java]
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateStudentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Load dữ liệu sinh viên vào form
        binding.edtMssv.setText(args.student.mssv)
        binding.edtName.setText(args.student.fullName)
        binding.edtPhone.setText(args.student.phone)
        binding.edtAddress.setText(args.student.address)
        
        binding.btnUpdate.setOnClickListener {
            if (validateInput()) {
                val updatedStudent = Student(
                    binding.edtMssv.text.toString().trim(),
                    binding.edtName.text.toString().trim(),
                    binding.edtPhone.text.toString().trim(),
                    binding.edtAddress.text.toString().trim()
                )
                viewModel.updateStudent(args.position, updatedStudent)
                findNavController().popBackStack()
            }
        }
        
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    
    private fun validateInput(): Boolean {
        if (binding.edtMssv.text.isNullOrBlank() || binding.edtName.text.isNullOrBlank()) {
            Toast.makeText(requireContext(), "MSSV và Họ tên không được để trống", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
