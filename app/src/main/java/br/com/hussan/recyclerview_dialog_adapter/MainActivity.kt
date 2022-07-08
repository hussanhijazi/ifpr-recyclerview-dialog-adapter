package br.com.hussan.recyclerview_dialog_adapter

import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.hussan.recyclerview_dialog_adapter.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val itemAdapter = ItemAdapter(mutableListOf(), ::clickItem)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        binding.listItems.run {
            adapter = itemAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun clickItem(item: String, position: Int) {
        startActionMode(ActionModeCallback(item, position))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_item -> {
                openDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun openDialog(item: String? = null, position: Int? = null) {
        val inputEditTextField = EditText(this)

        if (item != null) {
            inputEditTextField.setText(item)
        }

        val dialog = AlertDialog.Builder(this)
            .setMessage(R.string.item)
            .setView(inputEditTextField)
            .setPositiveButton(R.string.ok) { _, _ ->
                val editTextInput = inputEditTextField.text.toString()
                if (position != null) {
                    itemAdapter.updateItem(position, editTextInput)
                } else itemAdapter.addItem(editTextInput)

            }
            .setNegativeButton(R.string.cancel, null)
            .create()
        dialog.show()
    }


    inner class ActionModeCallback(
        private val item: String,
        private val position: Int
    ) : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?, menuItem: MenuItem?): Boolean {
            when (menuItem?.itemId) {
                R.id.edit_item -> {
                    openDialog(item, position)
                    mode?.finish()
                    return true
                }
                R.id.delete_item -> {
                    itemAdapter.deleteItem(item)
                    mode?.finish()
                    return true
                }
            }
            return false
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater = mode?.menuInflater
            inflater?.inflate(R.menu.action_mode_menu, menu)
            mode?.title = item
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {}
    }
}