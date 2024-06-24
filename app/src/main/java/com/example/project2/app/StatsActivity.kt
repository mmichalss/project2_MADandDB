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

/**
 * This is the activity for the statistics page. It displays the user's test results.
 */
class StatsActivity : AppCompatActivity() {
    private lateinit var adapterType1: TestResultAdapter
    private lateinit var adapterType2: TestResultAdapter
    private lateinit var adapterType3: TestResultAdapter
    private val dbClient = DbClient()
    private lateinit var tv: TextView

    /**
     * Called when the activity is starting. This is where most initialization should go.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
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

    /**
     * Get the test results for the user.
     * @param userId The user's ID.
     */
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

/**
 * Adapter for the test results.
 * @param testResults The list of test results.
 */
class TestResultAdapter(private var testResults: List<GetResultDto?>) : RecyclerView.Adapter<TestResultAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val testType: TextView = view.findViewById(R.id.test_type)
        val result: TextView = view.findViewById(R.id.result)
        val timeSpent: TextView = view.findViewById(R.id.time_spent)
    }

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent an item.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stats_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val testResult = testResults[position]
        holder.testType.text = testResult?.testId.toString()
        holder.result.text = testResult?.resultValue.toString()
        holder.timeSpent.text = testResult?.timeSpent.toString()
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    override fun getItemCount() = testResults.size

    /**
     * Update the data in the adapter.
     * @param newTestResults The new list of test results.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newTestResults: List<GetResultDto?>) {
        testResults = newTestResults
        notifyDataSetChanged()
    }
}