package com.example.newsapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.BR.variable
import com.example.newsapp.data.local.ArticleDatabaseModel
import org.jetbrains.anko.sdk25.coroutines.onClick
import kotlin.reflect.KClass


/**
 * This adapter should be initialized this way -
 *
 *  @usage  Declaring the adapter:
 *          private lateinit var genericAdapter: GenericAdapter<Any>
 *
 *  @usage In onCreateView:
 *         genericAdapter = GenericAdapter<Any>(R.layout.layout_x, x::class as KClass<Any>)
 *
 *  This adapter's constructor takes the layout file (R.layout.layout_x) for each item of the class
 *  and the Data Model class casted as KClass<Any>. The R.layout.layout_x must contain a variable
 *  of x::class. All ui components can use property of this variable to update GUI.
 *
 * @param T : Any
 * @property layoutId Int - The layout of the adpater item view
 * @property kClass KClass<Any> - the model class with which the adpater will be binded
 * @author Tonmoy Chandra Ray
 */

class GenericAdapter<T : Any>(@LayoutRes private val layoutId: Int, private val kClass: KClass<Any>, val listener: OnItemClickListener? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    /**
     * Generic list of item, each item of the list is binded with the view of layoutId in ViewHolder
     */
    private var dataList: List<T> = ArrayList()

    /**
     * Method called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
     * an item.
     *
     * @param parent ViewGroup
     * @param viewType Int
     * @return RecyclerView.ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        /*using data binding to inflate the layout*/
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false))
    }

    /**
     * Method called by RecyclerView to display the data at the specified position.
     *
     * @param holder ViewHolder
     * @param position Int
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        /*binds the item in position with xml variable*/
        (holder as ViewHolder).bind(dataList[position], kClass, listener)
    }

    /**
     * Method to fetch data list size or number of items in the list.
     *
     * @return Int
     */
    override fun getItemCount(): Int {
        return dataList.size
    }

    /**
     * Method to add data list.
     *
     */
    fun addDataList(dataList: List<T>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    /**
     * Method to remove all data
     *
     */
    fun clearDataList(){
        (dataList as ArrayList).clear()
    }


    /**
     * This ViewHolder will set the item variable to UI and the UI components will take the properties
     * of the variable to update GUI.
     *
     * @property binding ItemUserBinding
     * @constructor
     */
    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun <T> bind(t: T, kClass: KClass<Any>, listener: OnItemClickListener?) {

            when (kClass.simpleName) {
                /** All data classes ( POJO class equivalent in java) which will be passed
                 * to this adapter must be binded this way
                 * X::class.simpleName ->  binding.setVariable(variable, t as X)
                 */
                ArticleDatabaseModel::class.simpleName -> binding.setVariable(variable, t as ArticleDatabaseModel)

            }

            binding.root.onClick {
                listener?.onItemClick(t)
            }
        }
    }

    /**
     * Generic click event listener
     */
    interface OnItemClickListener {
        fun <T> onItemClick(item: T)
    }
}
