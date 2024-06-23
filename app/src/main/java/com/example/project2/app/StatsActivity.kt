package com.example.project2.app

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project2.DB_management.DbClient
import com.example.project2.DB_management.common_types.TestType
import com.example.project2.DB_management.dto.result.GetResultDto
import com.example.project2.R

class StatsActivity : AppCompatActivity() {
    private lateinit var adapterType1: TestResultAdapter
    private lateinit var adapterType2: TestResultAdapter
    private lateinit var adapterType3: TestResultAdapter
    private val dbClient = DbClient()
    private lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_stats)

        val recyclerViewType1 = findViewById<RecyclerView>(R.id.recycler_view_type1)
        val recyclerViewType2 = findViewById<RecyclerView>(R.id.recycler_view_type2)
        val recyclerViewType3 = findViewById<RecyclerView>(R.id.recycler_view_type3)

        recyclerViewType1.layoutManager = LinearLayoutManager(this)
        recyclerViewType2.layoutManager = LinearLayoutManager(this)
        recyclerViewType3.layoutManager = LinearLayoutManager(this)

        adapterType1 = TestResultAdapter(listOf())
        adapterType2 = TestResultAdapter(listOf())
        adapterType3 = TestResultAdapter(listOf())

        recyclerViewType1.adapter = adapterType1
        recyclerViewType2.adapter = adapterType2
        recyclerViewType3.adapter = adapterType3

        val user = dbClient.getLoggedUser()

        user?.let {
            getResultsByUserId(it.username)
        }

        Log.e("Test", "$user")
    }

    private fun getResultsByUserId(userId: String) {
        dbClient.getResultsByUserId(userId).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val resultsType1 = task.result.filter { it?.testId == TestType.TEST1 }
                val resultsType2 = task.result.filter { it?.testId == TestType.TEST2 }
                val resultsType3 = task.result.filter { it?.testId == TestType.TEST3 }

                adapterType1.updateData(resultsType1)
                adapterType2.updateData(resultsType2)
                adapterType3.updateData(resultsType3)
            } else {
                Log.w(TAG, "Error getting documents.", task.exception)
            }
        }
    }
}

class TestResultAdapter(private var testResults: List<GetResultDto?>) : RecyclerView.Adapter<TestResultAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val testType: TextView = view.findViewById(R.id.test_type)
        val result: TextView = view.findViewById(R.id.result)
        val timeSpent: TextView = view.findViewById(R.id.time_spent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stats_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val testResult = testResults[position]
        holder.testType.text = testResult?.testId.toString()
        holder.result.text = testResult?.resultValue.toString()
        holder.timeSpent.text = testResult?.timeSpent.toString()
    }

    override fun getItemCount() = testResults.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newTestResults: List<GetResultDto?>) {
        testResults = newTestResults
        notifyDataSetChanged()
    }
}