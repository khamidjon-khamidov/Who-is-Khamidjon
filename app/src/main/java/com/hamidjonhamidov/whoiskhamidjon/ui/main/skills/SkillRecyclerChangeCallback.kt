package com.hamidjonhamidov.whoiskhamidjon.ui.main.skills

import androidx.recyclerview.widget.ListUpdateCallback

internal class SkillRecyclerChangeCallback(
    private val adapter: SkillsListAdapter
) : ListUpdateCallback {

    override fun onChanged(position: Int, count: Int, payload: Any?) {
        adapter.notifyItemRangeChanged(position, count, payload)
    }

    override fun onInserted(position: Int, count: Int) {
        adapter.notifyItemRangeChanged(position, count)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        adapter.notifyDataSetChanged()
    }

    override fun onRemoved(position: Int, count: Int) {
        adapter.notifyDataSetChanged()
    }
}