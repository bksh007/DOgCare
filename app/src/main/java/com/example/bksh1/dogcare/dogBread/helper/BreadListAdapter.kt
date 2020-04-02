package com.example.bksh1.dogcare.dogBread.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bksh1.dogcare.R
import com.example.bksh1.dogcare.utils.EmptyUtil
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.bread_list_item.view.*


class BreadListAdapter(var list: ArrayList<String>) : RecyclerView.Adapter<BreadListVH>() {
    private val ps = PublishSubject.create<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreadListVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bread_list_item, parent, false)
        return BreadListVH(view!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BreadListVH, position: Int) {
        val bread = list[position]

        if (EmptyUtil.isNotNull(bread) && EmptyUtil.isNotNull(bread)) {
            holder.tvBreadName.text = bread
            holder.lnlDogBread.setOnClickListener {
                ps.onNext(bread)
            }
        }
    }

    fun getClickedItem(): Observable<String> {
        return ps
    }

    fun add(records: ArrayList<String>) {
        this.list = records
        notifyDataSetChanged()
    }
}

class BreadListVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvBreadName = itemView.tvBreadName
    val lnlDogBread = itemView.lnlDogBread
}