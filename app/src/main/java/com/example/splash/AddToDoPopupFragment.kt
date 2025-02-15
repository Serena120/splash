package com.example.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.splash.databinding.FragmentAddToDoPopupBinding
import com.example.splash.utils.ToDoData
import com.google.android.material.textfield.TextInputEditText

class AddToDoPopupFragment : DialogFragment() {

    private lateinit var binding: FragmentAddToDoPopupBinding
    private lateinit var listener: DialogNextBtnClickListener
    private var toDoData: ToDoData? = null

    fun setListener(listener: DialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "AddToDoPopupFragment"

        @JvmStatic
        fun newInstance(taskId: String, task: String) = AddToDoPopupFragment().apply {
            arguments = Bundle().apply {
                putString("taskId", taskId)
                putString("task", task)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddToDoPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            toDoData = ToDoData(
                arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString()
            )
            binding.todoEt.setText(toDoData?.task)
        }

        registerEvents()
    }

    private fun registerEvents() {
        binding.todoNextBtn.setOnClickListener {
            val todotask = binding.todoEt.text.toString()
            if (todotask.isNotEmpty()) {
                if (toDoData == null){
                    listener.onSaveTask(todotask, binding.todoEt)
                }else{
                    toDoData?.task = todotask
                    listener.onUpdateTask(toDoData!!, binding.todoEt)
                }

            } else {
                Toast.makeText(context, "Please type some text", Toast.LENGTH_SHORT).show()
            }
        }

        binding.todoClose.setOnClickListener {
            dismiss()
        }
    }

    interface DialogNextBtnClickListener {
        fun onSaveTask(todo: String, todoET: TextInputEditText)
        fun onUpdateTask(toDoData: ToDoData, todoET: TextInputEditText)
    }
}