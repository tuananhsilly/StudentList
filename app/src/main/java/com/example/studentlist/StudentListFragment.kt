package com.example.studentlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.studentlist.databinding.FragmentStudentListBinding

class StudentListFragment : Fragment() {
    
    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var lvStudents: ListView
    private lateinit var adapter: StudentAdapter
    
    // Sử dụng ViewModelProvider để truyền Application context
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
        _binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        lvStudents = binding.lvStudents
        
        viewModel.students.observe(viewLifecycleOwner) { studentList ->
            adapter = StudentAdapter(
                requireContext(), 
                studentList,
                onDeleteClick = { position ->
                    viewModel.deleteStudent(position)
                }
            )
            lvStudents.adapter = adapter
        }
        
        lvStudents.setOnItemClickListener { _, _, position, _ ->
            val student = viewModel.getStudent(position)
            student?.let {
                val action = StudentListFragmentDirections.actionStudentListFragmentToUpdateStudentFragment(it, position)
                findNavController().navigate(action)
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
