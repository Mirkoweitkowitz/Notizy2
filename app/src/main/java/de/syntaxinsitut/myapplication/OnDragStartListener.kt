package de.syntaxinsitut.myapplication

import de.syntaxinsitut.myapplication.adapter.NotizyAdapter

interface OnDragStartListener {

    fun onDragStart(holder: NotizyAdapter.NotizenViewHolder?)
}