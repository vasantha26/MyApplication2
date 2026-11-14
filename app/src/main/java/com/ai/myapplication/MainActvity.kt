package com.ai.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActvity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: EmplyoeeAdapter

    private val viewModel: EmplyoeeViewModel by viewModels()

    /**
     * null na CREATE mode, id irundha UPDATE mode.
     * Language maathumbodhu / rotate pannumbodhu activity recreate aagum,
     * appo indha field reset aagidum. Adhanaala onSaveInstanceState-la save pannuren.
     */
    private var editingId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupButtons()
        observeEmplyoee()

        // recreate aana piraku edit mode-a thirumba kondu varanum.
        // EditText-oda text-a system thaana restore pannidum, aana
        // "entha employee-a edit panren" nu andha id-a naama thaan save pannanum.
        savedInstanceState?.let { state ->
            if (state.containsKey(KEY_EDITING_ID)) {
                editingId = state.getInt(KEY_EDITING_ID)
                binding.btnSave.setText(R.string.action_update)
                binding.btnCancel.visibility = View.VISIBLE
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        editingId?.let { outState.putInt(KEY_EDITING_ID, it) }
    }

    private fun setupRecyclerView() {
        adapter = EmplyoeeAdapter(
            onEdit = { startEditing(it) },
            onDelete = { deleteEmplyoee(it) }
        )
        binding.rvEmplyoee.layoutManager = LinearLayoutManager(this)
        binding.rvEmplyoee.adapter = adapter
    }

    private fun setupButtons() {
        binding.btnSave.setOnClickListener { saveEmplyoee() }
        binding.btnCancel.setOnClickListener { clearForm() }
    }

    private fun observeEmplyoee() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allEmplyoee.collect { list ->
                    adapter.submitList(list)
                    binding.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    /** CREATE / UPDATE — button-oda mode-a poruthu */
    private fun saveEmplyoee() {
        val name = binding.etName.text.toString().trim()
        val dept = binding.etDept.text.toString().trim()

        if (name.isEmpty() || dept.isEmpty()) {
            toast(getString(R.string.error_empty_fields))
            return
        }

        val id = editingId
        if (id == null) {
            viewModel.createEmplyoee(name, dept)
            toast(getString(R.string.msg_created))
        } else {
            viewModel.updateEmplyoee(EmplyoeeModel(id = id, name = name, dept = dept))
            toast(getString(R.string.msg_updated))
        }
        clearForm()
    }

    private fun startEditing(emplyoee: EmplyoeeModel) {
        editingId = emplyoee.id
        binding.etName.setText(emplyoee.name)
        binding.etDept.setText(emplyoee.dept)
        binding.btnSave.setText(R.string.action_update)
        binding.btnCancel.visibility = View.VISIBLE
        binding.etName.requestFocus()
    }

    private fun deleteEmplyoee(emplyoee: EmplyoeeModel) {
        // edit panra employee-yeh delete pannina, form-a reset pannanum
        if (editingId == emplyoee.id) clearForm()
        viewModel.deleteEmplyoee(emplyoee)
        toast(getString(R.string.msg_deleted))
    }

    private fun clearForm() {
        editingId = null
        binding.etName.text.clear()
        binding.etDept.text.clear()
        binding.btnSave.setText(R.string.action_add)
        binding.btnCancel.visibility = View.GONE
    }

    // ---------- MENU ----------

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val KEY_EDITING_ID = "editing_id"
    }
}
