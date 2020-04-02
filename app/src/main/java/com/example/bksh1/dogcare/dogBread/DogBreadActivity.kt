package com.example.bksh1.dogcare.dogBread

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bksh1.dogcare.R
import com.example.bksh1.dogcare.dogBread.helper.BreadListAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_dog_breed_list.*
import java.util.concurrent.TimeUnit

class DogBreadActivity : AppCompatActivity(), DogBreadListContact.View {

    private lateinit var presenter: DogBreadListContact.Presenter
    private var adapter: BreadListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_breed_list)
        presenter = DogBreadListPresenter(this)
        presenter.onCreate()
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
    override fun setUpDogBreadList(list: ArrayList<String>): Observable<String> {
        adapter = BreadListAdapter(list)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rvBreadList.layoutManager = layoutManager
        rvBreadList.adapter = adapter
        return adapter?.getClickedItem()!!.debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
    }
}